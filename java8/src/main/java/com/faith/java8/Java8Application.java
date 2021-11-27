package com.faith.java8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//@SpringBootApplication
public class Java8Application {

    public static void main(String[] args) {
//        SpringApplication.run(Java8Application.class, args);
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.scheduleWithFixedDelay(()->{
            System.out.println("ssss");
        },1,3, TimeUnit.SECONDS);
    }

}
