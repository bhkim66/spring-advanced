package com.advanced.app.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {
    @Test
    void strategyV1() {
        ContextV2 contextV2 = new ContextV2();
        contextV2.execute(new StrategyLogic1());
        contextV2.execute(new StrategyLogic2());
    }

    @Test
    void strategyV2() {
        ContextV2 contextV2 = new ContextV2();
        contextV2.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직1 실행");
            }
        });
    }

    @Test
    void strategyV3() {
        ContextV2 contextV2 = new ContextV2();
        contextV2.execute(() -> log.info("비지니스 로직1 실행"));
    }

    @Test
    void strategyV4() {
        ContextV3 contextV3 = new ContextV3();
        contextV3.execute(new Strategy2() {
            @Override
            public void test() {

            }

            @Override
            public void call() {
                log.info("test!!");
            }

            @Override
            public void call2() {
                log.info("test22");
            }
        });
    }
}
