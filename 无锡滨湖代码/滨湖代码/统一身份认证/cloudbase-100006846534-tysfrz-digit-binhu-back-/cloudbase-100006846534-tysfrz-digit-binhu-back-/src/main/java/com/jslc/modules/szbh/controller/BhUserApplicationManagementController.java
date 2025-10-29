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

import com.jslc.modules.szbh.dto.DeptMap;
import com.jslc.modules.szbh.vo.BhUserApplicationManagementOrderVO;
import io.swagger.annotations.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import com.jslc.common.annotation.LcSysLog;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.szbh.entity.BhUserApplicationManagement;
import com.jslc.modules.szbh.vo.BhUserApplicationManagementVO;
import com.jslc.modules.szbh.service.IBhUserApplicationManagementService;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 用户应用管理表 控制器
 *
 * @author BladeX
 * @since 2023-05-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/bhuserapplicationmanagement")
@Api(value = "用户应用管理表", tags = "用户应用管理表接口")
public class BhUserApplicationManagementController extends BladeController {

	private final IBhUserApplicationManagementService bhUserApplicationManagementService;

//	/**
//	 * 详情
//	 */
//	@GetMapping("/detail")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "详情", notes = "传入bhUserApplicationManagement")
//	public R<BhUserApplicationManagement> detail(BhUserApplicationManagement bhUserApplicationManagement) {
//		BhUserApplicationManagement detail = bhUserApplicationManagementService.getOne(Condition.getQueryWrapper(bhUserApplicationManagement));
//		return R.data(detail);
//	}

	/**
	 * 不分页 用户应用管理表
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "用户授权查看", notes = "用户授权查看")
//	@LcSysLog("用户授权查看")
	public R<List<DeptMap>> list(Long id,Long deptId) {
		List<DeptMap> list = bhUserApplicationManagementService.getList(id,deptId);
		return R.data(list);
	}

//	/**
//	 * 自定义分页 用户应用管理表
//	 */
//	@GetMapping("/page")
//	@ApiOperationSupport(order = 3)
//	@ApiOperation(value = "分页", notes = "传入bhUserApplicationManagement")
//	public R<IPage<BhUserApplicationManagementVO>> page(BhUserApplicationManagementVO bhUserApplicationManagement, Query query) {
//		IPage<BhUserApplicationManagementVO> pages = bhUserApplicationManagementService.selectBhUserApplicationManagementPage(Condition.getPage(query), bhUserApplicationManagement);
//		return R.data(pages);
//	}



	/**
	 * 新增或修改 用户应用管理表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改 用户授权", notes = "新增或修改 用户授权")
	@LcSysLog("用户授权")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "jsonStr", value = "账户集合", paramType = "query", dataType = "string"),
	})
	public R submit(@ApiIgnore @RequestBody Map<String,String> param) {
		return R.status(bhUserApplicationManagementService.submit(param.get("jsonStr")));
	}

	
//	/**
//	 * 删除 用户应用管理表
//	 */
//	@PostMapping("/remove")
//	@ApiOperationSupport(order = 7)
//	@ApiOperation(value = "逻辑删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(bhUserApplicationManagementService.removeBatchByIds(Func.toLongList(ids)));
//	}

	/*
		 * 拖拽排序 用户应用管理表
	 */
	@PostMapping("/submitOrder")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改 用户授权", notes = "新增或修改 用户授权")
	@LcSysLog("新增或修改用户授权")
	public R submitOrder(@ApiIgnore @RequestBody List<BhUserApplicationManagementOrderVO> param) {
		Boolean result = bhUserApplicationManagementService.submitOrder(param);
		return R.status(result);
	}


	
}
