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
import com.jslc.modules.szbh.entity.OauthClientDetails;
import com.jslc.modules.szbh.vo.OauthClientDetailsVO;
import com.jslc.modules.szbh.service.IOauthClientDetailsService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2023-05-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/oauthclientdetails")
@Api(value = "", tags = "接口")
public class OauthClientDetailsController extends BladeController {

	private final IOauthClientDetailsService oauthClientDetailsService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "应用app存储 Appid 和Secret详情", notes = "应用app存储 Appid 和Secret详情")
	public R<OauthClientDetails> detail(OauthClientDetails oauthClientDetails) {
		OauthClientDetails detail = oauthClientDetailsService.getOne(Condition.getQueryWrapper(oauthClientDetails));
		return R.data(detail);
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "应用app存储 Appid 和Secret分页", notes = "应用app存储 Appid 和Secret分页")
	public R<IPage<OauthClientDetails>> list(OauthClientDetails oauthClientDetails, Query query) {
		IPage<OauthClientDetails> pages = oauthClientDetailsService.page(Condition.getPage(query), Condition.getQueryWrapper(oauthClientDetails));
		return R.data(pages);
	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "应用app存储 Appid 和Secret分页", notes = "应用app存储 Appid 和Secret分页")
	public R<IPage<OauthClientDetailsVO>> page(OauthClientDetailsVO oauthClientDetails, Query query) {
		IPage<OauthClientDetailsVO> pages = oauthClientDetailsService.selectOauthClientDetailsPage(Condition.getPage(query), oauthClientDetails);
		return R.data(pages);
	}



	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "应用app存储 Appid 和Secret新增或修改", notes = "应用app存储 Appid 和Secret新增或修改")
	@LcSysLog("Appid 和Secret新增或修改")
	public R submit(@Valid @RequestBody OauthClientDetails oauthClientDetails) {
		return R.status(oauthClientDetailsService.submit(oauthClientDetails));
	}

	
	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "应用app存储 Appid 和Secret删除", notes = "应用app存储 Appid 和Secret删除")
	@LcSysLog("Appid和Secret删除")
	public void remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		oauthClientDetailsService.delAll(Func.toStrList(ids));
	}

	
}
