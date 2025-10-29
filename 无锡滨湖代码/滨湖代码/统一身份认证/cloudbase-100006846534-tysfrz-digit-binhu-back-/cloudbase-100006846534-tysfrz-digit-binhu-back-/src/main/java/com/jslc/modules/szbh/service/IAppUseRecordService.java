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

import com.baomidou.mybatisplus.extension.service.IService;
import com.jslc.modules.szbh.entity.AppUseRecord;
import com.jslc.modules.szbh.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 应用使用记录 服务类
 *
 * @author BladeX
 * @since 2023-06-02
 */
public interface IAppUseRecordService extends IService<AppUseRecord> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param appUseRecord
	 * @return
	 */
	IPage<UserOnlineVO> selectAppUseRecordPage(IPage<UserOnlineVO> page, String key, String start, String end , String deptIds, String appIds);

	Boolean dels(List<Long> ids);

	void downLoad(HttpServletResponse response, String key, String start, String end, String deptIds, String appIds,String ids);

    List<HeadAppInfoVO> getHead(Integer type, String start, String end, String deptIds, String appIds);


	IPage<AppRecordVO> getRecordPage(IPage<AppRecordVO> page, Integer type, String start, String end, String deptIds, String appIds);


	List<LoginRecordEcharsVO> getRecordEchars(Integer type, String start, String end, String deptIds, String appIds);

	void appRecordLoad(HttpServletResponse response, Integer type, String start, String end, String deptIds, String appIds);


    List<AppUseRecordVO> getAppRecord( LocalDateTime start, LocalDateTime end);


	List<NVMapVO> getUserRecord(Integer type);


    List<HeadPageVO> getYOYRecord();


	List<AppClassificationStatisticsVO> getAppClassificationStatistics();


    List<HeadPageVO> getApplicationAccessRanking();


    List<AccessStatisticsDSQVO> getAccessStatistics();


    EchartsDSQVO getAccessEcharts();



}
