package com.advanced;

import com.advanced.app.trace.logtrace.FieldLogTrace;
import com.advanced.app.trace.logtrace.LogTrace;
import com.advanced.app.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
