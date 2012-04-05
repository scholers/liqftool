package com.net.pic;

import java.util.Set;

public interface DataHandler {
	 /**
     * 获取文档中的网址
     * 
     * @param html html文档内容
     * @return
     */
    public Set<String> getUrls(StringBuffer html);
    
    /**
     * 获取文档中图片地址
     * 
     * @param html html文档内容
     * @return
     */
    public Set<String> getImageUrls(StringBuffer html);
    
    
}

