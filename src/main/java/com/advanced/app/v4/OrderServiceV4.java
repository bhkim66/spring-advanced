package com.advanced.app.v4;

import com.advanced.app.trace.TraceStatus;
import com.advanced.app.trace.logtrace.LogTrace;
import com.advanced.app.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {
    private final OrderRepositoryV4 orderRepositoryV1;
    private final LogTrace trace;

    public void orderItem(String itemId) {

        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepositoryV1.save(itemId);
                return null;
            }
        };
        template.execute("OrderService.orderItem()");

    }
}
