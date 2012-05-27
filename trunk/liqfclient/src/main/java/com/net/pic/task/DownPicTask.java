package com.net.pic.task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import com.net.pic.util.FileUtil;

/**
 * 
 * @author weique.lqf
 *
 */
public class DownPicTask implements Callable<Object> {

	private static Logger logger = Logger.getLogger(DownPicThread.class);

	private String urlStr;
	private String saveFileName;
	private JTextArea messageArea;

	private String filePath = null;
	private boolean isDownload = true;

	public DownPicTask(String url, String saveFileName,
			CountDownLatch cb) {
		this.urlStr = url;
		this.saveFileName = saveFileName;
	}
	
	public DownPicTask(String url, String saveFileName,String filePath,
			CountDownLatch cb, JTextArea messageArea) {
		this.urlStr = url;
		this.saveFileName = saveFileName;
		this.filePath = filePath;
		this.messageArea = messageArea;
	}

	public Object call() {
		logger.debug(Thread.currentThread().getName() + "开始...");
	     URL source = null;
		try {
			logger.debug("开始下载" + this.saveFileName + "...");
			source = new URL(this.urlStr);
			FileUtil.copyUrlToFile(source, filePath, this.saveFileName);
			logger.debug("下载文件" + this.saveFileName + "完成");
			if(messageArea != null) {
				messageArea.setText(
	                    messageArea.getText() + "/n" + filePath + "//" 
						+  this.saveFileName
	                            + " 下载完成！");
			}
		} catch (MalformedURLException e) {
			logger.error("下载文件" + this.saveFileName + "失败" + e.fillInStackTrace());
			this.setDownload(false);
		} catch (IOException e) {
			logger.error("下载文件" + this.saveFileName + "失败" + e.fillInStackTrace());
			this.setDownload(false);
		}
		Map<String ,Boolean> tempMap = new HashMap<String,Boolean>();
		tempMap.put(this.saveFileName, Boolean.valueOf(this.isDownload()));
		return tempMap;

	}

	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}
}


