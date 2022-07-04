package com.example.demo.Thread;

import lombok.Data;

@Data
public class TestThread extends Thread {
    String message;

    @Override
    public void run(){
        for (int i = 0 ; i < 100 ; i ++){
            System.out.println(message + ":" + i);
        }

    }
}
