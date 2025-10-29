package com.znv.manage.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
}
