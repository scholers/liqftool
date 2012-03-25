package com.net.pic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import javax.swing.JTextArea;

import com.net.pic.ui.HttpClientUrl;
import com.net.pic.ui.MainWin;

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
        /*
        int i = 1;
        for (String url : imgUrls) {
            File file = fetcher.fecthFile(url, imgSaveDir + "//" + i + ".jpg");
            System.out.println(file.getPath()
                    + " 下载完成！");
            messageArea.setText(
                    messageArea.getText() + "/n" + file.getPath()
                            + " 下载完成！");
            mainWin.update(mainWin.getGraphics());
            fileList.add(file);
            i++;
        }*/
        //多线程下载
        CyclicBarrier cb = new CyclicBarrier(imgUrls.size()); 
        int i = 1;
        for (String url : imgUrls) {
        	String newFileName = "00" + i +".jpg";
        	String threadName = "d" + i;
        	new Downloader(threadName, url, newFileName, imgSaveDir, cb, messageArea).start();
        	 //fileList.add(file);
             i++;
        }
        if(messageArea != null)
        messageArea.setText(
                messageArea.getText() + "/n" + " 任务完成，共下载"+i+"个图片!");
        
        return fileList;
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


