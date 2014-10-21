package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author weique.lqf
 *
 */
public class DateUtil {

    public static String getCurDate() {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMDD");
        String strDt = df.format(dt);
        return strDt;
    }

    public static String getFormateDate(String inputDate) {
        return inputDate.substring(0, 10);
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
        } catch (Exception e) {
            System.err.println("iso2gb error・" + e.getMessage());
        }
        return "NULL";
    }

    public static void main(String args[]) {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == Calendar.SUNDAY)
            System.out.println("SUNDAY");
        if (dayofweek == Calendar.MONDAY)
            System.out.println("MONDAY");
        if (dayofweek == Calendar.TUESDAY)
            System.out.println("TUESDAY");
        if (dayofweek == Calendar.WEDNESDAY)
            System.out.println("WEDNESDAY");
        if (dayofweek == Calendar.THURSDAY)
            System.out.println("THURSDAY");
        if (dayofweek == Calendar.FRIDAY)
            System.out.println("FRIDAY");
        if (dayofweek == Calendar.SATURDAY) {
            System.out.println("SATURDAY");
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        //return dayForWeek;
        System.out.println(dayForWeek);
        //
        dayForWeek = 4;
        //账单日+4
        int iBillDate = (dayForWeek + 4) % 7;
        String billingDate = "0" + String.valueOf(iBillDate);
        System.out.println(billingDate);
        //还款日+7
        String billedDate = "0" + String.valueOf(dayForWeek);
        System.out.println(billedDate);
    }

    /**
     * 获取当前属于一周的哪一天, 周日0，周一1，周二2以此类推
     * @return 周几
     */
    public static int getWeekOfDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
    }
}
