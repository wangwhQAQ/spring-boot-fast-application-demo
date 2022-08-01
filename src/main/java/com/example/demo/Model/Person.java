package com.example.demo.Model;

import com.example.demo.Aspect.Interface.FiledAspect;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

@ToString
@FiledAspect
//@Data
public class Person {
    String name;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    int age;

    public int getAge() {
        return age;
    }

    public Person(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    String other;


}
