package com.net.pic.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * ��װ���ļ�����
 * @author jill
 *
 */
public class FileUtil {
	private static Logger logger = Logger.getLogger(FileUtil.class); 

	public static void toFile(InputStream in, String filePath, String fileName) {
		
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
		} catch (MalformedURLException e) {
			logger.error(e.fillInStackTrace());
		} catch (IOException e) {
			logger.error(e.fillInStackTrace());
		}
	}

	/**
	 * ��������·��(�����༶)
	 * 
	 * @param header
	 *            ����·����ǰ�벿��(�Ѵ���)
	 * @param tail
	 *            ����·���ĺ�벿��(��һ�������һ���ַ�������/����ʽ��123/258/456)
	 * @return �´����ľ���·��
	 */ 
	public String makeDir(String header, String tail) { 
	    String[] sub = tail.split("/"); 
	    File dir = new File(header); 
	    for (int i = 0; i < sub.length; i++) { 
	        if (!dir.exists()) { 
	            dir.mkdir(); 
	        } 
	        File dir2 = new File(dir + File.separator + sub[i]); 
	        if (!dir2.exists()) { 
	            dir2.mkdir(); 
	        } 
	        dir = dir2; 
	    } 
	    return dir.toString(); 
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
		if (writeFile.exists()) {//�ļ����ڣ���Ƚ��ļ�
			logger.info("File " + filePath + fileName + " is exists!");
			isAppend = true;
			// ȥ��
			Set<FileBean> currentFileList = readFileByLines(filePath + fileName);
			StringBuilder strBuilder = new StringBuilder();
			List<FileBean> tempFileList = new ArrayList<FileBean>();
			for (FileBean fileBean : fileList) {
				// ɾ���ظ����ļ�
				if (!currentFileList.contains(fileBean)) {
					tempFileList.add(fileBean);
					strBuilder.append(fileBean.getFileName()).append("\n");
				}
			}
			fileList.clear();
			fileList.addAll(tempFileList);
		} else {
			logger.info("File " + filePath + fileName + " is not exists!");
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			
			}
			//�����ļ�
			try {
				writeFile.createNewFile();
				logger.info("Create " + filePath + fileName);
			} catch (IOException e) {
				logger.error("Create File Failed:::" + e.fillInStackTrace());
			} 
		}
		
		FileWriter fw;
		try {
			fw = new FileWriter(filePath + fileName, isAppend);
			BufferedWriter bw = new BufferedWriter(fw); 
			for(FileBean fileBean : fileList) {
				bw.write(fileBean.getFileName()); 
		        bw.newLine();//���� 
			}
			bw.close();
	        fw.close();
		} catch (IOException e) {
			logger.error(e.fillInStackTrace());
		}
		   
	}

	/**
	 * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ�
	 */
	public static Set<FileBean> readFileByLines(String fileName) {
		File file = new File(fileName);
		Set<FileBean> fileList = new HashSet<FileBean>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
			while ((tempString = reader.readLine()) != null) {
				FileBean fileBean = new FileBean();
				fileBean.setFileName(tempString);
				fileList.add(fileBean);
			}
			reader.close();
		} catch (IOException e) {
			logger.error(e.fillInStackTrace());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					logger.error(e1.fillInStackTrace());
				}
			}
		}
		return fileList;
	}
	
	public void writeFile() {
		//FileUtils.w(null);
	}

}
