package com.example.demo.Thread;

import com.example.demo.Config.ThreadConfig;
import com.example.demo.Config.ThreadSingleConfig;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.*;

@Component("testThreadFactory")
public class TestThreadFactory {

    @Resource
    ThreadConfig threadConfig;

    private ThreadFactory factory;

    private ExecutorService service;

    public ThreadFactory getThreadFactory(){
        return factory;
    }
    public ExecutorService getExecutorService(){
        return service;
    }

//    @Bean(name = "test")
//    public TestThreadFactory testThreadFactory(){
//        return new TestThreadFactory();
//    }

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

//    @Bean("threadPoolFactory")
    public ThreadFactory initThreadPoolFactory(){
        //ExecutorService service = Executors.newFixedThreadPool(3);  不允许用Executors创建线程池

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("testPool-thread-%d").build();
        //创建一个名为“”的线程池

        ExecutorService service = new ThreadPoolExecutor(1,1,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1),
                threadFactory,new ThreadPoolExecutor.CallerRunsPolicy());

        this.factory = threadFactory;
        this.service = service;

        return threadFactory;
    }

}
