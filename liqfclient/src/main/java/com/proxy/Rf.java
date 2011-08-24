package com.proxy;

import java.lang.reflect.Field;

public class Rf {
	public static void main(String[] args) throws Exception {
		Class<Reflect1> clazz = Reflect1.class;
		Reflect1 rf1 = clazz.newInstance();
		// 含有Declared字符串的是获取所有的元素，否则就只能获取公有元素
		Field[] f = clazz.getDeclaredFields();
		for (Field field : f) {
			// 设置这里本不具有访问权限的元素为可访问
			field.setAccessible(true);
			// 使用基本数据类型专有的API
			if (field.getType().getCanonicalName().equals("int")
					|| field.getType().getCanonicalName().equals(
							"java.lang.Integer")) {
				field.setInt(rf1, 9);
			}
			System.out.println("Field is " + field.getName() + "\t"
					+ field.toGenericString() + "\t" + field.get(rf1));
		}
	}
}
