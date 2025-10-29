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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.szbh.entity.IpAbnormalLog;
import com.jslc.modules.szbh.vo.IpAbnormalLogVO;
import com.jslc.modules.szbh.service.IIpAbnormalLogService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * ip相同动作异常记录表 控制器
 *
 * @author BladeX
 * @since 2023-10-09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/ipabnormallog")
@Api(value = "ip相同动作异常记录表", tags = "ip相同动作异常记录表接口")
public class IpAbnormalLogController extends BladeController {

	private final IIpAbnormalLogService ipAbnormalLogService;

	/**
	 * 自定义分页 ip相同动作异常记录表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入ipAbnormalLog")
	public R<IPage<IpAbnormalLogVO>> page(IpAbnormalLogVO ipAbnormalLog, Query query) {
		IPage<IpAbnormalLogVO> pages = ipAbnormalLogService.selectIpAbnormalLogPage(Condition.getPage(query), ipAbnormalLog);
		return R.data(pages);
	}

	/**
	 * 新增或修改 ip相同动作异常记录表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入ipAbnormalLog")
	public R submit(@Valid @RequestBody IpAbnormalLog ipAbnormalLog) {
		return R.status(ipAbnormalLogService.submit(ipAbnormalLog));
	}


	
}
