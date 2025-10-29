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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import com.jslc.common.annotation.LcSysLog;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.szbh.entity.SecuritySetting;
import com.jslc.modules.szbh.vo.SecuritySettingVO;
import com.jslc.modules.szbh.service.ISecuritySettingService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 安全设置 控制器
 *
 * @author BladeX
 * @since 2023-06-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/securitysetting")
@Api(value = "安全设置", tags = "安全设置接口")
public class SecuritySettingController extends BladeController {

	private final ISecuritySettingService securitySettingService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "安全设置详情", notes = "安全设置详情")
	public R<SecuritySetting> detail() {
		SecuritySetting detail = securitySettingService.getDetail();
		return R.data(detail);
	}

	/**
	 * 分页 安全设置
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "安全设置分页", notes = "安全设置分页")
	public R<IPage<SecuritySetting>> list(SecuritySetting securitySetting, Query query) {
		IPage<SecuritySetting> pages = securitySettingService.page(Condition.getPage(query), Condition.getQueryWrapper(securitySetting));
		return R.data(pages);
	}



	/**
	 * 新增或修改 安全设置
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "安全设置新增或修改", notes = "安全设置新增或修改")
	@LcSysLog("安全设置新增或修改")
	public R submit(@Valid @RequestBody SecuritySetting securitySetting) {
		return R.status(securitySettingService.submit(securitySetting));
	}

	

	
}
