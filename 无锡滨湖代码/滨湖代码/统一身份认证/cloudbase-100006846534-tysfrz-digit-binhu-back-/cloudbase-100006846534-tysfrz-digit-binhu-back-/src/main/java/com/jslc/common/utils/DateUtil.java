package com.jslc.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @author BladeX
 * @since
 */


public class DateUtil {
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
    private static Calendar calendar=Calendar.getInstance();

//    public static String DEFAULT_FORMAT = "yyyy";

    /*
       输入日期字符串比如2022-03，返回当月第一天的String
    */
    public static String getMinDateMonth(String month){
        try {
            Date nowDate=sdf.parse(month);
            calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            Date date = calendar.getTime();
            String format = new SimpleDateFormat("YYYY-MM-dd").format(date);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
        输入日期字符串，返回当月最后一天的String
    */
    public static String getMaxDateMonth(String month){
        try {
            Date nowDate=sdf.parse(month);
            calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date date = calendar.getTime();
            String format = new SimpleDateFormat("YYYY-MM-dd").format(date);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) throws ParseException{
//        String month="2022-02";
//        System.out.println(getMinDateMonth(month));
//        System.out.println(getMaxDateMonth(month));
//    }





    /**

     * 默认日期格式

     */

    public static String FORMAT = "yyyy-MM-dd";

    /**

     * 测试主方法

     * @param args

     */

    public static void main(String[] args) {

        int i = 2022;

        System.out.println(formatDate(getCurrYearFirst(i)));
        System.out.println(formatDate(getCurrYearLast(i)));

        LocalDate a = LocalDate.of(2022, 10, 22);
        LocalDate b = LocalDate.of(2022, 11, 22);
        int currentPeriodDay =(int)(b.toEpochDay() - a.toEpochDay());
        System.out.println(currentPeriodDay);


//
    }

    /**

     * 格式化日期

     * @param date 日期对象

     * @return String 日期字符串

     */

    public static String formatDate(Date date){

        SimpleDateFormat f = new SimpleDateFormat(FORMAT);

        String sDate = f.format(date);

        return sDate;

    }

    /**

     * 获取某年第一天日期

     * @param year 年份

     * @return Date

     */

    public static Date getCurrYearFirst(int year){

        Calendar calendar = Calendar.getInstance();

        calendar.clear();

        calendar.set(Calendar.YEAR, year);

        Date currYearFirst = calendar.getTime();

        return currYearFirst;

    }

    /**

     * 获取某年最后一天日期

     * @param year 年份

     * @return Date

     */

    public static Date getCurrYearLast(int year){

        Calendar calendar = Calendar.getInstance();

        calendar.clear();

        calendar.set(Calendar.YEAR, year);

        calendar.roll(Calendar.DAY_OF_YEAR, -1);

        Date currYearLast = calendar.getTime();

        return currYearLast;

    }


    /**
     * 对set中的时间进行排序 yyyy-MM-dd 日期格式
     * @param set
     * @return
     */
    public static Set<String> getOrderByDate(Set<String> set){
        Set<String> sortSet = new TreeSet<String>(new Comparator<String>() {
            public int compare(String o1, String o2) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date d1 = df.parse(o1);
                    Date d2 = df.parse(o2);
                    if(d1.getTime()<d2.getTime()){
                        return -1;
                    }else if(d1.getTime()>d2.getTime()){
                        return 1;
                    }else{
                        return 0;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        sortSet.addAll(set);

        return sortSet;
    }


    /**
     * 对set中的时间进行排序 yyyy-MM 日期格式
     * @param set
     * @return
     */
    public static Set<String> getOrderByDateMonth(Set<String> set){
        Set<String> sortSet = new TreeSet<String>(new Comparator<String>() {
            public int compare(String o1, String o2) {
                DateFormat df = new SimpleDateFormat("yyyy-MM");
                try {
                    Date d1 = df.parse(o1);
                    Date d2 = df.parse(o2);
                    if(d1.getTime()<d2.getTime()){
                        return -1;
                    }else if(d1.getTime()>d2.getTime()){
                        return 1;
                    }else{
                        return 0;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        sortSet.addAll(set);

        return sortSet;
    }
}