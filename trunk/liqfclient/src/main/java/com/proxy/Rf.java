package com.proxy;

import java.lang.reflect.Field;

public class Rf {
	public static void main(String[] args) throws Exception {
		Class<Reflect1> clazz = Reflect1.class;
		Reflect1 rf1 = clazz.newInstance();
		// ����Declared�ַ������ǻ�ȡ���е�Ԫ�أ������ֻ�ܻ�ȡ����Ԫ��
		Field[] f = clazz.getDeclaredFields();
		for (Field field : f) {
			// �������ﱾ�����з���Ȩ�޵�Ԫ��Ϊ�ɷ���
			field.setAccessible(true);
			// ʹ�û�����������ר�е�API
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
