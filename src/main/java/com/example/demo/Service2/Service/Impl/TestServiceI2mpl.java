package com.example.demo.Service2.Service.Impl;

import com.example.demo.Aspect.Interface.TestAspect;
import com.example.demo.DemoApplication;
import com.example.demo.Service2.Service.TestService2;
import com.example.demo.Service2.Service.TestService3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class TestServiceI2mpl implements TestService2 {
    @Autowired
    TestService3 testService3;

    @Override
    @TestAspect
    public Integer catchExpectionMethod(Integer input) {
        System.out.println("这是服务2");
        for (int i = 0 ; i < 200 ; i ++){
            input+= 1;
        }
        return input;
    }
}
