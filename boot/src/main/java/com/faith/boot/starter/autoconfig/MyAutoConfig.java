package com.faith.boot.starter.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Config.class)
@ConditionalOnClass(MyService.class)
public class MyAutoConfig {

    @Bean
    @ConditionalOnMissingBean(MyService.class)
    public MyService getMyService() {
        return new MyService();
    }
}
