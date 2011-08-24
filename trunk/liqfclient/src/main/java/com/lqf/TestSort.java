package com.lqf;

import java.util.Calendar;

public class TestSort {

	/**
	 * 
	 * getImagePath(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param sellerId
	 * @return
	 * String
	 * @since 1.0.0
	 */
    public String getImagePath(String sellerId) {
    	int number  = 0;
    	int iTemp = sellerId.hashCode();
    	if(iTemp != Integer.MIN_VALUE)
         number = Math.abs(iTemp) % 12;

		return String.valueOf(number);
	}
    
    /**
     * 增加天数
     * @param date
     * @param day
     * @return Date
     */
    public static java.util.Date addDate(java.util.Date date, int day) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
        return calendar.getTime();
    }
    
    
    public static void testMinInteger() {
    	int iTemp = Integer.MIN_VALUE;
    	final int iServer = Math.abs(iTemp) % 10 + 1; 
    	System.out.println(iServer);
    }
    
    public static void main(String[] arges) {
    	testMinInteger();
    }
}
