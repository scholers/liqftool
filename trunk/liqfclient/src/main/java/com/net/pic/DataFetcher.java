package com.net.pic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.net.pic.ui.HttpClientUrl;

public interface DataFetcher {
	 /**
     * ץȡ��ҳ
     * 
     * @param htmlUrl ��ҳ��ַ
     * @return 
     * @throws IOException 
     * @throws MalformedURLException 
     */
    public StringBuffer fetchHtml(String httpUrl, HttpClientUrl clintUrl) throws MalformedURLException, IOException;
    
    /**
     * ץȡ�ļ�
     * 
     * @param fileUrl �ļ���ַ
     * @param fileSavePath �ļ������ַ
     * @return
     * @throws IOException 
     * @throws MalformedURLException 
     */
    public File fecthFile(String httpUrl,String fileSavePath) throws MalformedURLException, IOException;
}