package com.advanced.app.trace.FieldLogTraceTest;

import com.advanced.app.trace.TraceStatus;
import com.advanced.app.trace.logtrace.FieldLogTrace;
import org.junit.jupiter.api.Test;

public class FieldLogTraceTest {
    FieldLogTrace trace = new FieldLogTrace();

    @Test
    void begin_end_level2() {
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");

        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exception_level2() {
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");

        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }
}
