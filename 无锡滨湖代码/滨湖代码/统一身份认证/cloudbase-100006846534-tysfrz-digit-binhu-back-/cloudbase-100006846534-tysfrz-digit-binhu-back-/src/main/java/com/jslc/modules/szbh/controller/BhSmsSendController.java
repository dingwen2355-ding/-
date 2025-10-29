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
package com.jslc.modules.szbh.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jslc.common.constant.CommonConstant;
import com.jslc.modules.szbh.service.IBhSmsSendService;
import com.jslc.modules.szbh.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import com.jslc.common.annotation.LcSysLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 短信通知记录 控制器
 *
 * @author BladeX
 * @since 2023-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping(CommonConstant.SQUIRREL_SZBH + "/bhsmssend")
@Api(value = "短信通知记录", tags = "短信通知记录接口")
public class BhSmsSendController extends BladeController {

    private final IBhSmsSendService bhSmsSendService;

    /*
     * @method getList
     * @description 列表
     * @author xxz
     * @date 2023/5/18
     * @param start
     * @param end
     * @param param
     * @param result
     * @param query
     */
    @GetMapping("/getList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "短信通知记录列表", notes = "短信通知记录列表")
    public R<IPage<BhSmsSendVO>> getList(String start, String end, String param, Integer result, Query query) {
        IPage<BhSmsSendVO> pages = bhSmsSendService.getList(Condition.getPage(query), start, end, param, result);
        return R.data(pages);
    }


    @GetMapping("/getSmsPage")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "统计分析 短信统计", notes = "统计分析 短信统计")
    public R<IPage<AppRecordVO>> getSmsPage(Integer type , String start, String end, String deptIds,String appIds, Integer result, Query query) {
        IPage<AppRecordVO> pages = bhSmsSendService.getSmsPage(Condition.getPage(query), type , start, end, deptIds, appIds, result);
        return R.data(pages);
    }

    /**
     * 获取短信统计 表头
     */
    @GetMapping("/getHead")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "获取短信统计 表头", notes = "获取短信统计 表头")
    public R<List<HeadAppInfoVO>> getHead(Integer type , String start, String end, String deptIds,String appIds, Integer result) {
        List<HeadAppInfoVO> pages = bhSmsSendService.getHead(type, start,end, deptIds, appIds,result);
        return R.data(pages);
    }

    /**
     * 获取短信统计 echars数据
     */
    @GetMapping("/getSmsEchars")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "获取短信统计 echars数据", notes = "获取短信统计 echars数据")
    public R<List<LoginRecordEcharsVO>> getRecordEchars(Integer type , String start, String end, String deptIds,String appIds, Integer result) {
        List<LoginRecordEcharsVO> map = bhSmsSendService.getRecordEchars(type , start, end,  deptIds, appIds,  result);
        return R.data(map);
    }

    /**
     * 近三个月 短信发送
     */
    @GetMapping("/getRecentlyEchars")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "近三个月 短信发送", notes = "近三个月 短信发送")
    public R getRecentlyEchars() {
        List<StatisticsMapVO> list = bhSmsSendService.getRecentlyEchars();
        return R.data(list);
    }


    /**
     * 全平台发送统计
     */
    @GetMapping("/getAllSendEchars")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "全平台发送统计", notes = "全平台发送统计")
    public R getAllSendEchars() {
        List<StatisticsMapVO> list = bhSmsSendService.getAllSendEchars();
        return R.data(list);
    }


    /**
     * 短信统计数据卡片
     */
    @GetMapping("/getSMSStatistics")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "短信统计数据卡片", notes = "短信统计数据卡片")
    public R getSMSStatistics() {
        List<HeadPageVO> list = bhSmsSendService.getSMSStatistics();
        return R.data(list);
    }



    /**
     *  获取短信统计 导出
     */
    @GetMapping("/smsRecordLoad")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "短信统计 导出", notes = "短信统计 导出")
    @LcSysLog("短信统计导出")
    public void smsRecordLoad(HttpServletResponse response ,Integer type , String start, String end, String deptIds,String appIds, Integer result) {
        bhSmsSendService.smsRecordLoad( response, type, start, end , deptIds, appIds,result);

    }




}
