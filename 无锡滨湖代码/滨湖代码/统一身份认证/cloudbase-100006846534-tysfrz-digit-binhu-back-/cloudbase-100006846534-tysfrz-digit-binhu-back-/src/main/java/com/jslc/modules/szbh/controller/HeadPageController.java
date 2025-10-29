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


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jslc.modules.szbh.service.IDownstreamSyncService;
import com.jslc.modules.szbh.service.IHeadPageService;
import com.jslc.modules.szbh.vo.HeadActiveVO;
import com.jslc.modules.szbh.vo.HeadPageVO;
import com.jslc.modules.szbh.vo.SyncRecordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 报警信息 控制器
 *
 * @author BladeX
 * @since 2020-07-14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/headPage")
@Api(value = "首页", tags = "首页")
public class HeadPageController extends BladeController{

    private final IHeadPageService headPageService;

    /**
     * 首页 登录人员
     */
    @GetMapping("/getUserInfo")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "首页登录人员", notes = "首页登录人员")
    public R<Map<String,Object>> getUserInfo() {
        Map<String,Object> map = headPageService.getUserInfo();
        return R.data(map);
    }

    /**
     * 首页数据简报
     */
    @GetMapping("/getBulletin")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "首页数据简报", notes = "首页数据简报")
    public R<List<HeadPageVO>> getBulletin() {
        List<HeadPageVO> pages = headPageService.getBulletin();
        return R.data(pages);
    }


    /**
     * 应用排名
     */
    @GetMapping("/getAppRecordRank")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "应用排名", notes = "应用排名")
    public R<List<HeadPageVO>> getAppRecordRank() {
        List<HeadPageVO> pages = headPageService.getAppRecordRank();
        return R.data(pages);
    }

    /**
     * 活跃趋势
     */
    @GetMapping("/getYearActive")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "活跃趋势", notes = "活跃趋势")
    public R<HeadActiveVO> getYearActive() {
        HeadActiveVO pages = headPageService.getYearActive();
        return R.data(pages);
    }






}
