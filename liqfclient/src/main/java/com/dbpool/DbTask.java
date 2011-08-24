package com.dbpool;

import java.util.concurrent.TimeUnit;

public class DbTask implements Runnable { 
    private int x;            //线程编号 
	 
    public DbTask(int x) { 
            this.x = x; 
    } 

    public void run() { 
            System.out.println(x + " thread doing something!"); 
            TestDbPool test = new TestDbPool();
            test.insertConPol(x);
            try { 
            		//insert();
                    TimeUnit.SECONDS.sleep(5L); 
                    System.out.println("第" + x + "个线程休息完毕"); 
            } catch (InterruptedException e) { 
                    e.printStackTrace(); 
            } 
    } 
} 