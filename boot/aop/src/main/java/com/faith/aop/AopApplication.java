package com.faith.aop;

import com.faith.aop.advises.FaithAroundAdvice;
import com.faith.aop.pointcut.FaithPointcut;
import com.faith.aop.target.FaithService;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class AopApplication {

    public static void main(String[] args) {
        System.out.println(0+'a');
        System.out.println(3+'a');
//        SpringApplication.run(AopApplication.class, args);
//        aopTest();
    }

    static void aopTest() {
        ProxyFactory proxyFactory = new ProxyFactory();

        // 一个 Advisor 代表的是一个已经跟指定切点绑定了的通知
        Advisor advisor = new DefaultPointcutAdvisor(new FaithPointcut(), new FaithAroundAdvice());

        // 添加一个绑定了指定切点的通知
//        proxyFactory.addAdvisor(advisor);
        proxyFactory.addAdvice(new FaithAroundAdvice());

        // 增加一个返回后的通知
//        proxyFactory.addAdvice(new FaithAfterReturnAdvice());
        // 增加一个 before 通知
//        proxyFactory.addAdvice(new FaithBeforeAdvice());
        // 为代理类引入一个虚的需要实现的接口 --- Runnable
//        proxyFactory.addAdvice(new FaithIntroductionAdvice());

        // 设置目标类
        proxyFactory.setTarget(new FaithService());

        // 因为要测试代理自己定义的方法，所以要启用cglib代理
        proxyFactory.setProxyTargetClass(true);

        // 创建代理对象
        Object proxy = proxyFactory.getProxy();

        // 调用代理对象的 toString 方法
//        proxy.toString();


        if (proxy instanceof FaithService) {
            ((FaithService) proxy).testAop();
        }

        // 判断引入是否成功，并执行引入的逻辑
        if (proxy instanceof Runnable) {
            ((Runnable) proxy).run();
        }
    }

}
