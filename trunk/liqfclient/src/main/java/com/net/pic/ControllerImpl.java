package com.net.pic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextArea;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.net.pic.task.DownPicThread;
import com.net.pic.task.TaskThread;
import com.net.pic.ui.HttpClientUrl;
import com.net.pic.ui.MainWin;
import com.net.pic.util.FileBean;
import com.net.pic.util.FileUtil;


public class ControllerImpl implements Controller {
	private static Log logger = LogFactory.getLog(ControllerImpl.class); 
	private MainWin mainWin;
    private JTextArea messageArea;
    private DataFetcher fetcher = new DataFetcherImpl();
    private DataHandler hander = new DataHandlerImpl();
    private DataHandler urlHander = new UrlHandlerImpl();
    private String siteUrl = null;
    private String loginUrl = null;
    private String userName = null;
    private String password = null;

    public ControllerImpl(MainWin mainWin) {
        this.mainWin = mainWin;
        this.messageArea = mainWin.getMessageArea();
    }

    public ControllerImpl(String siteUrl, String loginUrl, String userName, String password) {
    	this.siteUrl = siteUrl;
    	this.loginUrl = loginUrl;
    	this.userName = userName;
    	this.password = password;
    	
    }
    
    public List<File> fetchImages(String pageUrl, String imgSaveDir)
            throws MalformedURLException, IOException {
    	HttpClientUrl clintUrl = new HttpClientUrl(loginUrl, userName, password,null);
        // 获取html页面
        StringBuffer page = fetcher.fetchHtml(pageUrl, clintUrl);

        // 获取页面中的地址
        Set<String> imgUrls = hander.getImageUrls(page);
        
        //并发执行
        if(imgUrls.size() <= 0) {
        	//获取该页面下面所有图片链接的地址
        	Set<String> urlStrs =  urlHander.getUrls(page);
        	 int threadNum = urlStrs.size();
     		//初始化countDown
     		CountDownLatch threadSignal = new CountDownLatch(threadNum);
           //创建固定长度的线程池
           	ExecutorService executor = Executors.newFixedThreadPool(30);
           	for(String tempUrl : urlStrs) { //开threadNum个线程   
           		HttpClientUrl clintUrlTemp = new HttpClientUrl();
           		clintUrlTemp.setCookieArr(clintUrl.getCookieArr());
     			Runnable task = new TaskThread(siteUrl + tempUrl, clintUrlTemp,
     					threadSignal, fetcher, hander, imgUrls);
     			executor.execute(task);
     		}
           	try {
     			threadSignal.await();
     		} catch (InterruptedException e) {
     			// TODO Auto-generated catch block
     			logger.error(e.fillInStackTrace());
     		} //等待所有子线程执行完   
     		//do work
           	logger.debug(Thread.currentThread().getName() + "+++++++结束.");
     		//finish thread
     		executor.shutdown();
        }
        // 保存图片，返回文件列表
        List<File> fileList = new ArrayList<File>();
        //需要下载的文件对象列表
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
      	ExecutorService executor = Executors.newFixedThreadPool(30);
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
			logger.equals(e.fillInStackTrace());
		} //等待所有子线程执行完   
		//do work
		//finish thread
		executor.shutdown();
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
    	String siteUrl = args[0];
    	String loginUrl = args[1];
    	String password = args[2];
    	String userName = args[3];
    	String fileDir = args[4];
    	String testUrl = args[5];
    	String fileDir2 = args[6];
    	String fileDir3 = args[7];
    	if(siteUrl == null || siteUrl.length() <= 0) {
    		//defaule site url
    		siteUrl = "http://a.xfjiayuan.com/";
    	}
    	//login url
    	if(loginUrl == null || loginUrl.length() <= 0) {
    		loginUrl = siteUrl + "logging.php?action=login";
    	}
    	if(password == null || password.length() <= 0) {
    		password = "790521";
    	}
    	if(userName == null || userName.length() <= 0) {
    		userName = "scholerscn";
    	}
    	
    	//output dir
    	if(fileDir == null || fileDir.length() <= 0) {
    		fileDir = "d://pic2//";
    	}
    	Controller controller = new ControllerImpl(siteUrl,loginUrl,userName,password);
    	if(testUrl == null || testUrl.length() <= 0) {
    		testUrl = siteUrl + "forum-25-1.html";
    	}
        String testUrl2 = siteUrl + "forum-784-1.html";
        String testUrl3 = siteUrl + "forum-161-1.html";
        
        if(fileDir2 == null || fileDir2.length() <= 0) {
        	fileDir2 = "d://pic2//pic2//";
    	}
        
        if(fileDir3 == null || fileDir3.length() <= 0) {
        	fileDir3 = "d://pic2//pic3//";
    	}
        //二级解析
        try {
        	
        	//for(int i = 0; i < 9; i ++) {
        		//testUrl = siteUrl + "forum-25-" + 5 + ".html";
        		controller.fetchImages(testUrl, fileDir);
        		controller.fetchImages(testUrl2, fileDir2);
        		controller.fetchImages(testUrl3, fileDir3);
        		//Thread.sleep(5000);
        	//}
    	} catch (MalformedURLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	
		}
    }

}


