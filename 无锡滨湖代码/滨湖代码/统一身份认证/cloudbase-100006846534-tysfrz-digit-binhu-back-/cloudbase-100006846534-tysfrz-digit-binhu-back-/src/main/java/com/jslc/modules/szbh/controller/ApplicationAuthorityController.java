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

import com.jslc.modules.szbh.vo.UserAppVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
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
import com.jslc.modules.szbh.entity.ApplicationAuthority;
import com.jslc.modules.szbh.vo.ApplicationAuthorityVO;
import com.jslc.modules.szbh.service.IApplicationAuthorityService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 应用授权 控制器
 *
 * @author BladeX
 * @since 2023-06-07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/applicationauthority")
@Api(value = "应用授权", tags = "应用授权接口")
public class ApplicationAuthorityController extends BladeController {

	private final IApplicationAuthorityService applicationAuthorityService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "应用授权详情 传入应用授权id")
	public R<ApplicationAuthority> detail(Long id) {
		ApplicationAuthority detail = applicationAuthorityService.getById(id);
		return R.data(detail);
	}



	/**
	 * 自定义分页 应用授权
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = " 应用授权分页", notes = "应用授权分页")
	public R<IPage<ApplicationAuthorityVO>> page(String appNames,String deptNames,Integer type, Query query) {
		IPage<ApplicationAuthorityVO> pages = applicationAuthorityService.selectApplicationAuthorityPage(Condition.getPage(query), appNames,deptNames,type);
		return R.data(pages);
	}



	/**
	 * 自定义分页 应用访问关系
	 */
	@GetMapping("/getUserAppPage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = " 应用访问关系 分页", notes = "应用访问关系分页")
	public R<IPage<UserAppVO>> getUserAppPage(String account,String name,String deptIds,String appIds, Query query) {
		IPage<UserAppVO> pages = applicationAuthorityService.getUserAppPage(Condition.getPage(query), account, name, deptIds, appIds);
		return R.data(pages);
	}

	/**
	 *  应用访问关系 导出
	 */
	@GetMapping("/downLoad")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "应用访问关系 导出", notes = "应用访问关系 导出")
	@LcSysLog("应用访问关系导出")
	public void downLoad(HttpServletResponse response , String account,String name,String deptIds,String appIds, String ids) {
		applicationAuthorityService.downLoad( response, account, name, deptIds, appIds);

	}


	/**
	 * 新增或修改 应用授权
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改 应用授权", notes = "新增或修改 应用授权")
	@LcSysLog("新增或修改应用访问关系")
	public R submit(@Valid @RequestBody ApplicationAuthority applicationAuthority) {
		return R.status(applicationAuthorityService.submit(applicationAuthority));
	}

	
	/**
	 * 删除 应用授权
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除 应用授权", notes = "删除 应用授权")
	@LcSysLog("删除应用访问关系")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(applicationAuthorityService.dels(Func.toLongList(ids)));
	}

	
}
