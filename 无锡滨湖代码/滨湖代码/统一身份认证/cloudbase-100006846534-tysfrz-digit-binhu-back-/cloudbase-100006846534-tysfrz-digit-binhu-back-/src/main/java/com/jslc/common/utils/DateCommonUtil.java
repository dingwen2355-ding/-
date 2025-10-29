/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package com.jslc.common.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jslc.common.constant.SwapConstant;
import com.jslc.common.vo.BeginEndTimeVO;
import org.springblade.core.tool.utils.DateTimeUtil;
import org.springblade.core.tool.utils.Func;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author Chill
 */
public class DateCommonUtil {

    public static final String SECODE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MINTUE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String HOUR_FORMAT = "yyyy-MM-dd HH";
    public static final String DAY_FORMAT = "yyyy-MM-dd";
    public static final String MONTH_FORMAT = "yyyy-MM";
    public static final String YEAR_FORMAT = "yyyy";

    public static final DateTimeFormatter SECODE_DATEFORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter MINTUE_DATEFORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter HOUR_DATEFORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00");
    public static final DateTimeFormatter DAY_DATEFORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter MONTH_DATEFORMAT = DateTimeFormatter.ofPattern("yyyy-MM");
    public static final DateTimeFormatter YEAR_DATEFORMAT = DateTimeFormatter.ofPattern("yyyy");
	public static final DateTimeFormatter MONTH_DAY_DATEFORMAT = DateTimeFormatter.ofPattern("MM-dd");
	public static final DateTimeFormatter HOUR_MINUTE_DATEFORMAT = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * @param localDateTime
     * @return java.lang.String
     * @description 将第二天零点的时间转成上一天24点
     * @author xxz
     * @date 2020/11/25
     **/
    public static String ZeroFormatTwentyFour(LocalDateTime localDateTime) {
        Date time = DateUtil.offsetDay(DateUtil.date(localDateTime), -1);
        String date = DateUtil.formatDate(time) + " 24:00:00";
        return date;
    }

    /**
     * @param start
     * @param end
     * @return java.lang.String
     * @throws
     * @methodName getDistanceTime
     * @description 获取两个时间差
     * @author xxz
     * @date 2020/7/15
     **/
    public static String getDistanceTime(LocalDateTime start, LocalDateTime end) {

        String msg = "";

        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;

        //时间差，毫秒
        Long diff = LocalDateTimeUtil.between(start, end == null ? LocalDateTime.now() : end).toMillis();

        //天，小时，分，秒
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        if (day != 0) {
            msg += day + "天";
        }
        if (hour != 0) {
            msg += hour + "小时";
        }
        if (min != 0) {
            msg += min + "分";
        }
        return msg;
    }

    public static LocalDateTime getTimeByString(String date, LocalTime time) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return LocalDateTime.of(DateTimeUtil.parseDate(date, DateTimeUtil.DATE_FORMAT), time);
    }

    /*
     * @method getConditionalExpression
     * @description 预警条件
     * @author xxz
     * @date 2022/2/18
     * @param conditionalExpression
     */
    public static String getConditionalExpression(String conditionalExpression) {

        if (Func.isNotEmpty(conditionalExpression)) {
            //去掉collVal字符
            conditionalExpression = conditionalExpression.replace("collVal","");

            if (conditionalExpression.contains(">=")) {
                conditionalExpression = conditionalExpression.replace(">=", "大于等于");
            }
            if (conditionalExpression.contains(">")) {
                conditionalExpression = conditionalExpression.replace(">", "大于");
            }
            if (conditionalExpression.contains("<=")) {
                conditionalExpression = conditionalExpression.replace("<=", "小于等于");
            }
            if (conditionalExpression.contains("<")) {
                conditionalExpression = conditionalExpression.replace("<", "小于");
            }
            if (conditionalExpression.contains("||")) {
                conditionalExpression = conditionalExpression.replace("||", "或");
            }
            if (conditionalExpression.contains("&")) {
                conditionalExpression = conditionalExpression.replace("&", "且");
            }
        }

        return conditionalExpression;
    }


    public static BeginEndTimeVO getBeginAndEndTime(String time, int type) {

        BeginEndTimeVO result = new BeginEndTimeVO();

        Date begin = new Date();
        Date end = new Date();

        //type报表类型 ： 1-时报 2-日报 3-周报 4-月报 5-年报
        if (type == SwapConstant.ReportType.hour.getValue()) {
            begin = DateUtil.parse(time + ":00:00", SECODE_FORMAT);
            end = DateUtil.parse(time + ":59:59", SECODE_FORMAT);
        } else if (type == SwapConstant.ReportType.day.getValue()) {
            Date dateTime = DateUtil.parse(time, DAY_FORMAT);
            begin = DateUtil.beginOfDay(dateTime);
            end = DateUtil.endOfDay(dateTime);
        } else if (type == SwapConstant.ReportType.week.getValue()) {
            Date dateTime = DateUtil.parse(time, DAY_FORMAT);
            begin = DateUtil.beginOfWeek(dateTime);
            end = DateUtil.endOfWeek(dateTime);
        } else if (type == SwapConstant.ReportType.month.getValue()) {
            Date dateTime = DateUtil.parse(time, MONTH_FORMAT);
            begin = DateUtil.beginOfMonth(dateTime);
            end = DateUtil.endOfMonth(dateTime);
        } else if (type == SwapConstant.ReportType.year.getValue()) {
            DateTime dateTime = DateUtil.parse(time, YEAR_FORMAT);
            begin = DateUtil.beginOfYear(dateTime);
            end = DateUtil.endOfYear(dateTime);
        }

        result.setBegin(LocalDateTimeUtil.of(begin));
        result.setEnd(LocalDateTimeUtil.of(end));
        return result;
    }
}
