package com.net.pic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.net.pic.ui.HttpClientUrl;

public interface DataFetcher {
	 /**
     * 抓取网页
     * 
     * @param htmlUrl 网页地址
     * @return 
     * @throws IOException 
     * @throws MalformedURLException 
     */
    public StringBuffer fetchHtml(String httpUrl, HttpClientUrl clintUrl) throws MalformedURLException, IOException;
    
    /**
     * 抓取文件
     * 
     * @param fileUrl 文件地址
     * @param fileSavePath 文件保存地址
     * @return
     * @throws IOException 
     * @throws MalformedURLException 
     */
    public File fecthFile(String httpUrl,String fileSavePath) throws MalformedURLException, IOException;
}