package com.net.pic.task;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.swing.JTextArea;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.net.pic.util.FileUtil;

public class DownPicThread implements Runnable {
	
	private static Log logger = LogFactory.getLog(DownPicThread.class);
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
		System.out.println(Thread.currentThread().getName() + "��ʼ...");
		try {
			logger.debug("��ʼ����" + this.saveFileName + "...");
			URL url = new URL(this.urlStr);
			DataInputStream dis = new DataInputStream(url.openStream());
			
			FileUtil.toFile(dis, filePath, this.saveFileName);
			
			dis.close();
			logger.debug("�����ļ�" + this.saveFileName + "���");
			if(messageArea != null) {
				messageArea.setText(
	                    messageArea.getText() + "/n" + filePath + "//" 
						+  this.saveFileName
	                            + " ������ɣ�");
			}
		} catch (MalformedURLException e) {
			logger.error(e.fillInStackTrace());
		} catch (IOException e) {
			logger.error(e.fillInStackTrace());

		}
		//�߳̽���ʱ��������1
		threadsSignal.countDown();  
		logger.debug(Thread.currentThread().getName() + "����. ����"
				+ threadsSignal.getCount() + " ���߳�");
	}
}


