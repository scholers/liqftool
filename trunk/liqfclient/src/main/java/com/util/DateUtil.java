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
	
	
	public static String getFormateDate(String inputDate) {
		return inputDate.substring(0,10);
	}
	
	/**
	 * 
	 * @param qs
	 * @return
	 */
	public static String iso2gb(String qs) {  
	    try {  
	        if (qs == null)  
	            return "NULL";  
	        else {  
	            return new String(qs.getBytes("GBK"), "utf-8");  
	        }  
	    }  
	    catch (Exception e) {  
	        System.err.println("iso2gb error・" + e.getMessage());  
	    }  
	    return "NULL";  
	}  
}
