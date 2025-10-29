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

import com.jslc.modules.szbh.entity.AppUseRecord;
import com.jslc.modules.szbh.vo.AppUseRecordVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.szbh.vo.HeadAppInfoVO;
import com.jslc.modules.szbh.vo.UserOnlineVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 应用使用记录 Mapper 接口
 *
 * @author BladeX
 * @since 2023-06-02
 */
public interface AppUseRecordMapper extends BaseMapper<AppUseRecord> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param appUseRecord
	 * @return
	 */
	List<AppUseRecordVO> selectAppUseRecordPage(IPage page, AppUseRecordVO appUseRecord);

    List<UserOnlineVO> getAppUseRecordData(@Param("page") IPage page,
										   @Param("key")String key,
										   @Param("startTime")LocalDateTime startTime,
										   @Param("endTime")LocalDateTime endTime,
										   @Param("deptId")List<Long> deptId,
										   @Param("appId")List<Long> appId);

    List<AppUseRecordVO> getRecordDate(@Param("startTime")LocalDateTime startTime,
									 @Param("endTime")LocalDateTime endTime,
									 @Param("deptId")List<Long> deptId,
									 @Param("appId")List<Long> appId);

	List<HeadAppInfoVO> getAppIds();

	List<HeadAppInfoVO> getAppId(@Param("appId")List<Long> appId);


    List<AppUseRecordVO> getAppRecord(@Param("start")LocalDateTime start,@Param("end")LocalDateTime end);


	List<Long> getAppsId();


	List<UserOnlineVO> getUserInfo(@Param("accounts") List<String> accounts);


	List<AppUseRecordVO> getUserAppRecord(@Param("account") String account,
										  @Param("start") LocalDateTime start,
										  @Param("end") LocalDateTime end);

	List<AppUseRecordVO> getAppUseRecord(@Param("start") LocalDateTime start,
										 @Param("end") LocalDateTime end);

	List<AppUseRecordVO> getAppUseStatistics(@Param("start") LocalDateTime start,
											 @Param("end") LocalDateTime end);


    List<AppUseRecordVO> getAccessStatistics(@Param("start") LocalDateTime start,
											 @Param("end") LocalDateTime end);




}
