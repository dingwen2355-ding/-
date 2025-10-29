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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import com.jslc.common.annotation.LcSysLog;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.jslc.modules.szbh.entity.BhDept;
import com.jslc.modules.szbh.vo.BhDeptVO;
import com.jslc.modules.szbh.service.IBhDeptService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 部门管理 控制器
 *
 * @author BladeX
 * @since 2023-05-09
 */
@RestController
@AllArgsConstructor
@RequestMapping(CommonConstant.SQUIRREL_SZBH +"/bhdept")
@Api(value = "部门管理", tags = "部门管理接口")
public class BhDeptController extends BladeController {

	private final IBhDeptService bhDeptService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查看部门详情", notes = "查看部门详情")
//	@LcSysLog("查看部门详情")
	public R<BhDeptVO> detail(Long id) {
		BhDeptVO detail = bhDeptService.getDetail(id);
		return R.data(detail);
	}


	/**
	 *  部门管理
	 */
	@GetMapping("/list2")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查看部门列表2级", notes = "查看部门列表2级")
//	@LcSysLog("查看部门列表")
	public R<List<BhDeptVO>> getList2(String name , Integer type,Integer isEnable , Long parentId) {
		List<BhDeptVO> list = bhDeptService.getList2( name ,  type ,  parentId, isEnable);
		return R.data(list);
	}



	/**
	 *  部门管理
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查看部门列表", notes = "查看部门列表")
//	@LcSysLog("查看部门列表")
	public R<List<BhDeptVO>> getList(String name , Integer type,Integer isEnable , Long parentId) {
		List<BhDeptVO> list = bhDeptService.getList( name ,  type ,  parentId, isEnable);
		return R.data(list);
	}



	/**
	 * 新增或修改 部门管理
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "部门新增或修改", notes = "部门新增或修改")
	@LcSysLog("部门新增或修改")
	public R submit(@Valid @RequestBody BhDept bhDept) {
		return R.status(bhDeptService.submit(bhDept));
	}

	/**
	 * 停用或启用 部门管理
	 */
	@PostMapping("/changeEnable")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "部门停用或启用", notes = "部门停用或启用")
	@LcSysLog("部门停用或启用")
	public R changeEnable(@Valid @RequestParam Long id) {
		return R.status(bhDeptService.changeEnable(id));
	}
	
	/**
	 * 删除 部门管理
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "部门删除", notes = "部门删除")
	@LcSysLog("部门删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(bhDeptService.del(Func.toLongList(ids)));
	}



	@GetMapping("/getBhDeptTree")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "部门树", notes = "部门树")
	public R<List<SystemTreeNode>> getBhDeptTree(Long id) {
		List<SystemTreeNode> tree = bhDeptService.getBhDeptTree(id);
		return R.data(tree);
	}

	@GetMapping("/downLoad")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "导出部门信息", notes = "导出部门信息")
	@LcSysLog("导出部门信息")
	public void downLoadRanking(HttpServletResponse response,String name , String type,String ids,Integer isEnable) {
		Integer deptType= null;
		List<Long> id = null;
		if (Func.isNotEmpty(type) && !type.equals("undefined")){
			deptType = Integer.valueOf(type);
		}
		if (Func.isNotEmpty(name) && name.equals("undefined")){
			name = null;
		}
		if (Func.isNotEmpty(ids)){
			if (ids.equals("undefined") || ids.length()==0){
				id = null;
			}else {
				id = Func.toLongList(ids);
			}
		}
		bhDeptService.downLoad(response,name,deptType,id,isEnable);
	}


	/**
	 * 同步 部门管理
	 */
	@PostMapping("/syn")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "同步部门信息", notes = "同步部门信息")
	@LcSysLog("同步部门信息")
	public R syn() {
		return R.status(bhDeptService.syn());
	}

}
