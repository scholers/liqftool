package com.cglib;

import net.sf.cglib.proxy.Enhancer;

public class Cglib {
	
	public static void main(String[] args) {
		Monkey monkey = (Monkey) Enhancer.create(Monkey.class,
				new MyMethodInterceptor());
		monkey.eat("�㽶");
		monkey.type();
		monkey.think();
	}
}
