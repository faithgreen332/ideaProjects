package com.faithgreen.multisourcetype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MultiSourceTypeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiSourceTypeApplication.class, args);
    }

}
