package com.proxy;

public class Monkey implements Animal {
	
	public String type() {
		String type = "²¸Èé¶¯Îï";
		System.out.println(type);
		return type;
	}

	public void eat(String food) {
		System.out.println("The food is " + food + " !");
	}
}
