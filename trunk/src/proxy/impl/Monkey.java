package proxy.impl;

import proxy.Mammal;
import proxy.Primate;

public class Monkey implements Mammal, Primate {
	
	public String type() {
		String type = "���鶯��";
		System.out.println(type);
		return type;
	}

	public void eat(String food) {
		System.out.println("The food is " + food + " !");
	}

	public void think() {
		System.out.println("˼����");
	}
}