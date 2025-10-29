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

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jslc.common.constant.CommonConstant;
import com.jslc.modules.szbh.service.IBhDeptSyncService;
import com.jslc.modules.szbh.vo.SystemTreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import com.jslc.common.annotation.LcSysLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门管理同步原始表 控制器
 *
 * @author BladeX
 * @since 2023-05-09
 */
@RestController
@AllArgsConstructor
@RequestMapping(CommonConstant.SQUIRREL_SZBH + "/bhdeptsync")
@Api(value = "部门管理同步原始表", tags = "部门管理同步原始表接口")
public class BhDeptSyncController extends BladeController {

    private final IBhDeptSyncService bhDeptSyncService;

    /*
     * @method getAccessToken
     * @description 获取token
     * @author xxz
     * @date 2023/5/10
     */
    @GetMapping("/getAccessToken")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取token", notes = "获取token")
    public R<String> getAccessToken() {
        String pages = bhDeptSyncService.getAccessToken();
        return R.data(pages);
    }

    /*
     * @method syncDept
     * @description 同步部门至原始记录表
     * @author xxz
     * @date 2023/5/10
     */
    @GetMapping("/syncDept")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "同步部门至原始记录表", notes = "同步部门至原始记录表")
    @LcSysLog("同步部门至原始记录表")
    public R<Boolean> syncDept() {
        return R.status(bhDeptSyncService.syncDept());
    }


    @GetMapping("/getBhDeptTree")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "部门树syn", notes = "部门树syn")
    public R<List<SystemTreeNode>> getBhDeptTree() {
        List<SystemTreeNode> tree = bhDeptSyncService.getBhDeptSynTree();
        return R.data(tree);
    }

}
