package com.example.demo.Model;

import com.example.demo.Aspect.Interface.FiledAspect;
import lombok.ToString;
import org.springframework.stereotype.Component;

@ToString
@Component
public class TestField {
    Person person;

    String addr;
}
