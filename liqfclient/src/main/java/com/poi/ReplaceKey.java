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
 * ����ȶ�
 * 
 * @author weique.lqf
 * 
 */
public class ReplaceKey {

	public static void replaceAllKey(String filePath, String targetFilePath,
			String writeFilePath) {
		// ���Ľ���
		// ���Ľ���
		CommParseInerface temp1 = new XmlParse(targetFilePath);
		CommParseInerface temp2 = new WordParse(filePath);
		Map<String, String> sourceKey = temp1.parseDate();
		// Ӣ��ԭ��
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
		// �ؼ���ƥ��
		for (Map.Entry<String, String> temp : tempMap.entrySet()) {
			for (Map.Entry<String, String> sourceTemp : sourceKey.entrySet()) {
				// ƥ�����ˣ����滻
				if (sourceTemp.getKey().indexOf(temp.getKey()) >= 0) {
					String tempStr = temp.getValue() + "<p>"
							+ sourceTemp.getValue() + "</p>";
					// System.out.println("tempStr==" + tempStr);
					// ִ���滻����
					strTemp = replaceLongStr(strTemp, temp.getValue(), tempStr);
				}
			}

		}
		// ����µ�xml�ļ�
		try {
			FileWriter fw = new FileWriter(writeFilePath);
			fw.write(strTemp);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ����
	public static void replaceAllKeyCur(String filePath, String targetFilePath,
			String writeFilePath) {
		// ���Ľ���
		CommParseInerface temp1 = new XmlParse(targetFilePath);
		CommParseInerface temp2 = new WordParse(filePath);
		Map<String, String> sourceKey = temp1.parseDate();
		// Ӣ��ԭ��
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
		// �ؼ���ƥ��
		for (Map.Entry<String, String> temp : tempMap.entrySet()) {
			for (Map.Entry<String, String> sourceTemp : sourceKey.entrySet()) {
				// ƥ�����ˣ����滻
				if (sourceTemp.getKey().indexOf(temp.getKey()) >= 0) {
					String tempStr = temp.getValue() + "<p>"
							+ sourceTemp.getValue() + "</p>";
					// System.out.println("tempStr==" + tempStr);
					// ִ���滻����
					strTemp = replaceLongStr(strTemp, temp.getValue(), tempStr);
				}
			}

		}
		// ����µ�xml�ļ�
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
	 * ���Ժ���
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testThread() throws InterruptedException {
		String filePath = "C:\\����\\work\\FindBugs\\FindBugs��������_���İ�.doc";
		String targetFilePath = "C:\\����\\work\\FindBugs\\messages.xml";
		String writeFilePath = "C:\\����\\work\\FindBugs\\messages2.xml";
		// ��ʼ��countDown
		// CountDownLatch threadSignal = new CountDownLatch(2);
		long startTime = System.currentTimeMillis();

		// �����̶����ȵ��̳߳�
		ExecutorService executor = Executors.newFixedThreadPool(2);
		CommParseInerface temp1 = new XmlParse(targetFilePath);
		CommParseInerface temp2 = new WordParse(filePath);

		List<FutureTask<Map<String, String>>> list = new ArrayList<FutureTask<Map<String, String>>>();
		// �����̳߳أ��̳߳صĴ�С��List.sizeû��ɶ��Ȼ�Ĺ�ϵ��һ���ԭ����<=list.size,������˷Ѳ���
		ExecutorService exec = Executors.newFixedThreadPool(2);
		// for (int i = 10; i < 20; i++) {
		// ��������
		FutureTask<Map<String, String>> ft = new FutureTask<Map<String, String>>(
				new CountResult(temp1));
		// ��ӵ�list,�������ȡ�ý��
		list.add(ft);
		// һ�����ύ���̳߳أ���ȻҲ����һ���Ե��ύ���̳߳أ�exec.invokeAll(list);
		exec.submit(ft);
		// }
		FutureTask<Map<String, String>> ft2 = new FutureTask<Map<String, String>>(
				new CountResult(temp2));
		// ��ӵ�list,�������ȡ�ý��
		list.add(ft2);
		// һ�����ύ���̳߳أ���ȻҲ����һ���Ե��ύ���̳߳أ�exec.invokeAll(list);
		exec.submit(ft2);
		// ��ʼͳ�ƽ��
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

		// ������ϣ�һ��Ҫ��ס�ر��̳߳أ����������ͳ��֮ǰ�رգ���Ϊ����̶߳�Ļ�,ִ���еĿ��ܱ����
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
		// �ؼ���ƥ��
		for (Map.Entry<String, String> temp : tempMap.entrySet()) {
			for (Map.Entry<String, String> sourceTemp : sourceKey.entrySet()) {
				// ƥ�����ˣ����滻
				if (sourceTemp.getKey().indexOf(temp.getKey()) >= 0) {
					String tempStr = temp.getValue() + "<p>"
							+ sourceTemp.getValue() + "</p>";
					// System.out.println("tempStr==" + tempStr);
					// ִ���滻����
					strTemp = replaceLongStr(strTemp, temp.getValue(), tempStr);
				}
			}

		}
		// ����µ�xml�ļ�
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
	 * �����ܼ򵥣�����ͳ���¼򵥵ļӷ�����1 ��total)
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
	 * �ı��滻
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
		String filePath = "C:\\����\\work\\FindBugs\\FindBugs��������_���İ�.doc";
		String targetFilePath = "C:\\����\\work\\FindBugs\\messages.xml";
		String writeFilePath = "C:\\����\\work\\FindBugs\\messages2.xml";
		// replaceAllKey(filePath, targetFilePath, writeFilePath);
		ReplaceKey replaceKey = new ReplaceKey();
		//replaceKey.testThread();
		replaceKey.replaceAllKey(filePath, targetFilePath, writeFilePath);
	}
}
