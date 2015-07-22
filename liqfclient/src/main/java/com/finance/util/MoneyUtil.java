package com.finance.util;

import java.math.BigDecimal;

public class MoneyUtil {
	
	private static int scale = 4;//保留4位小数

	
	public static double add(double d,double d2) {
		BigDecimal bigDecimal = new BigDecimal(Double.toString(d)); 
		BigDecimal bigDecimal2 = new BigDecimal(Double.toString(d2)); 

		//加法 
		BigDecimal bigDecimalAdd = bigDecimal.add(bigDecimal2); 
		return bigDecimalAdd.doubleValue(); 
		
	}
	
	public static double subtract(double d,double d2) {
		BigDecimal bigDecimal = new BigDecimal(Double.toString(d)); 
		BigDecimal bigDecimal2 = new BigDecimal(Double.toString(d2)); 

		//减法 
		BigDecimal bigDecimalSubtract = bigDecimal.subtract(bigDecimal2); 
		return bigDecimalSubtract.doubleValue(); 

		
	}
	
	public static double multiply(double d,double d2) {
		BigDecimal bigDecimal = new BigDecimal(Double.toString(d)); 
		BigDecimal bigDecimal2 = new BigDecimal(Double.toString(d2)); 

		//乘法 
		BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2); 
		return bigDecimalMultiply.doubleValue(); 

		
	}
	
	public static double divide(double d,double d2) {
		BigDecimal bigDecimal = new BigDecimal(Double.toString(d)); 
		BigDecimal bigDecimal2 = new BigDecimal(Double.toString(d2)); 

		

		//除法 
		 
		BigDecimal bigDecimalDivide = bigDecimal.divide(bigDecimal2, scale, BigDecimal.ROUND_HALF_UP); 
		return bigDecimalDivide.doubleValue(); 
	}
	
	
	public static double getRealMoney(double f) {
		BigDecimal   b   =   new   BigDecimal(f);
		return b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	
}
