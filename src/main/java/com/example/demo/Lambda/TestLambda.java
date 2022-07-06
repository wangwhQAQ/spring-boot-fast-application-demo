package com.example.demo.Lambda;

import com.example.demo.Lambda.Interface.BaseInterface;

public class TestLambda implements BaseInterface {
    @Override
    public void doSomeThing() throws InterruptedException {
        System.out.println("do some thing");
//        Thread.sleep(1000);
    }
}
