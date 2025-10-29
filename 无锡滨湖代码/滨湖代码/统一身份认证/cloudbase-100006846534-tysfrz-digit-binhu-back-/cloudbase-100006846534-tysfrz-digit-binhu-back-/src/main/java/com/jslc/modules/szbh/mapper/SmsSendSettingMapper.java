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

import com.jslc.modules.szbh.entity.SmsSendSetting;
import com.jslc.modules.szbh.vo.SmsSendSettingVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 短信发送配置 Mapper 接口
 *
 * @author BladeX
 * @since 2023-06-07
 */
public interface SmsSendSettingMapper extends BaseMapper<SmsSendSetting> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param smsSendSetting
	 * @return
	 */
	List<SmsSendSettingVO> selectSmsSendSettingPage(IPage page, SmsSendSettingVO smsSendSetting);


	List<SmsSendSettingVO> getSmsSendSettingList(@Param("page") IPage<SmsSendSettingVO> page,
												 @Param("title")String title,
												 @Param("startTime")LocalDateTime startTime,
												 @Param("endTime")LocalDateTime endTime,
												 @Param("isRegularTime")Integer isRegularTime,
												 @Param("sendStatus")Integer sendStatus);

    List<String> getUserPhones();



}
