package com.construct;

/**
 * 
 * @author jill
 *
 */
public class Child extends Father {

	static {
		System.out.println("This is static block!");
	}

	public Child() {
		System.out.println("This is test constructor");
	}

	{
		System.out.println("Common init block!");
	}

	public static void main(String[] args) {
		
		System.out.println("Hello,Java!");
		Child test = new Child();
	}
}
