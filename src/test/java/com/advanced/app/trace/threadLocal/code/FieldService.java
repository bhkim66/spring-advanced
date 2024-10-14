package com.advanced.app.trace.threadLocal.code;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;


@Slf4j
public class FieldService {
    private String nameStore;

    public String logic(String name) {
        try {
            log.info("저장 name ={} -> nameStore={}", name, nameStore);
            nameStore = name;
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("조회 nameStore={}", nameStore);
        return nameStore;
    }
}
