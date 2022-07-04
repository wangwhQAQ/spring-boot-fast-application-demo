package com.example.demo.Thread;

import com.example.demo.Config.ThreadConfig;
import com.example.demo.Config.ThreadSingleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

//@Component
public class TestThreadFactory {

    @Resource
    ThreadConfig threadConfig;

    @Bean(name = "test")
    public TestThreadFactory testThreadFactory(){
        return new TestThreadFactory();
    }

    private TestThread[] threadPool;

    public void init(ThreadConfig threadConfig){
        threadPool = new TestThread[threadConfig.getThreadConfigs().size()];
        int i = 0;
        for (Map.Entry<String, ThreadSingleConfig> entry : threadConfig.getThreadConfigs().entrySet()){
            TestThread testThread = new TestThread();
            testThread.setMessage(entry.getValue().getMessage());
            testThread.setName(entry.getValue().getName());
            threadPool[i] = testThread;
            i++;
        }
    }

    public void runThread(int i){
        threadPool[i].start();
    }
}
