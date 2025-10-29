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
package com.jslc.modules.szbh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jslc.modules.szbh.entity.BhSmsSend;
import com.jslc.modules.szbh.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 短信通知记录 服务类
 *
 * @author BladeX
 * @since 2023-05-18
 */
public interface IBhSmsSendService extends IService<BhSmsSend> {

    /**
     * 列表
     */
    IPage<BhSmsSendVO> getList(IPage<BhSmsSendVO> page, String start, String end, String param, Integer result);


    IPage<AppRecordVO> getSmsPage(IPage<AppRecordVO> page, Integer type, String start, String end, String deptIds, String appIds, Integer result);

    List<LoginRecordEcharsVO> getRecordEchars(Integer type, String start, String end, String deptIds, String appIds, Integer result);


    List<HeadAppInfoVO> getHead(Integer type, String start, String end, String deptIds, String appIds, Integer result);


    void smsRecordLoad(HttpServletResponse response, Integer type, String start, String end, String deptIds, String appIds, Integer result);

    /**
     * 查询用户短信
     */
    List<BhSmsSendVO> getUserSmsList(String account, String start, String end);

    List<StatisticsMapVO> getRecentlyEchars();


    List<StatisticsMapVO> getAllSendEchars();


    List<HeadPageVO> getSMSStatistics();

    List<SmsNumberVO> getStatisticsNote();




}


