package com.example.demo.Config;

import com.example.demo.Model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Configuration
//@Component
public class MyConfig {
    @Bean("myPerson1")
    public Person initPerson1(){
        Person p = new Person("beanPerson",2333);
        return p;
    }

    @Bean("myPerson2")
    @Primary
    public Person initPerson2(){
        Person p = new Person("beanPerson",2333);
        return p;
    }

    @Bean("myPerson3")
    public Person initPerson3(){
        Person p = new Person("beanPerson",2333);
        return p;
    }
}
