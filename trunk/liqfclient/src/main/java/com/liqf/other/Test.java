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
	
	public float getOption(){
		float total = 5000.00f;
		float optionValue = 6.50f;
		float curValue = 13.50f;
		float slary = 16000.00f;
		float huilv = 6.3f;
		//总收入
		float getOptionValue = (curValue - optionValue) * total * huilv + slary;
		//行权成本
		float allSend = optionValue * total * huilv + computeTax(getOptionValue);
		return allSend;
	}
	
	public float computeTax(float income)    //income为工资
    {
        float f_income=income-3500;    //起征基数为3500，同时也可以参数化处理
        float[] tax_line={0f,1500f,4500f,9000f,35000f,55000f,80000f};//计算个人所得税的档次（假设员工为中国国籍，因为国籍不同起征点数不同，税率不同。）
        float[] tax_rate={0.03f,0.10f,0.20f,0.25f,0.30f,0.35f,0.40f};      //各档次税率
        float[] tax_sub={0.f,105f,555f,1005f,2755f,5505f,13505f};    //采用了简化算法
        int i_tax_level=0;    //为了计算简便，把工资进行分档
        while(i_tax_level<tax_line.length &&  f_income>tax_line[i_tax_level] )
        {
            i_tax_level ++;
        };
       
        if(i_tax_level>0)
        {
        	 if(i_tax_level >= tax_line.length) {
        		 i_tax_level = i_tax_level - 1;
        	 }
            float f_tax=tax_sub[i_tax_level-1]+(f_income-tax_line[i_tax_level-1])*tax_rate[i_tax_level];    //算税的公式
            return f_tax;
        }
        else
        {
            return 0f;
        }
    }
	
	public void testMod(int size, int dftNum) {
		int num = size/dftNum;
		int modNum = size%dftNum;
		if(modNum > 0) {
			num += 1;
		}
		System.out.println(num);
	}
	
	public void testSublist() {  
	    List<Object> lists = new ArrayList<Object>();  
	  
	    lists.add("1");  
	    lists.add("2");  
	    lists.add("3");  
	    lists.add("4");  
	  
	    //注意这里是和本文顶部的代码不同的....  
	    List<Object> tempList = new ArrayList<Object>(lists.subList(2, lists.size()));  
	  
	    //tempList.add("6");  
	  
	    System.out.println(tempList); // 1  
	  
	    System.out.println(lists); // 2  
	}  

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String testStr = "2222";
		Test t = new Test();
		t.testSublist();
		//t.changeSql(testStr);
		//t.testArr();
		//t.testStr();
		//System.out.println(testStr);
		//t.testListBean();
		//System.out.println(t.testMod());
	}

}
