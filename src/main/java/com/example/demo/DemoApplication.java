package com.example.demo;

import com.example.demo.Config.ThreadConfig;
import com.example.demo.Thread.TestThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

@SpringBootApplication
public class DemoApplication {
	@Autowired
	ThreadConfig threadConfig;

	@Resource
	TestThreadFactory factory;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public void runFactory(){
//		TestThreadFactory factory = new TestThreadFactory();

		factory.init(threadConfig);
		factory.runThread(0);
		factory.runThread(1);
	}
}
