package com.net.pic.ui;

import java.io.BufferedReader;
import java.io.IOException;
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

import com.finance.enums.FinanceTypeEnum;
import com.finance.enums.SubTypeEnum;
import com.net.pic.util.RegexUtil;

/**
 * 
 * @author jill
 * 
 */
public class HttpClientUrl {
	private static Logger logger = Logger.getLogger(HttpClientUrl.class);

	private FinanceTypeEnum type;
	private SubTypeEnum subType;
	private String url = null;
	// ��¼url
	private String loginUrl = null;
	private String siteUrl = null;

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	// �û�����
	private String userName = null;
	// ����
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

	// �Ƚ���һ���ͻ���ʵ������ģ��һ�������
	private DefaultHttpClient client = null;
	private CookieStore cookiestore = null;

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

	public HttpClientUrl(String siteUrl, String loginUrl, String userName,
			String password, String postUrl) {
		this.siteUrl = siteUrl;
		this.loginUrl = loginUrl;
		this.userName = userName;
		this.password = password;
		this.url = postUrl;
		client = new DefaultHttpClient();
	}

	/**
	 * 
	 * 
	 * @param siteUrl
	 * @param loginUrl
	 * @param userName
	 * @param password
	 * @param postUrl
	 */
	public HttpClientUrl(String siteUrl, String postUrl) {
		this.siteUrl = siteUrl;
		this.url = postUrl;
		client = new DefaultHttpClient();
	}

	public HttpClientUrl(String siteUrl, FinanceTypeEnum type,
			SubTypeEnum subType) {
		this.siteUrl = siteUrl;
		this.type = type;
		this.subType = subType;
		client = new DefaultHttpClient();
	}

	public FinanceTypeEnum getType() {
		return type;
	}

	public void setType(FinanceTypeEnum type) {
		this.type = type;
	}

	public SubTypeEnum getSubType() {
		return subType;
	}

	public void setSubType(SubTypeEnum subType) {
		this.subType = subType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String parseHtml() {
		if (loginUrl != null && !loginUrl.equals("")) {
			return parserHtmlByLogin();
		} else {
			try {
				return parserHtmlNotLogin();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}

	}

	private String parserHtmlNotLogin() throws IOException {
		// ��¼����
		String siteLoginUrl = getSiteUrl();
		StringBuilder strBuild = new StringBuilder();
		StringBuilder paramsBuilder = new StringBuilder(siteLoginUrl);
		// ��ʼ��¼
		HttpPost httpost = new HttpPost(paramsBuilder.toString());

		HttpResponse response = client.execute(httpost);
		HttpEntity entity = response.getEntity(); // ���HttpEntity
		// login is ok?
		if (response.getStatusLine().getStatusCode() == 200) {

			InputStream is = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String str = "";
			strBuild = new StringBuilder();
			while ((str = br.readLine()) != null) {
				strBuild.append(str);
			}
			is.close();
			br.close();

			// ���ַ�������Ϊhtml�ĵ�
			Document doc = Jsoup.parse(strBuild.toString());
			
			String message = null;
			if (type == FinanceTypeEnum.IN && subType == SubTypeEnum.PRIVATE) {
				// span
				Elements es = doc.getElementsByTag("span");
				int j = 0;
				// <span class="fl" style="margin:0 8px"> 0.8340
				// </span>
				
				for (Iterator<Element> i = es.iterator(); i.hasNext();) {
					Element e = i.next();
					String spanStr = e.toString();
					if (spanStr.indexOf("class=\"fl\" style=\"margin:0 8px\"") >= 0) {
						j++;
						message = e.text();
						System.out.println("��ǰ��ֵ��" + message);

						break;
					}
				}
			} else if(type == FinanceTypeEnum.IN && subType == SubTypeEnum.PUBLIC) {
				// span
				Elements es = doc.getElementsByClass("cRed");
				int j = 0;
				// div
				// <div class="cRed">
				// 0.8920
				// </div>
				
				for (Iterator<Element> i = es.iterator(); i.hasNext();) {
					Element e = i.next();
					String spanStr = e.toString();
					if (spanStr.indexOf("<div class=\"cRed\">") >= 0) {
						j++;
						message = e.ownText();
						System.out.println("fund value is" + message);

						break;
					}
				}
			}

			if (entity != null) {
				EntityUtils.consume(entity);
			}
			return message;
		} else {
			return null;
		}

	}

	private String parserHtmlByLogin() {
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
		// ��ȡcookie֮��
		if (cookiestore != null && cookiestore.getCookies() != null
				&& cookiestore.getCookies().size() > 0) {
			// ֮���ٽ���һ��Post���������ύˢ�µı�����Ϊ�ύ�Ĳ����϶࣬������Post�������
			HttpGet method = new HttpGet(url);
			try {
				// ������Ҫ��ͻ��˷���һ������ֱ�ӽ�PostMethod�����ȥ��
				HttpResponse response = client.execute(method);
				HttpEntity entity = response.getEntity();
				// ����Ҫ��entity���д���������ͬһ��httpClient����������ַʱ�����׳��쳣�������Ƕ�ȡ���ص�content��Ȼ��ر�����
				InputStream is = entity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						is, "utf-8"));
				String str = "";
				while ((str = br.readLine()) != null) {
					strBuild.append(str);
				}
				br.close();
				is.close();
				client.getConnectionManager().shutdown(); // �ر����httpclient
			} catch (Exception ex) {
				logger.error(ex.fillInStackTrace());
			}
		}
		return strBuild.toString();
	}

