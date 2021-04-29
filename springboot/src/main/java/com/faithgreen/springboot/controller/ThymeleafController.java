package com.faithgreen.springboot.controller;

import com.faithgreen.springboot.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("thymeleaf")
public class ThymeleafController {

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("msg", "faithgreen");
        return "hello";
    }

    @RequestMapping("person")
    public String person(ModelMap map) {
        map.put("thText", "th:text 设置文本内容 <b>加粗</b>");
        map.put("thUText", "th:utext 设置文本内容 <b>加粗</b>");
        map.put("thValue", "thValue 设置当前元素的value值");
        map.put("thEach", "Arrays.asList(\"th:each\",\"遍历列表\")");
        map.put("thIf", "msg is not null");
        map.put("thObject", new Person("faith", 18, "男"));
        return "thymeleaf";

    }
}
