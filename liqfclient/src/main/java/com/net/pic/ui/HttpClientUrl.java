package com.net.pic.ui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.net.pic.util.RegexUtil;

/**
 * 
 * @author jill
 * 
 */
public class HttpClientUrl {
    private static Logger logger   = Logger.getLogger(HttpClientUrl.class);

    private String        url      = null;
    // 登录url
    private String        loginUrl = null;
    private String        siteUrl  = null;

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    // 用户名称
    private String userName = null;
    // 密码
    private String password = null;

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 先建立一个客户端实例，将模拟一个浏览器
    private DefaultHttpClient client      = null;
    private CookieStore       cookiestore = null;

    public CookieStore getCookiestore() {
        return cookiestore;
    }

    public void setCookiestore(CookieStore cookiestore) {
        this.cookiestore = cookiestore;
    }

    public HttpClientUrl() {
    }

    public HttpClientUrl(CookieStore cookiestore) {
        this.cookiestore = cookiestore;
        client = new DefaultHttpClient();
        client.setCookieStore(cookiestore);
    }

    public HttpClientUrl(String siteUrl, String loginUrl, String userName, String password,
                         String postUrl) {
        this.siteUrl = siteUrl;
        this.loginUrl = loginUrl;
        this.userName = userName;
        this.password = password;
        this.url = postUrl;
        client = new DefaultHttpClient();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String parseHtml() {

        // not login
        if (cookiestore == null) {
            try {
                cookiestore = login();
            } catch (Exception e) {
                logger.error(e.fillInStackTrace());
                return "";
            }
        }
        StringBuilder strBuild = new StringBuilder();
        // 获取cookie之后
        if (cookiestore != null && cookiestore.getCookies() != null
            && cookiestore.getCookies().size() > 0) {
            // 之后再建立一个Post方法请求，提交刷新的表单，因为提交的参数较多，所以用Post请求好了
            HttpGet method = new HttpGet(url);
            try {
                // 这里是要求客户端发送一个请求。直接将PostMethod请求出去。
                HttpResponse response = client.execute(method);
                HttpEntity entity = response.getEntity();
                // 必须要对entity进行处理，否则用同一个httpClient访问其他地址时，会抛出异常。这里是读取返回的content，然后关闭流。
                InputStream is = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String str = "";
                while ((str = br.readLine()) != null) {
                    strBuild.append(str);
                }
                br.close();
                is.close();
                client.getConnectionManager().shutdown(); // 关闭这个httpclient
            } catch (Exception ex) {
                logger.error(ex.fillInStackTrace());
            }
        }
        return strBuild.toString();
    }

    /**
     * 登录验证，并且构造cookie
     * 
     * @param name
     * @param password
     * @param client
     * @return
     * @throws Exception
     */
    private CookieStore login() throws Exception {
        System.out.println("使用马甲[" + getUserName() + "]登录");
        // 登录请求
        String siteLoginUrl = getSiteUrl() + getLoginUrl();
        StringBuilder strBuild = new StringBuilder();
        /*// 得到login formhash
        HttpGet httpget = new HttpGet(siteLoginUrl);
        HttpResponse response = client.execute(httpget);
        HttpEntity entity = response.getEntity();
        
        // 输出页面内容
        if (entity != null) {
            // String charset = EntityUtils.getContentCharSet(entity);
            InputStream is = entity.getContent();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                strBuild.append(line);
            }
            is.close();
        }
        int pos = strBuild.indexOf("name=\"formhash\" value=");
        if (pos < 0) {
            throw new Exception("Get formhash failed!!!");
        }
        // 找出这个 formhash 的内容，这是登录用的 formhash
        String loginFormhash = strBuild.substring(pos + 23, pos + 23 + 8);
        System.out.println(loginFormhash);
        */
        //create login parames
        StringBuilder paramsBuilder = new StringBuilder(siteLoginUrl);
        paramsBuilder.append("&loginsubmit=yes").append("&loginfield=username")
            .append("&username=").append(getUserName()).append("&password=").append(getPassword());
        //.append("&formhash=").append(loginFormhash);
        // 开始登录
        HttpPost httpost = new HttpPost(paramsBuilder.toString());

        HttpResponse response = client.execute(httpost);
        HttpEntity entity = response.getEntity(); // 获得HttpEntity
        // login is ok?
        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println("Login form get: " + response.getStatusLine());

            InputStream is = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            strBuild = new StringBuilder();
            while ((str = br.readLine()) != null) {
                strBuild.append(str);
            }
            is.close();
            br.close();

            // 将字符串解析为html文档
            Document doc = Jsoup.parse(strBuild.toString());

            // 获取img标签
            Elements es = doc.getElementsByTag("div");
            int j = 0;
            // 获取没一个img标签src的内容，也就是图片地址
            for (Iterator<Element> i = es.iterator(); i.hasNext();) {
                Element e = i.next();
                String r = e.attr("class");
                if (RegexUtil.validateSting(r, "alert_info")) {
                    j++;
                    String message = e.text();
                    if (message.indexOf(getUserName()) >= 0) {
                        logger.info("Login sucess!");
                    } else {
                        logger.info(message);
                        throw new Exception("Login failed!");
                    }
                    break;
                }
            }
            if (j == 0) {
                // throw new Exception("Login failed!");
            }
            // 必须要对entity进行处理，否则用同一个httpClient访问其他地址时，会抛出异常。这里是销毁返回的content
            if (entity != null) {
                EntityUtils.consume(entity);
            }

            return client.getCookieStore();
        } else {
            return null;
        }

    }
}