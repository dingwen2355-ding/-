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

import com.jslc.modules.szbh.vo.BhNoticeRestVO;
import com.jslc.modules.szbh.vo.ResultVO;
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
import com.jslc.modules.szbh.entity.BhNotice;
import com.jslc.modules.szbh.vo.BhNoticeVO;
import com.jslc.modules.szbh.service.IBhNoticeService;
import org.springblade.core.boot.ctrl.BladeController;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 通知公告 控制器
 *
 * @author BladeX
 * @since 2023-08-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/bhnotice")
@Api(value = "通知公告", tags = "通知公告接口")
public class BhNoticeController extends BladeController {

	private final IBhNoticeService bhNoticeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入bhNotice")
	public R<BhNoticeVO> detail(Long id) {
		BhNoticeVO detail = bhNoticeService.getDetail(id);
		return R.data(detail);
	}

//	/**
//	 * 分页 通知公告
//	 */
//	@GetMapping("/list")
//	@ApiOperationSupport(order = 2)
//	@ApiOperation(value = "都市圈 分页", notes = "传入bhNotice")
//	public R<IPage<BhNoticeVO>> list(Integer noticeType, Query query) {
//		IPage<BhNoticeVO> pages = bhNoticeService.getPage(Condition.getPage(query), noticeType);
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 通知公告
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "后台管理 分页", notes = "传入type   0待发送 1已发送")
	public R<IPage<BhNoticeVO>> page(String title,Integer noticeType,Integer type,String start,String end,String downStart,String downEnd,Integer isTop, Query query) {
		IPage<BhNoticeVO> pages = bhNoticeService.selectBhNoticePage(Condition.getPage(query),  title, noticeType, type, start, end, downStart, downEnd, isTop);
		return R.data(pages);
	}

//	/**
//	 * 新增 通知公告
//	 */
//	@PostMapping("/save")
//	@ApiOperationSupport(order = 4)
//	@ApiOperation(value = "新增", notes = "传入bhNotice")
//	public R save(@Valid @RequestBody BhNotice bhNotice) {
//		return R.status(bhNoticeService.save(bhNotice));
//	}

	/**
	 * 修改 通知公告
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "发布", notes = "传入bhNotice")
	@LcSysLog("修改通知公告")
	public R update(@Valid @RequestBody BhNotice bhNotice) {
		bhNotice.setReleaseTime(LocalDate.now());
		return R.status(bhNoticeService.updateById(bhNotice));
	}

	/**
	 * 新增或修改 通知公告
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入bhNotice")
	@LcSysLog("新增或修改通知公告")
	public R submit(@Valid @RequestBody BhNotice bhNotice) {
		return R.status(bhNoticeService.submit(bhNotice));
	}

	
	/**
	 * 删除 通知公告
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	@LcSysLog("删除通知公告")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(bhNoticeService.removeBatchByIds(Func.toLongList(ids)));
	}

	/***********************************前台*****************************/

	@GetMapping("/getNoticeList")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "通知公告", notes = "通知公告")
	public R getNoticeList( Integer noticeType, Query query,String title) {
		IPage<BhNoticeVO> resultVO = bhNoticeService.getNoticeList(Condition.getPage(query),noticeType, title);
		return R.data(resultVO);
	}



	
}
