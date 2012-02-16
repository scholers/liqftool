package com.poi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.junit.Test;

/**
 * 翻译比对
 * 
 * @author weique.lqf
 * 
 */
public class ReplaceKey {

	public  void replaceAllKey(String filePath, String targetFilePath,
			String writeFilePath) {
		// 中文解释
		CommParseInerface xmlSource = new XmlParse(targetFilePath);
		CommParseInerface wordSource = new WordParse(filePath);
		// 中文解释
		Map<String, String> sourceKey = wordSource.parseDate();
		// 英文原版
		Map<String, String> tempMap = xmlSource.parseDate();

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
	public  void replaceAllKeyCur(String filePath, String targetFilePath,
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
	public void mutiThreadRaplace(String filePath, String targetFilePath,
			String writeFilePath) throws InterruptedException {
		
		CommParseInerface temp1 = new WordParse(filePath);
		CommParseInerface temp2 = new XmlParse(targetFilePath);

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
	 * 分布计算
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
	private String replaceLongStr(String str, String fromStr, String toStr) {
		StringBuilder result = new StringBuilder();
		if (str != null && !str.equals("")) {
			if (str.indexOf(fromStr) >= 0) {
				result.append(str.substring(0, str.indexOf(fromStr)));
				//追加中英文的解析
				result.append(toStr.trim());
				String tempEndStr = str.substring(str.indexOf(fromStr) + fromStr.length(),
						str.length());
				if(tempEndStr != null && !tempEndStr.equals("")) {
					result.append(tempEndStr);
				}
			}
			
		}
		return result.toString();
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		String filePath = "d:\\快盘\\work\\FindBugs\\FindBugs规则整理_中文版.doc";
		String targetFilePath = "d:\\快盘\\work\\FindBugs\\messages.xml";
		String writeFilePath = "d:\\快盘\\work\\FindBugs\\messages2.xml";
		// replaceAllKey(filePath, targetFilePath, writeFilePath);
		ReplaceKey replaceKey = new ReplaceKey();
		replaceKey.mutiThreadRaplace(filePath, targetFilePath, writeFilePath);
		//replaceKey.replaceAllKey(filePath, targetFilePath, writeFilePath);
	}
}
