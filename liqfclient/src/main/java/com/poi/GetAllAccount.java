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
	 * 获取：
	 * sellerAccountNo=
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
	 * 获取：商户非法的商家
	 * sellerAccountNo=
	 * @param fileName
	 * @param tableName
	 */
	public static void getFiledSellerWeek(String fileName, String curDate) throws IOException {
		List<ZhongjiangAcc> rtnList = new ArrayList<ZhongjiangAcc>();
		InputStream is = new FileInputStream(fileName);
		StringBuffer buffer = new StringBuffer();
		String line; // 用来保存每行读取的内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // 读取第一行
		while (line != null) { // 如果 line 为空说明读完了
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
				//交易平台
				for (int i = 0; i < tempStr.length; i++) {
					String tempAcc = tempStr[i];
					// 日期
					if (i == 0 ) {
						zhongjiangAcc.setDate(tempAcc);
					}
					// 支付宝账号
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
			line = reader.readLine(); // 读取下一行
		}
		// 排序
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
		//输出
		try {
			String textFileName = "sellerFiled_" + curDate + ".txt";
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
		//查询手机号码，便于运营发送短信
		
		return employSet;
	}
	
	
	
	/**
	 * 获取每天交易数据
	 * 1.插入到后台数据库
	 * 2.获取用户手机号码，便于运营发短信
	 * @param fileName
	 * @param tableName
	 */
	public static Set<String> readPayLogTxt2(String fileName, String curDate) throws IOException {
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
		//输出插入的SQL语句
		try {
			String textFileName = "SqlZiGe_" + curDate + ".txt";
			FileUtil.toFile(fileList, "d://temp//", textFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//查询手机号码，便于运营发送短信
		
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
		//总数
		Map<String,Integer> totalMap = new HashMap<String,Integer>();
		//成功
		Map<String,Integer> sucessMap = new HashMap<String,Integer>();
		//失败
		Map<String,Map<String, Integer>> failedMap = new HashMap<String,Map<String, Integer>>();
		//失败商家
		Map<String, String> filedAccount = new HashMap<String, String>();
		
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
				String curDate = "";
				for (int i = 0; i < tempStr.length; i++) {
					String tempAcc = tempStr[i];
					
					//日期
					if(i == 0 && tempAcc != null && tempAcc.length() >= 19) {
						curDate = com.util.DateUtil.getFormateDate(tempAcc);
					} 
					//System.out.print(curDate);
					// 支付宝账号
					if (tempAcc.indexOf("serviceCode=PAYMENT_CONSULT_AFTER_SIGN") >= 0) {
						if(totalMap.containsKey(curDate)) {
							int total = totalMap.get(curDate);
							total += 1;
							totalMap.remove(curDate);
							totalMap.put(curDate, total);
						} else {
							totalMap.put(curDate, 1);
						}
						
						//签约后咨询
						isTrue = true;
					} else {
						//continue;
						//break;
					}
					//签约咨询成功
					if (isTrue) {
						if(tempAcc.indexOf("forbidDesc=") >= 0) {
							//签约咨询成功
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
									//更新原因
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
			line = reader.readLine(); // 读取下一行
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
