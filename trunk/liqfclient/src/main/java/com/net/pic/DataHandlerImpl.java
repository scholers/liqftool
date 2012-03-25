package com.net.pic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.net.pic.util.RegexUtil;

public class DataHandlerImpl implements DataHandler {

    public List<String> getImageUrls(StringBuffer html) {

        List<String> result = new ArrayList<String>();

        // ���ַ�������Ϊhtml�ĵ�
        Document doc = Jsoup.parse(html.toString());

        // ��ȡimg��ǩ
        Elements es = doc.getElementsByTag("img");

        // ��ȡûһ��img��ǩsrc�����ݣ�Ҳ����ͼƬ��ַ
        for (Iterator<Element> i = es.iterator(); i.hasNext();) {
            Element e = i.next();
            String r = e.attr("file");
            if (RegexUtil.validateSting(r, "http://.*.jpg")) {
                result.add(r);
            }
        }
        
        return result;
    }


    public List<String> getUrls(StringBuffer html) {
        List<String> result = new ArrayList<String>();

        // ���ַ�������Ϊhtml�ĵ�
        Document doc = Jsoup.parse(html.toString());

        // ��ȡa��ǩ
        Elements es = doc.getElementsByTag("a");

        // ��ȡûһ��a��ǩsrc�����ݣ�Ҳ������ַ
        for (Iterator<Element> i = es.iterator(); i.hasNext();) {
            Element e = i.next();
            String r = e.attr("href");
            if (RegexUtil.validateSting(r, "[a-zA-z]+://[^//s]*")) {
                result.add(r);
            }
        }

        return result;
    }

}

