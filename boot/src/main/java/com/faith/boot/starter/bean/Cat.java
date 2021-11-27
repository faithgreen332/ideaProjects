package com.faith.boot.starter.bean;

import com.faith.boot.starter.condition.CatCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional({CatCondition.class})
public class Cat {

    public Cat() {
        System.out.println("猫来了，miaomiaomiao。。。");
    }
}
