package com.lqf;

import org.testng.annotations.Test;

public class AppTest2 {

	@Test
	public void testPrintln() {
		//System.out.println(compare("聚淘生活服务11","淘生活"));
		boolean isTrue = false;
		String a = "测试淘寶網授权旗舰店";
		String b = "淘寶網授权";
		
		if(a.contains(b)) {
			System.out.println("OK");
		} else {
			System.out.println("NONONON!");
		}

	}
	

	private static String __ENCODE__ = "GBK"; // file://一定要是GBK

	private static String __SERVER_ENCODE__ = "GB2312"; // file://服务器上的缺省编码

	/*
	 * 
	 * 比较两字符串
	 */

	public int compare(String s1, String s2)

	{

		String m_s1 = null, m_s2 = null;

		try

		{

			// 先将两字符串编码成GBK

			m_s1 = new String(s1.getBytes(__SERVER_ENCODE__), __ENCODE__);

			m_s2 = new String(s2.getBytes(__SERVER_ENCODE__), __ENCODE__);

		}

		catch (Exception ex)

		{

			return s1.compareTo(s2);

		}

		int res = chineseCompareTo(m_s1, m_s2);

		System.out.println("比较：" + s1 + " | " + s2 + "==== Result: " + res);

		return res;

	}

	// 获取一个汉字/字母的Char值

	public static int getCharCode(String s)

		{

		if (s==null || s.equals("")) 
			return -1; //保护代码
		byte[] b = s.getBytes();
		int value = 0;
		//保证取第一个字符（汉字或者英文）
		for (int i = 0; i < b.length && i <= 2; i ++) {
			value = value * 100 + b[i];
		}
		return value;
	}

	// 比较两个字符串

	public int chineseCompareTo(String s1, String s2)

	{

		int len1 = s1.length();

		int len2 = s2.length();

		int n = Math.min(len1, len2);

		for (int i = 0; i < n; i++)

		{

			int s1_code = getCharCode(s1.charAt(i) + "");

			int s2_code = getCharCode(s2.charAt(i) + "");

			if (s1_code != s2_code)
				return s1_code - s2_code;

		}

		return len1 - len2;

	}

}
