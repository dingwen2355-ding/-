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
package com.jslc.modules.szbh.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.common.utils.CommonUtil;
import com.jslc.common.utils.DateCommonUtil;
import com.jslc.common.utils.DateUtil;
import com.jslc.modules.szbh.entity.BhSmsSend;
import com.jslc.modules.szbh.mapper.BhSmsSendMapper;
import com.jslc.modules.szbh.service.IBhSmsSendService;
import com.jslc.modules.szbh.vo.*;
import org.springblade.core.tool.utils.DateTimeUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 短信通知记录 服务实现类
 *
 * @author BladeX
 * @since 2023-05-18
 */
@Service
public class BhSmsSendServiceImpl extends ServiceImpl<BhSmsSendMapper, BhSmsSend> implements IBhSmsSendService {

    @Override
    public IPage<BhSmsSendVO> getList(IPage<BhSmsSendVO> page, String start, String end, String param, Integer result) {
        return page.setRecords(getAllList(page, start, end, param, result));
    }

    public List<BhSmsSendVO> getAllList(IPage<BhSmsSendVO> page, String start, String end, String param, Integer result) {
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        if (Func.isNotEmpty(start)) {
            startTime = LocalDateTime.of(DateTimeUtil.parseDate(start, DateCommonUtil.DAY_FORMAT), LocalTime.MIN);
        }

        if (Func.isNotEmpty(end)) {
            endTime = LocalDateTime.of(DateTimeUtil.parseDate(end, DateCommonUtil.DAY_FORMAT), LocalTime.MAX);
        }

        List<BhSmsSendVO> list = baseMapper.getList(page, startTime, endTime, param, result);
        if (CollectionUtils.isNotEmpty(list)) {
            for (BhSmsSendVO bhSmsSendVO : list) {
                bhSmsSendVO.setAppName(Func.isNotEmpty(bhSmsSendVO.getAppName()) && bhSmsSendVO.getAppName().length() > 0 ? bhSmsSendVO.getAppName() : "统一认证");
            }
        }
        return list;
    }


    @Override
    public IPage<AppRecordVO> getSmsPage(IPage<AppRecordVO> page, Integer type, String start, String end, String deptIds, String appIds, Integer result) {
        List<AppRecordVO> list = getRecordDate(type, start, end, deptIds, appIds, result);
        IPage iPage = CommonUtil.manualPage(page, list);
        return iPage;

    }


    @Override
    public List<HeadAppInfoVO> getHead(Integer type, String start, String end, String deptIds, String appIds, Integer resultType) {
        List<HeadAppInfoVO> result = new ArrayList<>();
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        List<Long> deptId = null;
        List<Long> appId = null;
        if (Func.isNotEmpty(deptIds)) {
            deptId = Func.toLongList(deptIds);
        }
        if (Func.isNotEmpty(appIds)) {
            appId = Func.toLongList(appIds);
        }

        if (type != null && type == 1 && Func.isNotEmpty(start)) {//yyyy-MM-dd
            startTime = LocalDateTime.of(LocalDate.parse(start, DateTimeUtil.DATE_FORMAT), LocalTime.MIN);
            endTime = LocalDateTime.of(LocalDate.parse(end, DateTimeUtil.DATE_FORMAT), LocalTime.MAX);
        } else if (type != null && type == 2 && Func.isNotEmpty(start)) {//yyyy-MM
            startTime = LocalDateTime.of(DateTimeUtil.parseDate(DateUtil.getMinDateMonth(start), DateCommonUtil.DAY_FORMAT), LocalTime.MIN);
            endTime = LocalDateTime.of(DateTimeUtil.parseDate(DateUtil.getMaxDateMonth(end), DateCommonUtil.DAY_FORMAT), LocalTime.MAX);
        }

        result.add(new HeadAppInfoVO("times", "日期"));
        result.add(new HeadAppInfoVO("recordSum", "总次数"));
        if (Func.isEmpty(appIds) && appIds.length() == 0) {
            //为空全查
            result.addAll(baseMapper.getAppId(null));
        } else {//不为空 查指定应用
            result.addAll(baseMapper.getApp(appId));
        }
        return result;
    }


