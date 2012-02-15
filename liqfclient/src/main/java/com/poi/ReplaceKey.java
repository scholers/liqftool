package com.poi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jdom.JDOMException;
import org.junit.Test;



/**
 * 翻译比对
 * @author weique.lqf
 *
 */
public class ReplaceKey {
	
	public static void replaceAllKey(String filePath, String targetFilePath, String writeFilePath) {
		//中文解释
		//String filePath = "D:\\myword\\KPI\\FindBugs规则整理_中文版.doc";
		// System.out.println(judgeChina(filePath));
		//WordParse.readWord(filePath);
		Map<String, String> sourceKey = WordParse.readWord(filePath);
		
		//英文原版
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
		//关键词匹配
		for(Map.Entry<String, String> temp : tempMap.entrySet()) {
			for(Map.Entry<String, String> sourceTemp : sourceKey.entrySet()) {
				//匹配上了，就替换
				if(sourceTemp.getKey().indexOf(temp.getKey()) >= 0) {
					String tempStr = temp.getValue() + "<p>" + sourceTemp.getValue() + "</p>";
					//System.out.println("tempStr==" + tempStr);
					//执行替换操作
					strTemp = replaceLongStr(strTemp, temp.getValue(), tempStr);
				}
			}
			
		}
		//输出新的xml文件
		try { 
			FileWriter fw = new FileWriter(writeFilePath); 
			fw.write(strTemp); 
			fw.flush(); 
			fw.close();  
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 

	}
	
	
	//并发
	public static void replaceAllKeyCur(String filePath, String targetFilePath, String writeFilePath) {
		//中文解释
		WordParse.readWord(filePath);
		Map<String, String> sourceKey = WordParse.readWord(filePath);
		
		//英文原版
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
		//关键词匹配
		for(Map.Entry<String, String> temp : tempMap.entrySet()) {
			for(Map.Entry<String, String> sourceTemp : sourceKey.entrySet()) {
				//匹配上了，就替换
				if(sourceTemp.getKey().indexOf(temp.getKey()) >= 0) {
					String tempStr = temp.getValue() + "<p>" + sourceTemp.getValue() + "</p>";
					//System.out.println("tempStr==" + tempStr);
					//执行替换操作
					strTemp = replaceLongStr(strTemp, temp.getValue(), tempStr);
				}
			}
			
		}
		//输出新的xml文件
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
	 * 测试函数
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testThread() throws InterruptedException {
		
		//初始化countDown
		CountDownLatch threadSignal = new CountDownLatch(2);
		long startTime = System.currentTimeMillis();
		//创建固定长度的线程池
		ExecutorService executor = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 2; i++) { //开threadNum个任务线程   
			Runnable task = new InnerThread(threadSignal, false);
			executor.execute(task);
		}
		threadSignal.await(); //等待所有子线程执行完   
		//do work
		System.out.println(Thread.currentThread().getName() + "+++++++结束.");

		//finish thread
		executor.shutdown();
		long endTime = System.currentTimeMillis();
		System.out.println("总共耗时：" + (endTime - startTime));
	}
	

	/**
	 * 
	 * @author jill
	 *
	 */
	private class InnerThread implements Runnable {
		private CountDownLatch threadsSignal;
		private boolean isCached = false;

		public InnerThread(CountDownLatch threadsSignal, boolean isCached) {
			this.threadsSignal = threadsSignal;
			this.isCached = isCached;
		}

		public void run() {
			System.out.println(Thread.currentThread().getName() + "开始...");
			//do shomething
			//long time1 = gryExecutor.testExecute(isCached, scriptName);
			// 清理脚本
			//gryExecutor.clearScripts();
			//System.out.println("消耗时间：" + time1);
			
			long thredCount = threadsSignal.getCount();
			System.out.println("开始了线程：：：：" + thredCount);
			
			//TestSecrity.getInstance().printTest();
			//线程结束时计数器减1
			threadsSignal.countDown();  
			System.out.println(Thread.currentThread().getName() + "结束. 还有"
					+ threadsSignal.getCount() + " 个线程");
		}
	}
	
	/**
	 * 文本替换
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
		String filePath = "D:\\myword\\KPI\\FindBugs规则整理_中文版.doc";
		String targetFilePath = "D:\\myword\\KPI\\messages.xml";
		String writeFilePath = "D:\\myword\\KPI\\messages2.xml";
		replaceAllKey(filePath, targetFilePath, writeFilePath);
	}
}
