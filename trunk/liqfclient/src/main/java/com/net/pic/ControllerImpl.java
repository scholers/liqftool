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
        // ��ȡhtmlҳ��
        StringBuffer page = fetcher.fetchHtml(pageUrl, clintUrl);

        // ��ȡҳ���еĵ�ַ
        List<String> imgUrls = hander.getImageUrls(page);
        
        if(imgUrls.size() <= 0) {
        	List<String> urlStrs =  urlHander.getUrls(page);
        	for(String tempUrl : urlStrs) {
        		
        		page = fetcher.fetchHtml(PRX_URL + tempUrl, clintUrl);
        		imgUrls.addAll(hander.getImageUrls(page));
        	}
        }
        System.out.println(imgUrls.toString());
        // ����ͼƬ�������ļ��б�
        List<File> fileList = new ArrayList<File>();
       
        int threadNum = imgUrls.size();
		//��ʼ��countDown
		CountDownLatch threadSignal = new CountDownLatch(threadNum);
      //�����̶����ȵ��̳߳�
      	ExecutorService executor = Executors.newFixedThreadPool(50);
      	int i = 0;
      	for (String url : imgUrls) { //��threadNum���߳�   
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
		} //�ȴ��������߳�ִ����   
		//do work
		System.out.println(Thread.currentThread().getName() + "+++++++����.");
		//finish thread
		executor.shutdown();
		
        //���߳�����
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
	                messageArea.getText() + "/n" + " ������ɣ�������"+i+"��ͼƬ!");
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
			System.out.println(Thread.currentThread().getName() + "��ʼ...");
			//do shomething
			long thredCount = threadsSignal.getCount();
			try {
				System.out.println("��ʼ����" + this.saveFileName + "...");
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
				System.out.println("�����ļ�" + this.saveFileName + "���");
				if(messageArea != null) {
					messageArea.setText(
		                    messageArea.getText() + "/n" + filePath + "//" 
							+  this.saveFileName
		                            + " ������ɣ�");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
	
			}
			//�߳̽���ʱ��������1
			threadsSignal.countDown();  
			System.out.println(Thread.currentThread().getName() + "����. ����"
					+ threadsSignal.getCount() + " ���߳�");
		}
	}
    
    public static void main(String[] args) {
    	Controller controller = new ControllerImpl();
        String testUrl = "http://www.xfjiayuan.com/forum-25-1.html";
        //��������
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


