package com.liqf.other;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	
	public String changeSql(String str){
		str = "Test2";
		System.out.println(str);
		return str;
	}
	
	public void testArr(){
		TestBean temp = new TestBean();
		List tempList = new ArrayList();
		for( int i  = 0; i < 1000; i ++){
			temp = new TestBean();
			temp.setName(String.valueOf(i));
			tempList.add(temp);
		}
		
		for( int i  = 0; i < tempList.size(); i ++){
			System.out.println("(TestBean)tempList.get(i)===" + ((TestBean)tempList.get(i)).getName());
		}
	}
	
	
	public void testStr(){
		String temp = new String("");
		List tempList = new ArrayList();
		for( int i  = 0; i < 1000; i ++){
			temp = (String.valueOf(i));
			tempList.add(temp);
		}
		
		for( int i  = 0; i < tempList.size(); i ++){
			System.out.println("(TestBean)tempList.get(i)===" + ((String)tempList.get(i)));
		}
	}
	
	public void testListBean(){
		//TestBean temp = null;
		List<TestBean> tempList = new ArrayList<TestBean>();
		int i = 100;
		for(TestBean temp : getListBean()){
//			/temp = (TestBean)getListBean().get(i);
			temp.setName("i" + i);
			tempList.add(temp);
			i ++;
		}
		
		for( TestBean tBean : tempList){
			
			System.out.println("(TestBean)tempList.get(i)===" + tBean.getName());
		}
	}
	
	public List<TestBean> getListBean(){
		TestBean temp = null;
		List<TestBean> tempList = new ArrayList<TestBean>();
		for( int i  = 0; i < 1000; i ++){
			temp = new TestBean();
			temp.setName(String.valueOf(i));
			tempList.add(temp);
		}
		return tempList;
	}
	
	public void testArr2(){
		Integer temp = null;
		List tempList = new ArrayList();
		for( int i  = 0; i < 1000; i ++){
			temp = new Integer(i);
			//temp.setName(String.valueOf(i));
			tempList.add(temp);
		}
		
		for( int i  = 0; i < tempList.size(); i ++){
			System.out.println("(Integer)tempList.get(i)===" + ((Integer)tempList.get(i)));
		}
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String testStr = "2222";
		Test t = new Test();
		//t.changeSql(testStr);
		//t.testArr();
		//t.testStr();
		//System.out.println(testStr);
		t.testListBean();
	}

}
