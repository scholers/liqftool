package com.lqf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListTest {
		  
	    static final int N = 5000000;   
	  
	    static long timeList(List list){   
	  
	    long start=System.currentTimeMillis();   
	  
	    Object o = new Object();   
	  
	    for(int i=0;i<N;i++)   
	    	//在开头增加
	        //list.add(0, o); 
	    	//在末尾增加
	    	list.add(o);   
	  
	    return System.currentTimeMillis()-start;   
	  
	    }   
	  
	    public static void main(String[] args) {   
	  
	        System.out.println("ArrayList耗时："+timeList(new ArrayList()));   
	  
	        System.out.println("LinkedList耗时："+timeList(new LinkedList()));   
	  
	    }   
	  
}
