package com.grandlynn.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

public class DateUtils {
   public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
   public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
   public static final String TIME_FORMAT = "HH:mm";
   public static final String DATE_FORMAT = "yyyy-MM-dd";
   public static final String MONTH_FORMAT = "yyyy-MM";
   public static final String DAY_FORMAT = "dd";
   public static final String DATE_PATTERN = "^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$";
   public static long millionSecondsOfDay = 86400000L;
   public static long millionSecondsOfHour = 3600000L;
   private static final String FORMAT_DATE_STR = "yyyy-MM-dd";

   public static boolean isDate(String dateAsText) {
      return StringUtils.isNotEmpty(dateAsText) && dateAsText.matches("^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$");
   }

   public static Date now() {
      return new Date();
   }

   public static String toDateText(Date date) {
      return toDateText(date, "yyyy-MM-dd");
   }

   public static String toDateText(Date date, String pattern) {
      if (date != null && pattern != null) {
         SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
         return dateFormat.format(date);
      } else {
         return null;
      }
   }

   public static Date getDate(String dateText) {
      return getDate(dateText, "yyyy-MM-dd");
   }

   public static Date getDate(String dateText, String pattern) {
      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

      try {
         return dateFormat.parse(dateText);
      } catch (ParseException var4) {
         throw new IllegalStateException("Parse date from [" + dateText + "," + pattern + "] failed", var4);
      }
   }

   public static String toDateTime(Date date) {
      return toDateText(date, "yyyy-MM-dd HH:mm");
   }

   public static int currentYear() {
      return calendar().get(1);
   }

   public static Calendar calendar() {
      return Calendar.getInstance();
   }

   public static String getTimeShort() {
      SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
      Date currentTime = new Date();
      String dateString = formatter.format(currentTime);
      return dateString;
   }

   public static Date getLastDate(long day) {
      Date date = new Date();
      long date3hm = date.getTime() - 122400000L * day;
      Date date3hmdate = new Date(date3hm);
      return date3hmdate;
   }

   public static String getStringToday() {
      Date currentTime = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
      String dateString = formatter.format(currentTime);
      return dateString;
   }

   public static String getHour() {
      Date currentTime = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String dateString = formatter.format(currentTime);
      String hour = dateString.substring(11, 13);
      return hour;
   }

   public static String getMinute() {
      Date currentTime = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String dateString = formatter.format(currentTime);
      String min = dateString.substring(14, 16);
      return min;
   }

