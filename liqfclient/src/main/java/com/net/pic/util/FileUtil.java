package com.net.pic.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FileUtil {

	private static final String saveFileName = "";

	private static final String FILE_PATH = "d://pic//";

	public static void toFile(InputStream in, String filePath, String fileName) {
		if (filePath == null) {
			filePath = FILE_PATH;
		}
		if (fileName == null) {
			filePath = saveFileName;
		}
		try {
			DataInputStream dis = new DataInputStream(in);
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
			File writeFile = new File(filePath + fileName);
			OutputStream fos = new FileOutputStream(writeFile);
			byte[] buff = new byte[1024];
			int len = -1;
			while ((len = dis.read(buff)) != -1) {
				fos.write(buff, 0, len);
			}
			buff = null;
			fos.close();
			dis.close();
			
			System.out.println("下载文件" + fileName + "完成");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 
	 * @param fileList
	 * @param filePath
	 * @param fileName
	 */
	public static void toFile(List<FileBean> fileList, String filePath,
			String fileName) {
		File writeFile = new File(filePath + fileName);
		boolean isAppend = false;
		if (writeFile.exists()) {
			isAppend = true;
			// 去重
			Set<FileBean> currentFileList = readFileByLines(filePath + fileName);
			StringBuilder strBuilder = new StringBuilder();
			List<FileBean> tempFileList = new ArrayList<FileBean>();
			for (FileBean fileBean : fileList) {
				// 删除重复的文件
				if (!currentFileList.contains(fileBean)) {
					tempFileList.add(fileBean);
					strBuilder.append(fileBean.getFileName()).append("\n");
				}
			}
			fileList.clear();
			fileList.addAll(tempFileList);
		} 
		/*
		InputStream in = new ByteArrayInputStream(strBuilder.toString()
				.getBytes());
		toFile(in, filePath, fileName);*/
		

		FileWriter fw;
		try {
			fw = new FileWriter(filePath + fileName, isAppend);
			BufferedWriter bw = new BufferedWriter(fw); 
			for(FileBean fileBean : fileList) {
				bw.write(fileBean.getFileName()); 
		        bw.newLine();//断行 
			}
			bw.close();
	        fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static Set<FileBean> readFileByLines(String fileName) {
		File file = new File(fileName);
		Set<FileBean> fileList = new HashSet<FileBean>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				FileBean fileBean = new FileBean();
				fileBean.setFileName(tempString);
				fileList.add(fileBean);
				// 显示行号
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return fileList;
	}

}
