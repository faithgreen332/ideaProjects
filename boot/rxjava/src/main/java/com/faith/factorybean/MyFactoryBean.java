package com.faith.factorybean;

import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        System.out.println("getObject()");
        return new TestBean();
    }

    @Override
    public Class<?> getObjectType() {
        System.out.println("getObjectType...");
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
