package com.faith.autowire;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class AutowireApplication {

    public static void main(String[] args) {
//        SpringApplication.run(AutowireApplication.class, args);
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.refresh();
        annotationConfigApplicationContext.getBean("faithService");
    }

}
