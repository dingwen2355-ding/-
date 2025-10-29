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

import com.jslc.modules.szbh.vo.AuthorityApplyForIdsVO;
import com.jslc.modules.szbh.vo.DeptUserVO;
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
import com.jslc.modules.szbh.entity.AuthorityApplyFor;
import com.jslc.modules.szbh.vo.AuthorityApplyForVO;
import com.jslc.modules.szbh.service.IAuthorityApplyForService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 权限申请表 控制器
 *
 * @author BladeX
 * @since 2023-07-25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/authorityapplyfor")
@Api(value = "权限申请表", tags = "权限申请表接口")
public class AuthorityApplyForController extends BladeController {

	private final IAuthorityApplyForService authorityApplyForService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入authorityApplyFor")
	public R<AuthorityApplyForVO> detail(Long id) {
		AuthorityApplyForVO detail = authorityApplyForService.getDetail(id);
		return R.data(detail);
	}
	/**
	 * 详情
	 */
	@GetMapping("/getUserInfo")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取人员部门信息", notes = "获取人员部门信息")
	public R<DeptUserVO> getUserInfo() {
		DeptUserVO detail = authorityApplyForService.getUserInfo();
		return R.data(detail);
	}

//	/**
//	 * 分页 权限申请表
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "分页", notes = "传入authorityApplyFor")
//	public R<IPage<AuthorityApplyFor>> list(AuthorityApplyFor authorityApplyFor, Query query) {
//		IPage<AuthorityApplyFor> pages = authorityApplyForService.page(Condition.getPage(query), Condition.getQueryWrapper(authorityApplyFor));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 权限申请表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入authorityApplyFor")
	public R<IPage<AuthorityApplyForVO>> page(Query query) {
		IPage<AuthorityApplyForVO> pages = authorityApplyForService.selectAuthorityApplyForPage(Condition.getPage(query));
		return R.data(pages);
	}

//	/**
//	 * 新增 权限申请表
//	 */
//	@PostMapping("/save")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增", notes = "传入authorityApplyFor")
//	public R save(@Valid @RequestBody AuthorityApplyFor authorityApplyFor) {
//		return R.status(authorityApplyForService.save(authorityApplyFor));
//	}
//
//	/**
//	 * 修改 权限申请表
//	 */
//	@PostMapping("/update")
//	@ApiOperationSupport(order = 5)
//	@ApiOperation(value = "修改", notes = "传入authorityApplyFor")
//	public R update(@Valid @RequestBody AuthorityApplyFor authorityApplyFor) {
//		return R.status(authorityApplyForService.updateById(authorityApplyFor));
//	}

	/**
	 * 新增或修改 权限申请表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "权限申请表新增或修改", notes = "传入authorityApplyFor")
	@LcSysLog("权限申请表新增或修改")
	public R submit(@Valid @RequestBody AuthorityApplyFor authorityApplyFor) {
		return R.status(authorityApplyForService.submit(authorityApplyFor));
	}

	/**
	 * 新增或修改 权限申请表
	 */
	@PostMapping("/submitList")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "权限申请表新增或修改 批量授权", notes = "传入authorityApplyFor")
	public R submitList(@Valid @RequestBody AuthorityApplyForIdsVO authorityApplyFor) {
		return R.status(authorityApplyForService.submitList(authorityApplyFor));
	}


	/**
	 * 获取都市圈token
	 */
	@GetMapping("/getToken")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "获取都市圈token", notes = "传入authorityApplyFor")
	public R getToken() {
		return R.data(authorityApplyForService.getToken());
	}



//	/**
//	 * 删除 权限申请表
//	 */
//	@PostMapping("/remove")
//	@ApiOperationSupport(order = 7)
//	@ApiOperation(value = "逻辑删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(authorityApplyForService.removeByIds(Func.toLongList(ids)));
//	}

	/***********************************前台*****************************/

	@GetMapping("/getUserApplyFor")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "分页", notes = "传入authorityApplyFor")
	public R<IPage<AuthorityApplyForVO>> getUserApplyFor(String appName ,Integer reviewStatus,Query query) {
		IPage<AuthorityApplyForVO> pages = authorityApplyForService.getUserApplyFor(Condition.getPage(query), appName , reviewStatus);
		return R.data(pages);
	}
	
}
