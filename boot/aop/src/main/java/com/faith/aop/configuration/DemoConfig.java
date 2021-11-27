package com.faith.aop.configuration;

import com.faith.aop.vo.A;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoConfig {
    @Bean
    public A a() {
        return new A();
    }
}
