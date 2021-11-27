package com.faith.validation;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Set;

@SpringBootApplication
public class ValidationApplication {

    public static void main(String[] args) {

        Person person = new Person();
        person.setAge(-1);
        Set<ConstraintViolation<Person>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(person);
        validate.stream().map(a -> a.getPropertyPath() + " " + a.getMessage() + " " + a.getInvalidValue()).forEach(System.out::println);

//        SpringApplication.run(ValidationApplication.class, args);
    }


    public static class Person {
        @NotEmpty
        private String name;

        @Positive
        @Max(100)
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

}
