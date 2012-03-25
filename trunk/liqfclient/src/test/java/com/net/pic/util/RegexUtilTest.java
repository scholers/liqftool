package com.net.pic.util;

import static org.junit.Assert.*;

import org.junit.Test;



public class RegexUtilTest {

	@Test
	public void validateStingTest() {
		boolean isTrue = false;
		String r = "http://tu.huzhu6.com/forum/month_1203/120316181114e931d361e399b7.jpg";
		isTrue = RegexUtil.validateSting(r, "http://.*.jpg");
		assertTrue(isTrue);
	}
	
	@Test
	public void validateStingTest2() {
		boolean isTrue = false;
		String r = "thread-353210-1-1.html";
		isTrue = RegexUtil.validateSting(r, "thread.*.html");
		assertTrue(isTrue);
	}
}
