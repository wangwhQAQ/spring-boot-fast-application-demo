package com.example.demo.Service.Impl;

import com.example.demo.Service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public Integer catchExpectionMethod(Integer input) {
        for (int i = 0 ; i < 200 ; i ++){
            input+= 1;
        }
        return input;
    }
}
