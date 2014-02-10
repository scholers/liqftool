package com.zj.biz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.util.DateUtil;

import com.dbpool.ExampleJDBC;
import com.net.pic.util.FileBean;
import com.net.pic.util.FileUtil;
import com.poi.GetAllAccount;
import com.poi.ZhongjiangAcc;

public class ZjInit {
	
	//初始化抽奖用户
	public void initChouJiang() {
		try {
			String strDate = DateUtil.formatDate(new Date(),"yyyyMMdd");
			Set<String> tempEmployeeList = GetAllAccount.readPayLogTxt2("d:\\temp\\pay" + strDate + ".txt", strDate);
			ExampleJDBC exampleJDBC = new ExampleJDBC(true);
			exampleJDBC.insertBatch(tempEmployeeList);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 插入多次抽奖的用户
	 */
	public void mutiChouJiang() {
		try {
			String strDate = "20130525";DateUtil.formatDate(new Date(),"yyyyMMdd");
			Set<String> tempEmployeeList = GetAllAccount.readPayLogTxt("d:\\temp\\pay" + strDate + ".txt", strDate);
			ExampleJDBC exampleJDBC = new ExampleJDBC(false);
			exampleJDBC.insertBatch(tempEmployeeList);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//将T-1天使用信用支付的数据插入到线上库中
	//同时查询出这些用户的手机号码，写入本地
	public static void main(String[] args) {
		ZjInit zjInit = new ZjInit();
		zjInit.initChouJiang();
		/*try {
			String strDate = DateUtil.formatDate(new Date(),"yyyyMMdd");
			Set<String> tempEmployeeList = GetAllAccount.readPayLogTxt("d:\\temp\\pay" + strDate + ".txt", strDate);
			ExampleJDBC exampleJDBC = new ExampleJDBC(false);
			exampleJDBC.insertBatch(tempEmployeeList);
			Map<String, ZhongjiangAcc> tempMap = exampleJDBC.executeQuery(tempEmployeeList);
			List<FileBean> fileList = new ArrayList<FileBean>();
			for (Map.Entry<String, ZhongjiangAcc> m : tempMap.entrySet()) {
				FileBean fileBean = new FileBean();
				fileBean.setFileName(m.getValue().getMobile() +"," + m.getValue().getEmployee());
				fileList.add(fileBean);
			}
			//输出插入的SQL语句
			try {
				String textFileName = "mobile_" + strDate + ".txt";
				FileUtil.toFile(fileList, "d://temp//", textFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//查询手机号码
			//exampleJDBC
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
