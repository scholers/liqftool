package com.net.pic.util;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

public class FileUtilTest {
	@Test
	public void validateStingTest() {
		String test = "ddddd";
		String filePath = "d://pics//";
		String fileName = "a.txt";
		InputStream in  = new   ByteArrayInputStream(test.getBytes());
		
		FileUtil.toFile(in, filePath, fileName);
	}
}
