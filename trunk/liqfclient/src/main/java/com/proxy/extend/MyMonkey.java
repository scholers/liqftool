package com.proxy.extend;

import com.proxy.Monkey;

public class MyMonkey extends Monkey {
	
	@Override
	public void eat(String food) {
		System.out.println("+++Wrapped Before!+++");
		super.eat(food);
		System.out.println("+++Wrapped After!+++");
	}

	@Override
	public String type() {
		System.out.println("---Wrapped Before!---");
		String type = super.type();
		System.out.println("---Wrapped After!---");
		return type;
	}
}