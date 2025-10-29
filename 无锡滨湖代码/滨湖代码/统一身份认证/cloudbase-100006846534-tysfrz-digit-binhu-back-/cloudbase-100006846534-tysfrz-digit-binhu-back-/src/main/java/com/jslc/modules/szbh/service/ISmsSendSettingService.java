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
import com.jslc.modules.system.entity.User;
import com.jslc.modules.szbh.entity.BhSmsSend;
import com.jslc.modules.szbh.entity.SmsSendSetting;
import com.jslc.modules.szbh.vo.SmsSendSettingVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 短信发送配置 服务类
 *
 * @author BladeX
 * @since 2023-06-07
 */
public interface ISmsSendSettingService extends IService<SmsSendSetting> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param smsSendSetting
	 * @return
	 */
	IPage<SmsSendSettingVO> selectSmsSendSettingPage(IPage<SmsSendSettingVO> page, String title, String start , String end, Integer isRegularTime, Integer sendStatus);

	Boolean submit(SmsSendSetting smsSendSetting);

	Boolean dels(List<Long> dels);

	void sendMsg(String appId, String mobile, String msg,Long id);

	IPage<BhSmsSend> getDetail(Long id ,IPage<BhSmsSend> page);

	void downLoad(HttpServletResponse response, Long id);


    Boolean testSend(String phone, String msg);

	void applictionNotification(List<User> users, String message, String appId, String name);


	void resultNotification(String majorMobile, String name, String message, String appId, String appName);

    Boolean sendSmsNotice(String msg);

	String getLoginUserPhone();
}
