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
import com.jslc.modules.szbh.entity.LoginRecord;
import com.jslc.modules.szbh.vo.*;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 客户端登录记录 服务类
 *
 * @author BladeX
 * @since 2023-06-02
 */
public interface ILoginRecordService extends IService<LoginRecord> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param loginRecord
	 * @return
	 */
	IPage<UserOnlineVO> selectLoginRecordPage(IPage<UserOnlineVO> page, Long deptId,String  userAccount ,String userName);

	IPage<ActiveUserVO> getActivePage(IPage<ActiveUserVO> page, Integer type, String start, String end, String deptIds);


	void downLoad(HttpServletResponse response, Integer type, String start, String end, String deptIds);


	LoginUserEcharsVO getEcharsList(Integer type, String start, String end, String deptIds);


	IPage<UserOnlineVO> getDatAPage(IPage<UserOnlineVO> page, String userAccount);

	List<HeadPageVO> getUserData();


	List<HeadPageVO> getDeptRank();


	List<StatisticsMapVO> getAccessStatistics();


    List<LoginRecord> getBhUserRecord(LocalDateTime startTime, LocalDateTime endTime);

	LoginRecord getLastLoginRecord(String userAccount);


    List<SmsNumberVO> getAppUse();




}
