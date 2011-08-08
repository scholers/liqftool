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
	    	//�ڿ�ͷ����
	        //list.add(0, o); 
	    	//��ĩβ����
	    	list.add(o);   
	  
	    return System.currentTimeMillis()-start;   
	  
	    }   
	  
	    public static void main(String[] args) {   
	  
	        System.out.println("ArrayList��ʱ��"+timeList(new ArrayList()));   
	  
	        System.out.println("LinkedList��ʱ��"+timeList(new LinkedList()));   
	  
	    }   
	  
}
