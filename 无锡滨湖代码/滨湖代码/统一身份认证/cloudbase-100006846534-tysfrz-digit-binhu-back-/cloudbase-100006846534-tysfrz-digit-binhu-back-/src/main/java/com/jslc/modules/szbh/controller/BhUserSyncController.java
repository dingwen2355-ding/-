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
import com.jslc.modules.szbh.entity.BhUserSync;
import com.jslc.modules.szbh.service.IBhUserSyncService;
import com.jslc.modules.szbh.vo.SystemTreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.tool.api.R;
import com.jslc.common.annotation.LcSysLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理 控制器
 *
 * @author BladeX
 * @since 2023-05-09
 */
@RestController
@AllArgsConstructor
@RequestMapping(CommonConstant.SQUIRREL_SZBH + "/bhusersync")
@Api(value = "用户管理同步原始表", tags = "用户管理同步原始表接口")
public class BhUserSyncController extends BladeController {

    private final IBhUserSyncService bhUserSyncService;

    /*
     * @method syncUser
     * @description 同步用户至原始记录表
     * @author xxz
     * @date 2023/5/10
     */
    @GetMapping("/syncUser")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "同步用户至原始记录表", notes = "同步用户至原始记录表")
    @LcSysLog("同步用户至原始记录表")
    public R<Boolean> syncUser() {
        return R.status(bhUserSyncService.syncUser());
    }


    @GetMapping("/getUserSyn")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "部门下的人员", notes = "部门下的人员")
    public R<List<BhUserSync>> getUserSyn(String ids) {
        List<BhUserSync> tree = bhUserSyncService.getUserSyn(ids);
        return R.data(tree);
    }

}
