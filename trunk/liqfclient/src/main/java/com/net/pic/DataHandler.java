package com.net.pic;

import java.util.Set;

public interface DataHandler {
	 /**
     * ��ȡ�ĵ��е���ַ
     * 
     * @param html html�ĵ�����
     * @return
     */
    public Set<String> getUrls(StringBuffer html);
    
    /**
     * ��ȡ�ĵ���ͼƬ��ַ
     * 
     * @param html html�ĵ�����
     * @return
     */
    public Set<String> getImageUrls(StringBuffer html);
    
    
}

