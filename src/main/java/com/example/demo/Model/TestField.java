package com.example.demo.Model;

import com.example.demo.Aspect.Interface.FiledAspect;
import com.example.demo.Aspect.Interface.TrueFieldAspect;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@ToString
@Service
public class TestField {
    @Autowired
    Person person;

    @TrueFieldAspect(nickname = "nickname2")
    String addr ;
}
