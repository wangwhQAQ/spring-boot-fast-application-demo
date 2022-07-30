package com.example.demo.Service2.Service.Impl;

import com.example.demo.Aspect.Interface.TestAspect;
import com.example.demo.Service2.Service.TestService2;
import org.springframework.stereotype.Service;

@Service
public class TestServiceI2mpl implements TestService2 {
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
