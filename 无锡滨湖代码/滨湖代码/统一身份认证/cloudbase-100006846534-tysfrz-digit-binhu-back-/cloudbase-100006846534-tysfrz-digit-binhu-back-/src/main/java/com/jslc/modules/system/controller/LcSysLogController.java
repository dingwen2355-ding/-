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
package com.jslc.modules.system.controller;


import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jslc.common.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import net.sf.cglib.core.Local;
import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import com.jslc.modules.system.entity.LcSysLogEntity;
import com.jslc.modules.system.service.LcSysLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 控制器
 *
 * @author Chill
 */
@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.APPLICATION_LOG_NAME + "/log/operation")
public class LcSysLogController {

    private final LcSysLogService logService;


    /**
     * 操作日志(分页)
     */
    @GetMapping("/list")
    public R<IPage<LcSysLogEntity>> list(@ApiIgnore @RequestParam Map<String, Object> log, Query query) {

        IPage<LcSysLogEntity> page = Condition.getPage(query.setDescs("create_date"));
//		Condition.getQueryWrapper(LcSysLogEntity.class)
        LambdaQueryWrapper<LcSysLogEntity> queryWrapper = Wrappers.lambdaQuery(LcSysLogEntity.class);
        if (log.get("id") != null) {
            queryWrapper.eq(LcSysLogEntity::getId, log.get("id"));
        }

        if (log.get("username") != null) {
            queryWrapper.like(LcSysLogEntity::getUsername, log.get("username"));
        }

        if (log.get("operation") != null) {
            queryWrapper.like(LcSysLogEntity::getOperation, log.get("operation"));
        }

        if (log.get("method") != null) {
            queryWrapper.like(LcSysLogEntity::getMethod, log.get("method"));
        }

        if (log.get("ip") != null) {
            queryWrapper.like(LcSysLogEntity::getIp, log.get("ip"));
        }
        if (log.get("start") != null && log.get("end") != null) {
            LocalDateTime startTim = LocalDateTime.of(LocalDate.parse((String) log.get("start")), LocalTime.MIN);
            LocalDateTime endTim = LocalDateTime.of(LocalDate.parse((String) log.get("end")), LocalTime.MAX);
            queryWrapper.between(LcSysLogEntity::getCreateDate, startTim, endTim);
        }
        queryWrapper.ne(LcSysLogEntity::getOperation, "登录");

        /*List<LcSysLogEntity> list = logService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list = list.stream().filter(f -> !f.getOperation().contains("登录")).sorted(Comparator.comparing(LcSysLogEntity::getCreateDate).reversed()).collect(Collectors.toList());
        }*/

        IPage<LcSysLogEntity> result = logService.page(page, queryWrapper);
        for (LcSysLogEntity lcSysLogEntity : result.getRecords()) {
            String operSm = logService.encryptOper(lcSysLogEntity);
            String oldOperSm = lcSysLogEntity.getOperSm();
            lcSysLogEntity.setOperVerif(operSm != null ? (operSm.equals(oldOperSm) ? "否" : "是") :  "否");
        }

//        IPage iPage = CommonUtil.manualPage(page, list);
        return R.data(result);
    }

    /**
     * 登录日志(分页)
     */
    @GetMapping("/getLoginList")
    public R<IPage<LcSysLogEntity>> getLoginList(@ApiIgnore @RequestParam Map<String, Object> log, Query query) {
        IPage<LcSysLogEntity> page = Condition.getPage(query.setDescs("create_date"));
        LambdaQueryWrapper<LcSysLogEntity> queryWrapper = Wrappers.lambdaQuery(LcSysLogEntity.class);
        if (log.get("id") != null) {
            queryWrapper.eq(LcSysLogEntity::getId, log.get("id"));
        }

        if (log.get("username") != null) {
            queryWrapper.like(LcSysLogEntity::getUsername, log.get("username"));
        }
        if (log.get("operation") != null) {
            queryWrapper.like(LcSysLogEntity::getOperation, log.get("operation"));
        }

        if (log.get("method") != null) {
            queryWrapper.like(LcSysLogEntity::getMethod, log.get("method"));
        }

        if (log.get("ip") != null) {
            queryWrapper.like(LcSysLogEntity::getIp, log.get("ip"));
        }

        if (log.get("start") != null && log.get("end") != null) {
            LocalDateTime startTim = LocalDateTime.of(LocalDate.parse((String) log.get("start")), LocalTime.MIN);
            LocalDateTime endTim = LocalDateTime.of(LocalDate.parse((String) log.get("end")), LocalTime.MAX);
            queryWrapper.between(LcSysLogEntity::getCreateDate, startTim, endTim);
        }


        /*List<LcSysLogEntity> list = logService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            list = list.stream().filter(f -> f.getOperation().contains("登录")).sorted(Comparator.comparing(LcSysLogEntity::getCreateDate).reversed()).collect(Collectors.toList());
        }*/
        queryWrapper.eq(LcSysLogEntity::getOperation, "登录");
        IPage<LcSysLogEntity> result = logService.page(page, queryWrapper);
        for (LcSysLogEntity lcSysLogEntity : result.getRecords()) {
            String operSm = logService.encryptOper(lcSysLogEntity);
            String oldOperSm = lcSysLogEntity.getOperSm();
            lcSysLogEntity.setOperVerif(operSm != null ? (operSm.equals(oldOperSm) ? "否" : "是") :  "否");
        }
        //IPage iPage = CommonUtil.manualPage(page, list);
        return R.data(result);
    }


}
