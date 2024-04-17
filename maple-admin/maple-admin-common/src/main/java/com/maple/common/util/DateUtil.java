package com.maple.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间处理工具类
 *
 * @author 笑小枫
 * @date 2022/8/15
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
public class DateUtil {
    private DateUtil() {
    }

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYMMDD = "yyMMdd";
    public static final String YYMMDDHHMMSS = "yyMMddHHmmss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 所有区间日期.
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 所有区间日期
     */
    public static List<String> allBetweenDate(String startDate, String endDate) {
        final List<String> allDate = new ArrayList<>();
        final SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        try {
            final Date dateStart = sdf.parse(startDate);
            final Date dateEnd = sdf.parse(endDate);
            final Calendar calStart = Calendar.getInstance();
            calStart.setTime(dateStart);
            final Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(dateEnd);
            calEnd.add(Calendar.DATE, 1);
            StringBuilder date = new StringBuilder();
            while (!calStart.equals(calEnd)) {
                date.append(calStart.get(Calendar.YEAR)).append("-");
                if ((calStart.get(Calendar.MONTH) + 1) < 10) {
                    date.append("0");
                }
                date.append((calStart.get(Calendar.MONTH) + 1)).append("-");
                if (calStart.get(Calendar.DAY_OF_MONTH) < 10) {
                    date.append("0");
                }
                date.append(calStart.get(Calendar.DAY_OF_MONTH));
                allDate.add(date.toString());
                calStart.add(Calendar.DATE, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return allDate;
    }

    /**
     * 将指定格式的字符串化为时间格式.
     *
     * @param dateStr 时间字符串
     * @param pattern 指定格式
     * @return 转换后的时间
     */
    public static Date strToDate(String dateStr, String pattern) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            final Date datetime = dateFormat.parse(dateStr);
            final Calendar cal = Calendar.getInstance();
            cal.setTime(datetime);

            return cal.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将时间格式化为指定格式的字符串.
     *
     * @param date    指定时间
     * @param pattern 时间格式
     * @return 转换后的时间字符串
     */
    public static String dateToStr(Date date, String pattern) {
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 获取当前系统日期.
     *
     * @return Date 当前系统日期
     */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取特定格式的当前系统日期.
     *
     * @param formatType 格式化类型
     * @return Date 当前系统日期
     */
    public static String getCurrentDate(String formatType) {
        final Date now = Calendar.getInstance().getTime();
        return dateToStr(now, formatType);
    }

    /**
     * 获取当前系统日期获取格式.
     *
     * @param calendarType 日历类型
     * @param value        要添加到字段的日期或时间的数量
     * @return Date 当前系统日期
     */
    public static Date getTime(int calendarType, int value) {
        final Calendar calDay = Calendar.getInstance();
        calDay.add(calendarType, value);
        return calDay.getTime();
    }

    /**
     * 获取当前系统日期获取格式.
     *
     * @param time         指定时间
     * @param calendarType 日历类型
     * @param value        要添加到字段的日期或时间的数量
     * @return Date 当前系统日期
     */
    public static Date getTime(Date time, int calendarType, int value) {
        final Calendar calDay = Calendar.getInstance();
        calDay.setTime(time);
        calDay.add(calendarType, value);
        return calDay.getTime();
    }

    /**
     * 获得输入日期的后几月的日期.
     *
     * @param date  输入日期
     * @param month 月数
     * @return 输入日期的后几天的日期
     */
    public static Date getMonthAfter(Date date, int month) {
        try {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, month);
            date = calendar.getTime();
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 两个日期中相差的分钟数.
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 分钟数
     */
    public static long minutesBetween(Date date1, Date date2) {

        long date1Time = 0;
        long date2Time = 0;
        if (date1 != null) {
            date1Time = date1.getTime();
        }
        if (date2 != null) {
            date2Time = date2.getTime();
        }
        return (date2Time - date1Time) / (1000 * 60);
    }

    /**
     * 加指定的月.
     *
     * @param date  时间
     * @param month 需要计算增加的月
     * @return 计算后的日期
     */
    public static Date addMonth(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    /**
     * 加指定的天数.
     *
     * @param date 时间
     * @param day  需要增加的天数
     * @return 计算后的日期
     */
    public static Date addDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 加指定的小时.
     *
     * @param date 时间
     * @param hour 需要增加的小时
     * @return 计算后的日期
     */
    public static Date addHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);
        return cal.getTime();
    }

    /**
     * 加指定的分钟.
     *
     * @param date   时间
     * @param minute 需要增加的分钟
     * @return 计算后的日期
     */
    public static Date addMinute(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    /**
     * 加指定的秒
     *
     * @param date   时间
     * @param second 需要增加的秒
     * @return 计算后的日期
     */
    public static Date addSecond(Date date, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * 将时间转为 yyyyMMdd 格式.
     *
     * @param date 日期
     * @return 转换后的时间字符串
     */
    public static String dateToYyyyMmDd(Date date) {
        return dateToStr(date, YYYYMMDD);
    }

    /**
     * 将时间转为 yyyyMMdd 格式.
     *
     * @param date 日期
     * @return 转换后的时间字符串
     */
    public static String dateToYyMmDd(Date date) {
        return dateToStr(date, YYMMDD);
    }
    
    /**
     * 将时间转为 yyyyMMddHHmmss 格式.
     *
     * @param date 日期
     * @return 转换后的时间字符串
     */
    public static String dateToYyyyMmDdHhMmSs(Date date) {
        return dateToStr(date, YYYYMMDDHHMMSS);
    }
}
