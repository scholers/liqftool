package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ���Լ����
 * @author jill
 *
 */
public class WatchThread {

	/**
	 * ���Ժ���
	 * @throws InterruptedException
	 */
	public void testThread() throws InterruptedException {
		int threadNum = 10;
		//��ʼ��countDown
		CountDownLatch threadSignal = new CountDownLatch(threadNum);
		//�����̶����ȵ��̳߳�
		Executor executor = Executors.newFixedThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) { //��threadNum���߳�   
			Runnable task = new TestThread(threadSignal);
			if(threadNum == 9){
				task.wait(10000);
			}
			executor.execute(task);
			if(threadNum == 9){
				task.notify();
			}
			
		}
		threadSignal.await(); //�ȴ��������߳�ִ����   
		//do work
		System.out.println(Thread.currentThread().getName() + "+++++++����.");
	}

	/**  
	 * ���Ժ��� 
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
			System.out.println(Thread.currentThread().getName() + "��ʼ...");
			//do shomething
			long thredCount = threadsSignal.getCount();
			System.out.println("��ʼ���̣߳�������" + thredCount);
			//TestSecrity test = TestSecrity.getInstance();
			
			long testLog = TestSecrity.getInstance().printTest(thredCount);
			System.out.println("(testLog - 100====" + (testLog - 100));
			if((testLog - 100) != thredCount){
				System.out.println("�����̰߳�ȫ����");
			}
			
			//TestSecrity.getInstance().printTest();
			//�߳̽���ʱ��������1
			threadsSignal.countDown();  
			System.out.println(Thread.currentThread().getName() + "����. ����"
					+ threadsSignal.getCount() + " ���߳�");
		}
	}

}