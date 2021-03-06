package com.net.pic.util;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class FileUtilTest {
	@Test
	public void validateStingTest() {
		String test = "ddddd";
		String filePath = "d://pic//pic3//";
		String fileName = "a.txt";
		InputStream in  = new   ByteArrayInputStream(test.getBytes());
		
		try {
			FileUtil.toFile(in, filePath, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void validateStingTest2() {
		List<FileBean> fileList = new ArrayList<FileBean>();
		FileBean fileBean = new FileBean();
		fileBean.setFileName("http://tu.huzhu6.com/forum/month_1004/1004212038592c86f3bd2af9f7.jpg");
		fileList.add(fileBean);
		FileBean fileBean2 = new FileBean();
		fileBean2.setFileName("http://tu.huzhu6.com/forum/month_1102/110227122284557e2b22c9c7ad.jpg");
		fileList.add(fileBean2);
		FileBean fileBean3 = new FileBean();
		fileBean3.setFileName("http://tu.huzhu6.com/forum/month_1102/1102271222050dc73f81e0d280.jpg");
		fileList.add(fileBean3);
		String test = "ddddd";
		String filePath = "d://pic//";
		String fileName = "fileList.txt";
		
		try {
			FileUtil.toFile(fileList, filePath, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testSet() {
		Set<String> testSet = new HashSet<String>();
		String a1 = "http://www.sina.com.cn";
		testSet.add(a1);
		String a2 = "http://www.sina.com.cn";
		if(testSet.contains(a2)) {
			testSet.add(a2);
			System.out.println("trur");
		}
		
	}
	@Test
	public void testGetFileName() {
		String fileName = "http://tu.huzhu6.com/forum/month_1102/11022712068973dd4944e931fe.jpg";
		String newFileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
		System.out.println(newFileName);
	}
}
