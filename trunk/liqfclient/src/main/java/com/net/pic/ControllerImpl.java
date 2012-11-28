package com.net.pic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import com.net.pic.task.DownPicTask;
import com.net.pic.task.TaskThread;
import com.net.pic.ui.HttpClientUrl;
import com.net.pic.ui.MainWin;
import com.net.pic.util.FileBean;
import com.net.pic.util.FileUtil;

/**
 * 
 * @author weique.lqf
 *
 */
public class ControllerImpl implements Controller {
	private static Logger logger = Logger.getLogger(ControllerImpl.class);
	private MainWin mainWin;
	private JTextArea messageArea;
	private DataFetcher fetcher = new DataFetcherImpl();
	private DataHandler hander = new DataHandlerImpl();
	private DataHandler urlHander = new UrlHandlerImpl();
	private String siteUrl = null;
	private String loginUrl = null;
	private String userName = null;
	private String password = null;

	public ControllerImpl(MainWin mainWin) {
		this.mainWin = mainWin;
		this.messageArea = mainWin.getMessageArea();
	}

	public ControllerImpl(String siteUrl, String loginUrl, String userName,
			String password) {
		this.siteUrl = siteUrl;
		this.loginUrl = loginUrl;
		this.userName = userName;
		this.password = password;

	}

	public List<File> fetchImages(String pageUrl, String imgSaveDir)
			throws MalformedURLException, IOException {
		HttpClientUrl clintUrl = new HttpClientUrl(siteUrl, loginUrl, userName,
				password, null);
		// ��ȡhtmlҳ��
		StringBuffer page = fetcher.fetchHtml(pageUrl, clintUrl);

		// ��ȡ
		Set<String> linkUrls = hander.getImageUrls(page);

		// ����ִ��
		if (linkUrls.size() <= 0) {
			// ��ȡ��ҳ����������ͼƬ���ӵĵ�ַ
			Set<String> urlStrs = urlHander.getUrls(page);
			int threadNum = urlStrs.size();
			if (threadNum <= 0) {
				throw new MalformedURLException("Don't get image urls!");

			}
			// ��ʼ��countDown
			CountDownLatch threadSignal = new CountDownLatch(threadNum);
			// �����̶����ȵ��̳߳�
			ExecutorService executor = Executors.newFixedThreadPool(10);
			for (String tempUrl : urlStrs) { // ��threadNum���߳�
				HttpClientUrl clintUrlTemp = new HttpClientUrl(
						clintUrl.getCookiestore());
				Runnable task = new TaskThread(siteUrl + tempUrl, clintUrlTemp,
						threadSignal, fetcher, hander, linkUrls);
				executor.execute(task);
			}
			try {
				threadSignal.await();
			} catch (InterruptedException e) {
				logger.error(e.fillInStackTrace());
			} // �ȴ��������߳�ִ����
				// do work
			logger.debug(Thread.currentThread().getName() + "+++++++����.");
			// finish thread
			executor.shutdown();
		}
		// ����ͼƬ�������ļ��б�
		List<File> fileList = new ArrayList<File>();
		// ��Ҫ���ص��ļ������б�
		List<FileBean> fileBeanList = new ArrayList<FileBean>();
		Map<String, FileBean> fileMap = new HashMap<String, FileBean>();
		for (String tempUrl : linkUrls) { // ��threadNum���߳�
			FileBean fileBean = new FileBean();
			fileBean.setFileName(tempUrl);
			fileBeanList.add(fileBean);
			fileMap.put(tempUrl, fileBean);
		}

		int threadNum = fileBeanList.size();
		// ������ļ�
		// ������ļ�
		if (threadNum > 0) {
			FileUtil.toFile(fileBeanList, imgSaveDir, "fileList.txt");
		}

		
		// ��ʼ��countDown
		CountDownLatch threadSignal = new CountDownLatch(threadNum);
		// �����̶����ȵ��̳߳�
		ExecutorService executor = Executors.newFixedThreadPool(10);
		/*
		 * for (FileBean fileBean : fileBeanList) { // ��threadNum���߳� String
		 * newFileName = fileBean.getFileName().substring(
		 * fileBean.getFileName().lastIndexOf("/") + 1,
		 * fileBean.getFileName().length()); Runnable task = new
		 * DownPicThread(fileBean.getFileName(), newFileName, imgSaveDir,
		 * threadSignal, messageArea); executor.execute(task); i++; } try {
		 * threadSignal.await(); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block logger.error(e.fillInStackTrace()); } //
		 * �ȴ��������߳�ִ����
		 */
		List<FutureTask<Object>> listObj = new ArrayList<FutureTask<Object>>();
		for (FileBean fileBean : fileBeanList) { // ��threadNum���߳�
			String newFileName = fileBean.getFileName().substring(
					fileBean.getFileName().lastIndexOf("/") + 1,
					fileBean.getFileName().length());
			FutureTask<Object> ft = new FutureTask<Object>(new DownPicTask(
					fileBean.getFileName(), newFileName, imgSaveDir,
					threadSignal, messageArea));
			listObj.add(ft);
			executor.submit(ft);
		}
		int i = 0;
		// ���سɹ����ļ�
		List<FileBean> fileBeanListSuc = new ArrayList<FileBean>();
		for (FutureTask<Object> tempFt : listObj) {
			Map<String, Boolean> tempMap = new HashMap<String,Boolean>();
			try {
				
				tempMap = ((HashMap<String, Boolean>) tempFt
						.get(10, TimeUnit.SECONDS));
				
			} catch (InterruptedException e) {
				logger.error(e.fillInStackTrace());
			} catch (ExecutionException e) {
				logger.error(e.fillInStackTrace());
			} catch (TimeoutException e) {
				logger.error(e.fillInStackTrace());
			} finally {
				for (Map.Entry<String, Boolean> temp : tempMap.entrySet()) {
					String picUrlStr = temp.getKey();
					if (fileMap.containsKey(picUrlStr)
							&& temp.getValue().booleanValue() == true) {
						fileBeanListSuc.add(fileMap.get(picUrlStr));
						i++;
					}
				}
			}
			
		}

		// do work
		// finish thread
		executor.shutdown();

		if (fileBeanListSuc.size() > 0) {
			FileUtil.toFile(fileBeanListSuc, imgSaveDir, "failBeanSucc.txt");
		}
		if (messageArea != null) {
			messageArea.setText(messageArea.getText() + "/n" + " ������ɣ�������" + i
					+ "��ͼƬ!");
		}
		logger.info(fileBeanList);
		return fileList;
	}

