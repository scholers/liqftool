package com.poi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.jdom.JDOMException;
import org.junit.Test;

/**
 * 翻译比对
 * 
 * @author weique.lqf
 * 
 */
public class ReplaceKey {

	public static void replaceAllKey(String filePath, String targetFilePath,
			String writeFilePath) {
		// 中文解释
		// 中文解释
		CommParseInerface temp1 = new XmlParse(targetFilePath);
		CommParseInerface temp2 = new WordParse(filePath);
		Map<String, String> sourceKey = temp1.parseDate();
		// 英文原版
		Map<String, String> tempMap = temp2.parseDate();

		StringBuilder oldReplaceStr = new StringBuilder();
		FileReader fr;
		try {
			fr = new FileReader(targetFilePath);
			BufferedReader br = new BufferedReader(fr);
			String tempLine = br.readLine();
			oldReplaceStr.append(tempLine);
			while (tempLine != null) {
				tempLine = br.readLine();
				oldReplaceStr.append(tempLine + "\n");
			}
			br.close();
			fr.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String strTemp = oldReplaceStr.toString();
		// 关键词匹配
		for (Map.Entry<String, String> temp : tempMap.entrySet()) {
			for (Map.Entry<String, String> sourceTemp : sourceKey.entrySet()) {
				// 匹配上了，就替换
				if (sourceTemp.getKey().indexOf(temp.getKey()) >= 0) {
					String tempStr = temp.getValue() + "<p>"
							+ sourceTemp.getValue() + "</p>";
					// System.out.println("tempStr==" + tempStr);
					// 执行替换操作
					strTemp = replaceLongStr(strTemp, temp.getValue(), tempStr);
				}
			}

		}
		// 输出新的xml文件
		try {
			FileWriter fw = new FileWriter(writeFilePath);
			fw.write(strTemp);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 并发
	public static void replaceAllKeyCur(String filePath, String targetFilePath,
			String writeFilePath) {
		// 中文解释
		CommParseInerface temp1 = new XmlParse(targetFilePath);
		CommParseInerface temp2 = new WordParse(filePath);
		Map<String, String> sourceKey = temp1.parseDate();
		// 英文原版
		Map<String, String> tempMap = temp2.parseDate();

		StringBuilder oldReplaceStr = new StringBuilder();
		FileReader fr;
		try {
			fr = new FileReader(targetFilePath);
			BufferedReader br = new BufferedReader(fr);
			String tempLine = br.readLine();
			oldReplaceStr.append(tempLine);
			while (tempLine != null) {
				tempLine = br.readLine();
				oldReplaceStr.append(tempLine + "\n");
			}
			br.close();
			fr.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String strTemp = oldReplaceStr.toString();
		// 关键词匹配
		for (Map.Entry<String, String> temp : tempMap.entrySet()) {
			for (Map.Entry<String, String> sourceTemp : sourceKey.entrySet()) {
				// 匹配上了，就替换
				if (sourceTemp.getKey().indexOf(temp.getKey()) >= 0) {
					String tempStr = temp.getValue() + "<p>"
							+ sourceTemp.getValue() + "</p>";
					// System.out.println("tempStr==" + tempStr);
					// 执行替换操作
					strTemp = replaceLongStr(strTemp, temp.getValue(), tempStr);
				}
			}

		}
		// 输出新的xml文件
		try {
			FileWriter fw = new FileWriter(writeFilePath);
			fw.write(strTemp);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 测试函数
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testThread() throws InterruptedException {
		String filePath = "C:\\快盘\\work\\FindBugs\\FindBugs规则整理_中文版.doc";
		String targetFilePath = "C:\\快盘\\work\\FindBugs\\messages.xml";
		String writeFilePath = "C:\\快盘\\work\\FindBugs\\messages2.xml";
		// 初始化countDown
		// CountDownLatch threadSignal = new CountDownLatch(2);
		long startTime = System.currentTimeMillis();

		// 创建固定长度的线程池
		ExecutorService executor = Executors.newFixedThreadPool(2);
		CommParseInerface temp1 = new XmlParse(targetFilePath);
		CommParseInerface temp2 = new WordParse(filePath);

		List<FutureTask<Map<String, String>>> list = new ArrayList<FutureTask<Map<String, String>>>();
		// 创建线程池，线程池的大小和List.size没有啥必然的关系，一般的原则是<=list.size,多出来浪费不好
		ExecutorService exec = Executors.newFixedThreadPool(2);
		// for (int i = 10; i < 20; i++) {
		// 创建对象
		FutureTask<Map<String, String>> ft = new FutureTask<Map<String, String>>(
				new CountResult(temp1));
		// 添加到list,方便后面取得结果
		list.add(ft);
		// 一个个提交给线程池，当然也可以一次性的提交给线程池，exec.invokeAll(list);
		exec.submit(ft);
		// }
		FutureTask<Map<String, String>> ft2 = new FutureTask<Map<String, String>>(
				new CountResult(temp2));
		// 添加到list,方便后面取得结果
		list.add(ft2);
		// 一个个提交给线程池，当然也可以一次性的提交给线程池，exec.invokeAll(list);
		exec.submit(ft2);
		// 开始统计结果
		Map<String, String> sourceKey = null;
		try {
			sourceKey = list.get(0).get();
		} catch (ExecutionException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Map<String, String> tempMap = null;
		try {
			tempMap = list.get(1).get();
		} catch (ExecutionException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// 处理完毕，一定要记住关闭线程池，这个不能在统计之前关闭，因为如果线程多的话,执行中的可能被打断
		exec.shutdown();

		StringBuilder oldReplaceStr = new StringBuilder();
		FileReader fr;
		try {
			fr = new FileReader(targetFilePath);
			BufferedReader br = new BufferedReader(fr);
			String tempLine = br.readLine();
			oldReplaceStr.append(tempLine);
			while (tempLine != null) {
				tempLine = br.readLine();
				oldReplaceStr.append(tempLine + "\n");
			}
			br.close();
			fr.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String strTemp = oldReplaceStr.toString();
		// 关键词匹配
		for (Map.Entry<String, String> temp : tempMap.entrySet()) {
			for (Map.Entry<String, String> sourceTemp : sourceKey.entrySet()) {
				// 匹配上了，就替换
				if (sourceTemp.getKey().indexOf(temp.getKey()) >= 0) {
					String tempStr = temp.getValue() + "<p>"
							+ sourceTemp.getValue() + "</p>";
					// System.out.println("tempStr==" + tempStr);
					// 执行替换操作
					strTemp = replaceLongStr(strTemp, temp.getValue(), tempStr);
				}
			}

		}
		// 输出新的xml文件
		try {
			FileWriter fw = new FileWriter(writeFilePath);
			fw.write(strTemp);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 这个类很简单，就是统计下简单的加法（从1 到total)
	 * 
	 * @author Administrator
	 * 
	 */
	private class CountResult implements Callable {

		private CommParseInerface commParseInerface = null;

		public CountResult(CommParseInerface commParseInerface) {
			this.commParseInerface = commParseInerface;
		}

		public Object call() throws Exception {
			Map<String, String> tempMap = commParseInerface.parseDate();
			System.out.println(Thread.currentThread().getName() + " tempMap:"
					+ tempMap);
			return tempMap;
		}
	}

	/**
	 * 文本替换
	 * 
	 * @param str
	 * @param fromStr
	 * @param toStr
	 * @return
	 */
	public static String replaceLongStr(String str, String fromStr, String toStr) {
		StringBuilder result = new StringBuilder();
		if (str != null && !str.equals("")) {
			// System.out.println("fromStr==" + fromStr);
			while (str.indexOf(fromStr) >= 0) {
				// System.out.println("str==" + str);
				result.append(str.substring(0, str.indexOf(fromStr)));
				result.append(toStr);
				str = str.substring(str.indexOf(fromStr) + fromStr.length(),
						str.length());

			}
			result.append(str);
		}
		return result.toString();
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		String filePath = "C:\\快盘\\work\\FindBugs\\FindBugs规则整理_中文版.doc";
		String targetFilePath = "C:\\快盘\\work\\FindBugs\\messages.xml";
		String writeFilePath = "C:\\快盘\\work\\FindBugs\\messages2.xml";
		// replaceAllKey(filePath, targetFilePath, writeFilePath);
		ReplaceKey replaceKey = new ReplaceKey();
		//replaceKey.testThread();
		replaceKey.replaceAllKey(filePath, targetFilePath, writeFilePath);
	}
}
