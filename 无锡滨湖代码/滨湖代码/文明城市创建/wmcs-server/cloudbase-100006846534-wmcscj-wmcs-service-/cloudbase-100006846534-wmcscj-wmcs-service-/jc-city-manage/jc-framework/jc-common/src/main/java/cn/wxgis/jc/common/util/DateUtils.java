package cn.wxgis.jc.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 */
public class DateUtils {

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDD = "yyyy/MM/dd";

    public static String yyyyMMdd = "yyyyMMdd";

    public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String HH_MM_SS = "HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前年月, 格式yyyy-MM
     */
    public static String getCurrentMonth()
    {
        return formatCurrentDateTime(YYYY_MM);
    }

    /**
     * 获取当前日期和时分秒, 格式yyyy-MM-dd
     */
    public static String getCurrentDate()
    {
        return formatCurrentDateTime(YYYY_MM_DD);
    }
    /**
     * 获取当前日期和时分秒, 格式yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime()
    {
        return formatCurrentDateTime(YYYY_MM_DD_HH_MM_SS);
    }
    /**
     * 获取当前日期和时分秒, 格式yyyyMMddHHmmss
     */
    public static String geCurrentDateTime(){
        return formatCurrentDateTime(yyyyMMddHHmmss);
    }

    /**
     * 根据格式化方式获取当前时间
     * @param format 格式化
     * @return 当前日期字符串
     */
    public static String formatCurrentDateTime(String format)
    {
        if (StringUtils.isEmpty(format)) {
            format = YYYY_MM_DD_HH_MM_SS;
        }
        return parseDateToString(new Date(), format);
    }

