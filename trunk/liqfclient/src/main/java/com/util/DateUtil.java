package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author weique.lqf
 *
 */
public class DateUtil {

	public static String getCurDate() {
		Date dt = new Date();
		DateFormat   df   =   new   SimpleDateFormat( "yyyyMMDD");
		String strDt = df.format(dt);
		return strDt;
	}
}
