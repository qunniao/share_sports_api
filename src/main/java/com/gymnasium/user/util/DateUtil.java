package com.gymnasium.user.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static Calendar calendar = Calendar.getInstance();

    public static Integer getSeconds(int days) {
        return 24 * 60 * 60 * days;
    }

    /**
     * 获取当前时间的Unix时间戳
     *
     * @return 时间戳
     */
    public static Integer getTimestamp() {
        long now = System.currentTimeMillis() / 1000;
        return Integer.parseInt(now + "");
    }

    /**
     * 获取指定时间的时间戳
     */
    public static Integer getTimestamp(int year, int month, int day_of_month, int hour_of_day, int minutes, int seconds) {
        calendar.set(year, month, day_of_month, hour_of_day, minutes, seconds);
        long time = calendar.getTimeInMillis() / 1000;
        return Integer.parseInt(time + "");
    }

    /**
     * Unix时间戳转换成指定日期格式
     *
     * @param seconds Unix时间戳
     * @param regex   日期格式
     * @return 转换后的字符串
     */
    public static String timestampToDate(Integer seconds, String regex) {
        return timestampToDate(seconds * 1000L, regex);
    }

    public static String timestampToDate(Long timeMillis, String regex) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(regex);
        Date date = new Date();
        date.setTime(timeMillis);
        return dateFormat.format(date);
    }

    public static String timestampToDate(String seconds, String regex) {
        long longTime = Long.parseLong(seconds);
        return timestampToDate(longTime, regex);
    }

    /**
     * 日期转换成字符串
     *
     * @param date  待转换的日期
     * @param regex 日期格式
     * @return 转换后的字符串
     */
    public static String dateToString(Date date, String regex) {
        if (date == null) {
            return regex.replaceAll("yyyy", "0000").replaceAll("MM", "00").replaceAll("dd", "00").replaceAll("HH", "00").replaceAll("hh", "00").replaceAll("mm", "00").replaceAll("ss", "00");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(regex);
        return dateFormat.format(date);
    }
}
