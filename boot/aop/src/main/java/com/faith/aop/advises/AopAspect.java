package com.faith.aop.advises;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopAspect {

    @Pointcut("execution(public java.lang.String com.faith.aop.vo.*.aop*(..))")
    private void executionPointcut() {

    }

    @Before("executionPointcut()")
    public void executionBefore() {
        System.out.println("aopService before advice");
    }

    @After("executionPointcut()")
    public void executionAfter() {
        System.out.println("aopService before after");
    }

    @Around("executionPointcut()")
    public Object executionAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around advice begin");
        System.out.println("args:" + pjp.getArgs().toString());
        System.out.println("this: " + pjp.getThis().toString());
        System.out.println("target:" + pjp.getTarget().toString());
        pjp.proceed();
        System.out.println("around advice after");
        return null;
    }
}
