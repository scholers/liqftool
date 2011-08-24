package com.cache;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.liqf.other.Paire;

public class MainTest {
	public static void main(String args[]){   
		test1();
		//test2();
    }      
	
	
	private static void test1(){
		 String DEFAULT_CONTEXT_FILE = "/applicationContext.xml";   
	        ApplicationContext context =  new ClassPathXmlApplicationContext(DEFAULT_CONTEXT_FILE); 
	        //SeaBean seaBean = new SeaBean();
	        
	        SeaBean seaBean = new SeaBean();
	        SeaBean seaBean2 = (SeaBean)context.getBean("pair");   
	        TestServiceImpl testService = (TestServiceImpl)context.getBean("testService");   
	        
	        seaBean.setName("LIQF");
	        //System.out.println("testService.getPaire().getName()===" + testService.getPaire().getName());   
	        //TestServiceImpl testService = new TestServiceImpl();  
	  
	        System.out.println("1--第一次查找并创建cache");   
	        
	        List<SeaBean> restList1 = testService.getAllObject(seaBean); 
	        for(SeaBean seaBeanTem : restList1){
	        	System.out.println("strTem1===" + seaBeanTem.getName()); 
	        }
	        //paire = new com.liqf.other.Paire();
	        //SeaBean seaBean2 = (SeaBean)context.getBean("pair");  
	        seaBean2.setName("LIQF77");
	        System.out.println("2--在cache中查找");
	        System.out.println(seaBean2.getName());
	        List<SeaBean> restList = testService.getAllObject(seaBean2);   
	        for(SeaBean seaBeanTem : restList){
	        	System.out.println("strTem2===" +  seaBeanTem.getName()); 
	        }
	           
	        System.out.println("3--remove cache");   
	        testService.updateObject(null);   
	           
	        //System.out.println("4--需要重新查找并创建cache");   
	        List<SeaBean> restList2 = testService.getAllObject(seaBean);   
	        for(SeaBean seaBeanTem : restList2){
	        	System.out.println("strTem3===" +  seaBeanTem.getName()); 
	        }
	}
	
	
	private static void test2(){
		  String DEFAULT_CONTEXT_FILE = "/applicationContext.xml";   
		  ApplicationContext context =  new ClassPathXmlApplicationContext(DEFAULT_CONTEXT_FILE);   
	        TestService testService = (TestService)context.getBean("testService2");   
	  
	        System.out.println("1--第一次查找并创建cache");   
	        testService.getAllObject();   
	           
	        System.out.println("2--在cache中查找");   
	        testService.getAllObject();   
	           
	        System.out.println("3--remove cache");   
	        testService.updateObject(null);   
	           
	        System.out.println("4--需要重新查找并创建cache");   
	        testService.getAllObject();   
	}
	
	
	
}  

