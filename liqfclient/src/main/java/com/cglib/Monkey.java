package com.cglib;

/**
 * 第一点是没有实现任何接口，第二点是eat()方法是
final的。
 * @author jill
 *
 */
public class Monkey {
	public String type() {
		String type = "哺乳动物";
		System.out.println(type);
		return type;
	}

	public final void eat(String food) {
		System.out.println("The food is " + food + " !");
	}

	public void think() {
		System.out.println("思考！");
	}
}
