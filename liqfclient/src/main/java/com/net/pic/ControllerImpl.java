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
        /*
        int i = 1;
        for (String url : imgUrls) {
            File file = fetcher.fecthFile(url, imgSaveDir + "//" + i + ".jpg");
            System.out.println(file.getPath()
                    + " ������ɣ�");
            messageArea.setText(
                    messageArea.getText() + "/n" + file.getPath()
                            + " ������ɣ�");
            mainWin.update(mainWin.getGraphics());
            fileList.add(file);
            i++;
        }*/
        //���߳�����
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
                messageArea.getText() + "/n" + " ������ɣ�������"+i+"��ͼƬ!");
        
        return fileList;
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


