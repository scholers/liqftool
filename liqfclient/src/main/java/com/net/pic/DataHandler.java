package com.net.pic;

import java.util.List;

public interface DataHandler {
	 /**
     * ��ȡ�ĵ��е���ַ
     * 
     * @param html html�ĵ�����
     * @return
     */
    public List<String> getUrls(StringBuffer html);
    
    /**
     * ��ȡ�ĵ���ͼƬ��ַ
     * 
     * @param html html�ĵ�����
     * @return
     */
    public List<String> getImageUrls(StringBuffer html);
    
    
}

