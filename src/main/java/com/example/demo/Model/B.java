package com.example.demo.Model;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class B {
    @Resource
    A a;
}
