package com.net.pic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import com.net.pic.ui.MainWin;

public class ControllerImpl implements Controller {
	private MainWin mainWin;
    private JTextArea messageArea;
    private DataFetcher fetcher = new DataFetcherImpl();
    private DataHandler hander = new DataHandlerImpl();

    public ControllerImpl(MainWin mainWin) {
        this.mainWin = mainWin;
        this.messageArea = mainWin.getMessageArea();
    }


    public List<File> fetchImages(String pageUrl, String imgSaveDir)
            throws MalformedURLException, IOException {
        
        // ��ȡhtmlҳ��
        StringBuffer page = fetcher.fetchHtml(pageUrl);

        // ��ȡҳ���еĵ�ַ
        List<String> imgUrls = hander.getImageUrls(page);

        // ����ͼƬ�������ļ��б�
        List<File> fileList = new ArrayList<File>();
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
        }
        
        messageArea.setText(
                messageArea.getText() + "/n" + " ������ɣ�������"+fileList.size()+"��ͼƬ!");
        
        return fileList;
    }

}


