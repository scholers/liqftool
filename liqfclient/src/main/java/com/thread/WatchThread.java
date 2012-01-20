package com.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试监控类
 * @author jill
 *
 */
public class WatchThread {

	/**
	 * 测试函数
	 * @throws InterruptedException
	 */
	public void testThread() throws InterruptedException {
		int threadNum = 1000;
		//初始化countDown
		CountDownLatch threadSignal = new CountDownLatch(threadNum);
		//创建固定长度的线程池
		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) { //开threadNum个线程   
			Runnable task = new TestThread(threadSignal);
			if(threadNum == 999){
				task.wait(10000);
			}
			executor.execute(task);
			if(threadNum == 999){
				task.notify();
			}
			
		}
		threadSignal.await(); //等待所有子线程执行完   
		//do work
		System.out.println(Thread.currentThread().getName() + "+++++++结束.");
		//finish thread
		executor.shutdown();
	}

	/**  
	 * 测试函数 
	 */
	public static void main(String[] args) throws InterruptedException {
		WatchThread test = new WatchThread();
		test.testThread();
	}

	/**
	 * 
	 * @author jill
	 *
	 */
	private class TestThread implements Runnable {
		private CountDownLatch threadsSignal;

		public TestThread(CountDownLatch threadsSignal) {
			this.threadsSignal = threadsSignal;
		}

		public void run() {
			System.out.println(Thread.currentThread().getName() + "开始...");
			//do shomething
			long thredCount = threadsSignal.getCount();
			System.out.println("开始了线程：：：：" + thredCount);
			//TestSecrity test = TestSecrity.getInstance();
			
			long testLog = TestSecrity.getInstance().printTest(thredCount);
			System.out.println("(testLog - 100====" + (testLog - 100));
			if((testLog - 100) != thredCount){
				System.out.println("不是线程安全！！");
			}
			
			//TestSecrity.getInstance().printTest();
			//线程结束时计数器减1
			threadsSignal.countDown();  
			System.out.println(Thread.currentThread().getName() + "结束. 还有"
					+ threadsSignal.getCount() + " 个线程");
		}
	}

}
