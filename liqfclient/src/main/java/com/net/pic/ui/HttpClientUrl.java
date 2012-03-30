package com.net.pic.ui;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author jill
 *
 */
public class HttpClientUrl {
	private static Log logger = LogFactory.getLog(HttpClientUrl.class);

	private String url = null;
	// 登录url
	private String loginUrl = null;
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
	private HttpClient client = new HttpClient();
	private Cookie[] cookieArr = null;

	public Cookie[] getCookieArr() {
		return cookieArr;
	}

	public void setCookieArr(Cookie[] cookieArr) {
		this.cookieArr = cookieArr;
	}

	/**
	 * 8 * @param args 9 * @throws Exception 10
	 */
	public static void main(String[] args) {

	}

	public HttpClientUrl() {
	}

	public HttpClientUrl(String loginUrl, String userName, String password, String postUrl) {
		this.loginUrl = loginUrl;
		this.userName = userName;
		this.password = password;
		this.url = postUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String parseHtml() {

		// not login
		if (cookieArr == null) {
			try {
				cookieArr = login(client);
			} catch (Exception e) {
				logger.error(e.fillInStackTrace());
			}
		}
		String result = "";
		// 获取cookie之后
		if (cookieArr != null && cookieArr.length > 0) {
			// 之后再建立一个Post方法请求，提交刷新简历的表单，因为提交的参数较多，所以用Post请求好了
			PostMethod method = new PostMethod(url);
			client.getState().addCookies(cookieArr);

			// 开始死循环
			// while (true) {
			try {
				// 这里是要求客户端发送一个请求。直接将PostMethod请求出去。
				client.executeMethod(method);
				// 下面是获取返回的结果
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buff = new byte[1024];
				int len = -1;
				while ((len = in.read(buff)) != -1) {
					baos.write(buff, 0, len);
				}
				result = new String(baos.toByteArray());
				// 释放资源
				in.close();
				baos.close();
			} catch (Exception ex) {
				logger.error(ex.fillInStackTrace());
			}
		}
		return result;
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
	private Cookie[] login(HttpClient client) throws Exception {
		System.out.println("使用马甲[" + getUserName() + "]登录");

		// 这个是URL地址，我经过分析51job网站登录后的跳转到的地址，并分析得它在JavaScript里提交的URL的参数，不同网站就自已分析了，这个就是登录后刷新简历的URL地址
		String loginUrl = getLoginUrl();

		// 之后再建立一个Post方法请求，提交刷新简历的表单，因为提交的参数较多，所以用Post请求好了
		PostMethod method = new PostMethod(loginUrl);

		// 下面的就是将要提交的表单的数据填入PostMethod对象里面，以name , value 对加入！
		method.addParameter("username", getUserName());
		method.addParameter("password", getPassword());
		method.addParameter("loginsubmit", "1");
		// 执行提交任务
		client.executeMethod(method);

		return client.getState().getCookies();// 获得登录后的Cookie列
	}

}