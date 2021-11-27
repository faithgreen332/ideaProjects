package com.faith.boot.starter.bean;

import com.faith.boot.starter.condition.DogCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional({DogCondition.class})
public class Dog {
    public Dog() {
        System.out.println("狗来了，wangwangwang。。。");
    }
}
