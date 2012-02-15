package com.poi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jdom.JDOMException;

/**
 * ����ȶ�
 * @author weique.lqf
 *
 */
public class ReplaceKey {
	
	public static void replaceAllKey(String filePath, String targetFilePath, String writeFilePath) {
		//���Ľ���
		//String filePath = "D:\\myword\\KPI\\FindBugs��������_���İ�.doc";
		// System.out.println(judgeChina(filePath));
		WordParse.readWord(filePath);
		Map<String, String> sourceKey = WordParse.getKeyMap();
		
		//Ӣ��ԭ��
		//String filePath2 = "D:\\myword\\KPI\\messages.xml";
		Map<String, String> tempMap = new HashMap<String, String>();
		try {
			tempMap = XmlParse.buildRichPage(XmlParse.buildDocument(targetFilePath));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder oldReplaceStr = new StringBuilder();
		FileReader fr;
		try {
			fr = new FileReader(targetFilePath);
			BufferedReader br = new BufferedReader(fr); 
			String tempLine = br.readLine();
			oldReplaceStr.append(tempLine); 
			while (tempLine != null) { 
				tempLine = br.readLine(); 
				oldReplaceStr.append(tempLine + "\n"); 
			} 
			br.close(); 
			fr.close();  
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		String strTemp = oldReplaceStr.toString();
		//�ؼ���ƥ��
		for(Map.Entry<String, String> temp : tempMap.entrySet()) {
			for(Map.Entry<String, String> sourceTemp : sourceKey.entrySet()) {
				//ƥ�����ˣ����滻
				if(sourceTemp.getKey().indexOf(temp.getKey()) >= 0) {
					String tempStr = temp.getValue() + "<p>" + sourceTemp.getValue() + "</p>";
					//System.out.println("tempStr==" + tempStr);
					//ִ���滻����
					strTemp = replaceLongStr(strTemp, temp.getValue(), tempStr);
				}
			}
			
		}
		//����µ�xml�ļ�
		try { 
			FileWriter fw = new FileWriter(writeFilePath); 
			fw.write(strTemp); 
			fw.flush(); 
			fw.close();  
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 

	}
	
	//����
	public static void replaceAllKeyCur(String filePath, String targetFilePath, String writeFilePath) {
		//���Ľ���
		WordParse.readWord(filePath);
		Map<String, String> sourceKey = WordParse.getKeyMap();
		
		//Ӣ��ԭ��
		Map<String, String> tempMap = new HashMap<String, String>();
		try {
			tempMap = XmlParse.buildRichPage(XmlParse.buildDocument(targetFilePath));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder oldReplaceStr = new StringBuilder();
		FileReader fr;
		try {
			fr = new FileReader(targetFilePath);
			BufferedReader br = new BufferedReader(fr); 
			String tempLine = br.readLine();
			oldReplaceStr.append(tempLine); 
			while (tempLine != null) { 
				tempLine = br.readLine(); 
				oldReplaceStr.append(tempLine + "\n"); 
			} 
			br.close(); 
			fr.close();  
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		String strTemp = oldReplaceStr.toString();
		//�ؼ���ƥ��
		for(Map.Entry<String, String> temp : tempMap.entrySet()) {
			for(Map.Entry<String, String> sourceTemp : sourceKey.entrySet()) {
				//ƥ�����ˣ����滻
				if(sourceTemp.getKey().indexOf(temp.getKey()) >= 0) {
					String tempStr = temp.getValue() + "<p>" + sourceTemp.getValue() + "</p>";
					//System.out.println("tempStr==" + tempStr);
					//ִ���滻����
					strTemp = replaceLongStr(strTemp, temp.getValue(), tempStr);
				}
			}
			
		}
		//����µ�xml�ļ�
		try { 
			FileWriter fw = new FileWriter(writeFilePath); 
			fw.write(strTemp); 
			fw.flush(); 
			fw.close();  
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 

	}
	
	/**
	 * �ı��滻
	 * @param str
	 * @param fromStr
	 * @param toStr
	 * @return
	 */
	public static String replaceLongStr(String str, String fromStr, String toStr) {
	    StringBuffer result = new StringBuffer();
	    if (str != null && !str.equals("")) {
	    	//System.out.println("fromStr==" + fromStr);
	      while (str.indexOf(fromStr) >= 0) {
	    	//  System.out.println("str==" + str);
	        result.append(str.substring(0, str.indexOf(fromStr)));
	        result.append(toStr);
	        str = str.substring(str.indexOf(fromStr)+fromStr.length(), str.length());
	        
	      }
	      result.append(str);
	    }
	    return result.toString();
	  }
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = "D:\\myword\\KPI\\FindBugs��������_���İ�.doc";
		String targetFilePath = "D:\\myword\\KPI\\messages.xml";
		String writeFilePath = "D:\\myword\\KPI\\messages2.xml";
		replaceAllKey(filePath, targetFilePath, writeFilePath);
	}
}
