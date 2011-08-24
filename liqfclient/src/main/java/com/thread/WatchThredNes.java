package com.thread;

import java.util.concurrent.CountDownLatch;

public class WatchThredNes {

	/**  
	 * ²âÊÔº¯Êý 
	 */
	public static void main(String[] args) throws InterruptedException {
		WatchThredNes test = new WatchThredNes();
		test.execute();
	}

	public void execute() {
		final int workerCount = 10;
		CountDownLatch lunchSignal = new CountDownLatch(workerCount);
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(workerCount);

		System.out.println("driver: begin to lunch worker thread");
		for (int i = 0; i < workerCount; ++i) {
			Thread t = new Thread(new Worker(lunchSignal, startSignal,
					doneSignal));
			t.setName("worker" + (i + 1));
			t.start();
		}

		try {
			lunchSignal.await();
			System.out.println("driver: all worker lunched");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		System.out
				.println("driver: send signal to all worker to start work");
		startSignal.countDown();

		try {
			System.out.println("driver: wait for all worker");
			doneSignal.await();
			System.out.println("driver: all worker finished");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	private class Worker implements Runnable {
		private final CountDownLatch lunchSignal;
		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;

		Worker(CountDownLatch lunchSignal, CountDownLatch startSignal,
				CountDownLatch doneSignal) {
			this.lunchSignal = lunchSignal;
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
		}

		public void run() {
			String name = Thread.currentThread().getName();
			try {
				System.out.println(name + " lunched and wait for start signal");
				lunchSignal.countDown();
				lunchSignal.await();

				startSignal.await();
				System.out.println(name + " start to do work");

				doWork();
				System.out.println(name
						+ " finish work and wait for other worker");

				doneSignal.countDown();
				doneSignal.await();
				System.out.println(name + " quit");
			} catch (InterruptedException ex) {
			}
		}

		private void doWork() {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
