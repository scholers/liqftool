package com.lqf;

import java.util.Date;

/**
 * 
*
* @类名称：Test
* @类描述：测试查看 JDK1.4 ,JDK1.6关于线程对象回收的问题
* @创建人：卫缺
* @修改人：卫缺
* @修改时间：2011-2-16 上午09:15:41
* @修改备注：
* @version 1.0.0
*
 */
public class Test {  
		  
	    public Object doSomething(final BigObj obj) {  
	  
	        Thread t = new Thread() {  
	            Object o = obj;  
	        };  
	        //      t.start();  
	        return obj;  
	    } 
	    
	    public boolean compreTime() {  
	    	boolean isTrue = false;
	  	  Date date = new Date();
	  	long now = System.currentTimeMillis();
	  	  long lCahceTime = 1;
	  	long tiem = date.getTime();
	  	
	  	long setatTime = 1323235200027L;
	  	long h = tiem- setatTime;
	  	System.out.println(h / (60 * 60 * 1000));
	  	long lCaTime = lCahceTime * 60 * 60 * 1000;
	  	System.out.println(setatTime);
	  	System.out.println(lCaTime);
	  	System.out.println(setatTime +  lCaTime);
	  	System.out.println(tiem);
	  	System.out.println(now);
	  	if (tiem - setatTime>= lCaTime) {
	  		isTrue = true;
	  	}
	  	return isTrue;
	    } 
	  
	  
	    public static void main(String[] args) throws InterruptedException {  
	        Test t = new Test();  
	      System.out.println( t.compreTime());
	  
	    }  
	  
	}  
	  
	class BigObj {  
	    public BigObj(String name) {  
	        this.name = name;  
	    }  
	  
	    String name;  
	    final byte[] obj = new byte[10 * 1024 * 1024];  
	  
	    protected void finalize() throws Throwable {  
	        System.out.println(name + "  finalized");  
	    }  
	}  
