package com.net.pic.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.swing.JTextArea;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.net.pic.util.FileUtil;

public class DownPicThread implements Runnable {
	
	private static Logger logger = Logger.getLogger(DownPicThread.class);
	private CountDownLatch threadsSignal;

	private String urlStr;
	private String saveFileName;
	private JTextArea messageArea;

	private String filePath = null;

	public DownPicThread(String url, String saveFileName,
			CountDownLatch cb) {
		
		this.threadsSignal = cb;
		this.urlStr = url;
		this.saveFileName = saveFileName;
	}
	
	public DownPicThread(String url, String saveFileName,String filePath,
			CountDownLatch cb, JTextArea messageArea) {
		
		this.threadsSignal = cb;
		this.urlStr = url;
		this.saveFileName = saveFileName;
		this.filePath = filePath;
		this.messageArea = messageArea;
	}
	
	public DownPicThread(CountDownLatch threadsSignal) {
		this.threadsSignal = threadsSignal;
	}

	public void run() {
		logger.debug(Thread.currentThread().getName() + "��ʼ...");
		 //HttpClient client = new HttpClient();  
	    // GetMethod httpGet = new GetMethod(this.urlStr);  
	     URL source = null;
		try {
			logger.debug("��ʼ����" + this.saveFileName + "...");
			source = new URL(this.urlStr);
		   // client.executeMethod(httpGet);  
			//InputStream in = httpGet.getResponseBodyAsStream();  
			FileUtil.copyUrlToFile(source, filePath, this.saveFileName);
			
			logger.debug("�����ļ�" + this.saveFileName + "���");
			if(messageArea != null) {
				messageArea.setText(
	                    messageArea.getText() + "/n" + filePath + "//" 
						+  this.saveFileName
	                            + " ������ɣ�");
			}
		} catch (MalformedURLException e) {
			logger.error("�����ļ�" + this.saveFileName + "ʧ��" + e.fillInStackTrace());
		} catch (IOException e) {
			logger.error("�����ļ�" + this.saveFileName + "ʧ��" + e.fillInStackTrace());
		}finally {
			//�߳̽���ʱ��������1
			threadsSignal.countDown();  
			logger.debug(Thread.currentThread().getName() + "����. ����"
					+ threadsSignal.getCount() + " ���߳�");
		}

	}
}


