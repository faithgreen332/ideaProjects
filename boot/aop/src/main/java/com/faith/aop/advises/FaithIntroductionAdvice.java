package com.faith.aop.advises;

import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class FaithIntroductionAdvice extends DelegatingIntroductionInterceptor implements Runnable {
    @Override
    public void run() {
        System.out.println("running ...");
    }
}
