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

import com.jslc.modules.szbh.vo.*;
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
import com.jslc.modules.szbh.entity.LoginRecord;
import com.jslc.modules.szbh.service.ILoginRecordService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;
import java.util.Map;

/**
 * 客户端登录记录 控制器
 *
 * @author BladeX
 * @since 2023-06-02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/loginrecord")
@Api(value = "客户端登录记录", tags = "客户端登录记录接口")
public class LoginRecordController extends BladeController {

	private final ILoginRecordService loginRecordService;

//	/**
//	 * 详情
//	 */
//	@GetMapping("/detail")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "详情", notes = "传入loginRecord")
//	public R<LoginRecord> detail(LoginRecord loginRecord) {
//		LoginRecord detail = loginRecordService.getOne(Condition.getQueryWrapper(loginRecord));
//		return R.data(detail);
//	}

	/**
	 * 自定义分页 客户端登录记录
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "客户端在线登录记录 分页", notes = "客户端在线登录记录 分页")
	public R<IPage<UserOnlineVO>> page(Long treeId,String userAccount ,String userName, Query query) {
		IPage<UserOnlineVO> pages = loginRecordService.selectLoginRecordPage(Condition.getPage(query),treeId, userAccount , userName);
		return R.data(pages);
	}

	/**
	 * 客户端数据
	 */
	@GetMapping("/getDatAPage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "客户端数据 分页", notes = "客户端数据 分页")
	public R<IPage<UserOnlineVO>> getDatAPage(String userAccount, Query query) {
		IPage<UserOnlineVO> pages = loginRecordService.getDatAPage(Condition.getPage(query) ,userAccount);
		return R.data(pages);
	}


	/**
	 * 用户活跃度
	 */
	@GetMapping("/getActivePage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "用户活跃度", notes = "用户活跃度")
	public R<IPage<ActiveUserVO>> getActivePage(Integer type, String start, String end , String deptIds, Query query) {
		IPage<ActiveUserVO> pages = loginRecordService.getActivePage(Condition.getPage(query),  type, start, end , deptIds);
		return R.data(pages);
	}

	/**
	 * 用户活跃度 echars 数据
	 */
	@GetMapping("/getEcharsList")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "用户活跃度 echars 数据", notes = "用户活跃度 echars 数据")
	public R<LoginUserEcharsVO> getEcharsList(Integer type, String start, String end , String deptIds ) {
		LoginUserEcharsVO pages = loginRecordService.getEcharsList(type, start, end , deptIds);
		return R.data(pages);
	}

	/**
	 * 用户活跃度 数据卡片
	 */
	@GetMapping("/getUserData")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "用户活跃度 数据卡片", notes = "用户活跃度 数据卡片")
	public R<List<HeadPageVO>> getUserData() {
		List<HeadPageVO> pages = loginRecordService.getUserData();
		return R.data(pages);
	}

	/**
	 * 访问最多部门排名
	 */
	@GetMapping("/getDeptRank")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "访问最多部门排名", notes = "访问最多部门排名")
	public R<List<HeadPageVO>> getDeptRank() {
		List<HeadPageVO> pages = loginRecordService.getDeptRank();
		return R.data(pages);
	}
	/**
	 * 近3个月的访问统计
	 */
	@GetMapping("/getAccessStatistics")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "近3个月的访问统计", notes = "近3个月的访问统计")
	public R<List<StatisticsMapVO>> getAccessStatistics() {
		List<StatisticsMapVO> map = loginRecordService.getAccessStatistics();
		return R.data(map);
	}




	/**
	 *  用户活跃度 导出
	 */
	@GetMapping("/downLoad")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "用户活跃度 导出", notes = "用户活跃度 导出")
	@LcSysLog("用户活跃度导出")
	public void downLoad(HttpServletResponse response , Integer type, String start, String end , String deptIds) {
		loginRecordService.downLoad( response,  type,  start,  end ,  deptIds);

	}



//	/**
//	 * 新增或修改 客户端登录记录
//	 */
//	@PostMapping("/submit")
//	@ApiOperationSupport(order = 6)
//	@ApiOperation(value = "新增或修改", notes = "传入loginRecord")
//	public R submit(@Valid @RequestBody LoginRecord loginRecord) {
//		return R.status(loginRecordService.saveOrUpdate(loginRecord));
//	}

	
//	/**
//	 * 删除 客户端登录记录
//	 */
//	@PostMapping("/remove")
//	@ApiOperationSupport(order = 7)
//	@ApiOperation(value = "逻辑删除", notes = "传入ids")
//	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
//		return R.status(loginRecordService.removeBatchByIds(Func.toLongList(ids)));
//	}

	
}
