package com.net.pic.util.ui;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.net.pic.ui.HttpClientUrl;
import com.net.pic.util.RegexUtil;

public class HttpClientUrlTest {
	HttpClientUrl clientUrl = null;

	@Test
	public void validateStingTest() {
		String siteUrl = "http://a.xfjiayuan.com/";
		String loginUrl = "/logging.php?action=login";
		String userName = "scholerscn";
		String password = "790521";
		String httpUrl = "http://a.xfjiayuan.com/forum-25-1.html";
		clientUrl = new HttpClientUrl(siteUrl, loginUrl, userName,
				password, null);
		StringBuffer data = new StringBuffer();
		clientUrl.setUrl(httpUrl);
		data.append(clientUrl.parseHtml());

		System.out.println(data.toString());
	}
	
	@Test
	public void validTest2()  {
		String test = "<div class=\"alert_info\"><p>欢迎您回来，scholerscn。现在将转入登录前页面。  </p></div>";
		 // 将字符串解析为html文档
        Document doc = Jsoup.parse(test);

        // 获取img标签
        Elements es = doc.getElementsByTag("div");

        // 获取没一个img标签src的内容，也就是图片地址
        for (Iterator<Element> i = es.iterator(); i.hasNext();) {
            Element e = i.next();
            String r = e.attr("class");
            if (RegexUtil.validateSting(r, "alert_info")) {
            	String message = e.text();
            	if(message.indexOf("scholerscn") >= 0) {
            		System.out.println("Login sucess!");
            	} else {
            		System.out.println("Login failed!");
            	}
            	break;
            }
        }
	}
}
