package com.poi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
		String line; // 用来保存每行读取的内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // 读取第一行
		while (line != null) { // 如果 line 为空说明读完了
			//System.out.println(line.indexOf(","));
			//if (line.indexOf(startStr) > 0 && line.indexOf(" 10:") > 0) {
				if (line.indexOf(startStr) > 0 ) {
				String[] tempStr = line.split(startStr);
				ZhongjiangAcc zhongjiangAcc = new ZhongjiangAcc();
				for (int i = 0; i < tempStr.length; i++) {
					String tempAcc = tempStr[i];
					
					// 日期
					if (i == 0 ) {
						//获取10点之后的数据
						zhongjiangAcc.setDate(tempAcc);
					}
					// 支付宝账号
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
			line = reader.readLine(); // 读取下一行
		}
		// 排序
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
		//输出
		try {
			String textFileName = "zhongjiangAcc_" + curDate + ".txt";
			FileUtil.toFile(fileList, "d://temp//", textFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param fileName
	 * @param tableName
	 */
	public static void getTenClock(String fileName, String curDate) throws IOException {
		List<ZhongjiangAcc> rtnList = new ArrayList<ZhongjiangAcc>();
		InputStream is = new FileInputStream(fileName);
		StringBuffer buffer = new StringBuffer();
		String line; // 用来保存每行读取的内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // 读取第一行
		while (line != null) { // 如果 line 为空说明读完了
			//System.out.println(line.indexOf(","));
			if (line.indexOf(startStr) > 0 && line.indexOf(" 10:") > 0) {
				//if (line.indexOf(startStr) > 0 ) {
				String[] tempStr = line.split(startStr);
				ZhongjiangAcc zhongjiangAcc = new ZhongjiangAcc();
				for (int i = 0; i < tempStr.length; i++) {
					String tempAcc = tempStr[i];
					
					// 日期
					if (i == 0 ) {
						//获取10点之后的数据
						zhongjiangAcc.setDate(tempAcc);
					}
					// 支付宝账号
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
			line = reader.readLine(); // 读取下一行
		}
		// 排序
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
		//输出
		try {
			String textFileName = "zhongjiangAcc_" + curDate + ".txt";
			FileUtil.toFile(fileList, "d://temp//", textFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取每天交易数据
	 * 1.插入到后台数据库
	 * 2.获取用户手机号码，便于运营发短信
	 * @param fileName
	 * @param tableName
	 */
	public static Set<String> readPayLogTxt(String fileName, String curDate) throws IOException {
		//支付宝帐号
		Set<String> rtnList = new HashSet<String>();
		//员工工号
		Set<String> employSet = new HashSet<String>();
		InputStream is = new FileInputStream(fileName);
		StringBuffer buffer = new StringBuffer();
		String line; // 用来保存每行读取的内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // 读取第一行
		while (line != null) { // 如果 line 为空说明读完了
			//System.out.println(line.indexOf(","));
			if (line.indexOf(startStr) > 0) {
				String[] tempStr = line.split(startStr);
				ZhongjiangAcc zhongjiangAcc = new ZhongjiangAcc();
				for (int i = 0; i < tempStr.length; i++) {
					String tempAcc = tempStr[i];
					// 日期
					if (i == 0 ) {
						//获取10点之后的数据
						zhongjiangAcc.setDate(tempAcc);
					}
					// 支付宝账号
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
			line = reader.readLine(); // 读取下一行
		}
		// 排序
		//ComparatorAccount comparator = new ComparatorAccount();
		//Collections.sort(rtnList, comparator);

		//
		ReadExcelToSQL excelTemp = new ReadExcelToSQL(true);
		List<FileBean> fileList = new ArrayList<FileBean>();
		
		
		//for (int i = 0; i < rtnList.size(); i++) {
		for (Iterator iter = rtnList.iterator(); iter.hasNext();) {
			// 拼接插入语句的前段
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
		//输出插入的SQL语句
		try {
			String textFileName = "SqlZiGe_" + curDate + ".txt";
			FileUtil.toFile(fileList, "d://temp//", textFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return employSet;
	}
	
	//计算签约成功率

	/**
	 * 
	 * @param fileName
	 * @param tableName
	 */
	public static void sucessRate(String fileName) throws IOException {
		//serviceCode=PAYMENT_CONSULT_AFTER_SIGN
		//serviceCode=PAYMENT_CONSULT_BEFORE_SIGN
		int total = 0;
		int sucessTotal = 0;
		InputStream is = new FileInputStream(fileName);
		StringBuffer buffer = new StringBuffer();
		String line; // 用来保存每行读取的内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // 读取第一行
		while (line != null) { // 如果 line 为空说明读完了
			//System.out.println(line.indexOf(","));
			if (line.indexOf(startStr) > 0 ) {
				String[] tempStr = line.split(startStr);
				boolean isTrue = false;
				for (int i = 0; i < tempStr.length; i++) {
					String tempAcc = tempStr[i];
					
					// 支付宝账号
					if (tempAcc.indexOf("serviceCode=PAYMENT_CONSULT_AFTER_SIGN") >= 0) {
						total += 1;
						isTrue = true;
					}
					// 支付宝账号
					if (isTrue && tempAcc.indexOf("forbidDesc=<null>") >= 0) {
						sucessTotal += 1;
						
					}
				}
				isTrue = false;
				//rtnList.add(zhongjiangAcc);
			}
			line = reader.readLine(); // 读取下一行
		}
		System.out.println("total=" + total);
		System.out.println("sucessTotal=" + sucessTotal);
		System.out.println("rate=" + sucessTotal/total);
	}

	public static void main(String[] args) {
		try {
			String strDate = DateUtil.formatDate(new Date(),"yyyyMMdd");
			//readExcel("d:\\temp\\pay" + strDate + ".txt", strDate);
			getTenClock("d:\\temp\\" + strDate + ".txt", strDate);
			//readPayLogTxt("d:\\temp\\pay" + strDate + ".txt", strDate);
			//sucessRate("d:\\20130510.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
