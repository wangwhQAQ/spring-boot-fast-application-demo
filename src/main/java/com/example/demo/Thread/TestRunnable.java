package com.example.demo.Thread;

public class TestRunnable implements Runnable{
    int time;

    public TestRunnable(int t){
        time = t;
    }

    @Override
    public void run() {
        try {
            System.out.println("正在执行:" + time);
            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
