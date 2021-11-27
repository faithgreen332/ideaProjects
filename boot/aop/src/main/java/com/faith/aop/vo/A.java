package com.faith.aop.vo;

import org.springframework.beans.factory.annotation.Autowired;

public class A {

    @Autowired
    private B b;

    public String aopATest() {
        System.out.println("bean A aopATest");
        return "aaa";
    }
}
