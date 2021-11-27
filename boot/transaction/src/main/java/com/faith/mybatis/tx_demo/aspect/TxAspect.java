package com.faith.mybatis.tx_demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.faith.mybatis.tx_demo.util.TransactionUtil;

@Aspect
@Component
public class TxAspect {

    @Pointcut("execution(public * com.faith.mybatis.tx_demo..*.*(..))")
    private void pointcut() {
    }

    @Around("pointcut()")
    public Object around(JoinPoint joinPoint) throws Throwable {

        // 再方法开始之前开启事务
        TransactionUtil.startTransaction();

        // 执行业务逻辑
        Object proceed = null;
        try {
            ProceedingJoinPoint method = (ProceedingJoinPoint) joinPoint;
            proceed = method.proceed();
        } catch (Throwable throwable) {
            TransactionUtil.rollback();
            return null;
        }
        TransactionUtil.commit();
        return proceed;
    }
}
