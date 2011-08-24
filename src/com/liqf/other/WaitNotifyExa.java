package com.liqf.other;

/**
 * 
 * @author jill
 *
 */
public class WaitNotifyExa {
    public WaitNotifyExa(){};
	public void waitNotifyExa() throws InterruptedException {
		final Object object = new Object();
		Thread threadA = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (object) {
						object.wait();
					}
					System.out.println("threadA");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread threadB = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (object) {
						object.wait();
					}
					System.out.println("threadB");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		};
		System.out.println("waitNotifyExa-X");
		threadA.start();
		threadB.start();
		Thread.sleep(2000);// 保证通知动作在线程启动之后发出
		synchronized (object) {
			object.notifyAll();
		}
		System.out.println("waitNotifyExa-Y");
	}

	public static void main(String args[]) {
		try{
			WaitNotifyExa temp = new WaitNotifyExa();
			temp.waitNotifyExa();
		}catch(InterruptedException ex){
			ex.fillInStackTrace();
		}
	}
}