	/**
	 * ��¼��֤�����ҹ���cookie
	 * 
	 * @param name
	 * @param password
	 * @param client
	 * @return
	 * @throws Exception
	 */
	private CookieStore login() throws Exception {
		System.out.println("ʹ�����[" + getUserName() + "]��¼");
		// ��¼����
		String siteLoginUrl = getSiteUrl() + getLoginUrl();
		StringBuilder strBuild = new StringBuilder();
		/*
		 * // �õ�login formhash HttpGet httpget = new HttpGet(siteLoginUrl);
		 * HttpResponse response = client.execute(httpget); HttpEntity entity =
		 * response.getEntity();
		 * 
		 * // ���ҳ������ if (entity != null) { // String charset =
		 * EntityUtils.getContentCharSet(entity); InputStream is =
		 * entity.getContent();
		 * 
		 * BufferedReader br = new BufferedReader(new InputStreamReader(is));
		 * String line = null; while ((line = br.readLine()) != null) {
		 * strBuild.append(line); } is.close(); } int pos =
		 * strBuild.indexOf("name=\"formhash\" value="); if (pos < 0) { throw
		 * new Exception("Get formhash failed!!!"); } // �ҳ���� formhash
		 * �����ݣ����ǵ�¼�õ� formhash String loginFormhash =
		 * strBuild.substring(pos + 23, pos + 23 + 8);
		 * System.out.println(loginFormhash);
		 */
		// create login parames
		StringBuilder paramsBuilder = new StringBuilder(siteLoginUrl);
		paramsBuilder.append("&loginsubmit=yes").append("&loginfield=username")
				.append("&username=").append(getUserName())
				.append("&password=").append(getPassword());
		// .append("&formhash=").append(loginFormhash);
		// ��ʼ��¼
		HttpPost httpost = new HttpPost(paramsBuilder.toString());

		HttpResponse response = client.execute(httpost);
		HttpEntity entity = response.getEntity(); // ���HttpEntity
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

			// ���ַ�������Ϊhtml�ĵ�
			Document doc = Jsoup.parse(strBuild.toString());

			// ��ȡimg��ǩ
			Elements es = doc.getElementsByTag("div");
			int j = 0;
			// ��ȡûһ��img��ǩsrc�����ݣ�Ҳ����ͼƬ��ַ
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
			// ����Ҫ��entity���д���������ͬһ��httpClient����������ַʱ�����׳��쳣�����������ٷ��ص�content
			if (entity != null) {
				EntityUtils.consume(entity);
			}

			return client.getCookieStore();
		} else {
			return null;
		}

	}
}