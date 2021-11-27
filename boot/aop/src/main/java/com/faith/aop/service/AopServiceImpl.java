package com.faith.aop.service;

import org.springframework.stereotype.Service;

@Service
public class AopServiceImpl implements AopService {


    @Override
    public String aopService(String str) {
        return "hello " + str;
    }
}
