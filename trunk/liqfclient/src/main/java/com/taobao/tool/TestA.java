package com.taobao.tool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TestA {
	private   int   data=0; 
	private String name = "test TQL";
	
	public static void main(String[] args) {
		TestA test = new TestA();
		test.testReadStr2("c:\\antx.properties");
	}
	
	public void test2() {
		String text = " onclick=\"atpanelClick('1-2', '1604175028', '', '', '', '');\" target=\"_blank\" href=\"${}/item.htm?id=1604175028&spm=.6\"";
		String searchString = "${}/item.htm?id=1604175028&spm=.6";
		 int start = 0;
	        int end = text.indexOf(searchString, start);
	        System.out.println(end);
	        if (end == -1) {
	            //return text;
	        }
	}
	
	
	public void testReadStr(String fileName) {
		BufferedReader br = null;
		try {
			br = new   BufferedReader(new   InputStreamReader(new   FileInputStream(fileName), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String   instring;     
		  
        try {
			while   (   (instring   =   br.readLine())   !=   null)   {     
			    if(instring   !=  null )     
			    {     
			          System.out.println(instring);     
			    }     
			     
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	}
	
	
	public void testReadStr2(String fileName) {
		BufferedReader br = null;
		try {
			br = new   BufferedReader(new   InputStreamReader(new   FileInputStream(fileName), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String   instring;     
		  
        try {
        	for(String   line   =   br.readLine();   line   !=   null;   line   =   br.readLine())   {    
        	    System.out.println(line);    
        	}  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	}
}
