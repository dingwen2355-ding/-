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
import com.jslc.modules.szbh.entity.BhSystemParam;
import com.jslc.modules.szbh.vo.BhSystemParamVO;
import com.jslc.modules.szbh.service.IBhSystemParamService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 滨湖系统参数 控制器
 *
 * @author BladeX
 * @since 2023-05-17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/bhsystemparam")
@Api(value = "滨湖系统参数", tags = "滨湖系统参数接口")
public class BhSystemParamController extends BladeController {

	private final IBhSystemParamService bhSystemParamService;

//	/**
//	 * 详情
//	 */
//	@GetMapping("/detail")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "滨湖系统参数详情", notes = "滨湖系统参数详情")
//	public R<BhSystemParam> detail(BhSystemParam bhSystemParam) {
//		BhSystemParam detail = bhSystemParamService.getOne(Condition.getQueryWrapper(bhSystemParam));
//		return R.data(detail);
//	}
//
//	/**
//	 * 分页 滨湖系统参数
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "滨湖系统参数分页", notes = "滨湖系统参数分页")
//	public R<IPage<BhSystemParam>> list(BhSystemParam bhSystemParam, Query query) {
//		IPage<BhSystemParam> pages = bhSystemParamService.page(Condition.getPage(query), Condition.getQueryWrapper(bhSystemParam));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 滨湖系统参数
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "滨湖系统参数分页", notes = "传入bhSystemParam")
	public R<IPage<BhSystemParamVO>> page(BhSystemParamVO bhSystemParam, Query query) {
		IPage<BhSystemParamVO> pages = bhSystemParamService.selectBhSystemParamPage(Condition.getPage(query), bhSystemParam);
		return R.data(pages);
	}


	/**
	 * 新增或修改 滨湖系统参数
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "滨湖系统参数新增或修改", notes = "滨湖系统参数新增或修改")
	@LcSysLog("滨湖系统参数新增或修改")
	public R submit(@Valid @RequestBody BhSystemParam bhSystemParam) {
		return R.status(bhSystemParamService.saveOrUpdate(bhSystemParam));
	}

	
	/**
	 * 删除 滨湖系统参数
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "滨湖系统参数删除", notes = "传入ids")
	@LcSysLog("滨湖系统参数删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(bhSystemParamService.removeBatchByIds(Func.toLongList(ids)));
	}

	
}
