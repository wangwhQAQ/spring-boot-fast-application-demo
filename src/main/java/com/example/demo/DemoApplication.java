package com.example.demo;

import com.example.demo.Aspect.Initialization.FiledAspectInitialization;
import com.example.demo.Config.ThreadConfig;
import com.example.demo.Lambda.Interface.BaseInterface;
import com.example.demo.Lambda.Interface.BaseInterface2;
import com.example.demo.Model.Person;
import com.example.demo.Model.TestField;
import com.example.demo.Model.valueTest;
import com.example.demo.Service.TestService;
import com.example.demo.Service2.Service.TestService2;
import com.example.demo.Thread.TestThreadFactory;
import com.example.demo.Thread.TestRunnable;
import com.example.demo.Utils.TestSpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@SpringBootApplication
public class DemoApplication {
	@Autowired
	ThreadConfig threadConfig;

	@Resource
	TestThreadFactory factory;
	@Resource
	TestSpringUtil springUtil;
	@Resource
	TestService testService;
	@Resource
	TestService2 testService2;

	@Resource
	TestField testField;

	@Resource
	valueTest valueTest1;
	@Resource
	FiledAspectInitialization initialization;

	public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		SpringApplication.run(DemoApplication.class, args);

	}

//	@Bean
	public void runFactory(){
//		TestThreadFactory factory = new TestThreadFactory();

		factory.init(threadConfig);
		factory.runThread(0);
		factory.runThread(1);
	}

//	@Bean
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

//	@Bean
	public void testLambda() throws InterruptedException {
		BaseInterface lambda;
		lambda = ()->{
			System.out.println("testLambda");
		};
		lambda.doSomeThing();

//		Integer list = 1;
//		List<Integer> list = new ArrayList<Integer>(5);
		BaseInterface2 lambda2 = thing -> {
			System.out.println(thing.toString());
			return thing;
		};
		Object thing = lambda2.doSomeThing(new ArrayList<Integer>(3));
	}

	//@Bean
	public void valueSout(){
		System.out.println(valueTest1.getValue());
	}

	@Bean
	public Integer aspectTest() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//		initialization.Initialization();
//		initialization.Reflection();

		System.out.println(testField.toString());

		testService2.catchExpectionMethod(500);
		return testService.catchExpectionMethod(100);
	}
}
