package com.net.pic;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextArea;

import com.net.pic.ui.HttpClientUrl;
import com.net.pic.ui.MainWin;
import com.thread.TestSecrity;


public class ControllerImpl implements Controller {
	private MainWin mainWin;
    private JTextArea messageArea;
    private DataFetcher fetcher = new DataFetcherImpl();
    private DataHandler hander = new DataHandlerImpl();
    private DataHandler urlHander = new UrlHandlerImpl();
    private final static String PRX_URL = "http://www.xfjiayuan.com/";

    public ControllerImpl(MainWin mainWin) {
        this.mainWin = mainWin;
        this.messageArea = mainWin.getMessageArea();
    }

    public ControllerImpl() {

    }
    
    public List<File> fetchImages(String pageUrl, String imgSaveDir)
            throws MalformedURLException, IOException {
    	HttpClientUrl clintUrl = new HttpClientUrl();
        // 获取html页面
        StringBuffer page = fetcher.fetchHtml(pageUrl, clintUrl);

        // 获取页面中的地址
        List<String> imgUrls = hander.getImageUrls(page);
        
        if(imgUrls.size() <= 0) {
        	List<String> urlStrs =  urlHander.getUrls(page);
        	for(String tempUrl : urlStrs) {
        		
        		page = fetcher.fetchHtml(PRX_URL + tempUrl, clintUrl);
        		imgUrls.addAll(hander.getImageUrls(page));
        	}
        }
        System.out.println(imgUrls.toString());
        // 保存图片，返回文件列表
        List<File> fileList = new ArrayList<File>();
       
        int threadNum = imgUrls.size();
		//初始化countDown
		CountDownLatch threadSignal = new CountDownLatch(threadNum);
      //创建固定长度的线程池
      	ExecutorService executor = Executors.newFixedThreadPool(50);
      	int i = 0;
      	for (String url : imgUrls) { //开threadNum个线程   
      		String newFileName = "00" + i +".jpg";
        	String threadName = "d" + i;
			Runnable task = new DownPicThread(threadName, url, newFileName, imgSaveDir, threadSignal, messageArea);
			executor.execute(task);
			i ++;
		}
      	try {
			threadSignal.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //等待所有子线程执行完   
		//do work
		System.out.println(Thread.currentThread().getName() + "+++++++结束.");
		//finish thread
		executor.shutdown();
		
        //多线程下载
/*        CyclicBarrier cb = new CyclicBarrier(imgUrls.size()); 
        int i = 1;
        for (String url : imgUrls) {
        	String newFileName = "00" + i +".jpg";
        	String threadName = "d" + i;
        	new Downloader(threadName, url, newFileName, imgSaveDir, cb, messageArea).start();
             i++;
        }*/
        if(messageArea != null) {
	        messageArea.setText(
	                messageArea.getText() + "/n" + " 任务完成，共下载"+i+"个图片!");
        }
        
        return fileList;
    }
    
    /**
	 * 
	 * @author jill
	 *
	 */
	private class DownPicThread implements Runnable {
		private CountDownLatch threadsSignal;

		//private CyclicBarrier cb;
		private String urlStr;
		private String saveFileName;
		private JTextArea messageArea;

		//private static final String FILE_PATH = "d://pic//";
		private String filePath = "d://pic//";

		public DownPicThread(String name, String url, String saveFileName,
				CountDownLatch cb) {
			
			this.threadsSignal = cb;
			this.urlStr = url;
			this.saveFileName = saveFileName;
		}
		
		public DownPicThread(String name, String url, String saveFileName,String filePath,
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
    
    public static void main(String[] args) {
    	Controller controller = new ControllerImpl();
        String testUrl = "http://www.xfjiayuan.com/forum-25-1.html";
        //二级解析
        try {
			List<File> fileList = controller.fetchImages(testUrl, "d://pic//");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}


