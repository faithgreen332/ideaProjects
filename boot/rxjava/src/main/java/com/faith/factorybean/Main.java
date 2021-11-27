package com.faith.factorybean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyFactoryBean.class);
        System.out.println(annotationConfigApplicationContext.getBean("myFactoryBean"));
        System.out.println(annotationConfigApplicationContext.getBean("&myFactoryBean"));
    }
}
