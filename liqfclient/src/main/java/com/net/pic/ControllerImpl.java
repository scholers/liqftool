package com.net.pic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextArea;

import com.net.pic.task.DownPicThread;
import com.net.pic.task.TaskThread;
import com.net.pic.ui.HttpClientUrl;
import com.net.pic.ui.MainWin;
import com.net.pic.util.FileBean;
import com.net.pic.util.FileUtil;


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
/*        	for(String tempUrl : urlStrs) {
        		
        		page = fetcher.fetchHtml(PRX_URL + tempUrl, clintUrl);
        		imgUrls.addAll(hander.getImageUrls(page));
        	}*/
        	 int threadNum = urlStrs.size();
     		//初始化countDown
     		CountDownLatch threadSignal = new CountDownLatch(threadNum);
           //创建固定长度的线程池
           	ExecutorService executor = Executors.newFixedThreadPool(50);
           	for(String tempUrl : urlStrs) { //开threadNum个线程   
           		HttpClientUrl clintUrlTemp = new HttpClientUrl();
           		clintUrlTemp.setCookieArr(clintUrl.getCookieArr());
     			Runnable task = new TaskThread(PRX_URL + tempUrl, clintUrlTemp,
     					threadSignal, fetcher, hander, imgUrls);
     			executor.execute(task);
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
     		//executor.shutdown();
        }
        System.out.println(imgUrls.toString());
        // 保存图片，返回文件列表
        List<File> fileList = new ArrayList<File>();
        List<FileBean> fileBeanList = new ArrayList<FileBean>();
        for(String tempUrl : imgUrls) { //开threadNum个线程   
        	FileBean fileBean = new FileBean();
        	fileBean.setFileName(tempUrl);
        	fileBeanList.add(fileBean);
        }
       
        int threadNum = fileBeanList.size();
        //输出到文件
        if(threadNum > 0) {
        	FileUtil.toFile(fileBeanList, imgSaveDir, "fileList.txt");
        }
        
        threadNum = fileBeanList.size();
		//初始化countDown
		CountDownLatch threadSignal = new CountDownLatch(threadNum);
      //创建固定长度的线程池
      	ExecutorService executor = Executors.newFixedThreadPool(50);
      	int i = 0;
      	for (FileBean fileBean : fileBeanList) { //开threadNum个线程   
      		String newFileName = System.currentTimeMillis() +"_00" + i +".jpg";
			Runnable task = new DownPicThread(fileBean.getFileName(), newFileName, imgSaveDir, threadSignal, messageArea);
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


