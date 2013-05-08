package com.poi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
	public static void readExcel(String fileName) throws IOException {
		List<ZhongjiangAcc> rtnList = new ArrayList<ZhongjiangAcc>();
		InputStream is = new FileInputStream(fileName);
		StringBuffer buffer = new StringBuffer();
		String line; // ��������ÿ�ж�ȡ������
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // ��ȡ��һ��
		while (line != null) { // ��� line Ϊ��˵��������
			//System.out.println(line.indexOf(","));
			if (line.indexOf(startStr) > 0 && line.indexOf(" 10:0") > 0) {
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
			String textFileName = "zhongjiangAcc_" + DateUtil.formatDate(new Date(),"yyyy-MM-dd") + ".txt";
			FileUtil.toFile(fileList, "d://temp//", textFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			readExcel("d:\\temp\\3.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
