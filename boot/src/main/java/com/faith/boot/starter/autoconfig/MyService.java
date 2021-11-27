package com.faith.boot.starter.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;

public class MyService {

    @Autowired
    private Config config;

    public void say() {
        System.out.println("自定义的starter来了，say：" + config.getName());
    }
}
