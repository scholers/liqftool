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

        // ���ַ�������Ϊhtml�ĵ�
        Document doc = Jsoup.parse(html.toString());

        // ��ȡimg��ǩ
        Elements es = doc.getElementsByTag("img");

        // ��ȡûһ��img��ǩsrc�����ݣ�Ҳ����ͼƬ��ַ
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
     * ��ȡ��ҳ��
     * 
     */
    public Set<String> getUrls(StringBuffer html) {
        Set<String> result = new HashSet<String>();

        // ���ַ�������Ϊhtml�ĵ�
        Document doc = Jsoup.parse(html.toString());

        // ��ȡa��ǩ
        Elements es = doc.getElementsByTag("a");

        // ��ȡûһ��a��ǩsrc�����ݣ�Ҳ������ַ
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


