package com.net.pic.task;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.swing.JTextArea;

public class DownPicThread implements Runnable {
	private CountDownLatch threadsSignal;

	//private CyclicBarrier cb;
	private String urlStr;
	private String saveFileName;
	private JTextArea messageArea;

	//private static final String FILE_PATH = "d://pic//";
	private String filePath = "d://pic//";

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
		System.out.println(Thread.currentThread().getName() + "开始...");
		//do shomething
		long thredCount = threadsSignal.getCount();
		try {
			System.out.println("开始下载" + this.saveFileName + "...");
			URL url = new URL(this.urlStr);
			DataInputStream dis = new DataInputStream(url.openStream());
			OutputStream fos = new FileOutputStream(new File(filePath
					+ "//" + this.saveFileName));
			byte[] buff = new byte[1024];
			int len = -1;
			while ((len = dis.read(buff)) != -1) {
				fos.write(buff, 0, len);
			}
			buff = null;
			fos.close();
			dis.close();
			System.out.println("下载文件" + this.saveFileName + "完成");
			if(messageArea != null) {
				messageArea.setText(
	                    messageArea.getText() + "/n" + filePath + "//" 
						+  this.saveFileName
	                            + " 下载完成！");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
		//线程结束时计数器减1
		threadsSignal.countDown();  
		System.out.println(Thread.currentThread().getName() + "结束. 还有"
				+ threadsSignal.getCount() + " 个线程");
	}
}


