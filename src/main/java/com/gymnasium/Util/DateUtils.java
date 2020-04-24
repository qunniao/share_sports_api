/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gymnasium.Util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author hejinyi
 */
public class DateUtils {
	// 短日期格式
	public static String DATE_FORMAT = "yyyy-MM-dd";

	// 长日期格式
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static String getYYYYMM(Date d){
	    SimpleDateFormat formatter;
	    formatter = new SimpleDateFormat ("yyyy-MM");
	    String ctime = formatter.format(d);
	    return ctime;
	}

	public static String getYYYY_MM_DD(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (date == null) {
			return null;
		}
		return sf.format(date);
	}
	public static String getYYYY_MM_DD_mm(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (date == null) {
			return null;
		}
		return sf.format(date);
	}

	public static String getYYYYMMDD_CN(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
		if (date == null) {
			return null;
		}
		return sf.format(date);
	}
	public static String getYYYYMMDD_CN2(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if (date == null) {
			return null;
		}
		return sf.format(date);
	}
	public static String getYYYYMMDD_CN3(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");//获取30天前的时间方法
		if (date == null) {
			return null;
		}
		return sf.format(date);
	}
	public static String getYYYYMM_CN2(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		if (date == null) {
			return null;
		}
		return sf.format(date);
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getShortDate(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date stortDate = null;
		try {
			stortDate = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return stortDate;
	}
	/**
	 * 获取现在时间
	 *
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getShortDate_mm(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date stortDate = null;
		try {
			stortDate = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return stortDate;
	}
	/**
	 * 获取时间yyyy-MM
	 * 
	 * @return返回短时间格式 yyyy-MM
	 */
	public static Date getShortMouth(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		Date stortDate = null;
		try {
			stortDate = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return stortDate;
	}

    /**
     * 
     * @return返回短时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static Date getStringToDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date stortDate = null;
        try {
            stortDate = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stortDate;
    }
	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		// ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = null;
		try {
			currentTime_2 = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentTime_2;
	}
	/**
	 * 获取现在月份
	 * 
	 * @return返回短时间格式 yyyy-MM
	 */
	public static Date getNowMouthShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		String dateString = formatter.format(currentTime);
		// ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = null;
		try {
			currentTime_2 = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentTime_2;
	}

	/**
	 * 比较两个时间的天数是不是一样
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean eqDay(Date d1,Date d2){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return (fmt.format(d1)).equals(fmt.format(d2));
	}
	
	/**
	 * 比较两个时间的月份是不是一样
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean eqMouth(Date d1,Date d2){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");
		return (fmt.format(d1)).equals(fmt.format(d2));
	}

	/**
	 * 加天
	 * 
	 * @param
	 * @param n
	 * @return
	 */
	public static Date addDay(Date sDate, int n) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(sDate);
			calendar.add(Calendar.DATE, n);// 增加一天
			return calendar.getTime();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 加月
	 * @param sDate
	 * @param n
	 * @return
	 */
	public static Date addMonth(Date sDate, int n) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(sDate);
			calendar.add(Calendar.MONTH, n);// 增加一月
			return calendar.getTime();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 将日期格式的字符串转换为长整型
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static long convert2long(String date, String format) {
		try {
			if (StringUtils.isNotBlank(date)) {
				if (StringUtils.isBlank(format)) {
					format = DateUtils.TIME_FORMAT;
				}
				SimpleDateFormat sf = new SimpleDateFormat(format);
				return sf.parse(date).getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * 将长整型数字转换为日期格式的字符串
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String convert2String(long time, String format) {
		if (time > 0l) {
			if (StringUtils.isBlank(format)) {
				format = DateUtils.TIME_FORMAT;
			}
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date date = new Date(time);
			return sf.format(date);
		}
		return "";
	}

	/**
	 * 前几周的星期一的日期
	 * @return
	 */
	public static Date getLastWeekSunday(int i){

		Calendar date=Calendar.getInstance(Locale.CHINA);

		date.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天

		date.add(Calendar.WEEK_OF_MONTH,-i);//周数减一，即上周

		date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//日子设为星期天

		return date.getTime();

	}
	/**
	 * 获取当前系统的日期
	 * 
	 * @return
	 */
	public static long curTimeMillis() {
		return System.currentTimeMillis();
	}
	/**
	 * Timestamp转yyyy/MM/dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String convert3String(Timestamp time){
		String tsStr = "";   
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        tsStr = sdf.format(time);
        
		return tsStr;
	}
	/**
	 * Timestamp转yyyy/MM/dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String getTimestampyy_mm_dd_hh_mm_ss(Timestamp time){
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tsStr = sdf.format(time);

		return tsStr;
	}
	/**
	 * Timestamp转yyyy/MM/dd
	 * @param time
	 * @return
	 */
	public static String convert4String(Timestamp time){
		String tsStr = "";   
        DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        tsStr = sdf.format(time);
        
		return tsStr;
	}
	/**
	 * Timestamp转yyyy/MM/dd
	 * @param time
	 * @return
	 */
	public static String convert4String_yyyy(Timestamp time){
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		tsStr = sdf.format(time);

		return tsStr;
	}
	/**
	 * 获取某个月的天数
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取下一天的0点0分
	 * @return
	 */
	public static  String getnextday(){
		DateFormat datef =  new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH,1);
		return datef.format(cal.getTime())+" 00:00:00";
	}
	/**
	 * @Author : Liu Ri Yong
	 * @Description 获取当日的开始时间
	 * @Date : 上午11:31 2018/1/11
	 * @Params :
	 * @Return :
	 */
	public static  String getOneDayStartTime(Date date){
		DateFormat datef =  new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return datef.format(cal.getTime())+" 00:00:00";
	}

	public static Timestamp getDayBeginTimestamp() {
		Date date = new Date();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		Date date2 = new Date(date.getTime() - gc.get(gc.HOUR_OF_DAY) * 60 * 60
				* 1000 - gc.get(gc.MINUTE) * 60 * 1000 - gc.get(gc.SECOND)
				* 1000);
		return new Timestamp(date2.getTime());
	}
	public static void main(String[] args) {
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
////		String ss = convert4String(ts);
////		System.out.println(ss);
//		Date date = DateUtil.getNowMouthShort();
//		System.out.println(date);
////		DateFormat sdf = new SimpleDateFormat("yyyy-MM");
////		String tsStr = sdf.format(ts);
////		System.out.println(tsStr);
//		
////		  Calendar now=Calendar.getInstance();
////		  now.add(Calendar.MINUTE,30);
////		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////		  String dateStr=sdf.format(now.getTimeInMillis());
////		  System.out.println(dateStr);
//		Date start = DateUtil.getShortDate("2017-06-29");
//		System.out.println(start);
//		Date time=getShortDate("2018-02-11 21:46:55");
		Date d1 = DateUtils.getLastWeekSunday(1);
		Date d2 = DateUtils.getLastWeekSunday(2);
		Date d3 = DateUtils.getLastWeekSunday(3);
		Date d4 = DateUtils.getLastWeekSunday(4);
		Date d5 = DateUtils.getLastWeekSunday(5);
		Map m1 = getMondayAndSunday(d1);
		Map m2 = getMondayAndSunday(d2);
		Map m3 = getMondayAndSunday(d3);
		Map m4 = getMondayAndSunday(d4);
		Map m5 = getMondayAndSunday(d5);
		System.out.println(m1.get("MondayTime")+" "+m1.get("SundayTime"));
		System.out.println(m2.get("MondayTime")+" "+m2.get("SundayTime"));
		System.out.println(m3.get("MondayTime")+" "+m3.get("SundayTime"));
		System.out.println(m4.get("MondayTime")+" "+m4.get("SundayTime"));
		System.out.println(m5.get("MondayTime")+" "+m5.get("SundayTime"));
	}

	/**
	 * @Author : Liu Ri Yong
	 * @Description 获取当前月的开始时间和下个月的开始时间
	 * @Date : 下午1:58 2018/1/2
	 * @Params :
	 * @Return :
	 */
	public static Map getStartTimeAndEndTime(){
		Map map =new HashMap();
		Date date=DateUtils.getNowMouthShort();
		Date date2=DateUtils.addMonth(date,1);

		DateFormat datef =  new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String d2=datef.format(cal.getTime())+" 00:00:00";

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		String d3=datef.format(cal2.getTime())+" 00:00:00";
		//开始时间
		map.put("startTime",d2);
		//下个月的开始时间
		map.put("endTime",d3);
		return map;
	}
/**
 * @Author : Liu Ri Yong
 * @Description 获取上一周的开始时间和结束时间
 * @Date : 下午3:56 2018/1/10
 * @Params :
 * @Return :
 */
	public static Map getWeekStartTimeAndEndTime(){
		//获取上周一日期
		Date lastWeekMonday = DateUtils.getLastWeekSunday(1);
		String startTime = DateUtils.getOneDayStartTime(lastWeekMonday);

		//获取本周一日期
		Date NowWeekMonday = DateUtils.getLastWeekSunday(0);
		String endTime = DateUtils.getOneDayStartTime(NowWeekMonday);
		Map map =new HashMap();
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		return map;
	}
	/**
	 * @Author : Liu Ri Yong
	 * @Description 获取昨天的开始时间和结束时间
	 * @Date : 下午3:50 2018/1/10
	 * @Params :
	 * @Return :
	 */
	public static Map getYesterDay(){
		Map map =new HashMap();
		DateFormat datef =  new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH,-1);
		String startTime = datef.format(cal.getTime())+" 00:00:00";
		String endTime =datef.format(cal.getTime())+" 23:59:59";
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		return map;
	}
/**
 * @Author : Liu Ri Yong
 * @Description
 * @Date : 下午12:49 2018/1/11
 * @Params :
 * @Return :
 */

public static Map getStartTimeAndEndTimeFromMonth(int i){
	Map map =new HashMap();
	Date date=DateUtils.getNowMouthShort();
	Date date2=DateUtils.addMonth(date,i);

	DateFormat datef =  new SimpleDateFormat("yyyy-MM-dd");

	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	String d2=datef.format(cal.getTime())+" 00:00:00";

	Calendar cal2 = Calendar.getInstance();
	cal2.setTime(date2);
	String d3=datef.format(cal2.getTime())+" 00:00:00";
	//开始时间
	map.put("startTime",d2);
	//某个月的开始时间
	map.put("endTime",d3);
	return map;
}
/**
 * @Author : Liu Ri Yong
 * @Description 获取指定日期的所以的星期的周一和周日
 * @Date : 下午4:04 2018/2/8
 * @Params :
 * @Return :
 */
public static Map getMondayAndSunday(Date time){
	Map map =new HashMap();
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal = Calendar.getInstance();
	cal.setTime(time);
	//判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
	int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
	if(1 == dayWeek) {
		cal.add(Calendar.DAY_OF_MONTH, -1);
	}
	cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
	int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
	cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
	String imptimeBegin = sdf.format(cal.getTime());

	cal.add(Calendar.DATE, 6);
	String imptimeBegin1 = sdf.format(cal.getTime());
	map.put("MondayTime",imptimeBegin);
	map.put("SundayTime",imptimeBegin1);
	return map;
}
}
