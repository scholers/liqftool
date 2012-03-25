package com.net.pic.ui;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientUrl {
	private String url = "http://www.xfjiayuan.com/thread-352084-1-1.html";
	
	// 先建立一个客户端实例，将模拟一个浏览器
	private HttpClient client = new HttpClient();
	private Cookie[] cookieArr = null;
	/**
	 * 8 * @param args 9 * @throws Exception 10
	 */
	public static void main(String[] args) {
		String url = "http://www.xfjiayuan.com/thread-352084-1-1.html";
		HttpClientUrl test = new HttpClientUrl(url);
		
		test.parseHtml();
	}
	public HttpClientUrl() {
	}
	
	public HttpClientUrl(String postUrl) {
		this.url = postUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String parseHtml() {
		
		//not login
		if(cookieArr == null) {
			try {
				cookieArr = login("scholerscn", "790521", client);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String result = "";
		// 获取cookie之后
		if (cookieArr != null && cookieArr.length > 0) {
			//String url = "http://www.xfjiayuan.com/thread-352084-1-1.html";

			// 之后再建立一个Post方法请求，提交刷新简历的表单，因为提交的参数较多，所以用Post请求好了
			PostMethod method = new PostMethod(url);

			// 这里是建立请求时服务器需要用到的Cookie。
			/*
			 * String[][] cookies = new String[][] { { "cnzz_a2181640", "74" },
			 * { "sin2181640", "none" }, { "ltime", "1332645008469" }, {
			 * "rtime", "0" }, { "cnzz_eid", "44738346-1332603652-" }, {
			 * "smile", "1D1" }, { "McE_oldtopics", "D353866D" }, { "McE_sid",
			 * "1QZ3m3" }, { "McE_visitedfid", "25" } }; for (int i = 0; i <
			 * cookies.length; i++) { Cookie cookieTemp = new
			 * Cookie(".xfjiayuan.com", cookies[i][0], cookies[i][1], "/", null,
			 * false);
			 * 
			 * // 将设置好的Cookie加入模拟的客户端里。当请求发生时，就会将Cookie写进请求头里了
			 * client.getState().addCookie(cookieTemp); }
			 */
			client.getState().addCookies(cookieArr);

			// 开始死循环
			// while (true) {
			try {
				// 这里是要求客户端发送一个请求。直接将PostMethod请求出去。
				client.executeMethod(method);
				// byte[] postResonse = method.getResponseBody();
				// 下面是获取返回的结果
				InputStream in = method.getResponseBodyAsStream();
				// FileUtil.toFile(in, null, "a.txt");

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
				// System.out.println("result=" + result);
			} catch (Exception ex) {
				ex.fillInStackTrace();
			}
		}
		return result;
	}

	private  Cookie[] login(String name, String password,
			org.apache.commons.httpclient.HttpClient client) throws Exception {
		System.out.println("使用马甲[" + name + "]登录");

		// 这个是URL地址，我经过分析51job网站登录后的跳转到的地址，并分析得它在JavaScript里提交的URL的参数，不同网站就自已分析了，这个就是登录后刷新简历的URL地址
		// String url =
		// "http://www.xfjiayuan.com/logging.php?action=login&username=scholers&password=790521";
		String loginUrl = "http://www.xfjiayuan.com/logging.php?action=login";

		// 之后再建立一个Post方法请求，提交刷新简历的表单，因为提交的参数较多，所以用Post请求好了
		PostMethod method = new PostMethod(loginUrl);

		// 下面的就是将要提交的表单的数据填入PostMethod对象里面，以name , value 对加入！
		method.addParameter("username", name);
		method.addParameter("password", password);
		method.addParameter("loginsubmit", "1");
		// 执行提交任务
		client.executeMethod(method);

		return client.getState().getCookies();// 获得登录后的Cookie列
		/*
		 * for (Cookie c : cookies) { cookie += split + c.toString(); split =
		 * ";"; } return cookie;
		 */
	}

}