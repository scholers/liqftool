package com.poi;

import java.util.Comparator;

public class ComparatorAccount  implements Comparator{

	 public int compare(Object arg0, Object arg1) {
		 ZhongjiangAcc account0=(ZhongjiangAcc)arg0;
		 ZhongjiangAcc account1=(ZhongjiangAcc)arg1;

	   //首先比较年龄，如果年龄相同，则比较名字

	  int flag = account0.getDate().compareTo(account1.getDate());
	  return flag;
	 }
}
