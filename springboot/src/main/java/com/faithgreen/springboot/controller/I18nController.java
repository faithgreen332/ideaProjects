package com.faithgreen.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class I18nController {

    @RequestMapping("login")
    public String login(){
        return "login";
    }
}
