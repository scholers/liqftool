package com.lqf;

/**
 * 
*
* @�����ƣ�Test
* @�����������Բ鿴 JDK1.4 ,JDK1.6�����̶߳�����յ�����
* @�����ˣ���ȱ
* @�޸��ˣ���ȱ
* @�޸�ʱ�䣺2011-2-16 ����09:15:41
* @�޸ı�ע��
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
