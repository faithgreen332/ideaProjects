package com.faith.aop.pointcut;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class FaithPointcut implements Pointcut {
    @Override
    public ClassFilter getClassFilter() {

        new ArrayList<>();
        // 在类上不进行拦截
        return ClassFilter.TRUE;
    }

    @Override
    @Bean(autowireCandidate = true)
    public MethodMatcher getMethodMatcher() {
        return new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> aClass) {
                // toString 方法不进行拦截
                return !method.getName().equals("toString");
            }
        };
    }
}
