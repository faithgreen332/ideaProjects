package com.faithgreen.dubbo.controller;

import com.faithgreen.dubbo.service.IDemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("main")
public class MainController {

    @Reference(version = "1.0.0")
    IDemoService demoService;

    @RequestMapping("say")
    public String say() {
        return demoService.say("黎锦飞");
    }
}
