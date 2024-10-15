package com.advanced.app.trace.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV3 {

    public void execute(Strategy2 strategy) {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        strategy.call(); //위임
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

}
