package com.advanced.app.v5.v4;

import com.advanced.app.trace.callBack.TraceTemplate;
import com.advanced.app.trace.logtrace.LogTrace;
import com.advanced.app.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {
    private final OrderRepositoryV5 orderRepositoryV1;
    private final TraceTemplate template;

    public OrderServiceV5(OrderRepositoryV5 orderRepositoryV1, LogTrace trace) {
        this.orderRepositoryV1 = orderRepositoryV1;
        this.template = new TraceTemplate(trace);
    }

    public void orderItem(String itemId) {
        template.execute("OrderService.orderItem()", () -> {
            orderRepositoryV1.save(itemId);
            return null;
        });
    }
}