   public static String getTwoHourInterval(String st1, String st2) {
      String[] kk = null;
      String[] jj = null;
      kk = st1.split(":");
      jj = st2.split(":");
      if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0])) {
         return "0";
      } else {
         double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60.0;
         double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60.0;
         return y - u > 0.0 ? y - u + "" : "0";
      }
   }

   public static String getTwoDayInterval(String sj1, String sj2) {
      SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
      long day = 0L;

      try {
         Date date = myFormatter.parse(sj1);
         Date mydate = myFormatter.parse(sj2);
         day = (date.getTime() - mydate.getTime()) / 86400000L;
      } catch (Exception var7) {
         return "";
      }

      return day + "";
   }

   public static String getTimeByInterval(String sj1, String interval) {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String mydate1 = "";

      try {
         Date date1 = format.parse(sj1);
         long time = date1.getTime() / 1000L + (long)(Integer.parseInt(interval) * 60);
         date1.setTime(time * 1000L);
         mydate1 = format.format(date1);
      } catch (Exception var7) {
      }

      return mydate1;
   }

   public static String getDayByInterval(String nowdate, String delay) {
      try {
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         String mdate = "";
         Date d = getDate(nowdate);
         long myTime = d.getTime() / 1000L + (long)(Integer.parseInt(delay) * 24 * 60 * 60);
         d.setTime(myTime * 1000L);
         mdate = format.format(d);
         return mdate;
      } catch (Exception var7) {
         return "";
      }
   }

   public static Date getDayByInterval(Date d, int delay) {
      try {
         long myTime = d.getTime() / 1000L + (long)(delay * 24 * 60 * 60);
         d.setTime(myTime * 1000L);
         return d;
      } catch (Exception var4) {
         return new Date();
      }
   }

   public static boolean isLeapYear(String ddate) {
      Date d = getDate(ddate);
      GregorianCalendar gc = (GregorianCalendar)Calendar.getInstance();
      gc.setTime(d);
      int year = gc.get(1);
      if (year % 400 == 0) {
         return true;
      } else if (year % 4 == 0) {
         return year % 100 != 0;
      } else {
         return false;
      }
   }

   public static Date nextYear(Date date, Integer delay) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(1, delay);
      return calendar.getTime();
   }

   public static String getEndDateOfMonth(String dat) {
      String str = dat.substring(0, 8);
      String month = dat.substring(5, 7);
      int mon = Integer.parseInt(month);
      if (mon != 1 && mon != 3 && mon != 5 && mon != 7 && mon != 8 && mon != 10 && mon != 12) {
         if (mon != 4 && mon != 6 && mon != 9 && mon != 11) {
            if (isLeapYear(dat)) {
               str = str + "29";
            } else {
               str = str + "28";
            }
         } else {
            str = str + "30";
         }
      } else {
         str = str + "31";
      }

      return str;
   }

   public static boolean isSameWeekDates(Date date1, Date date2) {
      Calendar cal1 = Calendar.getInstance();
      Calendar cal2 = Calendar.getInstance();
      cal1.setTime(date1);
      cal2.setTime(date2);
      int subYear = cal1.get(1) - cal2.get(1);
      if (0 == subYear) {
         if (cal1.get(3) == cal2.get(3)) {
            return true;
         }
      } else if (1 == subYear && 11 == cal2.get(2)) {
         if (cal1.get(3) == cal2.get(3)) {
            return true;
         }
      } else if (-1 == subYear && 11 == cal1.get(2) && cal1.get(3) == cal2.get(3)) {
         return true;
      }

      return false;
   }

   public static String getSeqWeek() {
      Calendar c = Calendar.getInstance(Locale.CHINA);
      String week = Integer.toString(c.get(3));
      if (week.length() == 1) {
         week = "0" + week;
      }

      String year = Integer.toString(c.get(1));
      return year + week;
   }

   public static String getWeek(String sdate, String num) {
      Date dd = getDate(sdate);
      Calendar c = Calendar.getInstance();
      c.setTime(dd);
      if ("1".equals(num)) {
         c.set(7, 2);
      } else if ("2".equals(num)) {
         c.set(7, 3);
      } else if ("3".equals(num)) {
         c.set(7, 4);
      } else if ("4".equals(num)) {
         c.set(7, 5);
      } else if ("5".equals(num)) {
         c.set(7, 6);
      } else if ("6".equals(num)) {
         c.set(7, 7);
      } else if ("0".equals(num)) {
         c.set(7, 1);
      }

      return (new SimpleDateFormat("yyyy-MM-dd")).format(c.getTime());
   }

   public static String getWeek(String sdate) {
      Date date = getDate(sdate);
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      return (new SimpleDateFormat("EEEE")).format(c.getTime());
   }

   public static void main(String[] args) {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      System.out.println(df.format(new Date()));
      System.out.println(getWeek(df.format(new Date())));
      System.out.println(getWeekStr(df.format(new Date())));
      df = new SimpleDateFormat("HH mm ss dd MM yyyy");
      System.out.println(df.format(new Date()));
   }

   public static String getWeekStr(String sdate) {
      String str = "";
      str = getWeek(sdate);
      if ("1".equals(str)) {
         str = "星期日";
      } else if ("2".equals(str)) {
         str = "星期一";
      } else if ("3".equals(str)) {
         str = "星期二";
      } else if ("4".equals(str)) {
         str = "星期三";
      } else if ("5".equals(str)) {
         str = "星期四";
      } else if ("6".equals(str)) {
         str = "星期五";
      } else if ("7".equals(str)) {
         str = "星期六";
      }

      return str;
   }

   public static long getDays(String date1, String date2) {
      if (date1 != null && !"".equals(date1)) {
         if (date2 != null && !"".equals(date2)) {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            Date mydate = null;

            try {
               date = myFormatter.parse(date1);
               mydate = myFormatter.parse(date2);
            } catch (Exception var7) {
            }

            long day = (date.getTime() - mydate.getTime()) / 86400000L;
            return day;
         } else {
            return 0L;
         }
      } else {
         return 0L;
      }
   }

   public static String getNowMonth(String sdate) {
      sdate = sdate.substring(0, 8) + "01";
      Date date = getDate(sdate);
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      int u = c.get(7);
      String newday = getDayByInterval(sdate, 1 - u + "");
      return newday;
   }

   public static boolean rightDate(String date) {
      new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      if (date == null) {
         return false;
      } else {
         SimpleDateFormat sdf;
         if (date.length() > 10) {
            sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
         } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
         }

         try {
            sdf.parse(date);
            return true;
         } catch (ParseException var3) {
            return false;
         }
      }
   }

   public static int getDifferYear(Date date1, Date date2) {
      Calendar c1 = Calendar.getInstance();
      Calendar c2 = Calendar.getInstance();
      c1.setTime(date1);
      c2.setTime(date2);
      return c2.get(1) - c1.get(1);
   }

   public static int getDifferYear(String date1, String date2) {
      Date dateTime1tmp = parse(date1, "yyyy-MM-dd");
      Date dateTime2tmp = parse(date2, "yyyy-MM-dd");
      return getDifferYear(dateTime1tmp, dateTime2tmp);
   }

   public static int getDifferMonth(Date date1, Date date2) {
      Calendar c1 = Calendar.getInstance();
      Calendar c2 = Calendar.getInstance();
      c1.setTime(date1);
      c2.setTime(date2);
      int year = getDifferYear(date1, date2);
      int months = c2.get(2) - c1.get(2) + year * 12;
      if (c2.get(5) < c1.get(5)) {
         --months;
      }

      return months;
   }

   public static int getDifferMonth(String date1, String date2) {
      Date dateTime1tmp = parse(date1, "yyyy-MM-dd");
      Date dateTime2tmp = parse(date2, "yyyy-MM-dd");
      return getDifferMonth(dateTime1tmp, dateTime2tmp);
   }

   /** @deprecated */
   private static int getDifferDay(Date date1, Date date2) {
      Long d1 = date1.getTime();
      Long d2 = date2.getTime();
      return (int)((d2 - d1) / millionSecondsOfDay);
   }

   public static int getDifferDay(String date1, String date2) {
      Date dateTime1tmp = parse(date1, "yyyy-MM-dd");
      Date dateTime2tmp = parse(date2, "yyyy-MM-dd");
      Long d1 = dateTime1tmp.getTime();
      Long d2 = dateTime2tmp.getTime();
      return (int)((d2 - d1) / millionSecondsOfDay);
   }

   public static int[] getDifferHourAndMinute(String date1, String time1, String date2, String time2) {
      Date dateTime1tmp = parse(date1 + " " + time1, "yyyy-MM-dd HH:mm");
      Date dateTime2tmp = parse(date2 + " " + time2, "yyyy-MM-dd HH:mm");
      Long d2 = dateTime2tmp.getTime();
      Long d1 = dateTime1tmp.getTime();
      int hours = (int)((d2 - d1) / millionSecondsOfHour);
      int mins = (int)((d2 - d1) % millionSecondsOfHour);
      mins /= 60000;
      int[] resultHM = new int[]{hours, mins};
      return resultHM;
   }

   public static int[] getDifferHourAndMinute(Date date1, Date date2) {
      Long d2 = date2.getTime();
      Long d1 = date1.getTime();
      int hours = (int)((d2 - d1) / millionSecondsOfHour);
      int mins = (int)((d2 - d1) % millionSecondsOfHour);
      mins /= 60000;
      int[] resultHM = new int[]{hours, mins};
      return resultHM;
   }

   public static int[] getDifferHourAndMinute(String date1, String date2) {
      Date dateTime1tmp = parse(date1, "yyyy-MM-dd HH:mm");
      Date dateTime2tmp = parse(date2, "yyyy-MM-dd HH:mm");
      Long d2 = dateTime2tmp.getTime();
      Long d1 = dateTime1tmp.getTime();
      int hours = (int)((d2 - d1) / millionSecondsOfHour);
      int mins = (int)((d2 - d1) % millionSecondsOfHour);
      mins /= 60000;
      int[] resultHM = new int[]{hours, mins};
      return resultHM;
   }

   public static int getDifferHour(Date date1, Date date2) {
      Long d1 = date1.getTime();
      Long d2 = date2.getTime();
      int hours = (int)((d2 - d1) / millionSecondsOfHour);
      return hours;
   }

   public static int getDifferHour(String date1, String date2) {
      Date dateTime1tmp = parse(date1, "yyyy-MM-dd HH:mm");
      Date dateTime2tmp = parse(date2, "yyyy-MM-dd HH:mm");
      Long d2 = dateTime2tmp.getTime();
      Long d1 = dateTime1tmp.getTime();
      int hours = (int)((d2 - d1) / millionSecondsOfHour);
      return hours;
   }

   public static int getDifferMinute(String date1, String date2) {
      Date dateTime1tmp = parse(date1, "yyyy-MM-dd HH:mm");
      Date dateTime2tmp = parse(date2, "yyyy-MM-dd HH:mm");
      return getDifferMinute(dateTime1tmp, dateTime2tmp);
   }

   public static int getDifferMinute(Date date1, Date date2) {
      Long d2 = date2.getTime();
      Long d1 = date1.getTime();
      int hours = (int)((d2 - d1) / 60000L);
      return hours;
   }

   public static Date addYear(Date date, int years) {
      Calendar c = new GregorianCalendar();
      c.setTime(date);
      c.add(1, years);
      return c.getTime();
   }

   public static Date addMonth(Date date, int months) {
      Calendar c = new GregorianCalendar();
      c.setTime(date);
      c.add(2, months);
      return c.getTime();
   }

   public static Date addDay(Date date, int days) {
      Calendar c = new GregorianCalendar();
      c.setTime(date);
      c.add(5, days);
      return c.getTime();
   }

   public static Date minusDay(Date date, int days) {
      Calendar c = new GregorianCalendar();
      c.setTime(date);
      c.set(5, c.get(5) - days);
      return c.getTime();
   }

   public static Date addMinutes(Date date, int minutes) {
      Calendar c = new GregorianCalendar();
      c.setTime(date);
      c.add(12, minutes);
      return c.getTime();
   }

   public static Date addHour(Date date, int hour) {
      Calendar c = new GregorianCalendar();
      c.setTime(date);
      c.add(10, hour);
      return c.getTime();
   }

   public static String format(Date date) {
      return format(date, "yyyy-MM-dd");
   }

   public static String format(Date date, String formater) {
      SimpleDateFormat sdf = new SimpleDateFormat(formater);
      return sdf.format(date);
   }

   public static Date parse(String date) {
      return parse(date, "yyyy-MM-dd");
   }

   public static Date parse(String date, String formater) {
      SimpleDateFormat sdf = new SimpleDateFormat(formater);
      Date result = null;

      try {
         result = sdf.parse(date);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return result;
   }

   public static int getWeekOfDate(Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.get(7) - 1 == 0 ? 7 : calendar.get(7) - 1;
   }

   public static String getWeekTextOfDate(Date date) {
      String[] dayNames = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
      int t = getWeekOfDate(date);
      if (t == 7) {
         t = 0;
      }

      return dayNames[t];
   }

   public static String getCurrenDate() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date d = new Date();
      return sdf.format(d).toString();
   }

   public static String getCurrentTime() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date d = new Date();
      return sdf.format(d).toString();
   }

   public static java.sql.Date convertUtilDateToSQLDate(Date date) {
      if (date == null) {
         return null;
      } else {
         Calendar cl = Calendar.getInstance();
         cl.setTime(date);
         java.sql.Date jd = new java.sql.Date(cl.getTimeInMillis());
         return jd;
      }
   }

   public static Date convertSQLDateToUtilDate(java.sql.Date date) {
      if (date == null) {
         return null;
      } else {
         Calendar cl = Calendar.getInstance();
         cl.setTime(date);
         Date jd = new Date(cl.getTimeInMillis());
         return jd;
      }
   }

   public static boolean isLeapYear(int year) {
      if (year % 400 == 0) {
         return true;
      } else if (year % 4 == 0) {
         return year % 100 != 0;
      } else {
         return false;
      }
   }

   public static boolean isToday(Date date) {
      Calendar today = Calendar.getInstance();
      today.setTime(new Date());
      Calendar day = Calendar.getInstance();
      day.setTime(date);
      return today.get(1) == day.get(1) && today.get(2) == day.get(2) && today.get(5) == day.get(5);
   }

   public static Timestamp getSysTimestamp() {
      return new Timestamp(System.currentTimeMillis());
   }

   public static Date getSysDate() {
      Calendar cl = Calendar.getInstance();
      return cl.getTime();
   }

   public static boolean isBetweenDateByClosedInterval(Date date1, Date date2) {
      String nowDate = format(new Date(), "yyyy-MM-dd");
      String sDate1 = format(date1, "yyyy-MM-dd");
      String sDate2 = format(date2, "yyyy-MM-dd");
      if (!java.sql.Date.valueOf(sDate1).before(java.sql.Date.valueOf(sDate2)) && !java.sql.Date.valueOf(sDate1).equals(java.sql.Date.valueOf(sDate2))) {
         return (java.sql.Date.valueOf(nowDate).after(java.sql.Date.valueOf(sDate2)) || java.sql.Date.valueOf(nowDate).equals(java.sql.Date.valueOf(sDate2))) && java.sql.Date.valueOf(nowDate).before(java.sql.Date.valueOf(sDate1)) || java.sql.Date.valueOf(nowDate).equals(java.sql.Date.valueOf(sDate1));
      } else {
         return (java.sql.Date.valueOf(nowDate).after(java.sql.Date.valueOf(sDate1)) || java.sql.Date.valueOf(nowDate).equals(java.sql.Date.valueOf(sDate1))) && (java.sql.Date.valueOf(nowDate).before(java.sql.Date.valueOf(sDate2)) || java.sql.Date.valueOf(nowDate).equals(java.sql.Date.valueOf(sDate2)));
      }
   }

   public static boolean isBetweenDateByOpenInterval(Date date1, Date date2) {
      String nowDate = format(new Date(), "yyyy-MM-dd");
      String sDate1 = format(date1, "yyyy-MM-dd");
      String sDate2 = format(date2, "yyyy-MM-dd");
      if (java.sql.Date.valueOf(sDate1).before(java.sql.Date.valueOf(sDate2))) {
         return java.sql.Date.valueOf(nowDate).after(java.sql.Date.valueOf(sDate1)) && java.sql.Date.valueOf(nowDate).before(java.sql.Date.valueOf(sDate2));
      } else if (java.sql.Date.valueOf(sDate1).after(java.sql.Date.valueOf(sDate2))) {
         return java.sql.Date.valueOf(nowDate).after(java.sql.Date.valueOf(sDate2)) && java.sql.Date.valueOf(nowDate).before(java.sql.Date.valueOf(sDate1));
      } else {
         return false;
      }
   }

   public static boolean dateisBetweenDateByClosedInterval(Date curDate, String sDate1, String date2) {
      String nowDate = format(curDate, "yyyy-MM-dd");
      if (!java.sql.Date.valueOf(sDate1).before(java.sql.Date.valueOf(date2)) && !java.sql.Date.valueOf(sDate1).equals(java.sql.Date.valueOf(date2))) {
         return (java.sql.Date.valueOf(nowDate).after(java.sql.Date.valueOf(date2)) || java.sql.Date.valueOf(nowDate).equals(java.sql.Date.valueOf(date2))) && java.sql.Date.valueOf(nowDate).before(java.sql.Date.valueOf(sDate1)) || java.sql.Date.valueOf(nowDate).equals(java.sql.Date.valueOf(sDate1));
      } else {
         return (java.sql.Date.valueOf(nowDate).after(java.sql.Date.valueOf(sDate1)) || java.sql.Date.valueOf(nowDate).equals(java.sql.Date.valueOf(sDate1))) && (java.sql.Date.valueOf(nowDate).before(java.sql.Date.valueOf(date2)) || java.sql.Date.valueOf(nowDate).equals(java.sql.Date.valueOf(date2)));
      }
   }

   public static boolean dateisBetweenDateByOpenInterval(Date curDate, String sDate1, String date2) {
      String nowDate = format(curDate, "yyyy-MM-dd");
      if (java.sql.Date.valueOf(sDate1).before(java.sql.Date.valueOf(date2))) {
         return java.sql.Date.valueOf(nowDate).after(java.sql.Date.valueOf(sDate1)) && java.sql.Date.valueOf(nowDate).before(java.sql.Date.valueOf(date2));
      } else if (java.sql.Date.valueOf(sDate1).after(java.sql.Date.valueOf(date2))) {
         return java.sql.Date.valueOf(nowDate).after(java.sql.Date.valueOf(date2)) && java.sql.Date.valueOf(nowDate).before(java.sql.Date.valueOf(sDate1));
      } else {
         return false;
      }
   }

   public static boolean isBetweenDateByClosedInterval(String sDate1, String date2) {
      String nowDate = format(new Date(), "yyyy-MM-dd");
      if (!java.sql.Date.valueOf(sDate1).before(java.sql.Date.valueOf(date2)) && !java.sql.Date.valueOf(sDate1).equals(java.sql.Date.valueOf(date2))) {
         return (java.sql.Date.valueOf(nowDate).after(java.sql.Date.valueOf(date2)) || java.sql.Date.valueOf(nowDate).equals(java.sql.Date.valueOf(date2))) && java.sql.Date.valueOf(nowDate).before(java.sql.Date.valueOf(sDate1)) || java.sql.Date.valueOf(nowDate).equals(java.sql.Date.valueOf(sDate1));
      } else {
         return (java.sql.Date.valueOf(nowDate).after(java.sql.Date.valueOf(sDate1)) || java.sql.Date.valueOf(nowDate).equals(java.sql.Date.valueOf(sDate1))) && (java.sql.Date.valueOf(nowDate).before(java.sql.Date.valueOf(date2)) || java.sql.Date.valueOf(nowDate).equals(java.sql.Date.valueOf(date2)));
      }
   }

   public static boolean isBetweenDateByOpenInterval(String sDate1, String date2) {
      String nowDate = format(new Date(), "yyyy-MM-dd");
      if (java.sql.Date.valueOf(sDate1).before(java.sql.Date.valueOf(date2))) {
         return java.sql.Date.valueOf(nowDate).after(java.sql.Date.valueOf(sDate1)) && java.sql.Date.valueOf(nowDate).before(java.sql.Date.valueOf(date2));
      } else if (java.sql.Date.valueOf(sDate1).after(java.sql.Date.valueOf(date2))) {
         return java.sql.Date.valueOf(nowDate).after(java.sql.Date.valueOf(date2)) && java.sql.Date.valueOf(nowDate).before(java.sql.Date.valueOf(sDate1));
      } else {
         return false;
      }
   }

   public static String formatToEng(String date) {
      DateFormat df = new SimpleDateFormat("MMM", Locale.ENGLISH);
      return df.format(parse(date));
   }

   public static String getCurrentTimeNum() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
      Date d = new Date();
      return sdf.format(d).toString();
   }

   public static int[] getTimeByCalendar(Date date) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      int year = cal.get(1);
      int month = cal.get(2) + 1;
      int day = cal.get(5);
      int hour = cal.get(11);
      int minute = cal.get(12);
      int second = cal.get(13);
      int[] str = new int[]{year, month, day, hour, minute, second};
      System.out.println("现在的时间是：公元" + year + "年" + month + "月" + day + "日      " + hour + "时" + minute + "分" + second + "秒       星期");
      return str;
   }
}
