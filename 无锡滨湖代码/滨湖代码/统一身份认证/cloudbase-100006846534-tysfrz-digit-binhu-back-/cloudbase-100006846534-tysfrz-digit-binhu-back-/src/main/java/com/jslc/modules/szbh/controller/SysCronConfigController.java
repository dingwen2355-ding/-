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

import com.jslc.modules.szbh.vo.SyncRecordVO;
import com.jslc.modules.szbh.vo.UpSynVO;
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
import com.jslc.modules.szbh.entity.SysCronConfig;
import com.jslc.modules.szbh.vo.SysCronConfigVO;
import com.jslc.modules.szbh.service.ISysCronConfigService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 *  控制器
 *
 * @author BladeX
 * @since 2023-05-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/syscronconfig")
@Api(value = "定时任务接口", tags = "定时任务接口")
public class SysCronConfigController extends BladeController {

	private final ISysCronConfigService sysCronConfigService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "定时任务接口详情", notes = "定时任务接口详情")
	public R<SysCronConfigVO> detail(Integer type) {
		SysCronConfigVO detail = sysCronConfigService.getDetail(type);
		return R.data(detail);
	}


	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "用户管理 设置定时时间", notes = "用户管理 设置定时时间 24小时之内的")
	@LcSysLog("用户管理设置定时时间")
	public R submit(@Valid @RequestBody SysCronConfigVO sysCronConfigVO) {
		return R.status(sysCronConfigService.submit(sysCronConfigVO));
	}


	/**
	 * 上游同步列表
	 */
	@GetMapping("/getUpSyn")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "上游同步 列表", notes = "上游同步 列表")
	public R<IPage<UpSynVO>> getUpSyn(String dataSource, Query query) {
		return R.data(sysCronConfigService.getUpSyn(Condition.getPage(query),dataSource));
	}


	
//	/**
//	 * 删除
//	 */
//	@PostMapping("/remove")
//	@ApiOperationSupport(order = 7)
//	@ApiOperation(value = "逻辑删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(sysCronConfigService.removeBatchByIds(Func.toLongList(ids)));
//	}

	
}
