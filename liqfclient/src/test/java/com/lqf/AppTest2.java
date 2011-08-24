package com.lqf;

import org.testng.annotations.Test;

public class AppTest2 {

	@Test
	public void testPrintln() {
		//System.out.println(compare("�����������11","������"));
		boolean isTrue = false;
		String a = "�����Ԍ��W��Ȩ�콢��";
		String b = "�Ԍ��W��Ȩ";
		
		if(a.contains(b)) {
			System.out.println("OK");
		} else {
			System.out.println("NONONON!");
		}

	}
	

	private static String __ENCODE__ = "GBK"; // file://һ��Ҫ��GBK

	private static String __SERVER_ENCODE__ = "GB2312"; // file://�������ϵ�ȱʡ����

	/*
	 * 
	 * �Ƚ����ַ���
	 */

	public int compare(String s1, String s2)

	{

		String m_s1 = null, m_s2 = null;

		try

		{

			// �Ƚ����ַ��������GBK

			m_s1 = new String(s1.getBytes(__SERVER_ENCODE__), __ENCODE__);

			m_s2 = new String(s2.getBytes(__SERVER_ENCODE__), __ENCODE__);

		}

		catch (Exception ex)

		{

			return s1.compareTo(s2);

		}

		int res = chineseCompareTo(m_s1, m_s2);

		System.out.println("�Ƚϣ�" + s1 + " | " + s2 + "==== Result: " + res);

		return res;

	}

	// ��ȡһ������/��ĸ��Charֵ

	public static int getCharCode(String s)

		{

		if (s==null || s.equals("")) 
			return -1; //��������
		byte[] b = s.getBytes();
		int value = 0;
		//��֤ȡ��һ���ַ������ֻ���Ӣ�ģ�
		for (int i = 0; i < b.length && i <= 2; i ++) {
			value = value * 100 + b[i];
		}
		return value;
	}

	// �Ƚ������ַ���

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
