# 스레드 로컬 - ThreadLocal

## ThreadLocal

스레드 로컬은 해당 스레드만 접근할 수 있는 특별한 저장소를 의미한다.

**일반적인 변수 필드**

- 여러 스레드가 같은 인스턴스의 필드에 접근하면 처음 스레드가 보관한 데이터가 사라질 수 있다

**스레드 로컬**

- 스레드 로컬을 사용하면 각 스레드마다 별도의 내부 저장소를 제공한다. 따라서 같은 인스턴스의 스레드 로컬 필드에 접근해도 문제가 없다

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/357dd3c4-c19d-4333-a365-d1b07d96cb7d/e75b7b19-1a8a-47ed-a583-baf526bb1809/image.png)

```java
public class ThreadClassMain() {
	private ThreadLocal<String> nameStore = new ThreadLocal<>();
	public void main(String[] args) {
		String name = "test"
		nameStore.set(name) //값 저장
		nameStore.get() // 값 조회
		nameStore.remove() //값 제거
	}
}
```

### ThreadLocal.remove()

스레드 로컬을 모두 사용하고 나면 꼭 `ThreadLocal.remove()`를 호출해서 스레드 로컬에 저장된 값을 제거해주어야 한다

`스레드 로컬`의 값을 사용 후 제거하지 않고 그냥 두면 **WAS(톰캣)처럼 쓰레드 풀을 사용하는 경우**에 심각한 문제가 발생할 수 있다.

- 사용자가 HTTP를 요청하여 WAS가 스레드 풀에서 스레드 하나를 조회한다.
- 그리고 불러온 데이터를 스레드 로컬에 저장한다
- HTTP 응답이 끝났지만 스레드 풀에서 스레드는 제거되지 않고 재사용된다
- 다른 HTTP 요청이 들어왔을 경우 제거 되지 않은 스레드의 스레드 로컬에 있는 데이터 값을 조회하게 되므로 문제가 발생한다

  # 템플릿 메서드 패턴

## 템플릿 메서드 패턴

**변하는 것과 변하지 않는 것을 분리**

좋은 설계는 변하는 것과 변하지 않는 것을 분리하는 것이다. **핵심 기능**과 **부가 기능**은 분리하여 모듈화해야 한다. 이를 해결한 문제를 `템플릿 메서드 패턴`이라 한다

**좋은 설계란?**

- 좋은 설계란 **변경**이 일어날 때 자연스럽게 드러난다
- 반복되는 기능들을 하나의 모듈화하고 비즈니스 로직 부분을 분리한다. 그러면 분리된 모듈 부분만 수정하면 된다

```java
public abstract class AbstractTemplate<T> {
	private final LogTrace trace;
		public AbstractTemplate(LogTrace trace) {
			this.trace = trace;
		}
		public T execute(String message) {
			TraceStatus status = null;
			try {
				status = trace.begin(message);
				//로직 호출
				T result = call();
				trace.end(status);
				return result;
			} catch (Exception e) {
				trace.exception(status, e);
				throw e;
			}
		}
	protected abstract T call();
}
```

- `AbstractTemplate`은 템플릿 메서드 패턴에서 부모 클래스이고, 템플릿 역할을 한다.
- 템플릿 코드 중간에 `call()` 메서드를 통해서 변하는 부분을 처리한다.
- `abstract T call()` 은 변하는 부분을 처리하는 메서드이다. 이 부분은 상속으로 구현해야 한다.

```java
@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {
	private final OrderServiceV4 orderService;
	private final LogTrace trace;
	
	@GetMapping("/v4/request")
	public String request(String itemId) {
		AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
			@Override
			protected String call() {
				orderService.orderItem(itemId);
			return "ok";
		}
	};
	return template.execute("OrderController.request()");
	}
}
```

- 익명 내부 클래스
    - 익명 내부 클래스를 사용한다. 객체를 생성하면서 `AbstractTemplate`를 상속받은 자식 클래스를 정의했다
    - 따라서 **별도의 자식 클래스를 직접 만들지 않아도 된다**

> 템플릿 메서드 디자인 패턴의 목적은 다음과 같다
> 
> 
> “작업에서 알고리즘의 골격을 정의하고 일부 단계를 하위 클래스로 연기한다. 템플릿 메서드를 사용하면 하위 클래스가 알고리즘의 구조를 변경하지 않고도 알고리즘의 특정 단계를 재정의할 수 있다”
> 

풀어서 설명하면 다음과 같다.
부모 클래스에 알고리즘의 골격인 `템플릿`을 정의하고, 일부 변경되는 로직은 자식 클래스에 정의하는 것이다. 이렇게 하면 자식 클래스가 알고리즘의 전체 구조를 변경하지 않고, 특정 부분만 재정의할 수 있다. 결국 **상속과 오버라이딩을 통한 다형성으로 문제를 해결**하는 것이다

**하지만**

템플릿 메서드는 상속을 사용한다. *따라서 상속에서 오는 단점들을 그대로 안고간다.* 

특히 자식 클래스가 부모 클래스와 컴파일 시점에 강하게 결합되는 문제가 있다. 이것은 의존관계에 대한 문제이다. 자식 클래스 입장에서는 부모 클래스의 기능을 전혀 사용하지 않는다.

상속을 받는 다는 것은 특정 부모 클래스를 의존하고 있다는 것이다. 자식 클래스의 `extends` 다음에 바로 부모 클래스가 지정되어 있다. *부모 클래스를 사용하든 안하든 강하게 의존된다*

자식 클래스 입장에서는 부모 클래스의 기능을 전혀 사용하지 않는데, 부모 클래스를 알아야한다. 이것은 좋은 설계가아니다. 그리고 이런 잘못된 의존관계 때문에 부모 클래스를 수정하면, 자식 클래스에도 영향을 줄 수 있다.