	/**
	 * 
	 * @author jill
	 * 
	 */
	public static void main(String[] args) {
		String siteUrl = null;
		String loginUrl = null;
		String password = null;
		String userName = null;
		String fileDir = null;
		String testUrl = null;
		String fileDir2 = null;
		String fileDir3 = null;
		String pageNum = "1";
		if (args != null && args.length > 0) {
			siteUrl = args[0];
			loginUrl = args[1];
			password = args[2];
			userName = args[3];
			fileDir = args[4];
			testUrl = args[5];
			fileDir2 = args[6];
			fileDir3 = args[7];
			pageNum = args[8];
		}
		if (siteUrl == null || siteUrl.length() <= 0) {
			// defaule site url
			//http://www.xfjiayuan.com/logging.php?action=login
			siteUrl = "http://www.xfjiayuan.com/";
		}
		// login url
		if (loginUrl == null || loginUrl.length() <= 0) {
			loginUrl = "logging.php?action=login";
		}
		if (password == null || password.length() <= 0) {
			password = "790521";
		}
		if (userName == null || userName.length() <= 0) {
			userName = "scholers";
		}

		// output dir
		if (fileDir == null || fileDir.length() <= 0) {
			fileDir = "d://testpic//pic6//";
		}
		Controller controller = new ControllerImpl(siteUrl, loginUrl, userName,
				password);
		if (testUrl == null || testUrl.length() <= 0) {
			testUrl = siteUrl + "forum-25-" + pageNum + ".html";
		}
		String testUrl2 = siteUrl + "forum-790-" + pageNum + ".html";
		// --����ԭ��
		String testUrl3 = siteUrl + "forum-882-" + pageNum + ".html";
		String testUrl4 = siteUrl + "forum-300-" + pageNum + ".html";

		if (fileDir2 == null || fileDir2.length() <= 0) {
			fileDir2 = "d://testpic//pic0//";
		}

		if (fileDir3 == null || fileDir3.length() <= 0) {
			fileDir3 = "d://testpic//pic1//";
		}
		// ��������
		try {

			// for(int i = 0; i < 9; i ++) {
			// testUrl = siteUrl + "forum-25-" + 5 + ".html";
			controller.fetchImages(testUrl, fileDir);
			//controller.fetchImages(testUrl2, fileDir);
			//controller.fetchImages(testUrl3, fileDir);
			//controller.fetchImages(testUrl4, fileDir);
			// Thread.sleep(5000);
			// }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error(e.fillInStackTrace());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.fillInStackTrace());

		}
		System.exit(0);

		// �ļ�ת�Ʋ�����ÿ���ļ���ֻ����1000���ļ�
		// FileAccess.batchMove(fileDir, fileDir + "/pic1", 1000);
	}

}
