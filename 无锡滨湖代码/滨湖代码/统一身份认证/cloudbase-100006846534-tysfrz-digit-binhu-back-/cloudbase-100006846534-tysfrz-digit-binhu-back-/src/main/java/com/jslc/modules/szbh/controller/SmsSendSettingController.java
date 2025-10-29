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

import com.jslc.modules.szbh.entity.BhSmsSend;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import com.jslc.common.annotation.LcSysLog;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.szbh.entity.SmsSendSetting;
import com.jslc.modules.szbh.vo.SmsSendSettingVO;
import com.jslc.modules.szbh.service.ISmsSendSettingService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.Map;

/**
 * 短信发送配置 控制器
 *
 * @author BladeX
 * @since 2023-06-07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/smssendsetting")
@Api(value = "短信发送配置", tags = "短信发送配置接口")
public class SmsSendSettingController extends BladeController {

	private final ISmsSendSettingService smsSendSettingService;

	/**
	 * 明细
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = " 短信发送 明细", notes = "短信发送 明细")
	public R<IPage<BhSmsSend>> detail(Long id,Query query) {
		IPage<BhSmsSend> detail = smsSendSettingService.getDetail(id,Condition.getPage(query));
		return R.data(detail);
	}

	/**
	 *  短信发送 导出明细
	 */
	@GetMapping("/downLoad")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "短信发送 导出明细", notes = "短信发送 导出明细")
	@LcSysLog("短信发送 导出明细")
	public void passCheck(HttpServletResponse response , Long id) {
		smsSendSettingService.downLoad( response,  id);

	}


	/**
	 * 测试短信发送
	 */
	@PostMapping("/testSend")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "测试短信发送", notes = "测试短信发送")
	public R testSend(@Valid @RequestBody Map<String,String> param) {
		return R.status(smsSendSettingService.testSend(param.get("phone"),param.get("msg")));
	}

	/**
	 * 短信发送
	 */
	@PostMapping("/sendSmsNotice")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "通知全员", notes = "测试短信发送")
	public R sendSmsNotice(String msg) {
		return R.status(smsSendSettingService.sendSmsNotice(msg));
	}


	/**
	 * 自定义分页 短信发送配置
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = " 短信发送配置 分页", notes = "短信发送配置 分页")
	public R<IPage<SmsSendSettingVO>> page(String title, String start , String end, Integer isRegularTime, Integer sendStatus, Query query) {
		IPage<SmsSendSettingVO> pages = smsSendSettingService.selectSmsSendSettingPage(Condition.getPage(query), title, start , end, isRegularTime, sendStatus);
		return R.data(pages);
	}


	/**
	 * 新增或修改 短信发送配置
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "短信发送配置新增或修改", notes = "短信发送配置新增或修改")
	@LcSysLog("短信发送配置新增或修改")
	public R submit(@Valid @RequestBody SmsSendSetting smsSendSetting) {
		return R.status(smsSendSettingService.submit(smsSendSetting));
	}


	/**
	 * 新增或修改 短信发送配置
	 */
	@GetMapping("/getLoginUserPhone")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "获取所有登录人的手机号", notes = "获取所有登录人的手机号")
	public R getLoginUserPhone() {
		return R.data(smsSendSettingService.getLoginUserPhone());
	}

	
	/**
	 * 删除 短信发送配置
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "短信发送配置删除", notes = "短信发送配置删除")
	@LcSysLog("短信发送配置删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(smsSendSettingService.dels(Func.toLongList(ids)));
	}

	
}
