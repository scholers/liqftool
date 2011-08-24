package com.liqf.other;

public class Paire {

	private String name = "";
	
    public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public static void main(String[] args) {

    	Paire paier = new Paire();

                   paier.test();

    }

   

    public void test() {

                   TestClass para1 = new TestClass();

                   para1.setTest(new Integer(10));

                   TestClass result1 = test1(para1);

                   System.out.println("para1   = " + para1.getTest());

                   System.out.println("result1 = " + result1.getTest());

                  

                   TestClass para2 = new TestClass();

                   para2.setTest(new Integer(10));

                   TestClass result2 = test2(para2);

                   System.out.println("para2   = " + para2.getTest());

                   System.out.println("result2 = " + result2.getTest());

    }

   

    public TestClass test1(TestClass t) {

                   t = new TestClass();

                   t.setTest(new Integer(20));

                   return t;

    }

   

    public TestClass test2(TestClass t) {

                   t.setTest(new Integer(20));

                   return t;

    }

   

    class TestClass {

                   Integer test = null;

                   public void setTest(Integer i) {

                                 test = i;

                   }

                   public Integer getTest() {

                                 return test;

                   }

    }

}