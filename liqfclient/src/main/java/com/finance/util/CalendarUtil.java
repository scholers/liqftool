package com.finance.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarUtil {

	/**
	 * 
	 * @author jill
	 * 
	 */
	public static void main(String[] args) {
		//convertWeekByDate(new Date());
		int[] temp = new int[]{2,3,4};
		Calendar cal = Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);
		cal.set(year + 1, 0, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		String imptimeBegin = sdf.format(cal.getTime());
		System.out.println("imptimeBegin==" + imptimeBegin);
		printlnAll(new Date(), cal.getTime(), temp);
	}
	
	
	private static void convertWeekByDate(Date startTime, Date endTime) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		String imptimeBegin = sdf.format(cal.getTime());
		System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		System.out.println("所在周星期日的日期：" + imptimeEnd);

	}

	/**
	 * @param args
	 */
	public static void printlnAll(Date startTime, Date endTime, int[] temp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar tempCal = Calendar.getInstance();// 当前日期
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(startTime);
		String imptimeBegin1 = sdf.format(startDate.getTime());
		System.out.println(imptimeBegin1);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(endTime);
		String endDate1 = sdf.format(endDate.getTime());
		System.out.println(endDate1);
		String tempCal1 = sdf.format(tempCal.getTime());
		System.out.println(tempCal1);
		List<String> supportDates = new ArrayList<String>();
		System.out.println("flagflagv==" + tempCal.before(endDate));;
		System.out.println("flagflagv2==" + tempCal.after(startDate));;
		for (; tempCal.before(endDate) && tempCal.after(startDate); tempCal.add(
				Calendar.DAY_OF_YEAR, 1)) {
			tempCal1 = sdf.format(tempCal.getTime());
			System.out.println(tempCal1);
			for(int iWeek : temp) {
				if((tempCal.get(Calendar.DAY_OF_WEEK) + 1) == iWeek) {
					String imptimeBegin = sdf.format(tempCal.getTime());
					supportDates.add(imptimeBegin);
					System.out.println(imptimeBegin);
				}
			}
		}

		
		System.out.println(supportDates.toString());
		Calendar calendar2 = Calendar.getInstance();// 当前日期
		String imptimeBegin = sdf.format(calendar2.getTime());
		System.out.println("imptimeBegin" + imptimeBegin);
		/*for(String tempDate : supportDates) {
			System.out.println(tempDate);
		}*/
	}
	
	private static void getCalendar(Calendar calendar,Calendar nexty,Calendar nowyear,int iDate) {
		calendar.add(Calendar.DAY_OF_MONTH, calendar.get(iDate));//
		System.out.println("iDate===" + iDate + ";;;;"+ calendar.get(Calendar.YEAR) + "-"
				+ (1 + calendar.get(Calendar.MONTH)) + "-"
				+ calendar.get(Calendar.DATE));
		Calendar c = (Calendar) calendar.clone();
		
		for (; calendar.before(nexty) && calendar.after(nowyear); calendar.add(
				Calendar.DAY_OF_YEAR, iDate)) {
			System.out.println(calendar.get(Calendar.YEAR) + "-"
					+ (1 + calendar.get(Calendar.MONTH)) + "-"
					+ calendar.get(Calendar.DATE));
		}
		
		
	}
}
