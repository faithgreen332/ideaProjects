package com.faith.aop.advises;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class FaithAfterReturnAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("after invoke method ["+method.getName()+"],aop afterReturning logic invoked");
    }
}
