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
package com.jslc.modules.szbh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.szbh.entity.BhSmsSend;
import com.jslc.modules.szbh.vo.AppRecordVO;
import com.jslc.modules.szbh.vo.BhSmsSendVO;
import com.jslc.modules.szbh.vo.HeadAppInfoVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 短信通知记录 Mapper 接口
 *
 * @author BladeX
 * @since 2023-05-18
 */
public interface BhSmsSendMapper extends BaseMapper<BhSmsSend> {

    /**
     * 列表
     */
    List<BhSmsSendVO> getList(IPage page, LocalDateTime start, LocalDateTime end, String param, Integer result);

    List<BhSmsSendVO> getRecordDate(@Param("startTime") LocalDateTime startTime,
                                    @Param("endTime")LocalDateTime endTime,
                                    @Param("deptId")List<Long> deptId,
                                    @Param("appId")List<Long> appId,
                                    @Param("resultType")Integer resultType);

    List<HeadAppInfoVO> getAppIds(@Param("startTime")LocalDateTime startTime,
                                  @Param("endTime")LocalDateTime endTime,
                                  @Param("deptId")List<Long> deptId,
                                  @Param("appId")List<Long> appId,
                                  @Param("resultType")Integer resultType);

    List<HeadAppInfoVO>  getAppId(@Param("appId")List<Long> appId);

    List<HeadAppInfoVO> getApp(@Param("appId") List<Long> appId);

//    List<HeadAppInfoVO> getAppIds();

    List<BhSmsSendVO> getUserSmsList(@Param("account")String account, @Param("start")LocalDateTime start, @Param("end")LocalDateTime end);

    List<BhSmsSendVO> getAllSendEchars(@Param("start")LocalDateTime start,@Param("end")LocalDateTime end);


    List<BhSmsSendVO> getSMSRecord(@Param("start")LocalDateTime start,@Param("end")LocalDateTime end);


}
