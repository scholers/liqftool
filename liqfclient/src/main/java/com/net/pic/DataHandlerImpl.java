package com.net.pic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.net.pic.util.RegexUtil;

public class DataHandlerImpl implements DataHandler {

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
        System.out.println("result==" + result);
        return result;
    }


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
            if (RegexUtil.validateSting(r, "[a-zA-z]+://[^//s]*")) {
            	if(!result.contains(r)) {
            		result.add(r);
            	}
            }
        }

        return result;
    }

}