    /**
     * 从Date类型的时间中提取日期部分
     */
    public static Date getDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 从Date类型的时间中提取时间部分
     */
    public static Date getTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, 1970);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static String getYearAndMonthByDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM);
        return sdf.format(date);
    }

    /**
     * @description 获取格式化日期
     * @param character     null    20190620
     *                      "/"     2019/06/20
     * @return 2019/06/20
     */
    public static String getFormatDateByCurrent(String character)
    {
        Date now = new Date();
        if ("/".equals(character)) {
            return DateFormatUtils.format(now, "yyyy/MM/dd");
        } else if ("-".equals(character)) {
            return DateFormatUtils.format(now, "yyyy-MM-dd");
        }
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    public static String getFormatDate(String dateStr, String character) {
        Date date = parseStringToDate(dateStr, YYYY_MM_DD_HH_MM_SS);
        if (null == character || "".equals(character.trim())) {
            character = YYYY_MM_DD;
        }
        return DateFormatUtils.format(date, character);
    }

    /**
     * @description 将日期类型的对象转换成字符串格式
     * @date  2019/6/20 17:39
     * @param date 需要转换的日期对象
     * @param fmt 转换格式，例如"yyyy年MM月dd日 HH时mm分ss秒 E"；默认是"yyyy年MM月dd日"
     * @return 2019年06月20日 17时40分54秒
     */
    public static String parseDateToString(Date date, String fmt) {
        if (null == fmt || "".equals(fmt.trim())) {
            fmt = YYYY_MM_DD;
        }
        DateFormat df = new SimpleDateFormat(fmt);
        return df.format(date);
    }

    /**
     * @description 将日期字符串转换成Date类型
     * @date  2019/6/20 17:41
     * @param strDate 需要转换的日期字符串
     * @param fmt 转换格式，例如"yyyy年MM月dd日 HH时mm分ss秒 E"；默认是"yyyy-MM-dd"
     */
    public static Date parseStringToDate(String strDate, String fmt) {
        if (null == fmt || "".equals(fmt.trim())) {
            fmt = YYYY_MM_DD;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(fmt);
            return df.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @description 将日期字符串转换成Date类型
     * @date  2019/6/20 17:41
     * @param strDate 需要转换的日期字符串
     */
    public static Date parseStringToBeginDate(String strDate) {
        strDate = strDate + " 00:00:00";
        String fmt = YYYY_MM_DD;
        try {
            SimpleDateFormat df = new SimpleDateFormat(fmt);
            return df.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @description 将日期字符串转换成Date类型
     * @date  2019/6/20 17:41
     * @param strDate 需要转换的日期字符串
     */
    public static Date parseStringToEndDate(String strDate) {
        strDate = strDate + " 23:59:59";
        String fmt = YYYY_MM_DD;
        try {
            SimpleDateFormat df = new SimpleDateFormat(fmt);
            return df.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @description 获取当前时间年份/月份/日期
     * @date  2019/6/20 17:47
     * @param place Y-年份、M-月份、D-日期
     * @return 2019
     */
    public static String getCurrentDate(String place){
        if ("Y".equals(place)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            return sdf.format(date);
        } else if ("M".equals(place)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM");
            Date date = new Date();
            return sdf.format(date);
        } else if ("D".equals(place)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            Date date = new Date();
            return sdf.format(date);
        }
        return getFormatDateByCurrent(null);
    }

    /**
     * @description 获取指定时间的年月日
     * @date  2019/6/21 8:48
     * @param strDate 需要查询的日期
     * @return {date=21, month=6, year=2019}
     */
    public static Map<String,Object> getParamDate(String strDate) {
        Map<String,Object> map = new HashMap<>();
        Date date = parseStringToDate(strDate, YYYY_MM_DD_HH_MM_SS);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH ) +1 ;
        int day = cal.get(Calendar.DATE);
        map.put("year",year);
        map.put("month",month);
        map.put("date",day);
        return map;
    }
    public static Map<String,Object> getParamDate(Date date) {
        Map<String,Object> map = new HashMap<String,Object>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH ) +1 ;
        int day = cal.get(Calendar.DATE);
        map.put("year",year);
        map.put("month",month);
        map.put("date",day);
        return map;
    }


    /**
     * @description 获取前一年或者前几年的时间
     * @date  2019/6/21 0021 8:52
     * @param strDate 指定的时间
     * @param num 前几个月，如果是空则取上个月
     * @return 2018-06-21 17:20:34
     */
    public static String getLastYearDate(String strDate, Integer num) {
        if (null == num || num <= 0) {
            num = 1;
        }
        Date date = parseStringToDate(strDate, YYYY_MM_DD_HH_MM_SS);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }
    public static String getLastYearDate(Date date, Integer num) {
        if (null == num || num <= 0) {
            num = 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, -num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @description 获取下一年或者下几个年的时间
     * @date  2019/6/21 8:57
     * @param strDate 传入的时间
     * @param num 后面第几年
     * @return 2020-06-21 17:20:34
     */
    public static String getNextYearDate(String strDate,Integer num) {
        if (null == num || num <= 0) {
            num = 1;
        }
        Date date = parseStringToDate(strDate, YYYY_MM_DD_HH_MM_SS);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }
    public static String getNextYearDate(Date date,Integer num) {
        if (null == num || num <= 0) {
            num = 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @description 获取上个月或前几个月的时间
     * @date  2019/6/21 0021 8:52
     * @param strDate 指定的时间
     * @param num 前几个月，如果是空则取上个月
     * @return 2019-05-21 17:20:34
     */
    public static String getLastMonthDate(String strDate, Integer num) {
        if (null == num || num <= 0) {
            num = 1;
        }
        Date date = parseStringToDate(strDate, YYYY_MM_DD_HH_MM_SS);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }
    public static String getLastMonthDate(Date date, Integer num) {
        if (null == num || num <= 0) {
            num = 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @description 获取下个月或者下几个月的时间
     * @date  2019/6/21 8:57
     * @param strDate 传入的时间
     * @param num 后面第几个月
     * @return 2019-07-21 17:20:34
     */
    public static String getNextMonthDate(String strDate, Integer num) {
        if (null == num || num <= 0) {
            num = 1;
        }
        Date date = parseStringToDate(strDate, YYYY_MM_DD_HH_MM_SS);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }
    public static String getNextMonthDate(Date date, Integer num) {
        if (null == num || num <= 0) {
            num = 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @description 获取前一天或者前几天的日期
     * @date  2019/6/21 9:28
     * @param strDate 指定的时间
     * @param num 前几天
     * @return 2019-06-20 17:20:34
     */
    public static String getLastDayDate(String strDate,Integer num){
        if (null == num || num <= 0) {
            num = 1;
        }
        Date date = parseStringToDate(strDate, YYYY_MM_DD_HH_MM_SS);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -(num));
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }
    public static String getLastDayDate(Date date,Integer num){
        if (null == num || num <= 0) {
            num = 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -(num));
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @description 获取后一天或者后几天的日期
     * @date  2019/6/21 9:28
     * @param strDate 指定的时间
     * @param num 后几天
     * @return 2019-06-22 17:20:34
     */
    public static String getNextDayDate(String strDate,Integer num){
        if (null == num || num <= 0) {
            num = 1;
        }
        Date date = parseStringToDate(strDate, YYYY_MM_DD_HH_MM_SS);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }
    public static String getNextDayDate(Date date,Integer num){
        if (null == num || num <= 0) {
            num = 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @description 获取指定日期多少分钟前的日期
     * @date  2019/6/21 9:42
     * @param strDate 指定日期
     * @param num 多少分钟
     * @return 2019-06-21 17:21:34
     */
    public static String getLastMinuteDate(String strDate,Integer num){
        if (null == num || num <= 0) {
            num = 1;
        }
        Date date = parseStringToDate(strDate, YYYY_MM_DD_HH_MM_SS);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }
    public static String getLastMinuteDate(Date date,Integer num){
        if (null == num || num <= 0) {
            num = 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @description 获取指定日期多少分钟后的日期
     * @date  2019/6/21 9:42
     * @param strDate 指定日期
     * @param num 多少分钟
     * @return 2019-06-21 17:21:34
     */
    public static String getNextMinuteDate(String strDate,Integer num){
        if (null == num || num <= 0) {
            num = 1;
        }
        Date date = parseStringToDate(strDate, YYYY_MM_DD_HH_MM_SS);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }
    public static String getNextMinuteDate(Date date,Integer num){
        if (null == num || num <= 0) {
            num = 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, num);
        return parseDateToString(cal.getTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @description 获取指定月份的第一天
     * @date  2019/6/21 9:12
     * @param strDate 日期
     * @return 2019-06-01
     */
    public static String getBeginDayByMonth(String strDate) {
        Date date = parseStringToDate(strDate, YYYY_MM);
        return parseDateToString(date, YYYY_MM) + "-01";
    }
    public static String getBeginDayByMonth(Date date) {
        return parseDateToString(date, YYYY_MM) + "-01";
    }


    /**
     * @description 获取指定月份的最后一天
     * @date  2019/6/21 9:14
     * @param strDate 指定日期
     * @return 2019-06-30
     */
    public static String getEndDayByMonth(String strDate) {
        Date date = parseStringToDate(getBeginDayByMonth(strDate),YYYY_MM);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return parseDateToString(calendar.getTime(),YYYY_MM_DD);
    }
    public static String getEndDayByMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return parseDateToString(calendar.getTime(),YYYY_MM_DD);
    }

    /**
     * @description 获取指定月份总天数
     * @date  2019/6/21 9:48
     * @param month 指定月份
     * @return 30
     */
    public static int getDaysByMonth(String month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseStringToDate(month, YYYY_MM));
        calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
        calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return calendar.get(Calendar.DATE);
    }
    public static int getDaysByMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
        calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return calendar.get(Calendar.DATE);
    }

    /**
     * 凌晨
     * @param dateStr
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     *       1 返回yyyy-MM-dd 23:59:59日期
     */
    public static Date weeHours(String dateStr, int flag) {
        Date date = parseStringToDate(dateStr, YYYY_MM_DD_HH_MM_SS);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //时分秒（毫秒数）
        long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;
        //凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);

        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            //凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis()+23*60*60*1000 + 59*60*1000 + 59*1000);
        }
        return cal.getTime();
    }

    /**
     * 凌晨
     * @param date
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     *       1 返回yyyy-MM-dd 23:59:59日期
     * @return
     */
    public static Date weeHours(Date date, int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //时分秒（毫秒数）
        long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;
        //凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);

        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            //凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis()+23*60*60*1000 + 59*60*1000 + 59*1000);
        }
        return cal.getTime();
    }


    /**
     * @description 获取两个时间月份之差
     * @date  2019/6/21 9:53
     * @param oneDate 第一个月份
     * @param twoDate 第二个月份
     * @return 2
     */
    public static long getMonthQuot(String oneDate, String twoDate) {
        long quot = 0;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(parseStringToDate(oneDate, YYYY_MM));
        long yearDate1 = cal1.get(Calendar.YEAR);
        long monthDate1 = cal1.get(Calendar.MONTH) + 1;
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(parseStringToDate(twoDate, YYYY_MM));
        long yearDate2 = cal2.get(Calendar.YEAR);
        long monthDate2 = cal2.get(Calendar.MONTH) + 1;
        quot = (yearDate1 - yearDate2) * 12 + (monthDate1 - monthDate2);
        return quot;
    }
    public static long getMonthQuot(Date oneDate, Date twoDate) {
        long quot = 0;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(oneDate);
        long yearDate1 = cal1.get(Calendar.YEAR);
        long monthDate1 = cal1.get(Calendar.MONTH) + 1;
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(twoDate);
        long yearDate2 = cal2.get(Calendar.YEAR);
        long monthDate2 = cal2.get(Calendar.MONTH) + 1;
        quot = (yearDate1 - yearDate2) * 12 + (monthDate1 - monthDate2);
        return quot;
    }

    /**
     * @description 获取两个日期之差
     * @date  2019/6/21 9:55
     * @param oneDate 第一个日期
     * @param twoDate 第二个日期
     * @return 67
     */
    public static long getDateQuot(String oneDate, String twoDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseStringToDate(oneDate,YYYY_MM_DD));
        long time1 = cal.getTimeInMillis();
        Calendar ca2 = Calendar.getInstance();
        ca2.setTime(parseStringToDate(twoDate,YYYY_MM_DD));
        long time2 = ca2.getTimeInMillis();
        long between_days = (time1 - time2)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    public static long getDateQuot(Date oneDate, Date twoDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(oneDate);
        long time1 = cal.getTimeInMillis();
        Calendar ca2 = Calendar.getInstance();
        ca2.setTime(twoDate);
        long time2 = ca2.getTimeInMillis();
        long between_days = (time1 - time2)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取前几个月的集合
     * @param num 标记为多少就是几个月
     * @return
     */
    public static List<String> getMonthList(Integer num){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -num);
        List<String> previousSixMonths = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            cal.add(Calendar.MONTH, 1);
            previousSixMonths.add(format.format(cal.getTime()));
        }
        System.out.println(previousSixMonths);
        return previousSixMonths;
    }

    /**
     * @description 查询两个时间的中间月份集合
     * @param startStr 开始日期
     * @param endStr 结束日期
     * @return 中间月份的集合
     * @date 2018/9/12 9:33
     */
    public static List<String> getMonthsByDates(String startStr, String endStr) {
        try {
            ArrayList<String> result = new ArrayList<String>();
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM);//格式化为年月
            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();
            min.setTime(sdf.parse(startStr));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
            max.setTime(sdf.parse(endStr));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static List<String> getMonthsByDates(Date startStr, Date endStr)  {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM);//格式化为年月
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(startStr);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        max.setTime(endStr);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }

    /**
     * @description 查询两个时间的中间每周集合
     * @date  2019/6/21 10:01
     * @param @param startStr 开始日期
     * @param endStr 结束日期
     * @return
     */
    public static List<String> getWeeksByDates(String startStr, String endStr)  {
        try {
            ArrayList<String> result = new ArrayList<String>();
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);//格式化为年月
            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();
            min.setTime(sdf.parse(startStr));
            max.setTime(sdf.parse(endStr));
            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.WEEK_OF_MONTH, 1);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static List<String> getWeeksByDates(Date startStr, Date endStr)  {
        try {
            ArrayList<String> result = new ArrayList<String>();
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);//格式化为年月
            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();
            min.setTime(startStr);
            max.setTime(endStr);
            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.WEEK_OF_MONTH, 1);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @description 获取指定日期的月份有多少个工作日
     * @date  2019/6/21 10:05
     * @param dateStr 指定月份
     * @return {month=6, year=2019, weeksAmount=5, workDaysAmount=20, daysAmount=30}
     */
    public static Map<Object, Integer> getWorkDaysByDate(String dateStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseStringToDate(dateStr,YYYY_MM_DD));
        Map<Object, Integer> map = new HashMap<Object, Integer>();
        int workDays = 0;
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        try {
            calendar.set(Calendar.DATE, 1);//从每月1号开始
            for (int i = 0; i < days; i++) {
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                if (!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)) {
                    workDays++;
                }
                calendar.add(Calendar.DATE, 1);
            }
            map.put("year", calendar.get(Calendar.YEAR));//实时年份
            map.put("month", calendar.get(Calendar.MONTH));//实时月份
            map.put("weeksAmount", calendar.getActualMaximum(Calendar.WEEK_OF_MONTH));//周
            map.put("workDaysAmount", workDays);//工作日
            map.put("daysAmount", days);//自然日
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }
    public static Map<Object, Integer> getWorkDaysByDate(Date dateStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateStr);
        Map<Object, Integer> map = new HashMap<Object, Integer>();
        int workDays = 0;
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        try {
            calendar.set(Calendar.DATE, 1);//从每月1号开始
            for (int i = 0; i < days; i++) {
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                if (!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)) {
                    workDays++;
                }
                calendar.add(Calendar.DATE, 1);
            }
            map.put("year", calendar.get(Calendar.YEAR));//实时年份
            map.put("month", calendar.get(Calendar.MONTH));//实时月份
            map.put("weeksAmount", calendar.getActualMaximum(Calendar.WEEK_OF_MONTH));//周
            map.put("workDaysAmount", workDays);//工作日
            map.put("daysAmount", days);//自然日
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * @description 获取两个数之间的工作日
     * @date  2019/6/21 10:08
     * @param strStartDate 开始日期
     * @param strEndDate 结束日期
     * @return 48
     */
    public static int getDutyDays(String strStartDate,String strEndDate) {
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
        Calendar cl1 = Calendar.getInstance();
        Calendar cl2 = Calendar.getInstance();
        try {
            cl1.setTime(df.parse(strStartDate));
            cl2.setTime(df.parse(strEndDate));
        } catch (ParseException e) {
            System.out.println("日期格式非法");
            e.printStackTrace();
        }
        int count = 0;
        while (cl1.compareTo(cl2) <= 0) {
            if (cl1.get(Calendar.DAY_OF_WEEK) != 7 && cl1.get(Calendar.DAY_OF_WEEK) != 1)
                count++;
            cl1.add(Calendar.DAY_OF_MONTH, 1);
        }
        return count;
    }
    public static int getDutyDays(Date strStartDate,Date strEndDate) {
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
        Calendar cl1 = Calendar.getInstance();
        Calendar cl2 = Calendar.getInstance();
        cl1.setTime(strStartDate);
        cl2.setTime(strEndDate);
        int count = 0;
        while (cl1.compareTo(cl2) <= 0) {
            if (cl1.get(Calendar.DAY_OF_WEEK) != 7 && cl1.get(Calendar.DAY_OF_WEEK) != 1)
                count++;
            cl1.add(Calendar.DAY_OF_MONTH, 1);
        }
        return count;
    }

    /**
     * @description 由出生日期获得年龄
     * @date  2019/6/21 10:09
     * @param birthDay 生日日期
     * @return 32
     */
    public static int getAge(String birthDay) {
        Date date = parseStringToDate(birthDay, YYYY_MM_DD);
        Calendar cal = Calendar.getInstance();
        if (cal.before(date)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(date);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }

    /**
     * 计算开始事件和结束时间之间的时间差
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static String getStringBetweenStartTimeAndEndTime(Date startTime, Date endTime) {
        long nd = 1000 * 24 * 60 * 60;//每天毫秒数
        long nh = 1000 * 60 * 60;//每小时毫秒数
        long nm = 1000 * 60;//每分钟毫秒数
        long ts = 0L;
        if(endTime.after(startTime)){
            ts=endTime.getTime()-startTime.getTime();
        }else{
            ts=startTime.getTime()-endTime.getTime();
        }
        long day = ts / nd;//计算多少天
        long hour = ts % nd / nh;//计算多少小时
        long min = ts % nd % nh / nm;// 计算差多少分钟
        if(0 == day){
            if(0 == hour){
                return min + "分钟";
            }else{
                return hour + "小时" + min + "分钟";
            }
        }else{
            return day + "天" + hour + "小时" + min + "分钟";
        }
    }

    public static void main(String[] args) {

//        String dataStr = "2023-01-01 03:20:22";
//
//        System.out.println("getNowDate()====>" + getNowDate());
//        System.out.println("getCurrentDate()====>" + getCurrentDate());
//        System.out.println("getCurrentTime()====>" + getCurrentTime());
//        System.out.println("geCurrentDateTime()====>" + geCurrentDateTime());
//
//        System.out.println("getDate()====>" + getDate(new Date()));
//        System.out.println("getTime()====>" + getTime(new Date()));
//        System.out.println("getFormatDate()====>" + getFormatDate(dataStr));
//
//        System.out.println("parseDateToString()====>" + parseDateToString(new Date(), YYYY_MM_DD_HH_MM_SS));
//        System.out.println("parseStringToDate()====>" + parseStringToDate(dataStr, YYYY_MM_DD_HH_MM_SS));
//
//        System.out.println("getCurrentDate()====>" + getCurrentDate("Y"));
//        System.out.println("getCurrentDate()====>" + getCurrentDate("M"));
//        System.out.println("getCurrentDate()====>" + getCurrentDate("D"));
//
//        System.out.println("getParamDate()====>" + getParamDate(dataStr));
//        System.out.println("getParamDate()====>" + getParamDate(new Date()));
//
//        System.out.println("getLastYearDate()====>" + getLastYearDate(dataStr, 1));
//        System.out.println("getLastYearDate()====>" + getLastYearDate(new Date(), 2));
//        System.out.println("getNextYearDate()====>" + getNextYearDate(dataStr, 1));
//        System.out.println("getNextYearDate()====>" + getNextYearDate(new Date(), 2));

//        Long currentTime =  System.currentTimeMillis();
//        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date(currentTime);
//        System.out.println(getLastMonthDate(new Date(), 6));
//        System.out.println(getMonthsByDates(new Date(), new Date()));
        System.out.println(getFormatDate(getLastMonthDate(parseStringToDate("2023-07", YYYY_MM), 1), YYYY_MM));
        System.out.println(getFormatDate(getLastYearDate(parseStringToDate("2023-07", YYYY_MM), 1), YYYY_MM));
    }
}