    @Override
    public void smsRecordLoad(HttpServletResponse response, Integer type, String start, String end, String deptIds, String appIds, Integer result) {
        OutputStream out = null;
        List<AppRecordVO> list = getRecordDate(type, start, end, deptIds, appIds, result);
        try {
            String fileName = "短信统计.xlsx";

            out = response.getOutputStream();
            //设置返回头
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("utf-8");

            List<HeadAppInfoVO> head = getHead(type, start, end, deptIds, appIds, result);
            List<List<String>> headList = head.stream().map(f -> {
                List<String> lists = new ArrayList<>();
                lists.add(f.getAppName());
                return lists;
            }).collect(Collectors.toList());

            // 所有行的集合
            List<List<Object>> resultList = new ArrayList<List<Object>>();

            for (AppRecordVO appRecordVO : list) {
                List<Object> row = new ArrayList<Object>();
                row.add(appRecordVO.getTimes());
                row.add(appRecordVO.getRecordSum());
                if (CollectionUtils.isNotEmpty(appRecordVO.getAppInfo())) {
                    for (AppMapVO appMapVO : appRecordVO.getAppInfo()) {
                        row.add(appMapVO.getAppRecordSum());
                    }
                }
                resultList.add(row);

            }

            EasyExcel.write(response.getOutputStream())
                    .head(headList)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet("短信统计")
                    .doWrite(resultList);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<LoginRecordEcharsVO> getRecordEchars(Integer type, String start, String end, String deptIds, String appIds, Integer resultType) {
        List<LoginRecordEcharsVO> resultList = new ArrayList<>();
//        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM/dd");
        Map<String, List> result = new HashMap<>();
        List<AppRecordVO> recordDate = getRecordDate(type, start, end, deptIds, appIds, resultType);
        if (CollectionUtils.isNotEmpty(recordDate)) {
            List<String> time = new ArrayList<>();
            result.put("time", time);
            for (int i = 0; i < recordDate.size(); i++) {
                if (i == 0) {
                    if (type != null && type == 3) {
                        String date = recordDate.get(i).getTimes().replaceAll("-", "/");
                        time.add(date);
                    } else {
//						Date date1 = new Date(recordDate.get(i).getTimes());
//                        String date = DateTimeUtil.parseDate(recordDate.get(i).getTimes()).format(pattern);
                        String date = recordDate.get(i).getTimes().substring(recordDate.get(i).getTimes().indexOf("-") + 1).replaceAll("-", "/");
                        time.add(date);
                    }
                    List<AppMapVO> appInfo = recordDate.get(i).getAppInfo();
                    if (CollectionUtils.isNotEmpty(appInfo)) {
                        for (AppMapVO appMapVO : appInfo) {
                            List<String> k = new ArrayList<>();
                            k.add(appMapVO.getAppRecordSum() + "");
                            result.put(appMapVO.getAppName(), k);
                        }
                    }


                } else {
                    if (type != null && type == 3) {
                        String date = recordDate.get(i).getTimes().replaceAll("-", "/");
                        time.add(date);
                    } else {
//						Date date1 = new Date(recordDate.get(i).getTimes());
//                        String date = DateTimeUtil.parseDate(recordDate.get(i).getTimes()).format(pattern);
                        String date = recordDate.get(i).getTimes().substring(recordDate.get(i).getTimes().indexOf("-") + 1).replaceAll("-", "/");
                        time.add(date);
                    }

                    List<AppMapVO> appInfo = recordDate.get(i).getAppInfo();
                    if (CollectionUtils.isNotEmpty(appInfo)) {
                        for (AppMapVO appMapVO : appInfo) {
//                            List<String> k = new ArrayList<>();
                            result.get(appMapVO.getAppName()).add(appMapVO.getAppRecordSum() + "");

                        }
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(result)) {
            for (Map.Entry<String, List> entry : result.entrySet()) {
                LoginRecordEcharsVO loginRecordEcharsVO = new LoginRecordEcharsVO();
                loginRecordEcharsVO.setName(entry.getKey());
                loginRecordEcharsVO.setX(entry.getValue());
                resultList.add(loginRecordEcharsVO);
            }
        }
        return resultList;
    }


    private List<AppRecordVO> getRecordDate(Integer type, String start, String end, String deptIds, String appIds, Integer resultType) {
        List<AppRecordVO> result = new ArrayList<>();

        List<HeadAppInfoVO> appList = new ArrayList<>();
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        List<Long> deptId = null;
        List<Long> appId = null;
        if (Func.isNotEmpty(deptIds)) {
            deptId = Func.toLongList(deptIds);
        }
        if (Func.isNotEmpty(appIds)) {
            appId = Func.toLongList(appIds);
        }

        //本周 本月 本年 自定义时间范围
        if (type == null && Func.isEmpty(start) && Func.isEmpty(end)) {//近7天
            endTime = LocalDateTime.now();
            startTime = endTime.plusDays(-7L);
        } else if (type != null && type == 1 && Func.isEmpty(start)) {//yyyy-MM-dd
            //获取本周开始结束时间
            LocalDate now = LocalDate.now();
            startTime = LocalDateTime.of(now.minusDays(now.getDayOfWeek().getValue() - 1), LocalTime.MIN);
            endTime = LocalDateTime.of(now.plusDays(7 - now.getDayOfWeek().getValue()), LocalTime.MAX);
        } else if (type != null && type == 2 && Func.isEmpty(start)) {//yyyy-MM
            //获取本月开始结束时间
            startTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
            endTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
        } else if (type != null && type == 3 && Func.isEmpty(start)) {//yyyy
            //获取本年开始结束时间
            startTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
            endTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX);
        } else {
            startTime = LocalDateTime.of(LocalDate.parse(start, DateTimeUtil.DATE_FORMAT), LocalTime.MIN);
            endTime = LocalDateTime.of(LocalDate.parse(end, DateTimeUtil.DATE_FORMAT), LocalTime.MAX);
        }


        List<BhSmsSendVO> list = baseMapper.getRecordDate(startTime, endTime, deptId, appId, resultType);


        //根据时间进行分组
        Map<String, List<BhSmsSendVO>> collect = new HashMap<>();
        if (CollectionUtils.isNotEmpty(list)) {
            if (type != null && type == 3) {
                collect = list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy/MM"))));
            } else {
                collect = list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("MM/dd"))));
            }
        }
        //最大时间
        LocalDateTime maxTime = endTime;
        //最小时间
        LocalDateTime minTime = startTime;

        //获取应用列表
        if (Func.isEmpty(appIds) && appIds == "") {
//                //为空全查
            appList.addAll(baseMapper.getAppId(null));
        } else {//不为空 查指定应用
            appList.addAll(baseMapper.getApp(appId));
        }
        do {
            String str = null;
//				String time = null;
            String time = null;
            if (type != null && type == 3) {
                str = minTime.format(DateTimeFormatter.ofPattern("yyyy/MM"));
            } else {
                str = minTime.format(DateTimeFormatter.ofPattern("MM/dd"));
            }


            AppRecordVO appRecordVO = new AppRecordVO();
            appRecordVO.setTimes(str);
//			appRecordVO.setRecordSum(entry.getValue().size());
            List<AppMapVO> app = new ArrayList<>();
            appRecordVO.setAppInfo(app);
            Map<String, List<BhSmsSendVO>> appCollect = new HashMap<>();
            if (CollectionUtils.isNotEmpty(collect.get(str))) {
                appCollect = collect.get(str).stream().collect(Collectors.groupingBy(BhSmsSendVO::getAppId));

            }
            for (HeadAppInfoVO headAppInfoVO : appList) {
                AppMapVO appMapVO = new AppMapVO();
                List<BhSmsSendVO> appUseRecordVOS = appCollect.get(headAppInfoVO.getAppId());
                appMapVO.setAppId(headAppInfoVO.getAppId());
                appMapVO.setAppName(headAppInfoVO.getAppName());
                appMapVO.setAppRecordSum(0);
                if (CollectionUtils.isNotEmpty(appUseRecordVOS)) {
                    appMapVO.setAppRecordSum(appUseRecordVOS.size());
                }
                app.add(appMapVO);
            }
            result.add(appRecordVO);

            if (type != null && type == 3) {
                minTime = minTime.plusMonths(1);
            } else {
                minTime = minTime.plusDays(1);
            }
        } while (minTime.isBefore(maxTime));


//        if (CollectionUtils.isNotEmpty(list)){
//            //获取应用列表
//            if (Func.isEmpty(appIds) && appIds ==""){
//                //为空全查
//                appList.addAll(baseMapper.getAppId(null));
//            } else {//不为空 查指定应用
//                appList.addAll(baseMapper.getApp(appId));
//            }
//
//            //以时间进行分组
//            Map<String, List<BhSmsSendVO>> collect = new HashMap<>();
//            if (type != null && type ==3){
//                collect = list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM"))));
//
//            }else {
//                collect =list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
//            }
//            if (CollectionUtils.isNotEmpty(appList)){
//
//
//                for (Map.Entry<String, List<BhSmsSendVO>> entry : collect.entrySet()) {
//                    AppRecordVO appRecordVO = new AppRecordVO();
//                    appRecordVO.setTimes(entry.getKey());
//                    appRecordVO.setRecordSum(entry.getValue().size());
//                    List<AppMapVO> app = new ArrayList<>();
//                    appRecordVO.setAppInfo(app);
//                    //以应用进行分组
//                    Map<String, List<BhSmsSendVO>> appCollect = entry.getValue().stream().collect(Collectors.groupingBy(BhSmsSendVO::getAppId));
//                    for (HeadAppInfoVO headAppInfoVO : appList) {
//                        AppMapVO appMapVO = new AppMapVO();
//                        List<BhSmsSendVO> appUseRecordVOS = appCollect.get(headAppInfoVO.getAppId());
//                        appMapVO.setAppId(headAppInfoVO.getAppId());
//                        appMapVO.setAppName(headAppInfoVO.getAppName());
//                        appMapVO.setAppRecordSum(0);
//                        if (CollectionUtils.isNotEmpty(appUseRecordVOS)){
//                            appMapVO.setAppRecordSum(appUseRecordVOS.size());
//                        }
//                        app.add(appMapVO);
//                    }
//
//                    result.add(appRecordVO);
//                }
//            }
//        }
//        if (CollectionUtils.isNotEmpty(result)){
//            result= result.stream().sorted(Comparator.comparing(i -> i.getTimes())).collect(Collectors.toList());
//        }
        return result;
    }


    @Override
    public List<BhSmsSendVO> getUserSmsList(String account, String start, String end) {
        LocalDateTime startTime = null, endTime = null;
        if (Func.isNotEmpty(start)) {
            startTime = LocalDateTime.of(LocalDate.parse(start), LocalTime.MIN);
        }
        if (Func.isNotEmpty(end)) {
            endTime = LocalDateTime.of(LocalDate.parse(end), LocalTime.MAX);
        }
        return baseMapper.getUserSmsList(account, startTime, endTime);
    }


    @Override
    public List<StatisticsMapVO> getRecentlyEchars() {
        List<StatisticsMapVO> result = new ArrayList<>();
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.plusMonths(-2L).with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        //近三个月访问统计
        LambdaUpdateWrapper<BhSmsSend> queryLogin = Wrappers.lambdaUpdate(BhSmsSend.class).between(BhSmsSend::getCreateTime, start, end);
        List<BhSmsSendVO> list = baseMapper.getSMSRecord( start, end);
//        List<BhSmsSend> list = this.list(queryLogin);
        if (CollectionUtils.isNotEmpty(list)) {
            //将list进行以时间进行分组
            Map<Integer, List<BhSmsSend>> collect = list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().getMonthValue()));
            for (LocalDateTime i = start; i.isBefore(end); ) {

                StatisticsMapVO statisticsMapVO = new StatisticsMapVO();
                statisticsMapVO.setName(i.getMonthValue() + "月");
                statisticsMapVO.setNumber(0);
                if (CollectionUtils.isNotEmpty(collect.get(i.getMonthValue()))) {
                    statisticsMapVO.setNumber(collect.get(i.getMonthValue()).size());
                }
                result.add(statisticsMapVO);
                i = i.plusMonths(1L);
            }

        } else {
            for (LocalDateTime i = start; i.isBefore(end); ) {
                StatisticsMapVO statisticsMapVO = new StatisticsMapVO();
                statisticsMapVO.setName(i.getMonthValue() + "月");
                statisticsMapVO.setNumber(0);
                result.add(statisticsMapVO);
                i = i.plusMonths(1L);
            }
        }
        return result;
    }

    @Override
    public List<StatisticsMapVO> getAllSendEchars() {
        List<StatisticsMapVO> result = new ArrayList<>();
        LocalDateTime start = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        List<BhSmsSendVO> list = baseMapper.getAllSendEchars(start,end);
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, List<BhSmsSendVO>> collect = list.stream().collect(Collectors.groupingBy(BhSmsSendVO::getAppId));
            for (Map.Entry<String, List<BhSmsSendVO>> entry : collect.entrySet()) {
                StatisticsMapVO statisticsMapVO = new StatisticsMapVO();
                statisticsMapVO.setName(entry.getValue().get(0).getAppName());
                statisticsMapVO.setNumber(entry.getValue().size());
                result.add(statisticsMapVO);
            }
        }
        return result;
    }


    @Override
    public List<SmsNumberVO> getStatisticsNote() {
        List<SmsNumberVO> result = new ArrayList<>();
        LocalDateTime start = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        //获取总发送短信条数
        List<BhSmsSendVO> list = baseMapper.getSMSRecord(start, end);
        //获取本月发送短信条数
        List<BhSmsSendVO> nowMonth = baseMapper.getSMSRecord(start, end);
//        //获取上月发送短信条数
//        List<BhSmsSendVO> lastMonth = baseMapper.getSMSRecord(start.plusMonths(-1L), end.plusMonths(-1L));


        //计算接入应用同比 （本-上）/上
        String percent = "0.00";
//        if (CollectionUtils.isNotEmpty(nowMonth)) {
//            if (CollectionUtils.isNotEmpty(lastMonth)) {
//                percent = new BigDecimal(nowMonth.size() - lastMonth.size()).divide(new BigDecimal(lastMonth.size()), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)) + "";
//            } else {
//                percent = "100.00";
//            }
//        }
        result.add(new SmsNumberVO("短信总计", list.size()));

        //今日发送数量
        LocalDateTime now = LocalDateTime.now();

        List<BhSmsSendVO> nowDay = baseMapper.getSMSRecord(now.with(LocalTime.MIN), now);
        result.add(new SmsNumberVO("今日发送", nowDay.size()));
        return result;
    }

    @Override
    public List<HeadPageVO> getSMSStatistics() {
        List<HeadPageVO> result = new ArrayList<>();
        LocalDateTime start = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        //获取总发送短信条数
        List<BhSmsSendVO> list = baseMapper.getSMSRecord(start, end);
        //获取本月发送短信条数
        List<BhSmsSendVO> nowMonth = baseMapper.getSMSRecord(start, end);
        //获取上月发送短信条数
        List<BhSmsSendVO> lastMonth = baseMapper.getSMSRecord(start.plusMonths(-1L), end.plusMonths(-1L));


        //计算接入应用同比 （本-上）/上
        String percent = "0.00";
        if (CollectionUtils.isNotEmpty(nowMonth)) {
            if (CollectionUtils.isNotEmpty(lastMonth)) {
                percent = new BigDecimal(nowMonth.size() - lastMonth.size()).divide(new BigDecimal(lastMonth.size()), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)) + "";
            } else {
                percent = "100.00";
            }
        }
        result.add(new HeadPageVO("短信总计", list.size(), percent));

        //今日发送数量
        LocalDateTime now = LocalDateTime.now();

        List<BhSmsSendVO> nowDay = baseMapper.getSMSRecord(now.with(LocalTime.MIN), now);
        result.add(new HeadPageVO("今日发送", nowDay.size(), null));
        return result;
    }
}
