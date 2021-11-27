package com.faith.aop.advises;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class FaithBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("before invoke method [" + method.getName() + "],aop before logic invoked");
    }
}
