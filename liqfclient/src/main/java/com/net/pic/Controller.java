package com.net.pic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface Controller {
	/**
     * ����ͼƬץȡ
     * @param pageUrl ҪץȡͼƬ����ҳ��ַ
     * @return
     * @throws IOException 
     * @throws MalformedURLException 
     */
    public List<File> fetchImages(String pageUrl,String imgSaveDir) throws MalformedURLException, IOException;
}
