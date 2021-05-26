package com.faith.java8;

import com.faith.java8.entity.AClass;
import com.faith.java8.entity.AFatherClass;
import com.faith.java8.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SpringBootTest
class Java8ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void supply() {
        Supplier<Person> p1 = Person::new;
        Person person1 = p1.get();
        Person person2 = p1.get();
        person1.setName("faith");
        person2.setName("green");

        System.out.println(person1.getName() + " " + person2.getName());
    }

    @Test
    public void predicate() {
        Stream<Integer> s = Stream.iterate(1, i -> i + 1);
        Predicate<Integer> p1 = i -> i > 1;
        Predicate<Integer> p2 = i -> i < 10;
        Predicate<Integer> p3 = i -> i % 2 == 0;

        s.limit(20).filter(p1.and(p2).and(p3.negate())).forEach(System.out::println);

    }

    @Test
    public void function() {
        Function<Integer, Integer> f1 = i -> i * 2;
        Function<Integer, Integer> f2 = i -> i * 3;
        System.out.println(f1.andThen(f2).apply(2));
    }

    @Test
    public void consumer() {
        Consumer<Integer> c1 = i -> System.out.println(i * 2);
        Consumer<Integer> c2 = i -> System.out.println(i * 3);

        c1.andThen(c2).accept(2);
    }

    @Test
    public void testInstanceOf() {
        AClass a = new AClass();
        AFatherClass aFatherClass = new AFatherClass();
        System.out.println(a instanceof AFatherClass);
        System.out.println(aFatherClass instanceof AClass);
    }

    @Test
    public void testScheduledThreadPoolExecutor(){
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.scheduleWithFixedDelay(()->{
            System.out.println("ssss");
        },0,3, TimeUnit.SECONDS);
//        executor.schedule(()->{
//            System.out.println("aaaa");
//        },3,TimeUnit.SECONDS);
    }

}
