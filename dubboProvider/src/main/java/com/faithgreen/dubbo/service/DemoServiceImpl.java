package com.faithgreen.dubbo.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Service(version = "1.0.0", timeout = 10000, interfaceClass = IDemoService.class)
@Component
public class DemoServiceImpl implements IDemoService {

    @Override
    public String say(String name) {
        System.out.println("hi " + name);
        return "hi " + name;
    }
}
