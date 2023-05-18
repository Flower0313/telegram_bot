package com.example.telegram_bot.util;

/**
 * @ClassName wxproject-DateUtil
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2022年12月15日11:50 - 周四
 * @Describe
 */

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class DateUtil {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd");
    static SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMdd");
    static SimpleDateFormat yMFormat = new SimpleDateFormat("yyyy-MM");
    static SimpleDateFormat hanDateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    /**
     * 时间格式：yyyy-MM-dd
     */
    public static final String DATE_FORMAT_OUTPUT_ACCURATE_TIME_YEAR_MONTH = "yyyyMM";

    /**
     * 时间格式：yyyy-MM-dd
     */
    public static final String DATE_FORMAT_OUTPUT_ACCURATE_TIME_DAY = "yyyy-MM-dd";

    /**
     * 时间格式：yyyy-MM-dd
     */
    public static final String DATE_FORMAT_OUTPUT_ACCURATE_TIME_MONTH = "yyyy-MM";

    /**
     * 时间格式，yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMAT_OUTPUT_TIME = "yyyy-MM-dd HH:mm:ss";


    private static final int FIRST_DAY = Calendar.MONDAY;

    private DateUtil() {
    }

    /**
     * 某个月的最后一个星期天
     */
    public static String getMonth_LastSUNDAY(String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(yMFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return dateFormat.format(cal.getTime());
    }

    /**
     * 某个月的第一个星期天
     */
    public static String getMonth_firstSUNDAY(String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(yMFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        return dateFormat.format(cal.getTime());
    }

    // 本周的周一
    public static long getMonday(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        // System.out.println("所在周星期一的日期："+dateFormat.format(cal.getTime()));
        return cal.getTime().getTime();
    }

    // 本周的周一
    public static String getMondayStr(Date time, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        // System.out.println("所在周星期一的日期："+dateFormat.format(cal.getTime()));
        return format(cal.getTime(), format);
    }


    // 本周的周末
    public static long getSunday(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DATE, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, 6);
        System.out.println("所在周星期日的日期：" + dateFormat.format(cal.getTime()));

        return cal.getTime().getTime();
    }

    // 本周的周末
    public static String getSunday(Date time, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DATE, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, 6);
        // System.out.println("所在周星期日的日期："+dateFormat.format(cal.getTime()));

        return format(cal.getTime(), format);
    }

    // 当前日期的周一 ——周末的日期
    public static String getWeekStr(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        // System.out.println("要计算日期为:"+dateFormat.format(cal.getTime()));
        // //输出要计算日期

        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        // System.out.println("所在周星期一的日期："+dateFormat.format(cal.getTime()));
        // System.out.println(cal.getFirstDayOfWeek()+"-"+day+"+6="+(cal.getFirstDayOfWeek()-day+6));
        String mon = hanDateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        // System.out.println("所在周星期日的日期："+dateFormat.format(cal.getTime()));

        String str = mon + "——" + hanDateFormat.format(cal.getTime());

        return str;

    }

    // 获取本周的周一-周末的日期
    public static List<String> printWeekdays() {
        Calendar calendar = Calendar.getInstance();
        setToFirstDay(calendar);
        List<String> weeks = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            printDay(calendar);
            calendar.add(Calendar.DATE, 1);
            weeks.add(printDay(calendar));
        }
        return weeks;
    }

    public static void setToFirstDay(Calendar calendar) {
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            calendar.add(Calendar.DATE, -1);
        }
    }

    public static String printDay(Calendar calendar) {
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd EE");
        // System.out.println(dateFormat.format(calendar.getTime()));
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    public static int getWeekDayOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0) {
            w = 7;

        }
        return w;
    }

    public static int getWeekDayOfDate(Calendar cal) {
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0) {
            w = 7;

        }
        return w;
    }

    /**
     * 方法说明:获取当前日期属于本月的第几周
     */
    public static int getMonthWeekNumOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        // System.out.println(calendar.get(Calendar.WEEK_OF_MONTH));
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 方法说明:获取当前日期属于本月的第几周
     */
    public static int getMonthWeekNumOfStr(String today) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        int res = 0;
        try {
            date = format.parse(today);
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTime(date);
            res = calendar.get(Calendar.WEEK_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;

        }
        // System.out.println(calendar.get(Calendar.WEEK_OF_MONTH));
        return res;
    }

    /**
     * 方法说明:两个日期分钟差
     */
    public static long dateMinuteDiff(Date one, Date two) {
        // System.out.println(DateUtil.format(one,
        // "yyyy-MM-dd HH:mm:ss")+"//"+DateUtil.format(two,
        // "yyyy-MM-dd HH:mm:ss"));
        Calendar dateOne = Calendar.getInstance(), dateTwo = Calendar
                .getInstance();
        dateOne.setTime(one);
        dateTwo.setTime(two);
        long timeOne = dateOne.getTimeInMillis();
        long timeTwo = dateTwo.getTimeInMillis();
        long minute = (timeOne - timeTwo) / (1000 * 60);// 转化minute
        // System.out.println("相隔"+minute+"分钟");
        return minute;

    }

    public static long DateTimeInMillis(Date one) {
        Calendar dateOne = Calendar.getInstance();
        dateOne.setTime(one);
        long timeOne = dateOne.getTimeInMillis();
        return timeOne;

    }

    /**
     * 方法说明:两个时间差
     */
    public static String dateDiff_min(String startTime, String endTime,
                                      String format) throws Exception {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;

        // 获得两个时间的毫秒时间差异
        diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
        System.out.println("=" + diff / (60 * 1000) % 60);
        long day = diff / nd;// 计算差多少天
        long hour = diff % nd / nh;// 计算差多少小时
        long min = diff % nd % nh / nm;// 计算差多少分钟
        long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
        System.out.println("时间相差：" + day + "天" + hour + "小时" + min + "分钟" + sec
                + "秒。");

        return "时间相差：" + day + "天" + hour + "小时" + min + "分钟" + sec + "秒。";
    }

    /**
     * 、可自行设置“2013-06-03”格式的日期
     *
     * @return 返回1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六
     */

    public static int getDayofweek(String date) {
        Calendar cal = Calendar.getInstance();
        // cal.setTime(new Date(System.currentTimeMillis()));
        if (date.equals("")) {
            cal.setTime(new Date(System.currentTimeMillis()));
        } else {
            cal.setTime(new Date(StrToDate(date).getTime()));
        }
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;

        try {

            date = format.parse(str);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return date;
    }

    public static Date strToDate(String str) {

        if (str == null || "".equals(str)) {
            return null;
        }
        Date date = null;
        try {
            date = dateformat.parse(str);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return date;
    }

    public static Date StrToDate(String str, String format1) {

        SimpleDateFormat format = new SimpleDateFormat(format1);

        Date date = null;

        try {

            date = format.parse(str);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return date;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        Integer between = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            between = Integer.valueOf(String.valueOf(between_days));
        } catch (ParseException e) {

        }
        return between;
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @param @param  dt
     * @param @param  minute
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: addOrMinusMinute
     * @Description: TODO(分钟加减)
     */
    public static String addOrMinusMinute(Date dt, int minute, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        /*
         * rightNow.add(Calendar.YEAR,-1);//日期减1年
         * rightNow.add(Calendar.MONTH,3);//日期加3个月
         * rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
         */
        rightNow.add(Calendar.MINUTE, minute); // 分钟
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    public static Date addOrMinusMinuteDate(Date dt, int minute) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MINUTE, minute); // 分钟
        Date dt1 = rightNow.getTime();
        return dt1;
    }

    /**
     * @param @param  dt
     * @param @param  hour
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: addOrMinusHour
     * @Description: TODO(小时加减)
     */
    public static String addOrMinusHour(Date dt, int hour) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.HOUR, hour);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    /**
     * @param @param  dt
     * @param @param  day
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: addOrMinusDay
     * @Description: TODO(日期加减)
     */
    public static String addOrMinusDay(Date dt, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DAY_OF_YEAR, day); // 日期加30天
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    public static Date addOrMinusDayDate(Date dt, int day) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DAY_OF_YEAR, day); // 日期加30天
        Date dt1 = rightNow.getTime();
        return dt1;
    }

    public static String addOrMinusDay(Date dt, int day, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DAY_OF_YEAR, day); // 日期加30天
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    public static String addOrMinusDay(String dt, int day, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(StrToDate(dt, format));
        rightNow.add(Calendar.DAY_OF_YEAR, day); // 日期加30天
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    public static String addOrMinusMonth(Date dt, int day, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MONTH, day); // 日期加30天
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    public static Date addOrMinusMonth_Date(Date dt, int day) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MONTH, day); // 日期加30天
        Date dt1 = rightNow.getTime();
        // Calendar calendar = new GregorianCalendar();
        // Calendar cal = Calendar.getInstance();
        // cal.add(Calendar.MONTH, -2);
        // SimpleDateFormat formatter_shuzi = new SimpleDateFormat("yyyyMM");
        // String mdatetimeshuzi=formatter_shuzi.format(cal.getTime());
        // System.out.print(mdatetimeshuzi);
        return dt1;
    }

    /**
     * 方法说明:年份加减
     *
     * @param dt
     * @param day
     * @param dateFormat
     * @return
     * @Title: addOrMinusYear
     * @author ht_zhouxiuhong
     * @date 2016年9月1日 下午5:11:09
     * @updateAuthor
     * @updateTime
     * @updateReason
     */
    public static String addOrMinusYear(Date dt, int day, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR, day);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    /**
     * @param @param  date
     * @param @param  format
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: format
     * @Description: TODO(日期转 指定格式的 string)
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sft = new SimpleDateFormat(format);
        return sft.format(date);
    }

    public static long addMinutes(Date one, Date two) {
        if (one == null) {
            return 0;
        }
        if (two == null) {
            return 0;
        }
        Calendar from = Calendar.getInstance();
        from.setTime(one);
        Calendar to = Calendar.getInstance();
        to.setTime(two);
        //
        // System.out.println(Math.abs((from.getTimeInMillis() - to
        // .getTimeInMillis())));
        return Math.abs((from.getTimeInMillis() - to.getTimeInMillis())
                / (1000 * 60));
    }

    /**
     * 判断传入字符是否是日期格式(yyyy-MM-dd)
     *
     * @param @param  date
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: isDateStringValid
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public static boolean isDateStringValid(String date) {
        boolean isValidDateStr = false;
        String yyyyMMddFmt = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
        Pattern p = Pattern.compile(yyyyMMddFmt);
        if (p.matcher(date).matches()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                sdf.parse(date);
                isValidDateStr = true;
            } catch (ParseException parseExp) {
            }
        }
        return isValidDateStr;
    }

    /**
     * 判断传入字符是否是日期格式(yyyy-MM-dd hh:mm:ss)
     *
     * @param @param  date
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: isDateStringValidYMDHMS
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public static boolean isDateStringValidYMDHMS(String date) {
        boolean isValidDateStr = false;
        String yyyyMMddFmt = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
        Pattern p = Pattern.compile(yyyyMMddFmt);
        if (p.matcher(date).matches()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                sdf.parse(date);
                isValidDateStr = true;
            } catch (ParseException parseExp) {
            }
        }
        return isValidDateStr;
    }

    /**
     * 判断传入字符是否是日期格式(yyyy-yyyy)
     *
     * @param @param  date
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: isDateStringValid
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public static boolean isSearchDateStringValid(String date) {
        boolean isValidDateStr = false;
        String yyyyMMddFmt = "[0-9]{4}-[0-9]{4}";
        Pattern p = Pattern.compile(yyyyMMddFmt);
        if (p.matcher(date).matches()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-yyyy");
            try {
                sdf.parse(date);
                isValidDateStr = true;
            } catch (ParseException parseExp) {
            }
        }
        return isValidDateStr;
    }

    /**
     * @param @param  date
     * @param @return
     * @param @throws ParseException 设定文件
     * @return String 返回类型
     * @throws
     * @Title: nextMonthFirstDate
     * @Description: TODO(获取日期的下一月)
     */
    public static String nextMonthFirstDate(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());

    }

    public static String nextMonthFirstDate(Date date, String format)
            throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());

    }

    public static void printDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(date));
    }

    /**
     * 获取日期年份
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static int getYear(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取日期年份
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static int getYear(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * @param @param  date
     * @param @return
     * @param @throws ParseException 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getYearMonth
     * @Description: TODO(获取日期年月)
     */
    public static String getYearMonth(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        return calendar.get(Calendar.YEAR) + "-"
                + (calendar.get(Calendar.MONTH) + 1);
    }

    public static String getYearMonth() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR) + "-"
                + (calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取日期月份
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static int getMonth(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        return (calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取日期月份
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static int getMonth(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取日期号
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static int getDay(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取日期号
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static int getDay(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 获取月份起始日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMinMonthDate(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取月份最后日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMaxMonthDate(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 获取月份最后日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMaxMonthDate(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }

    public static Boolean checkDate(String inputDate, String format)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return inputDate.equals(sdf.format(sdf.parse(inputDate)));
    }

    /**
     * @param @return
     * @param @throws ParseException 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getOldMonthfirstDay
     * @Description: TODO(获取上月第一天)
     */
    public static String getOldMonthfirstDay() {
        // 获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();// 获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        String firstDay = dateFormat.format(cal_1.getTime());
        // System.out.println("-----1------firstDay:"+firstDay);
        return firstDay;
    }

    /**
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getOldMonthlastDay
     * @Description: TODO(获取上月最后一天)
     */
    public static String getOldMonthlastDay() {
        // 获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
        String lastDay = dateFormat.format(cale.getTime());
        // System.out.println("-----2------lastDay:"+lastDay);
        return lastDay;
    }

    /**
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getMonthfirstDay
     * @Description: TODO(获取当前月第一天)
     */
    public static String getMonthfirstDay() {
        // 获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        String first = dateFormat.format(c.getTime());
        // System.out.println("===============first:"+first);
        return first;
    }

    /**
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getMonthlastDay
     * @Description: TODO(获取当前月最后一天)
     */
    public static String getMonthlastDay() {
        // 获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH,
                ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = dateFormat.format(ca.getTime());
        // System.out.println("===============last:"+last);
        return last;
    }


    /**
     * @return
     * @throws
     * @Title: getHour
     * @Description: TODO 返回小时
     */
    public static int getHour() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);//获取日
    }


    /**
     * @param @param  DATE1
     * @param @param  DATE2
     * @param @return 设定文件
     * @return int 返回类型 （0：日期相等 1：date1大于date2 -1： date2大于date1）
     * @throws
     * @Title: compare_date
     * @Description: TODO(比较日期大小)
     */
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;

            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String getStrDateForYYYYMMDDHHMMSS(Date date) {
        String result = "";
        if (date == null) {
            return result;
        }
        SimpleDateFormat l_sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        result = l_sdf.format(date);
        return result;
    }

    /**
     * 方法说明:某个月有多少周
     */
    public static int getMonthWeek(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date s = sdf.parse(date);
        Calendar ca = Calendar.getInstance();
        ca.setTime(s);
        ca.setFirstDayOfWeek(Calendar.MONDAY);
        // System.out.println(ca.getActualMaximum(Calendar.WEEK_OF_MONTH));
        return ca.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 方法说明:查看某个月有几周
     */
    public static int getMonthWeek(Date s) throws Exception {
        Calendar ca = Calendar.getInstance();
        ca.setTime(s);
        ca.setFirstDayOfWeek(Calendar.MONDAY);
        // System.out.println(ca.getActualMaximum(Calendar.WEEK_OF_MONTH));
        return ca.getActualMaximum(Calendar.WEEK_OF_MONTH);

    }

    public static void gettime() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017); // 2016年
        cal.set(Calendar.WEEK_OF_YEAR, 2); // 设置为2016年的第10周
        cal.set(Calendar.DAY_OF_WEEK, 2); // 1表示周日，2表示周一，7表示周六
        Date date = cal.getTime();

        System.out.println(dateFormat.format(date));

    }

    /**
     * 获取月日
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMonthDay(String date) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        String str = (calendar.get(Calendar.MONTH) + 1) + "月" + (getDay(date))
                + "号";
        return str;
    }

    // 获取月第一天：
    public static String getMonthfirstDay(Calendar c) {

        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        String first = dateFormat.format(c.getTime());
        // System.out.println("===============first:"+first);
        return first;
    }

    public static String getThisDayForOSS() {
        Calendar cal = Calendar.getInstance();
        return DateFormat.format(cal.getTime());
    }

    /**
     * 计算两个时间相差多少年多少个月
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static String dayComparePrecise(Date fromDate, Date toDate) {
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to = Calendar.getInstance();
        to.setTime(toDate);

        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int year = toYear - fromYear;
        int month = toMonth - fromMonth;
        int day = toDay - fromDay;
        String info = "";
        if (year > 0) {
            info += year + "年";
        }
        if (month > 0) {
            info += month + "个月";
        }
        if (day > 0) {
            info += day + "天";
        }
        if (info.equals("")) {
            info = "0";
        }
        return info;
    }


    /**
     * 获取某月天数
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据某月的第几天获取实际日期
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateByDay(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, day);
        return now.getTime();
    }

    /**
     * @param @param  date
     * @param @return
     * @param @throws ParseException 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getYearMonth
     * @Description: TODO(获取日期年月)
     */
    public static String getYearMonth(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) + "-"
                + (calendar.get(Calendar.MONTH) + 1);

    }

    public static Date getYmdDate(String dateStr) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = df.parse(dateStr);
        return dt;
    }

    public static Date formatYmdDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = null;
        try {
            dt = df.parse(df.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Date formatDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Date dt = null;
        try {
            dt = df.parse(df.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }




    public static Date salaryEndDate(Integer salaryDate) {
        Integer year = Integer.valueOf(salaryDate.toString().substring(0, 4));
        Integer month = Integer.valueOf(salaryDate.toString().substring(4, 6));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 25);
        return calendar.getTime();
    }

    /**
     * 方法说明:两个日期相差秒数
     */
    public static long dateSecondsDiff(Date one, Date two) {
        long seconds = (two.getTime() - one.getTime()) / 1000;
        return seconds;

    }

    /**
     * 获得两个时间段内所有的分钟，例如：传参“201911180955”和“201911181015”,
     * 将会返回["201911180955","201911180956","201911180957"......,"201911181014","201911181015"]
     *
     * @param beginDate
     * @param endDate
     * @return
     * @throws ParseException
     * @throws ParseException
     */
    public static List<String> getMinuteBetweenTwoDate(String beginDate,
                                                       String endDate) throws ParseException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        List<String> lDate = new ArrayList<String>();
        lDate.add(beginDate);// 把开始时间加入集合
        if (beginDate.equals(endDate)) {
            return lDate;
        }
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(sdf.parse(beginDate));
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.MINUTE, 1);
            // 测试此日期是否在指定日期之后
            if (sdf.parse(endDate).after(cal.getTime())) {
                lDate.add(sdf.format(cal.getTime()));
            } else {
                break;
            }
        }
        lDate.add(endDate);// 把结束时间加入集合
        return lDate;
    }

    /**
     * @description: 获取当天开始时间
     * @param: [date]
     * @return: java.util.Date
     * @author: sky
     * @date: 2020/5/27 17:16
     * @throw:
     */
    public static Date getTodayStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * @description: 得到几天后的时间
     * @param: [date, day]
     * @return: java.util.Date
     * @author: sky
     * @date: 2020/12/30 11:42
     * @throw:
     */
    public static Date getDateAfter(Date date, Integer day) {
        if (date == null || day == null) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * @description: 获取几天前的时间
     * @param: [date, day]
     * @return: java.util.Date
     * @author: sky
     * @date: 2021/1/14 14:35
     * @throw:
     */
    public static Date getDateBefore(Date date, Integer day) {
        if (date == null || day == null) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DATE, day);
        return now.getTime();
    }

    /**
     * @description: 获取几月前的时间
     * @param: [date, day]
     * @return: java.util.Date
     * @author: sky
     * @date: 2021/1/14 14:35
     * @throw:
     */
    public static Date getDateBefore4Month(Date date, Integer day) {
        if (date == null || day == null) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MONTH, day);
        return now.getTime();
    }

    /**
     * @description: 获取日期 结束时间  2019-11-01 59:59:59:999
     * @param: [date]
     * @return: java.util.Date
     * @author: sky
     * @date: 2021/3/26 15:37
     * @throw:
     */
    public static Date getDateEndDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * @description: 获取上个月第一天  2019-11-01 00:00:00:00
     * @param: [date]
     * @return: java.util.Date
     * @author: sky
     * @date: 2021/6/15 15:37
     * @throw:
     */
    public static Date getDateLastMonthFirstDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * @description: 获取上个月第一天  2019-11-01 00:00:00:00
     * @param: [date]
     * @return: java.util.Date
     * @author: sky
     * @date: 2021/6/15 15:37
     * @throw:
     */
    public static Date getDateThisMonthFirstDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前月份第一天
     *
     * @return
     */
    public static String getFirstDayOfThisMonth() {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return myFormatter.format(cal.getTime()) + " 00:00:00";
    }

    /**
     * @description: 获取上个月第一天  2019-11-01
     * @author: xh
     */
    public static String getStrDateLastMonthFirstDay(String dateStr) {
        Date date = StrToDate(dateStr, DATE_FORMAT_OUTPUT_ACCURATE_TIME_MONTH);
        return dateFormat.format(getDateLastMonthFirstDay(date));
    }

    /**
     * @description: 获取当月的第一天  2019-11-01
     * @author: xh
     */
    public static String getStrDateThisMonthFirstDay(String dateStr) {
        Date date = StrToDate(dateStr, DATE_FORMAT_OUTPUT_ACCURATE_TIME_MONTH);
        return dateFormat.format(getDateThisMonthFirstDay(date));
    }

    /**
     * 获取当前月份最后一天
     *
     * @return
     */
    public static String getMaxDayOfThisMonth() {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        return myFormatter.format(cal.getTime()) + " 23:59:59";
    }

    /**
     * 获取上个月份第一天
     *
     * @return
     */
    public static String getFirstDayOfLastMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取前一个月第一天
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(calendar1.getTime()) + " 00:00:00";
    }

    /**
     * 获取上月份最后一天
     *
     * @return
     */
    public static String getMaxDayOfLastMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 0);
        return sdf.format(calendar2.getTime()) + " 23:59:59";
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int currentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int currentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return Integer.valueOf(sdf.format(cal.getTime()));
    }

    /**
     * 获取当前月份上个月
     *
     * @return
     */
    public static int currentLastMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取当前月份的上个月所在的年
     *
     * @return
     */
    public static int lastMonthLastYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return Integer.valueOf(sdf.format(cal.getTime()));
    }

    /**
     * 获取两个时间段的日期集合，一天小为一个单位
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getBetweenDays(String startTime, String endTime) {
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        try {
            //1、定义转换格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Date start = df.parse(startTime);
            Date end = df.parse(endTime);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            result.add(sdf.format(start));
            while (tempStart.before(tempEnd)) {
                result.add(sdf.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
            result.add(sdf.format(end));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取两个时间段的日期集合，一周为一个单位
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getBetweenWeeks(String startTime, String endTime) {
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        try {
            //1、定义转换格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Date start = df.parse(startTime);
            Date end = df.parse(endTime);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            tempStart.add(Calendar.DAY_OF_MONTH, 7);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            result.add(sdf.format(start));
            while (tempStart.before(tempEnd)) {
                result.add(sdf.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_MONTH, 7);
            }
            result.add(sdf.format(end));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取两个时间段的日期集合，一月为一个单位
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> getBetweenMonths(String startTime, String endTime) {
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            // 获取开始年份和开始月份
            int startYear = calendar.get(Calendar.YEAR);
            int startMonth = calendar.get(Calendar.MONTH) + 1;
            // 获取结束年份和结束月份
            calendar.setTime(endDate);
            int endYear = calendar.get(Calendar.YEAR);
            int endMonth = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            //
            for (int i = startYear; i <= endYear; i++) {
                String date = "";
                if (startYear == endYear) {
                    for (int j = startMonth; j <= endMonth; j++) {
                        if (j < 9) {
                            date = i + "-0" + (j + 1);
                        } else {
                            date = i + "-" + (j + 1);
                        }
                        result.add(date + "-" + dd);
                    }

                } else {
                    if (i == startYear) {
                        for (int j = startMonth; j < 12; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            result.add(date + "-" + dd);
                        }
                    } else if (i == endYear) {
                        for (int j = 0; j <= endMonth; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            result.add(date + "-" + dd);
                        }
                    } else {
                        for (int j = 0; j < 12; j++) {
                            if (j < 9) {
                                date = i + "-0" + (j + 1);
                            } else {
                                date = i + "-" + (j + 1);
                            }
                            result.add(date + "-" + dd);
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

	/*public static void main(String[] args) {
		List<String> list = getBetweenWeeks("2022-03-01","2022-03-31");
		System.out.println(list.toString());
	}*/
}

