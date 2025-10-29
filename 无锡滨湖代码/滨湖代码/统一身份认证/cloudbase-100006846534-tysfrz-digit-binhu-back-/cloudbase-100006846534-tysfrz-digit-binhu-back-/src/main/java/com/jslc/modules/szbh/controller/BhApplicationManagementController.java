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

import com.jslc.common.constant.CommonConstant;
import com.jslc.modules.szbh.entity.BhDept;
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
import com.jslc.modules.szbh.entity.BhApplicationManagement;
import com.jslc.modules.szbh.vo.BhApplicationManagementVO;
import com.jslc.modules.szbh.service.IBhApplicationManagementService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 应用管理 控制器
 *
 * @author BladeX
 * @since 2023-05-09
 */
@RestController
@AllArgsConstructor
@RequestMapping(CommonConstant.SQUIRREL_SZBH + "/bhapplicationmanagement")
@Api(value = "应用管理", tags = "应用管理接口")
public class BhApplicationManagementController extends BladeController {

	private final IBhApplicationManagementService bhApplicationManagementService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "应用详情", notes = "应用详情")
//	@LcSysLog("应用详情")
	public R<BhApplicationManagementVO> detail(Long id) {
		BhApplicationManagementVO detail = bhApplicationManagementService.getDetail(id);
		return R.data(detail);
	}



	/**
	 * 自定义分页 应用管理
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "应用列表分页", notes = "应用列表分页")
//	@LcSysLog("应用列表分页")
	public R<IPage<BhApplicationManagementVO>> page(String code,String categoryId,Integer isGround, Integer isRecommend , Query query) {
		IPage<BhApplicationManagementVO> pages = bhApplicationManagementService.selectBhApplicationManagementPage(Condition.getPage(query), code, categoryId, isGround, isRecommend );
		return R.data(pages);
	}

	/**
	 * 自定义分页 应用管理
	 */
	@GetMapping("/getList")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "应用列表不分页", notes = "应用列表不分页")
//	@LcSysLog("应用列表不分页")
	public R<List<BhApplicationManagement>> getList(String key) {
		List<BhApplicationManagement> pages = bhApplicationManagementService.getList(key);
		return R.data(pages);
	}

	/**
	 * 新增或修改 应用管理
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "应用新增或修改", notes = "应用新增或修改")
	@LcSysLog("应用新增或修改")
	public R submit(@Valid @RequestBody BhApplicationManagementVO bhApplicationManagement) {
		return R.status(bhApplicationManagementService.submit(bhApplicationManagement));
	}

	


	/**
	 * 删除 应用管理
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "应用删除", notes = "应用删除")
	@LcSysLog("应用删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(bhApplicationManagementService.del(Func.toLongList(ids)));
	}

	/**
	 * 停用或启用 应用类别
	 */
	@PostMapping("/changeEnable")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "应用停用或启用", notes = "应用停用或启用")
	@LcSysLog("应用停用或启用")
	public R changeEnable(@Valid @RequestParam Long id ) {
		return R.status(bhApplicationManagementService.changeEnable(id));
	}


	/**
	 * 上架或者下架 应用类别
	 */
	@PostMapping("/changeGround")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "上架或者下架应用", notes = "上架或者下架应用")
	@LcSysLog("上架或者下架")
	public R changeGround(@Valid @RequestParam Long id ) {
		return R.status(bhApplicationManagementService.changeGround(id));
	}


	/**
	 * 随机生成 secret
	 */
	@GetMapping("/getSecret")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "随机生成 secret", notes = "随机生成应用 secret")
	public R getSecret() {
		return R.data(bhApplicationManagementService.getSecret());
	}


	/***********************************前台*****************************/
	/**
	 * 自定义分页 用户的权限应用
	 */
	@GetMapping("/getUserApp")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "用户的权限应用", notes = "用户的权限应用")
//	@LcSysLog("应用")
	public R<List<BhApplicationManagementVO>> getUserApp(String name) {
		List<BhApplicationManagementVO> pages = bhApplicationManagementService.getUserApp(name);
		return R.data(pages);
	}

	/**
	 * 自定义分页 用户的权限应用
	 */
	@GetMapping("/applicationByClassificationAndDept")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "根据应用分类应用单位查询应用", notes = "根据应用分类应用单位查询应用")
	public R<IPage<BhApplicationManagementVO>> 根据应用分类应用单位查询应用( Long applicationClassificationId, Long deptId,Integer isAuthorization,String appName, Query query) {
		IPage<BhApplicationManagementVO> pages = bhApplicationManagementService.applicationByClassificationAndDept(Condition.getPage(query),applicationClassificationId, deptId,isAuthorization, appName);
		return R.data(pages);
	}

	/**
	 * 自定义分页 用户的权限应用
	 */
	@GetMapping("/getUserDept")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "用户部门", notes = "用户部门")
//	@LcSysLog("用户部门")
	public R<List<BhDept>> getUserDept() {
		List<BhDept> pages = bhApplicationManagementService.getUserDept();
		return R.data(pages);
	}

	@GetMapping("/getUserAppId")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取用户应用appId", notes = "获取用户应用appId")
	public R<List<String>> getUserAppId(String account) {
		List<String> list = bhApplicationManagementService.getUserAppId(account);
		return R.data(list);
	}

}
