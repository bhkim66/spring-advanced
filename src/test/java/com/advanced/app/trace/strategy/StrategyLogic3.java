package com.advanced.app.trace.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrategyLogic3 implements Strategy2 {
    @Override
    public void call() {
        log.info("비지니스 로직2 실행");
    }

    @Override
    public void call2() {
        log.info("비지니스 로직3 실행");
    }

    @Override
    public void test() {

    }
}
