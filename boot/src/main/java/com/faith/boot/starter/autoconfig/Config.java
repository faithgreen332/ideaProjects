package com.faith.boot.starter.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "config")
public class Config {
    private String name;
    private Integer age;
}
