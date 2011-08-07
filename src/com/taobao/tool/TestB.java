package com.taobao.tool;

import java.lang.reflect.*;

/**
 * 
 * @author jill
 * 
 */
public class TestB {

	public void testAccess() {

		try {
			Class<TestA> cls = TestA.class;
			TestA gg = (TestA) cls.newInstance();
			Field field = cls.getDeclaredField("name");
			field.setAccessible(true);
			System.out.println((String) field.get(gg));
			//field.set(obj, value);
		} catch (SecurityException e) {
			 e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		TestB bTest = new TestB();
		bTest.testAccess();
	}
}
