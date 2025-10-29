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

import com.jslc.modules.szbh.entity.LoginRecord;
import com.jslc.modules.szbh.vo.LoginRecordVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.szbh.vo.UserOnlineVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 客户端登录记录 Mapper 接口
 *
 * @author BladeX
 * @since 2023-06-02
 */
public interface LoginRecordMapper extends BaseMapper<LoginRecord> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param loginRecord
	 * @return
	 */
	List<LoginRecordVO> selectLoginRecordPage(IPage page, LoginRecordVO loginRecord);

    List<UserOnlineVO> getOnlineRecord(@Param("deptId")Long deptId,
									   @Param("userAccount")String userAccount ,
									   @Param("userName")String userName,
									   @Param("startTime") LocalDateTime startTime,
									   @Param("endTime")LocalDateTime endTime);

	List<LoginRecordVO> getUserRecord(@Param("startTime")LocalDateTime startTime,
									  @Param("endTime")LocalDateTime endTime,
									  @Param("deptId")List<Long> deptId);


	Integer getUserSum(@Param("time")String time);


	Integer getCreateUserSum(@Param("start")String start,@Param("end")String end);

    List<LoginRecordVO> getList();


	List<LoginRecord> getBhUserRecord(@Param("startTime")LocalDateTime startTime, @Param("endTime")LocalDateTime endTime);

	LoginRecord getLastLoginRecord(@Param("userAccount")String userAccount);


}
