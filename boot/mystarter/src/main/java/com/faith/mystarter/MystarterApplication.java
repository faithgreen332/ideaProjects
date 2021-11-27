package com.faith.mystarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class MystarterApplication {

//    @Autowired
//    MyService myService;

    public static void main(String[] args) {
        SpringApplication.run(MystarterApplication.class, args);
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MystarterApplication.class);
        ac.start();
//        ac.stop();
    }

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
////        myService.say();
//    }

    @Component
    public class MyLifeCycle implements Lifecycle {

        boolean isRunning;

        @Override
        public void start() {
            isRunning = true;
            System.out.println("start");
        }

        @Override
        public void stop() {
            isRunning = false;
            System.out.println("stop");
        }

        @Override
        public boolean isRunning() {
            return isRunning;
        }
    }
}


