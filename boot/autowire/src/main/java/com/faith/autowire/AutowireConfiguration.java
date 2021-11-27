package com.faith.autowire;

import com.faith.autowire.service.FaithService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutowireConfiguration {

    @Bean(autowireCandidate = true)
    public FaithService faithService() {
        return new FaithService();
    }
}
