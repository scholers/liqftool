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
	// ��¼url
	private String loginUrl = null;
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
		// ��ȡcookie֮��
		if (cookieArr != null && cookieArr.length > 0) {
			// ֮���ٽ���һ��Post���������ύˢ�¼����ı�����Ϊ�ύ�Ĳ����϶࣬������Post�������
			PostMethod method = new PostMethod(url);
			client.getState().addCookies(cookieArr);

			// ��ʼ��ѭ��
			// while (true) {
			try {
				// ������Ҫ��ͻ��˷���һ������ֱ�ӽ�PostMethod�����ȥ��
				client.executeMethod(method);
				// �����ǻ�ȡ���صĽ��
				InputStream in = method.getResponseBodyAsStream();
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
			} catch (Exception ex) {
				logger.error(ex.fillInStackTrace());
			}
		}
		return result;
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
	private Cookie[] login(HttpClient client) throws Exception {
		System.out.println("ʹ�����[" + getUserName() + "]��¼");

		// �����URL��ַ���Ҿ�������51job��վ��¼�����ת���ĵ�ַ��������������JavaScript���ύ��URL�Ĳ�������ͬ��վ�����ѷ����ˣ�������ǵ�¼��ˢ�¼�����URL��ַ
		String loginUrl = getLoginUrl();

		// ֮���ٽ���һ��Post���������ύˢ�¼����ı�����Ϊ�ύ�Ĳ����϶࣬������Post�������
		PostMethod method = new PostMethod(loginUrl);

		// ����ľ��ǽ�Ҫ�ύ�ı�����������PostMethod�������棬��name , value �Լ��룡
		method.addParameter("username", getUserName());
		method.addParameter("password", getPassword());
		method.addParameter("loginsubmit", "1");
		// ִ���ύ����
		client.executeMethod(method);

		return client.getState().getCookies();// ��õ�¼���Cookie��
	}

}