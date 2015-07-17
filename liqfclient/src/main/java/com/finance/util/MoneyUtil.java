package com.finance.util;

import java.math.BigDecimal;

public class MoneyUtil {

	
	public double add(double d,double d2) {
		BigDecimal bigDecimal = new BigDecimal(Double.toString(d)); 
		BigDecimal bigDecimal2 = new BigDecimal(Double.toString(d2)); 

		//加法 
		BigDecimal bigDecimalAdd = bigDecimal.add(bigDecimal2); 
		return bigDecimalAdd.doubleValue(); 
		
	}
	
	public double subtract(double d,double d2) {
		BigDecimal bigDecimal = new BigDecimal(Double.toString(d)); 
		BigDecimal bigDecimal2 = new BigDecimal(Double.toString(d2)); 

		//减法 
		BigDecimal bigDecimalSubtract = bigDecimal.subtract(bigDecimal2); 
		return bigDecimalSubtract.doubleValue(); 

		
	}
	
	public double multiply(double d,double d2) {
		BigDecimal bigDecimal = new BigDecimal(Double.toString(d)); 
		BigDecimal bigDecimal2 = new BigDecimal(Double.toString(d2)); 

		//乘法 
		BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2); 
		return bigDecimalMultiply.doubleValue(); 

		
	}
	
	public double divide(double d,double d2) {
		BigDecimal bigDecimal = new BigDecimal(Double.toString(d)); 
		BigDecimal bigDecimal2 = new BigDecimal(Double.toString(d2)); 

		

		//除法 
		int scale = 4;//保留4位小数 
		BigDecimal bigDecimalDivide = bigDecimal.divide(bigDecimal2, scale, BigDecimal.ROUND_HALF_UP); 
		return bigDecimalDivide.doubleValue(); 
	}
	
	
}
