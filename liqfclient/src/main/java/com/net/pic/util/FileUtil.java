package com.net.pic.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.List;

public class FileUtil {

	private static final String saveFileName = "";

	private static final String FILE_PATH = "d://pic//";
	
	public static void toFile(InputStream in, String filePath, String fileName) {
		if(filePath == null) {
			filePath = FILE_PATH;
		}
		if(fileName == null) {
			filePath = saveFileName;
		}
		try {
			DataInputStream dis = new DataInputStream(in);
			 java.io.File myFilePath = new java.io.File(filePath);  
		      if (!myFilePath.exists()) {  
		        myFilePath.mkdir();  
		      }  
		      
			OutputStream fos = new FileOutputStream(new File(filePath
					+ fileName));
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
	public static void toFile(List<String> fileList, String filePath, String fileName) {
		StringBuilder strBuilder = new StringBuilder();
		for(String fileUrl : fileList) {
			strBuilder.append(fileUrl).append("\n");
		}
		InputStream in  = new   ByteArrayInputStream(strBuilder.toString().getBytes());
		toFile(in, filePath, fileName);
	}
	
	
}
