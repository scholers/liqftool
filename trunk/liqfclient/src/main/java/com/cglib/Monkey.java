package com.cglib;

/**
 * ��һ����û��ʵ���κνӿڣ��ڶ�����eat()������
final�ġ�
 * @author jill
 *
 */
public class Monkey {
	public String type() {
		String type = "���鶯��";
		System.out.println(type);
		return type;
	}

	public final void eat(String food) {
		System.out.println("The food is " + food + " !");
	}

	public void think() {
		System.out.println("˼����");
	}
}
