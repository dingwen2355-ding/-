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
import com.jslc.modules.szbh.vo.SystemTreeNode;
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
import com.jslc.modules.szbh.entity.BhApplicationCategory;
import com.jslc.modules.szbh.vo.BhApplicationCategoryVO;
import com.jslc.modules.szbh.service.IBhApplicationCategoryService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 应用类别 控制器
 *
 * @author BladeX
 * @since 2023-05-09
 */
@RestController
@AllArgsConstructor
@RequestMapping(CommonConstant.SQUIRREL_SZBH +"/bhapplicationcategory")
@Api(value = "应用类别", tags = "应用类别接口")
public class BhApplicationCategoryController extends BladeController {

	private final IBhApplicationCategoryService bhApplicationCategoryService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查看应用类别详情", notes = "查看应用类别详情")
//	@LcSysLog("查看应用类别详情")
	public R<BhApplicationCategoryVO> detail(Long id) {
		BhApplicationCategoryVO detail = bhApplicationCategoryService.getDetail(id);
		return R.data(detail);
	}

	/**
	 * 分页 应用类别
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "查看应用类别 不分页", notes = "查看应用类别 不分页")
	public R<List<BhApplicationCategoryVO>> list(String name , Integer isEnable, Long parentId,Integer type) {
		List<BhApplicationCategoryVO> pages = bhApplicationCategoryService.getList(name,isEnable,parentId,type);
		return R.data(pages);
	}


	/**
	 * 新增或修改 应用类别
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增或修改 应用类别", notes = "新增或修改 应用类别")
	@LcSysLog("新增或修改应用类别")
	public R submit(@Valid @RequestBody BhApplicationCategory bhApplicationCategory) {
		return R.status(bhApplicationCategoryService.submit(bhApplicationCategory));
	}

	
	/**
	 * 删除 应用类别
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "删除应用类别", notes = "删除应用类别")
	@LcSysLog("删除应用类别")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(bhApplicationCategoryService.del(Func.toLongList(ids)));
	}



	/**
	 * 停用或启用 应用类别
	 */
	@PostMapping("/changeEnable")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "停用或启用", notes = "停用或启用接口 状态 启用状态,1-启用，0-停用")
	@LcSysLog("停用或启用应用")
	public R changeEnable(@Valid @RequestParam Long id ) {
		return R.status(bhApplicationCategoryService.changeEnable(id));
	}


	@GetMapping("/getAppCategoryTree")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "应用类别树", notes = "应用类别树 treeType 1类别,2 应用 ")
	public R<List<SystemTreeNode>> getAppCategoryTree(String name,Integer treeType,Integer type) {
		List<SystemTreeNode> tree = bhApplicationCategoryService.getAppCategoryTree(name, treeType, type);
		return R.data(tree);
	}

	@GetMapping("/getUserAppCategoryTree")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "应用类别树", notes = "应用类别树 treeType 1类别,2 应用 ")
	public R<List<SystemTreeNode>> getUserAppCategoryTree(String name,Integer treeType) {
		List<SystemTreeNode> tree = bhApplicationCategoryService.getUserAppCategoryTree(name, treeType);
		return R.data(tree);
	}


	/***********************************前台*****************************/

	/**
	 * 分页 应用类别
	 */
	@GetMapping("/getUserApplicationCategory")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "获取应用类别", notes = "获取应用类别")
	public R<List<BhApplicationCategoryVO>> getUserApplicationCategory() {
		List<BhApplicationCategoryVO> pages = bhApplicationCategoryService.getUserApplicationCategory();
		return R.data(pages);
	}
}
