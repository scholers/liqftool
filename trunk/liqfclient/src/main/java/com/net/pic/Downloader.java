package com.net.pic;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * \
 * 
 * @author weique.lqf
 * 
 */
public class Downloader extends Thread {

	private CyclicBarrier cb;
	private String urlStr;
	private String saveFileName;

	private static final String FILE_PATH = "C://pic//";

	public Downloader(String name, String url, String saveFileName,
			CyclicBarrier cb) {
		this.setName(name);
		this.cb = cb;
		this.urlStr = url;
		this.saveFileName = saveFileName;
	}

	@Override
	public void run() {
		try {
			System.out.println("开始下载" + this.saveFileName + "...");
			URL url = new URL(this.urlStr);
			DataInputStream dis = new DataInputStream(url.openStream());
			OutputStream fos = new FileOutputStream(new File(FILE_PATH
					+ this.saveFileName));
			byte[] buff = new byte[1024];
			int len = -1;
			while ((len = dis.read(buff)) != -1) {
				fos.write(buff, 0, len);
			}
			buff = null;
			fos.close();
			dis.close();
			System.out.println("下载文件" + this.saveFileName + "完成");
			this.cb.await();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
