package com.poi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.util.DateUtil;


import com.net.pic.util.FileBean;
import com.net.pic.util.FileUtil;


public class GetAllAccount {

	private final static String startStr = ",";

	/**
	 * 
	 * @param fileName
	 * @param tableName
	 */
	public static void readExcel(String fileName, String curDate) throws IOException {
		List<ZhongjiangAcc> rtnList = new ArrayList<ZhongjiangAcc>();
		InputStream is = new FileInputStream(fileName);
		StringBuffer buffer = new StringBuffer();
		String line; // ��������ÿ�ж�ȡ������
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // ��ȡ��һ��
		while (line != null) { // ��� line Ϊ��˵��������
			//System.out.println(line.indexOf(","));
			//if (line.indexOf(startStr) > 0 && line.indexOf(" 10:") > 0) {
				if (line.indexOf(startStr) > 0 ) {
				String[] tempStr = line.split(startStr);
				ZhongjiangAcc zhongjiangAcc = new ZhongjiangAcc();
				for (int i = 0; i < tempStr.length; i++) {
					String tempAcc = tempStr[i];
					
					// ����
					if (i == 0 ) {
						//��ȡ10��֮�������
						zhongjiangAcc.setDate(tempAcc);
					}
					// ֧�����˺�
					if (tempAcc.indexOf("buyerAccountNo=") >= 0) {
						zhongjiangAcc.setAccount(tempAcc.substring(tempAcc
								.indexOf("=") + 1, tempAcc.length()));
						zhongjiangAcc
								.setAccount(zhongjiangAcc.getAccount()
										.substring(
												0,
												zhongjiangAcc.getAccount()
														.length() - 4));

					}
				}
				rtnList.add(zhongjiangAcc);
			}
			line = reader.readLine(); // ��ȡ��һ��
		}
		// ����
		ComparatorAccount comparator = new ComparatorAccount();
		Collections.sort(rtnList, comparator);

		//
		ReadExcelToSQL excelTemp = new ReadExcelToSQL(true);
		List<FileBean> fileList = new ArrayList<FileBean>();
		for (int i = 0; i < rtnList.size(); i++) {
			FileBean fileBean = new FileBean();
			
			ZhongjiangAcc zhongjiangAcc = (ZhongjiangAcc) rtnList.get(i);
			ZhongjiangAcc tempAccount = excelTemp.getRenMap().get(zhongjiangAcc.getAccount().trim());
			StringBuffer strBuf = new StringBuffer();
			//strBuf.append(zhongjiangAcc.getDate() + ",");
			if(tempAccount != null) {
				strBuf.append(tempAccount.getEmployee()+ ",");
				//strBuf.append(tempAccount.getEmail() + ",");
			}
			//strBuf.append(zhongjiangAcc.getAccount());
			
			System.out.println(strBuf.toString());
			fileBean.setFileName(strBuf.toString());
			fileList.add(fileBean);
		}
		//���
		try {
			String textFileName = "zhongjiangAcc_" + curDate + ".txt";
			FileUtil.toFile(fileList, "d://temp//", textFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ��
	 * sellerAccountNo=
	 * @param fileName
	 * @param tableName
	 */
	public static void getTenClock(String fileName, String curDate) throws IOException {
		List<ZhongjiangAcc> rtnList = new ArrayList<ZhongjiangAcc>();
		InputStream is = new FileInputStream(fileName);
		StringBuffer buffer = new StringBuffer();
		String line; // ��������ÿ�ж�ȡ������
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // ��ȡ��һ��
		while (line != null) { // ��� line Ϊ��˵��������
			//System.out.println(line.indexOf(","));
			if (line.indexOf(startStr) > 0 && line.indexOf(" 10:") > 0) {
				//if (line.indexOf(startStr) > 0 ) {
				String[] tempStr = line.split(startStr);
				ZhongjiangAcc zhongjiangAcc = new ZhongjiangAcc();
				for (int i = 0; i < tempStr.length; i++) {
					String tempAcc = tempStr[i];
					
					// ����
					if (i == 0 ) {
						//��ȡ10��֮�������
						zhongjiangAcc.setDate(tempAcc);
					}
					// ֧�����˺�
					if (tempAcc.indexOf("buyerAccountNo=") >= 0) {
						zhongjiangAcc.setAccount(tempAcc.substring(tempAcc
								.indexOf("=") + 1, tempAcc.length()));
						zhongjiangAcc
								.setAccount(zhongjiangAcc.getAccount()
										.substring(
												0,
												zhongjiangAcc.getAccount()
														.length() - 4));

					}
				}
				rtnList.add(zhongjiangAcc);
			}
			line = reader.readLine(); // ��ȡ��һ��
		}
		// ����
		ComparatorAccount comparator = new ComparatorAccount();
		Collections.sort(rtnList, comparator);

		//
		ReadExcelToSQL excelTemp = new ReadExcelToSQL(true);
		List<FileBean> fileList = new ArrayList<FileBean>();
		for (int i = 0; i < rtnList.size(); i++) {
			FileBean fileBean = new FileBean();
			
			ZhongjiangAcc zhongjiangAcc = (ZhongjiangAcc) rtnList.get(i);
			ZhongjiangAcc tempAccount = excelTemp.getRenMap().get(zhongjiangAcc.getAccount().trim());
			StringBuffer strBuf = new StringBuffer();
			strBuf.append(zhongjiangAcc.getDate() + ",");
			if(tempAccount != null) {
				strBuf.append(tempAccount.getEmployee()+ ",");
				strBuf.append(tempAccount.getEmail() + ",");
			}
			strBuf.append(zhongjiangAcc.getAccount());
			
			System.out.println(strBuf.toString());
			fileBean.setFileName(strBuf.toString());
			fileList.add(fileBean);
		}
		//���
		try {
			String textFileName = "zhongjiangAcc_" + curDate + ".txt";
			FileUtil.toFile(fileList, "d://temp//", textFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ��ȡ���̻��Ƿ����̼�
	 * sellerAccountNo=
	 * @param fileName
	 * @param tableName
	 */
	public static void getFiledSellerWeek(String fileName, String curDate) throws IOException {
		List<ZhongjiangAcc> rtnList = new ArrayList<ZhongjiangAcc>();
		InputStream is = new FileInputStream(fileName);
		StringBuffer buffer = new StringBuffer();
		String line; // ��������ÿ�ж�ȡ������
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // ��ȡ��һ��
		while (line != null) { // ��� line Ϊ��˵��������
			//System.out.println(line.indexOf(","));
			//System.out.println(line.toString());
			if (line.indexOf(startStr) > 0 && line.indexOf("forbidCode=012") >= 0
					 && line.indexOf("serviceCode=PAYMENT_CONSULT_AFTER_SIGN") >= 0) {
				String[] tempStr = line.split(startStr);
				ZhongjiangAcc zhongjiangAcc = new ZhongjiangAcc();
				if(line.indexOf("sellerAccountNo") >= 0) {
					zhongjiangAcc.setName(line);
				}
				//boolean isAfter = false;
				//����ƽ̨
				for (int i = 0; i < tempStr.length; i++) {
					String tempAcc = tempStr[i];
					// ����
					if (i == 0 ) {
						zhongjiangAcc.setDate(tempAcc);
					}
					// ֧�����˺�
					if (tempAcc.indexOf("sellerAccountNo=") >= 0) {
						zhongjiangAcc.setAccount(tempAcc.substring(tempAcc
								.indexOf("=") + 1, tempAcc.length()));
						zhongjiangAcc
								.setAccount(zhongjiangAcc.getAccount()
										.substring(
												0,
												zhongjiangAcc.getAccount()
														.length() - 4));

					}
					//if (tempAcc.indexOf("serviceCode=PAYMENT_CONSULT_AFTER_SIGN") >= 0) {
					//	isAfter = true;
					//}
					
					if (tempAcc.indexOf("platformType=") >= 0) {
						zhongjiangAcc.setGoods(tempAcc);
						
					}
					
				}
				rtnList.add(zhongjiangAcc);
				
			}
			line = reader.readLine(); // ��ȡ��һ��
		}
		// ����
		ComparatorAccount comparator = new ComparatorAccount();
		Collections.sort(rtnList, comparator);

		//
		//ReadExcelToSQL excelTemp = new ReadExcelToSQL(true);
		List<FileBean> fileList = new ArrayList<FileBean>();
		for (int i = 0; i < rtnList.size(); i++) {
			FileBean fileBean = new FileBean();
			
			ZhongjiangAcc zhongjiangAcc = (ZhongjiangAcc) rtnList.get(i);
			//ZhongjiangAcc tempAccount = excelTemp.getRenMap().get(zhongjiangAcc.getAccount().trim());
			StringBuffer strBuf = new StringBuffer();
			strBuf.append(zhongjiangAcc.getDate() + ",");
			
			strBuf.append(zhongjiangAcc.getAccount());
			strBuf.append("," + zhongjiangAcc.getGoods());
			//strBuf.append("," + zhongjiangAcc.getName());
			
			System.out.println(strBuf.toString());
			fileBean.setFileName(strBuf.toString());
			fileList.add(fileBean);
		}
		//���
		try {
			String textFileName = "sellerFiled_" + curDate + ".txt";
			FileUtil.toFile(fileList, "d://temp//", textFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ��ȡÿ�콻������
	 * 1.���뵽��̨���ݿ�
	 * 2.��ȡ�û��ֻ����룬������Ӫ������
	 * @param fileName
	 * @param tableName
	 */
	public static Set<String> readPayLogTxt(String fileName, String curDate) throws IOException {
		//֧�����ʺ�
		Set<String> rtnList = new HashSet<String>();
		//Ա������
		Set<String> employSet = new HashSet<String>();
		InputStream is = new FileInputStream(fileName);
		StringBuffer buffer = new StringBuffer();
		String line; // ��������ÿ�ж�ȡ������
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // ��ȡ��һ��
		while (line != null) { // ��� line Ϊ��˵��������
			//System.out.println(line.indexOf(","));
			if (line.indexOf(startStr) > 0) {
				String[] tempStr = line.split(startStr);
				ZhongjiangAcc zhongjiangAcc = new ZhongjiangAcc();
				for (int i = 0; i < tempStr.length; i++) {
					String tempAcc = tempStr[i];
					// ����
					if (i == 0 ) {
						//��ȡ10��֮�������
						zhongjiangAcc.setDate(tempAcc);
					}
					// ֧�����˺�
					if (tempAcc.indexOf("buyerAccountNo=") >= 0) {
						zhongjiangAcc.setAccount(tempAcc.substring(tempAcc
								.indexOf("=") + 1, tempAcc.length()));
						zhongjiangAcc
								.setAccount(zhongjiangAcc.getAccount()
										.substring(
												0,
												zhongjiangAcc.getAccount()
														.length() - 4));

					}
				}
				try {
					rtnList.add(zhongjiangAcc.getAccount().trim());
					
				} catch (Exception ex) {
					
				}
			}
			line = reader.readLine(); // ��ȡ��һ��
		}
		// ����
		//ComparatorAccount comparator = new ComparatorAccount();
		//Collections.sort(rtnList, comparator);

		//
		ReadExcelToSQL excelTemp = new ReadExcelToSQL(true);
		List<FileBean> fileList = new ArrayList<FileBean>();
		
		
		//for (int i = 0; i < rtnList.size(); i++) {
		for (Iterator iter = rtnList.iterator(); iter.hasNext();) {
			// ƴ�Ӳ�������ǰ��
			StringBuffer starSqlTemp = new StringBuffer();
			FileBean fileBean = new FileBean();
			starSqlTemp.append("insert into act_lottery (employee_num, lottery_time, gmt_created, gmt_modified) value(" +
					"");
			
			String strAccount = (String) iter.next();
			ZhongjiangAcc tempAccount = excelTemp.getRenMap().get(strAccount);
			
			if(tempAccount != null) {
				starSqlTemp.append(tempAccount.getEmployee());
				employSet.add(tempAccount.getEmployee());
				starSqlTemp.append(", 0,now(),now());\n");
				fileBean.setFileName(starSqlTemp.toString());
				fileList.add(fileBean);
			}
		}
		//��������SQL���
		try {
			String textFileName = "SqlZiGe_" + curDate + ".txt";
			FileUtil.toFile(fileList, "d://temp//", textFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��ѯ�ֻ����룬������Ӫ���Ͷ���
		
		return employSet;
	}
	
	
	
	/**
	 * ��ȡÿ�콻������
	 * 1.���뵽��̨���ݿ�
	 * 2.��ȡ�û��ֻ����룬������Ӫ������
	 * @param fileName
	 * @param tableName
	 */
	public static Set<String> readPayLogTxt2(String fileName, String curDate) throws IOException {
		//֧�����ʺ�
		Set<String> rtnList = new HashSet<String>();
		//Ա������
		Set<String> employSet = new HashSet<String>();
		InputStream is = new FileInputStream(fileName);
		StringBuffer buffer = new StringBuffer();
		String line; // ��������ÿ�ж�ȡ������
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // ��ȡ��һ��
		while (line != null) { // ��� line Ϊ��˵��������
			//System.out.println(line.indexOf(","));
			if (line.indexOf(startStr) > 0) {
				String[] tempStr = line.split(startStr);
				ZhongjiangAcc zhongjiangAcc = new ZhongjiangAcc();
				for (int i = 0; i < tempStr.length; i++) {
					String tempAcc = tempStr[i];
					// ����
					if (i == 0 ) {
						//��ȡ10��֮�������
						zhongjiangAcc.setDate(tempAcc);
					}
					// ֧�����˺�
					if (tempAcc.indexOf("buyerAccountNo=") >= 0) {
						zhongjiangAcc.setAccount(tempAcc.substring(tempAcc
								.indexOf("=") + 1, tempAcc.length()));
						zhongjiangAcc
								.setAccount(zhongjiangAcc.getAccount()
										.substring(
												0,
												zhongjiangAcc.getAccount()
														.length() - 4));

					}
				}
				try {
					rtnList.add(zhongjiangAcc.getAccount().trim());
					
				} catch (Exception ex) {
					
				}
			}
			line = reader.readLine(); // ��ȡ��һ��
		}
		// ����
		//ComparatorAccount comparator = new ComparatorAccount();
		//Collections.sort(rtnList, comparator);

		//
		ReadExcelToSQL excelTemp = new ReadExcelToSQL(true);
		List<FileBean> fileList = new ArrayList<FileBean>();
		
		
		//for (int i = 0; i < rtnList.size(); i++) {
		for (Iterator iter = rtnList.iterator(); iter.hasNext();) {
			// ƴ�Ӳ�������ǰ��
			StringBuffer starSqlTemp = new StringBuffer();
			FileBean fileBean = new FileBean();
			//starSqlTemp.append("insert into act_lottery (employee_num, lottery_time, gmt_created, gmt_modified) value(" +
			//		"");
			
			String strAccount = (String) iter.next();
			ZhongjiangAcc tempAccount = excelTemp.getRenMap().get(strAccount);
			
			if(tempAccount != null) {
				starSqlTemp.append(tempAccount.getEmployee());
				employSet.add(tempAccount.getEmployee());
				//starSqlTemp.append(", 0,now(),now());\n");
				starSqlTemp.append(", ").append(tempAccount.getEmail()).append("\n");
				starSqlTemp.append(", ").append(tempAccount.getAccount()).append("\n");
				fileBean.setFileName(starSqlTemp.toString());
				fileList.add(fileBean);
			}
		}
		//��������SQL���
		try {
			String textFileName = "SqlZiGe_" + curDate + ".txt";
			FileUtil.toFile(fileList, "d://temp//", textFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��ѯ�ֻ����룬������Ӫ���Ͷ���
		
		return employSet;
	}
	
	
	//����ǩԼ�ɹ���

	/**
	 * 
	 * @param fileName
	 * @param tableName
	 */
	public static void sucessRate(String fileName) throws IOException {
		//serviceCode=PAYMENT_CONSULT_AFTER_SIGN
		//serviceCode=PAYMENT_CONSULT_BEFORE_SIGN
		//����
		Map<String,Integer> totalMap = new HashMap<String,Integer>();
		//�ɹ�
		Map<String,Integer> sucessMap = new HashMap<String,Integer>();
		//ʧ��
		Map<String,Map<String, Integer>> failedMap = new HashMap<String,Map<String, Integer>>();
		//ʧ���̼�
		Map<String, String> filedAccount = new HashMap<String, String>();
		
		int sucessTotal = 0;
		InputStream is = new FileInputStream(fileName);
		StringBuffer buffer = new StringBuffer();
		String line; // ��������ÿ�ж�ȡ������
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // ��ȡ��һ��
		while (line != null) { // ��� line Ϊ��˵��������
			//System.out.println(line.indexOf(","));
			if (line.indexOf(startStr) > 0 ) {
				String[] tempStr = line.split(startStr);
				boolean isTrue = false;
				String curDate = "";
				for (int i = 0; i < tempStr.length; i++) {
					String tempAcc = tempStr[i];
					
					//����
					if(i == 0 && tempAcc != null && tempAcc.length() >= 19) {
						curDate = com.util.DateUtil.getFormateDate(tempAcc);
					} 
					//System.out.print(curDate);
					// ֧�����˺�
					if (tempAcc.indexOf("serviceCode=PAYMENT_CONSULT_AFTER_SIGN") >= 0) {
						if(totalMap.containsKey(curDate)) {
							int total = totalMap.get(curDate);
							total += 1;
							totalMap.remove(curDate);
							totalMap.put(curDate, total);
						} else {
							totalMap.put(curDate, 1);
						}
						
						//ǩԼ����ѯ
						isTrue = true;
					} else {
						//continue;
						//break;
					}
					//ǩԼ��ѯ�ɹ�
					if (isTrue) {
						if(tempAcc.indexOf("forbidDesc=") >= 0) {
							//ǩԼ��ѯ�ɹ�
							if(tempAcc.indexOf("forbidDesc=<null>") >= 0) {
								if(sucessMap.containsKey(curDate)) {
									int total = sucessMap.get(curDate);
									total += 1;
									sucessMap.remove(curDate);
									sucessMap.put(curDate, total);
								} else {
									sucessMap.put(curDate, 1);
								}
							} else {
								String strFailedDesc = tempAcc.substring(tempAcc.indexOf("forbidDesc=") + 11,tempAcc.length() - 1);
								strFailedDesc = com.util.DateUtil.iso2gb(strFailedDesc);
								if(failedMap.containsKey(curDate)) {
									//����ԭ��
									Map<String, Integer> tempMap = failedMap.get(curDate);
									if(tempMap.get(strFailedDesc) != null) {
										int thisDescCount = tempMap.get(strFailedDesc);
										thisDescCount += 1;
										tempMap.remove(strFailedDesc);
										tempMap.put(strFailedDesc, thisDescCount);
										failedMap.remove(curDate);
										failedMap.put(curDate, tempMap);
									} else {
										tempMap.put(strFailedDesc, 1);
										failedMap.put(curDate, tempMap);
									}
									
								} else {
									Map<String, Integer> tempMap = new HashMap<String,Integer>();
									tempMap.put(strFailedDesc, 1);
									failedMap.put(curDate, tempMap);
								}
							}
						}
						
					}
				}
				isTrue = false;
			}
			line = reader.readLine(); // ��ȡ��һ��
		}
		for (Map.Entry<String, Integer> entry : totalMap.entrySet()) {
			   String key = entry.getKey().toString();
			   String value = entry.getValue().toString();
			   System.out.println("totaolkey=" + key + ", value=" + value + 
					   ", sucessvalue=" + sucessMap.get(key) +", failedvalue=" + failedMap.get(key));
		}
		/*for (Map.Entry<String, Integer> entry : sucessMap.entrySet()) {
			   String key = entry.getKey().toString();
			   String value = entry.getValue().toString();
			   System.out.println("sucesskey=" + key + ", value=" + value);
		}
		for (Map.Entry<String, Map<String,Integer>> entry : failedMap.entrySet()) {
			   String key = entry.getKey().toString();
			   String value = entry.getValue().toString();
			   System.out.println("failedkey=" + key + ", value=" + value);
		}*/
	
	}
	
	

	public static void main(String[] args) {
		try {
			String strDate = DateUtil.formatDate(new Date(),"yyyyMMdd");
			//readExcel("d:\\temp\\pay" + strDate + ".txt", strDate);
			//getTenClock("d:\\temp\\20130518.txt", "20130518");
			//getTenClock("d:\\temp\\20130519.txt", "20130519");
			//readPayLogTxt("d:\\temp\\pay" + strDate + ".txt", strDate);
			//sucessRate("d:\\temp\\0524.txt");
			getFiledSellerWeek("d:\\temp\\20130530.txt", "20130530");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
