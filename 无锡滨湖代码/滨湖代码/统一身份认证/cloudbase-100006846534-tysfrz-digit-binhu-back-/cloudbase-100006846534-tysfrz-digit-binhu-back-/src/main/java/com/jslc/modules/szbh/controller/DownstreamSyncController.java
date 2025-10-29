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
import com.jslc.modules.szbh.entity.DownstreamSync;
import com.jslc.modules.szbh.vo.DownstreamSyncVO;
import com.jslc.modules.szbh.service.IDownstreamSyncService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 下游同步配置表 控制器
 *
 * @author BladeX
 * @since 2023-06-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/downstreamsync")
@Api(value = "下游同步配置表", tags = "下游同步配置表接口")
public class DownstreamSyncController extends BladeController {

	private final IDownstreamSyncService downstreamSyncService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "下游同步详情", notes = "下游同步详情")
	public R<DownstreamSyncVO> detail(Long id) {
		DownstreamSyncVO detail = downstreamSyncService.getDetail(id);
		return R.data(detail);
	}

//	/**
//	 * 分页 下游同步配置表
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "分页", notes = "传入downstreamSync")
//	public R<IPage<DownstreamSync>> list(DownstreamSync downstreamSync, Query query) {
//		IPage<DownstreamSync> pages = downstreamSyncService.page(Condition.getPage(query), Condition.getQueryWrapper(downstreamSync));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 下游同步配置表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "下游同步配置分页", notes = "下游同步配置分页")
	public R<IPage<DownstreamSyncVO>> page(String appName, Query query) {
		IPage<DownstreamSyncVO> pages = downstreamSyncService.selectDownstreamSyncPage(Condition.getPage(query),  appName);
		return R.data(pages);
	}


	/**
	 * 新增或修改 下游同步配置表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "下游同步配置新增或修改", notes = "下游同步配置新增或修改")
	@LcSysLog("下游同步配置新增或修改")
	public R submit(@Valid @RequestBody DownstreamSync downstreamSync) {
		return R.status(downstreamSyncService.submit(downstreamSync));
	}

	
	/**
	 * 删除 下游同步配置表
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "下游同步配置删除", notes = "下游同步配置删除")
	@LcSysLog("下游同步配置删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(downstreamSyncService.del(Func.toLongList(ids)));
	}

	
}
