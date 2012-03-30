package com.net.pic.task;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.swing.JTextArea;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
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
		logger.debug(Thread.currentThread().getName() + "开始...");
		 HttpClient client = new HttpClient();  
	     GetMethod httpGet = new GetMethod(this.urlStr);  
		try {
			logger.debug("开始下载" + this.saveFileName + "...");
		    client.executeMethod(httpGet);  
			InputStream in = httpGet.getResponseBodyAsStream();  
			FileUtil.toFile(in, filePath, this.saveFileName);
			logger.debug("下载文件" + this.saveFileName + "完成");
			if(messageArea != null) {
				messageArea.setText(
	                    messageArea.getText() + "/n" + filePath + "//" 
						+  this.saveFileName
	                            + " 下载完成！");
			}
		} catch (MalformedURLException e) {
			logger.error(e.fillInStackTrace());
		} catch (IOException e) {
			logger.error(e.fillInStackTrace());

		}
		//线程结束时计数器减1
		threadsSignal.countDown();  
		logger.debug(Thread.currentThread().getName() + "结束. 还有"
				+ threadsSignal.getCount() + " 个线程");
	}
}


