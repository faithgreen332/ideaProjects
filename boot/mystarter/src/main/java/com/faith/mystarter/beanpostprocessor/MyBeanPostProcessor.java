package com.faith.mystarter.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("indexService"))
            System.out.println(bean);
        System.out.println("bean config invoke postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("indexService")) {
            System.out.println(bean);
            System.out.println("bean config postProcessAfterInitialization");
        }
        return bean;
    }
}
