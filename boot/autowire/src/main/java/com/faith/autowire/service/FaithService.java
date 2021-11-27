package com.faith.autowire.service;

import org.springframework.stereotype.Component;

public class FaithService {

    TestService testService;

    public void setTestService(TestService testService) {
        System.out.println("注入 testService");
        this.testService = testService;
    }
}
