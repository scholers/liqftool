package com.poi;

import java.util.Comparator;

public class ComparatorAccount  implements Comparator{

	 public int compare(Object arg0, Object arg1) {
		 ZhongjiangAcc account0=(ZhongjiangAcc)arg0;
		 ZhongjiangAcc account1=(ZhongjiangAcc)arg1;

	   //���ȱȽ����䣬���������ͬ����Ƚ�����

	  int flag = account0.getDate().compareTo(account1.getDate());
	  return flag;
	 }
}
