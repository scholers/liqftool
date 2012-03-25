package com.net.pic.ui;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpClientUrl {
	private String url = "http://www.xfjiayuan.com/thread-352084-1-1.html";
	
	// �Ƚ���һ���ͻ���ʵ������ģ��һ�������
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
		// ��ȡcookie֮��
		if (cookieArr != null && cookieArr.length > 0) {
			//String url = "http://www.xfjiayuan.com/thread-352084-1-1.html";

			// ֮���ٽ���һ��Post���������ύˢ�¼����ı�����Ϊ�ύ�Ĳ����϶࣬������Post�������
			PostMethod method = new PostMethod(url);

			// �����ǽ�������ʱ��������Ҫ�õ���Cookie��
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
			 * // �����úõ�Cookie����ģ��Ŀͻ������������ʱ���ͻὫCookieд������ͷ����
			 * client.getState().addCookie(cookieTemp); }
			 */
			client.getState().addCookies(cookieArr);

			// ��ʼ��ѭ��
			// while (true) {
			try {
				// ������Ҫ��ͻ��˷���һ������ֱ�ӽ�PostMethod�����ȥ��
				client.executeMethod(method);
				// byte[] postResonse = method.getResponseBody();
				// �����ǻ�ȡ���صĽ��
				InputStream in = method.getResponseBodyAsStream();
				// FileUtil.toFile(in, null, "a.txt");

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buff = new byte[1024];
				int len = -1;
				while ((len = in.read(buff)) != -1) {
					baos.write(buff, 0, len);
				}
				result = new String(baos.toByteArray());
				// �ͷ���Դ
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
		System.out.println("ʹ�����[" + name + "]��¼");

		// �����URL��ַ���Ҿ�������51job��վ��¼�����ת���ĵ�ַ��������������JavaScript���ύ��URL�Ĳ�������ͬ��վ�����ѷ����ˣ�������ǵ�¼��ˢ�¼�����URL��ַ
		// String url =
		// "http://www.xfjiayuan.com/logging.php?action=login&username=scholers&password=790521";
		String loginUrl = "http://www.xfjiayuan.com/logging.php?action=login";

		// ֮���ٽ���һ��Post���������ύˢ�¼����ı�����Ϊ�ύ�Ĳ����϶࣬������Post�������
		PostMethod method = new PostMethod(loginUrl);

		// ����ľ��ǽ�Ҫ�ύ�ı�����������PostMethod�������棬��name , value �Լ��룡
		method.addParameter("username", name);
		method.addParameter("password", password);
		method.addParameter("loginsubmit", "1");
		// ִ���ύ����
		client.executeMethod(method);

		return client.getState().getCookies();// ��õ�¼���Cookie��
		/*
		 * for (Cookie c : cookies) { cookie += split + c.toString(); split =
		 * ";"; } return cookie;
		 */
	}

}