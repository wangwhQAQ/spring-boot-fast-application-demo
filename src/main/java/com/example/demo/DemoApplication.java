package com.example.demo;

import com.example.demo.Config.ThreadConfig;
import com.example.demo.Thread.TestThreadFactory;
import com.example.demo.Thread.TestRunnable;
import com.example.demo.Utils.TestSpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
public class DemoApplication {
	@Autowired
	ThreadConfig threadConfig;

	@Resource
	TestThreadFactory factory;
	@Resource
	TestSpringUtil springUtil;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public void runFactory(){
//		TestThreadFactory factory = new TestThreadFactory();

		factory.init(threadConfig);
//		factory.runThread(0);
//		factory.runThread(1);
	}

	@Bean
	public void getFactoryBean(){
		TestThreadFactory testThreadFactory = springUtil.getBean("testThreadFactory",TestThreadFactory.class);
		testThreadFactory.initThreadPoolFactory();
		try{
			int times = 100;
			while (times > 0){
				testThreadFactory.getExecutorService().submit(new TestRunnable(--times));
				System.out.println("times:"+times);
			}

		}catch (Exception e){
			System.out.println(e.getCause());
		}
		System.out.println("---------");
	}
}
