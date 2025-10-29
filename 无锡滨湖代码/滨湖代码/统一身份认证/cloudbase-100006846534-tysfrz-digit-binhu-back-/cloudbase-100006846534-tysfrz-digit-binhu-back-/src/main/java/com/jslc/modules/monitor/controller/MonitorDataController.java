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
package com.jslc.modules.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jslc.modules.monitor.service.IMonitorJvmService;
import com.jslc.modules.monitor.vo.ApiStatisticsVO;
import com.jslc.modules.monitor.vo.JvmVO;
import com.jslc.modules.szbh.entity.BhEnt;
import com.jslc.modules.szbh.service.IBhEntService;
import com.jslc.modules.szbh.vo.BhEntVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 企业表 控制器
 *
 * @author BladeX
 * @since 2023-06-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/monitor/data")
@Api(value = "监控数据", tags = "监控数据接口")
public class MonitorDataController extends BladeController {
    private final IMonitorJvmService monitorJvmService;

    @GetMapping("/jvm")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取jvm数据", notes = "获取jvm数据")
    public R<List<JvmVO>> jvmInfo() {
        List<JvmVO> list = monitorJvmService.getJvmInfo();
        return R.data(list);
    }

    @GetMapping("/callapi")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取接口调用统计数据", notes = "获取接口调用统计数据")
    public R<Map> apiInfo() {
//		List<ApiStatisticsVO> list = monitorJvmService.getStatisticsApi();
        Map apiStatisticsVO = monitorJvmService.getStatisticsApi();
        return R.data(apiStatisticsVO);
    }

    @GetMapping("/apiAbnormaAmount")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取接口陷阱命中次数", notes = "获取接口陷阱命中次数")
    public R<Long> apiAbnormaAmount() {
        long amount = monitorJvmService.getApiAbnormaAmount();
        return R.data(amount);
    }

}
