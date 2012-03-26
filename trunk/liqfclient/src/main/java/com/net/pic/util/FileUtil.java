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
		    	//׷�Ӽ�¼
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
			System.out.println("�����ļ�" + fileName + "���");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * B����׷���ļ���ʹ��FileWriter
     */
    public static void appendMethodB(String fileName, String content) {
        try {
            //��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
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
		//ȥ��
		List<String> currentFileList = readFileByLines(filePath + fileName);
		StringBuilder strBuilder = new StringBuilder();
		List<String> tempFileList = new ArrayList<String>();
		for(String fileUrl : fileList) {
			//ɾ���ظ����ļ�
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
     * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ�
     */
    public static List<String> readFileByLines(String fileName) {
        File file = new File(fileName);
        List<String> fileList = new LinkedList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
            	fileList.add(tempString);
                // ��ʾ�к�
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
