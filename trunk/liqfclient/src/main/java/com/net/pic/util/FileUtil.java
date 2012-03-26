package com.net.pic.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
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
		      
		    File writeFile = new File(filePath+ fileName);
		      
		    if(writeFile.exists()) {
		    	//追加记录
		    	appendMethodB(fileName, in.toString());
		    } else {
				OutputStream fos = new FileOutputStream(writeFile);
				byte[] buff = new byte[1024];
				int len = -1;
				while ((len = dis.read(buff)) != -1) {
					fos.write(buff, 0, len);
				}
				buff = null;
				fos.close();
				dis.close();
		    }
			System.out.println("下载文件" + fileName + "完成");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * B方法追加文件：使用FileWriter
     */
    public static void appendMethodB(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
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
		//去重
		List<String> currentFileList = readFileByLines(filePath + fileName);
		StringBuilder strBuilder = new StringBuilder();
		List<String> tempFileList = new ArrayList<String>();
		for(String fileUrl : fileList) {
			//删除重复的文件
			if(!currentFileList.contains(fileUrl)) {
				tempFileList.add(fileUrl);
				strBuilder.append(fileUrl).append("\n");
			}
		}
		fileList.clear();
		fileList.addAll(tempFileList);
		InputStream in  = new   ByteArrayInputStream(strBuilder.toString().getBytes());
		toFile(in, filePath, fileName);
	}
	
	 /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static List<String> readFileByLines(String fileName) {
        File file = new File(fileName);
        List<String> fileList = new LinkedList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
            	fileList.add(tempString);
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
