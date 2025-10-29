package com.znv.manage.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class DateUtils {

    public static final String formatYMDHms="yyyy-MM-dd HH:mm:ss";

    public static final String formatYMD="yyyy-MM-dd";
    /**
     * 判断是否当前时间段
     * @param startTime webSocketMap = new ConcurrentHashMap<String, WebSocket>();
     * @param endTime
     * @return
     */
    public static boolean isTimePeriod(int startTime,int endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        int format = Integer.parseInt(sdf.format(new Date()));
        if(format>=startTime&&format<=endTime){
            return true;
        }
        return false;
    }
    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 根据时间搓返回当前时间
     * @param timeStamp
     * @param format
     * @return
     */
    public static String getDateByTimeStamp(Long timeStamp,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(timeStamp);
        String time = sdf.format(date);
        return time;
    }

    /**
     * 获取指定时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取当前时间
     * @param format
     * @return
     */
    public static String getNowTime(String format){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String time = sdf.format(date);
        return time;
    }

    /**
     * 获取前多少分钟时间
     * @param format 转换成的时间格式 YYYYMMddHHmmss
     * @param forwardTime 前多少分钟
     * @return
     */
    public static String getBeforeTime(String format,int forwardTime){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar beforeTime = Calendar.getInstance();
        // xx分钟之前的时间
        beforeTime.add(Calendar.MINUTE, -forwardTime);
        Date beforeD = beforeTime.getTime();
        String time = sdf.format(beforeD);
        return time;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFetureDate(int past,String time,String format) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date parse = sdf.parse(time);
            String format1 = sdf.format(parse);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            calendar.add(Calendar.DATE,past);
            Date today = calendar.getTime();
            String result = sdf.format(today);
            return result;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    public static Long getTimeStampByDate(String time,String format){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(time);
            return date.getTime();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(strDate, pos);
            return strtodate;
        } catch (Exception e) {
            return null;
        }

    }

    public static String getTimeDay(String simpleDateFormat,int index){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(simpleDateFormat);
            Calendar g = Calendar.getInstance();
            g.setTime(d1);
            g.add(Calendar.YEAR,index);
            Date d2 = g.getTime();
            String date = df.format(d2);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static List<String> getHBDay(String beginTime,String endTime){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            Date d1 = df.parse(beginTime);
            Date d2 = df.parse(endTime);
            long time1 = d1.getTime();
            long time2 = d2.getTime();
            int count = (int)(time2-time1)/24/60/60/1000;
            Calendar g1 = Calendar.getInstance();
            g1.setTime(d1);
            g1.add(Calendar.DAY_OF_MONTH,-count);
            Date t1 = g1.getTime();
            String dateA = df.format(t1);
            Calendar g2 = Calendar.getInstance();
            g2.setTime(d2);
            g2.add(Calendar.DAY_OF_MONTH,-count);
            Date t2 = g2.getTime();
            String dateB = df.format(t2);
            List<String> s = new LinkedList<>();
            s.add(dateA);
            s.add(dateB);
            return s;
        } catch (ParseException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        //System.out.println(DateUtils.getTimeDay("2023-02-23 00:00:00",-1));
        List<String> a = DateUtils.getHBDay("2023-02-20 00:00:00","2023-02-23 00:00:00");
        System.out.println(a.get(0));
        System.out.println(a.get(1));
    }
}
