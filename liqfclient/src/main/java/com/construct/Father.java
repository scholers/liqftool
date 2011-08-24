package com.construct;

public class Father {
	static {
		System.out.println("static in super");
	}

	
	
	public Father() {
		System.out.println("This is super class!");
	}

	
	{
		System.out.println("common block in super");
	}
	
}
