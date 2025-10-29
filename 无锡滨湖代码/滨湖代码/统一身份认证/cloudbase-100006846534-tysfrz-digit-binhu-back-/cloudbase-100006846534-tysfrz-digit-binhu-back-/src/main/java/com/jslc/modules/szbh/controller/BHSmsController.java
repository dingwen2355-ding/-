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

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jslc.common.constant.CommonConstant;
import com.jslc.modules.szbh.service.IBHSmsService;
import com.jslc.modules.szbh.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tenant.annotation.NonDS;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping(CommonConstant.SQUIRREL_SZBH + "/sms")
@Api(value = "滨湖短信", tags = "滨湖短信")
public class BHSmsController extends BladeController {

	private final IBHSmsService smsService;

	@GetMapping("/sendSms")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "短信发送", notes = "短信发送")
	public ResultVO sendSms(String appId,String token, String mobile, String msg) {
		return smsService.sendSms(appId,token,mobile,msg);
	}

	@GetMapping("/initUserSendSms")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "用户初始化发送提示", notes = "用户初始化发送提示")
	public ResultVO initUserSendSms( String mobile, String msg) {
		return smsService.initUserSendSms(mobile,msg);
	}


	@GetMapping("/sendSmsVerCode")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "发送短信验证码", notes = "发送短信验证码")
	public ResultVO sendSmsVerCode(String mobile, String msg) {
		return smsService.sendSmsVerCode(mobile,msg);
	}


}
