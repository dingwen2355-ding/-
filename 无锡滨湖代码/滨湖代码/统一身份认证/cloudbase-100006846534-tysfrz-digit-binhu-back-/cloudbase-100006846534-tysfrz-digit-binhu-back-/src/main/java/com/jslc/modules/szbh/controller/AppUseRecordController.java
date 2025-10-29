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
import com.jslc.modules.szbh.entity.AppUseRecord;
import com.jslc.modules.szbh.service.IAppUseRecordService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;
import java.util.Map;

/**
 * 应用使用记录 控制器
 *
 * @author BladeX
 * @since 2023-06-02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/appuserecord")
@Api(value = "应用使用记录", tags = "应用使用记录接口")
public class AppUseRecordController extends BladeController {

	private final IAppUseRecordService appUseRecordService;

//	/**
//	 * 详情
//	 */
//	@GetMapping("/detail")
//	@ApiOperationSupport(order = 1)
//	@ApiOperation(value = "详情", notes = "传入appUseRecord")
//	public R<AppUseRecord> detail(AppUseRecord appUseRecord) {
//		AppUseRecord detail = appUseRecordService.getOne(Condition.getQueryWrapper(appUseRecord));
//		return R.data(detail);
//	}


	/**
	 * 用户管理 登录日志分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "用户管理 登录日志分页", notes = "用户管理 登录日志分页")
	public R<IPage<UserOnlineVO>> page(String userAccount, String start, String end , String deptIds, String appIds , Query query) {

		IPage<UserOnlineVO> pages = appUseRecordService.selectAppUseRecordPage(Condition.getPage(query),  userAccount, start, end , deptIds, appIds );
		return R.data(pages);
	}


	/**
	 * 获取应用登录统计 表头
	 */
	@GetMapping("/getHead")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取应用登录统计 表头", notes = "获取应用登录统计 表头")
	public R<List<HeadAppInfoVO>> getHead(Integer type, String start, String end , String deptIds,String appIds) {
		List<HeadAppInfoVO> pages = appUseRecordService.getHead(type, start,end, deptIds, appIds);
		return R.data(pages);
	}

	/**
	 * 获取应用登录统计 列表
	 */
	@GetMapping("/getRecordPage")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取应用登录统计 列表", notes = "获取应用登录统计 列表")
	public R<IPage<AppRecordVO>> getRecordPage(Integer type, String start, String end , String deptIds, String appIds,Query query) {
		IPage<AppRecordVO> pages = appUseRecordService.getRecordPage(Condition.getPage(query),type, start,end, deptIds, appIds);
		return R.data(pages);
	}

	/**
	 * 获取应用登录统计 echars数据
	 */
	@GetMapping("/getRecordEchars")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取应用登录统计 echars数据", notes = "获取应用登录统计 echars数据")
	public R<List<LoginRecordEcharsVO>> getRecordEchars(Integer type, String start, String end , String deptIds, String appIds) {
		List<LoginRecordEcharsVO> map = appUseRecordService.getRecordEchars(type, start,end, deptIds, appIds);
		return R.data(map);
	}


	/**
	 * 获取环比数据卡片
	 */
	@GetMapping("/getYOYRecord")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取环比数据卡片", notes = "获取环比数据卡片")
	public R<List<HeadPageVO>> getYOYRecord() {
		List<HeadPageVO> map = appUseRecordService.getYOYRecord();
		return R.data(map);
	}

	/**
	 * 应用访问分类统计
	 */
	@GetMapping("/getAppClassificationStatistics")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "应用访问分类统计", notes = "应用访问分类统计")
	public R<List<AppClassificationStatisticsVO>> getAppClassificationStatistics() {
		List<AppClassificationStatisticsVO> map = appUseRecordService.getAppClassificationStatistics();
		return R.data(map);
	}



	/**
	 *  应用登录统计 导出
	 */
	@GetMapping("/appRecordLoad")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "应用登录统计 导出", notes = "应用登录统计 导出")
	@LcSysLog("应用登录统计导出")
	public void appRecordLoad(HttpServletResponse response , Integer type, String start, String end , String deptIds, String appIds) {
		appUseRecordService.appRecordLoad( response, type, start, end , deptIds, appIds);

	}



	
	/**
	 * 删除 应用使用记录
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除 应用使用记录", notes = "删除 应用使用记录")
	@LcSysLog("删除应用使用记录")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(appUseRecordService.dels(Func.toLongList(ids)));
	}


	/**
	 *  用户管理 登录日志导出
	 */
	@GetMapping("/downLoad")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "用户管理 登录日志 导出", notes = "用户管理 登录日志 导出")
	@LcSysLog("用户管理登录日志导出")
	public void passCheck(HttpServletResponse response , String userAccount, String start, String end , String deptIds, String appIds,String ids) {
		appUseRecordService.downLoad( response,  userAccount,  start,  end ,  deptIds,  appIds, ids);

	}


	/***********************************前台*****************************/

	@GetMapping("/getUserRecord")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "当前登录账户的访问", notes = "当前登录账户的访问")
	public R<List<NVMapVO>> getUserRecord(Integer type) {
		List<NVMapVO> map = appUseRecordService.getUserRecord(type);
		return R.data(map);
	}





	
}
