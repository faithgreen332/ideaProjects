package com.faith.impl;

import com.faith.MyService;

public class MyServiceImpl implements MyService {
    @Override
    public void test1() {
        System.out.println("test1...");
    }

    @Override
    public void test2(String s) {
        System.out.println("test2 ... args : " + s);
    }
}
