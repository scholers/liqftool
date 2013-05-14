package com.zj.biz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import org.apache.commons.httpclient.util.DateUtil;

import com.dbpool.ExampleJDBC;
import com.poi.GetAllAccount;

public class ZjInit {

	//每天抽奖数据插入到线上库中
	public static void main(String[] args) {
		try {
			String strDate = DateUtil.formatDate(new Date(),"yyyyMMdd");
			Set<String> tempEmployeeList = GetAllAccount.readPayLogTxt("d:\\temp\\pay" + strDate + ".txt", strDate);
			ExampleJDBC exampleJDBC = new ExampleJDBC();
			exampleJDBC.insertBatch(tempEmployeeList);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
