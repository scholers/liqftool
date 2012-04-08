package com.net.pic.task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import com.net.pic.DataFetcher;
import com.net.pic.DataHandler;
import com.net.pic.ui.HttpClientUrl;

public class TaskThread implements Runnable {
	private static Logger logger = Logger.getLogger(TaskThread.class);
	private CountDownLatch threadsSignal;

    private DataFetcher fetcher = null;
    private DataHandler hander = null;
    private Set<String> imgList = null;
    private String url = null;
    private HttpClientUrl clintUrl = null;

	public TaskThread(String url, HttpClientUrl clintUrl,
			CountDownLatch cb, DataFetcher fetcher, DataHandler hander, Set<String> imgList) {
		
		this.threadsSignal = cb;
		this.fetcher = fetcher;
		this.hander = hander;
		this.imgList = imgList;
		this.url = url;
		this.clintUrl = clintUrl;
	}
	
	public TaskThread(String url, HttpClientUrl clintUrl,
			CountDownLatch cb, JTextArea messageArea, DataFetcher fetcher, DataHandler hander, Set<String> imgList) {
		
		this.threadsSignal = cb;
		this.fetcher = fetcher;
		this.hander = hander;
		this.imgList = imgList;
		this.url = url;
		this.clintUrl = clintUrl;
	}

	public void run() {
		
		try {
			StringBuffer page = fetcher.fetchHtml(url, clintUrl);
			imgList.addAll(hander.getImageUrls(page));
			
		} catch (MalformedURLException e) {
			logger.error(e.fillInStackTrace());
		} catch (IOException e) {
			logger.error(e.fillInStackTrace());	
		}finally {
			//线程结束时计数器减1
			threadsSignal.countDown();  
		}

	}
}
