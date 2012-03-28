package com.net.pic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.net.pic.util.RegexUtil;

public class UrlHandlerImpl implements DataHandler {

    public Set<String> getImageUrls(StringBuffer html) {

    	Set<String> result = new HashSet<String>();

        // 将字符串解析为html文档
        Document doc = Jsoup.parse(html.toString());

        // 获取img标签
        Elements es = doc.getElementsByTag("img");

        // 获取没一个img标签src的内容，也就是图片地址
        for (Iterator<Element> i = es.iterator(); i.hasNext();) {
            Element e = i.next();
            String r = e.attr("file");
            if (RegexUtil.validateSting(r, "http://.*.jpg")) {
            	if(!result.contains(r)) {
            		result.add(r);
            	}
            }
        }
        
        return result;
    }


    /**
     * 获取子页面
     * 
     */
    public Set<String> getUrls(StringBuffer html) {
        Set<String> result = new HashSet<String>();

        // 将字符串解析为html文档
        Document doc = Jsoup.parse(html.toString());

        // 获取a标签
        Elements es = doc.getElementsByTag("a");

        // 获取没一个a标签src的内容，也就是网址
        for (Iterator<Element> i = es.iterator(); i.hasNext();) {
            Element e = i.next();
            String r = e.attr("href");
            if (RegexUtil.validateSting(r,  "thread.*.html")) {
                if(!result.contains(r)) {
                	result.add(r);
                }
            }
        }

        return result;
    }

}


