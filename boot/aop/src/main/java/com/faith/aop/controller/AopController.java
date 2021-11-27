package com.faith.aop.controller;

import com.faith.aop.service.AopService;
import com.faith.aop.vo.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopController {

    AopService aopService;

    @Autowired
    public void setAopService(AopService aopService) {
        this.aopService = aopService;
    }

    @Autowired
    A a;

    @GetMapping("/aop/{name}")
    public String aopTest(@PathVariable("name") String str) {
        a.aopATest();
        return aopService.aopService(str);
    }
}
