package com.faith.aop.advises;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class FaithAroundAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("aroundAdvice invoked");
        methodInvocation.proceed();
        System.out.println("aroundAdvice after invoked!!");
        return null;
    }
}
