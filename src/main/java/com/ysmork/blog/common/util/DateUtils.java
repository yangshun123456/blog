package com.ysmork.blog.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 * 
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String HH_MM_SS = "HH:mm:ss";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date date = cal.getTime();
        return dateTime(date);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    public static double getDateDifferHours(Date beforeDate,Date afterDate) {
//        long nd = 1000 * 24 * 60 * 60;
//        long nh = 1000 * 60 * 60;
        long diff = afterDate.getTime() - beforeDate.getTime();
//        double hour = (double) diff % nd / nh;
        double hour = (double) diff / 1000 / (60 * 60);
        return Math.floor(hour * 10) / 10;
    }

    /**
     * 得到一个月的开始时间
     * @param year
     * @param month
     * @return
     */
    public static Date getBeginTime(int year,int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 得到一个月的结束时间
     * @param year
     * @param month
     * @return
     */
    public static Date getEndTime(int year,int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }


    /**
     * 将传入的时分转换为时分秒
     * @param date
     * @return
     */
    public static Date getHourTime(Date date){
        DateFormat dateFormat = new SimpleDateFormat ("HH:mm:ss");
        String format = dateFormat.format (date);
        String hour = format.substring (3,5);
        String minutes = format.substring (6,8);
        String newFormat = hour+":"+minutes+":"+"00";
        try {
            return dateFormat.parse (newFormat);
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 获取两日期间隔日期
     * @param endDate 结束日期
     * @param startDate 开始日期
     * @return
     */
    public static List<String> getDisDate(Date endDate, Date startDate) {
        if(startDate.getTime() > endDate.getTime()){
            return null;
        }
        // 获取日期时间差
        long nd = 1000 * 24 * 60 * 60;

        long diff = endDate.getTime() - startDate.getTime();
        long day = diff/nd;

        Calendar c = Calendar.getInstance();
        c.setTime(startDate);

        List<String> list = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        list.add(dateFormat.format(startDate));

        for(int i = 0;i < day;i++){
            c.add(Calendar.DAY_OF_MONTH,1);
            list.add(dateFormat.format(c.getTime()));
        }

        return list;
    }

    public static List getLastMonthList(Integer num) {
        DecimalFormat df = new DecimalFormat("00");
        List result = new ArrayList(num);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < num; i++) {
            if (i == 0) {
                cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
            } else {
                // 逐次往前推1个月
                cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
            }
            result.add(cal.get(Calendar.YEAR) + "-"+ df.format(cal.get(Calendar.MONTH) + 1));
        }

        return result;
    }

    /**
     * 获取指定时间前?小时的时间
     * @param date
     * @param hour
     * @return
     */
    public static Date getLastHourDate(Date date,Integer hour) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        if(date == null) return null;
        Integer time = Integer.parseInt (format.format (date).substring (0,2));
        String minAndSec = format.format (date).substring (2,8);
        Integer after = time - hour;
        if(after<0){
            after = 24 - 1;
        }
        String then = after + minAndSec;
        return dateTime ("HH:mm:ss",then);
    }

    public static double getDatesInterval(Date beforeDate,Date afterDate) {
        SimpleDateFormat format = new SimpleDateFormat ("HH:mm:ss");
        Integer start = Integer.parseInt (format.format (beforeDate).substring (0,2));
        Integer end =Integer.parseInt( format.format (afterDate).substring (0,2));
        String startTime = format.format (beforeDate);
        String endTime = format.format (afterDate);
        if(start.intValue ()>end.intValue ()){
            endTime = (end+24)+ format.format (afterDate).substring (2,8);
        }
        //结束时间
        Integer endHour = Integer.parseInt (endTime.substring (0,2));
        Integer endMinutes = Integer.parseInt (endTime.substring (3,5));
        Integer endSeconds = Integer.parseInt (endTime.substring (6,8));
        //开始时间
        Integer startHour = Integer.parseInt (startTime.substring (0,2));
        Integer startMinutes = Integer.parseInt (startTime.substring (3,5));
        Integer startSeconds = Integer.parseInt (startTime.substring (6,8));

        Integer subHour = endHour - startHour;
        Integer subMinutes = endMinutes - startMinutes;
        Integer subSeconds = endSeconds - startSeconds;

        Integer totalSeconds = subHour*3600 + subMinutes*60 + subSeconds;
        DecimalFormat format1 = new DecimalFormat ("#.0");
        format1.setRoundingMode (RoundingMode.HALF_UP);

        BigDecimal retuenData = new BigDecimal (format1.format (Arith.div (totalSeconds.doubleValue (),3600)));
        return retuenData.doubleValue ();
    }

    /**
     * 获取当前时间前n个小时的时间
     * @param ihour
     * @return
     */
    public static String getBeforeByHourTime(int ihour){
        String returnstr = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - ihour);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
        returnstr = df.format(calendar.getTime());
        return returnstr;
    }

    public static Date getBeforeByMinTime(Date date,int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间
//        calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间
        calendar.add(Calendar.MINUTE, -min);
        return calendar.getTime();
    }

    public static Date getAfterByHourTime(Date date,int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间
//        calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }





}
