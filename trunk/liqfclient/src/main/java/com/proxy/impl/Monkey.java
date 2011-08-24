package com.proxy.impl;

import com.proxy.Mammal;
import com.proxy.Primate;

public class Monkey implements Mammal, Primate {
	
	public String type() {
		String type = "²¸Èé¶¯Îï";
		System.out.println(type);
		return type;
	}

	public void eat(String food) {
		System.out.println("The food is " + food + " !");
	}

	public void think() {
		System.out.println("Ë¼¿¼£¡");
	}
}