package com.example.demo;

import com.example.demo.Aspect.Initialization.FiledAspectInitialization;
import com.example.demo.Config.ThreadConfig;
import com.example.demo.Lambda.Interface.BaseInterface;
import com.example.demo.Lambda.Interface.BaseInterface2;
import com.example.demo.Model.Person;
import com.example.demo.Model.TestField;
import com.example.demo.Model.User;
import com.example.demo.Model.valueTest;
import com.example.demo.Service.TestService;
import com.example.demo.Service2.Service.TestService2;
import com.example.demo.Thread.TestThreadFactory;
import com.example.demo.Thread.TestRunnable;
import com.example.demo.Utils.TestSpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author DELL
 */

@Slf4j
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
//	@Resource
//	TestService2 testService2;

	@Resource
	TestField testField;

	@Resource
	valueTest valueTest1;
	@Resource
	FiledAspectInitialization initialization;

	public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(DemoApplication.class);
//		String[] beanNames = configurableApplicationContext.getBeanDefinitionNames();
//		for (String beanName:
//				beanNames) {
//			System.out.println(beanName);
//		}

	}

//	@Bean
	public void testSemaphore(){
		Semaphore semaphore = new Semaphore(5);
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
				6,
				6,
				1000,
				TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(20),
				new ThreadPoolExecutor.DiscardPolicy()
		);

		threadPoolExecutor.execute(()->{
			try {
				semaphore.acquire(3);
				System.out.println("获取信号量3个");
				Thread.sleep(3000);
			}catch (Exception e){
				e.printStackTrace();
			}
			finally {
				System.out.println("释放信号量3个");
				semaphore.release(3);
			}
		});

		threadPoolExecutor.execute(()->{
			try {
				semaphore.acquire(2);
				System.out.println("获取信号量2个");
				Thread.sleep(2000);
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				System.out.println("释放信号量3个");
				semaphore.release(3);
			}
		});

		threadPoolExecutor.execute(()->{
			try {
				semaphore.acquire(6);
				System.out.println("获取信号量6个");
				Thread.sleep(2000);
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				System.out.println("释放信号量6个");
				semaphore.release(6);
			}
		});
	}

//	@Bean
	public void testCyclicBarrier(){
		CyclicBarrier cyclicBarrier = new CyclicBarrier(6);
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
				6,
				6,
				1000,
				TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(20),
				new ThreadPoolExecutor.DiscardPolicy()
		);

		threadPoolExecutor.execute(()->{
			try {
				Thread.sleep(1000);
				System.out.println("任务1到达栅栏");
				cyclicBarrier.await();
				System.out.println("任务1越过栅栏");
			}catch (Exception e){
				e.printStackTrace();
			}
		});

		threadPoolExecutor.execute(()->{
			try {
				Thread.sleep(2000);
				System.out.println("任务2到达栅栏");
				cyclicBarrier.await();
				System.out.println("任务2越过栅栏");
			}catch (Exception e){
				e.printStackTrace();
			}
		});

		threadPoolExecutor.execute(()->{
			try {
				Thread.sleep(3000);
				System.out.println("任务3到达栅栏");
				cyclicBarrier.await();
				System.out.println("任务3越过栅栏");
			}catch (Exception e){
				e.printStackTrace();
			}
		});

		threadPoolExecutor.execute(()->{
			try {
				Thread.sleep(4000);
				System.out.println("任务4到达栅栏");
				cyclicBarrier.await();
				System.out.println("任务4越过栅栏");
			}catch (Exception e){
				e.printStackTrace();
			}
		});

		threadPoolExecutor.execute(()->{
			try {
				Thread.sleep(5000);
				System.out.println("任务5到达栅栏");
				cyclicBarrier.await();
				System.out.println("任务5越过栅栏");
			}catch (Exception e){
				e.printStackTrace();
			}
		});

		threadPoolExecutor.execute(()->{
			try {
				Thread.sleep(6000);
				System.out.println("任务6到达栅栏");
				cyclicBarrier.await();
				System.out.println("任务6越过栅栏");
			}catch (Exception e){
				e.printStackTrace();
			}
		});

	}

//	@Bean
	public void testCondition(){
		ReentrantLock lock = new ReentrantLock();
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
				3,
				4,
				1000,
				TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(20),
				new ThreadPoolExecutor.DiscardPolicy()
		);


		threadPoolExecutor.execute(()->{
			lock.lock();
			try{
				condition1.await();
				Thread.sleep(1000);
				System.out.println("操作1");
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
		});

		threadPoolExecutor.execute(()->{
			lock.lock();
			try{
				condition2.await();
				Thread.sleep(2000);
				System.out.println("操作2");
				condition1.signal();
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				lock.unlock();
			}


		});
		threadPoolExecutor.execute(()->{
			lock.lock();
			try{
				Thread.sleep(5000);
				System.out.println("操作3");
				condition2.signal();
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				lock.unlock();
			}


		});

	}

//	@Bean
	public void testCountDownLatch() throws InterruptedException, ExecutionException {
		CountDownLatch latch = new CountDownLatch(3);
		ThreadPoolExecutor myThreadPoolExecutor = new ThreadPoolExecutor(
				3,
				6,
				1000,
				TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(100),
				new ThreadPoolExecutor.DiscardPolicy()
		);

		Future<Integer> ans1 = myThreadPoolExecutor.submit(()->{
			Thread.sleep(200);
			System.out.println("查询数据库");
			latch.countDown();
			return 1;
		});
		Future<Integer> ans2 = myThreadPoolExecutor.submit(()->{
			Thread.sleep(50);
			System.out.println("查询redis");
			latch.countDown();
			return 2;
		});
		Future<Integer> ans3 = myThreadPoolExecutor.submit(()->{
			Thread.sleep(5000);
			System.out.println("查询其他服务");
			latch.countDown();
			return 4;
		});

		System.out.println("到达倒计时锁");
		latch.await();
		System.out.println("ans1:" + ans1.get() + ",ans2:" + ans2.get() + ",ans3:" + ans3.get());

	}


//	@Bean
	public void testSynchronized(){
		ThreadPoolExecutor myThreadPoolExecutor = new ThreadPoolExecutor(
				3,
				6,
				1000,
				TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(100),
				new ThreadPoolExecutor.DiscardPolicy()
		);
		for (int i = 0; i < 100; i++) {
			myThreadPoolExecutor.execute(() -> {
				User user = new User();
				user.iIncr();
			});
		}
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

//	@Bean
	public Integer aspectTest() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//		initialization.Initialization();
//		initialization.Reflection();
		System.out.println("????????????");
		System.out.println(testField.toString());

//		testService2.catchExpectionMethod(500);
		return testService.catchExpectionMethod(100);
	}

//	@Bean
	public String[] soutBeanDefinition(){
		System.out.println("????????????2222222222222222");
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(DemoApplication.class);
		String[] beanNames = configurableApplicationContext.getBeanDefinitionNames();
		for (String beanName:
				beanNames) {
			System.out.println(beanName);
		}
//		return beanNames;
		return new String[]{"123"};
	}
}
