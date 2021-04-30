package com.faithgreen.multisourcetype;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(1)
@Component
public class DataSourceAspect {

    @Pointcut("@annotation(com.faithgreen.multisourcetype.DataSource)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        DataSource annotation = method.getAnnotation(DataSource.class);
        if (annotation != null) {
            DynamicDataSourceContextHolder.setDataSourceType(annotation.value().name());
        }
        try {
            return point.proceed();
        }  finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }
}
