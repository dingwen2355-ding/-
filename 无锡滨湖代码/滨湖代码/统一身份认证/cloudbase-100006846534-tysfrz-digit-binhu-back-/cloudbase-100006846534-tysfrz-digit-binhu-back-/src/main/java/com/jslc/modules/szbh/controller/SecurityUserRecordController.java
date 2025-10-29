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
import com.jslc.modules.szbh.entity.SecurityUserRecord;
import com.jslc.modules.szbh.vo.SecurityUserRecordVO;
import com.jslc.modules.szbh.service.ISecurityUserRecordService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 安全设置记录表 控制器
 *
 * @author BladeX
 * @since 2023-06-01
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/securityuserrecord")
@Api(value = "安全设置记录表", tags = "安全设置记录表接口")
public class SecurityUserRecordController extends BladeController {

	private final ISecurityUserRecordService securityUserRecordService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "安全设置记录详情", notes = "安全设置记录详情")
	public R<SecurityUserRecord> detail(SecurityUserRecord securityUserRecord) {
		SecurityUserRecord detail = securityUserRecordService.getOne(Condition.getQueryWrapper(securityUserRecord));
		return R.data(detail);
	}

	/**
	 * 通过账户名获取配置
	 */
	@GetMapping("/getAccountDetail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "通过账户名获取配置", notes = "通过账户名获取配置")
	public R<SecurityUserRecordVO> getAccountDetail(String username) {
		SecurityUserRecordVO detail = securityUserRecordService.getAccountDetail(username);
		return R.data(detail);
	}


	/**
	 * 自定义分页 安全设置记录表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "安全设置记录分页", notes = "安全设置记录分页")
	public R<IPage<SecurityUserRecordVO>> page(SecurityUserRecordVO securityUserRecord, Query query) {
		IPage<SecurityUserRecordVO> pages = securityUserRecordService.selectSecurityUserRecordPage(Condition.getPage(query), securityUserRecord);
		return R.data(pages);
	}



	/**
	 * 新增或修改 安全设置记录表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "安全设置记录新增或修改", notes = "安全设置记录新增或修改")
	@LcSysLog("安全设置记录新增或修改")
	public R submit(@Valid @RequestBody SecurityUserRecord securityUserRecord) {
		return R.status(securityUserRecordService.saveOrUpdate(securityUserRecord));
	}

	
	/**
	 * 删除 安全设置记录表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "安全设置记录删除", notes = "安全设置记录删除")
	@LcSysLog("安全设置记录删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(securityUserRecordService.removeBatchByIds(Func.toLongList(ids)));
	}

	
}
