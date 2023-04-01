package com.example.demo.Model;

public class User{
    static int i = 0;

    public synchronized void iIncr(){
//        synchronized (User.class){
            i++;
            System.out.println(i);
//        }
    }
}
