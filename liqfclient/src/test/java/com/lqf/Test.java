package com.lqf;

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
	  
	  
	    public static void main(String[] args) throws InterruptedException {  
	        Test t = new Test();  
	        t.doSomething(new BigObj("BigObj1"));  
	        System.out.println("===== 1");  
	        System.gc();  
	        Object obj = t.doSomething(new BigObj("BigObj2")); // keep reference    
	        System.out.println("===== 2");  
	        System.gc();  
	        Thread.sleep(1000);  
	  
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
