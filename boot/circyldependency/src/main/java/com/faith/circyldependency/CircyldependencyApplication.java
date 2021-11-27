package com.faith.circyldependency;

import com.faith.circyldependency.vo.A;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CircyldependencyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CircyldependencyApplication.class, args);
        run.getBean(A.class);
    }

}
