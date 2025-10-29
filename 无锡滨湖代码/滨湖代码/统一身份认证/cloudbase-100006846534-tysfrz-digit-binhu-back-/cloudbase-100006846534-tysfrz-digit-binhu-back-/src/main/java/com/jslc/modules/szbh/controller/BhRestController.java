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
import com.jslc.modules.szbh.dto.BhApplicationManagementUpDTO;
import com.jslc.modules.szbh.dto.BhUserAuthorityUpDTO;
import com.jslc.modules.szbh.service.IBhRestService;
import com.jslc.modules.szbh.vo.LoginRecordEcharsVO;
import com.jslc.modules.szbh.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 部门管理同步原始表 控制器
 *
 * @author BladeX
 * @since 2023-05-09
 */
@RestController
@AllArgsConstructor
@RequestMapping(CommonConstant.SQUIRREL_SZBH + "/rest")
@Api(value = "滨湖对外接口", tags = "滨湖对外接口")
public class BhRestController extends BladeController {

    private final IBhRestService bhTokenService;

    @GetMapping("/getToken")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取token", notes = "获取token")
    public ResultVO getAccessToken(String appId, String appSecret) {
        ResultVO resultVO = bhTokenService.getToken(appId, appSecret);
        return resultVO;
    }

    @GetMapping("/checkToken")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "校验token", notes = "校验token")
    public R<String> checkToken(String token) {
        String msg = bhTokenService.checkToken(null, token);
        return R.data(msg);
    }

    @GetMapping("/departmentList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "部门列表", notes = "部门列表")
    public ResultVO getDepartmentList(HttpServletRequest request, String appId, String token, String id, Integer noFetchChild) {
        ResultVO resultVO = bhTokenService.getDepartmentList(request, appId, token, id, noFetchChild);
        return resultVO;
    }

    @GetMapping("/userList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "用户列表", notes = "用户列表")
    public ResultVO getUserList(HttpServletRequest request, String appId, String token, String id) {
        ResultVO resultVO = bhTokenService.getUserList(request, appId, token, id);
        return resultVO;
    }

    @GetMapping("/userInfo")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "用户信息", notes = "用户信息")
    public ResultVO getUserInfo(String appId, String token, String account) {
        ResultVO resultVO = bhTokenService.getUserInfo(appId, token, account);
        return resultVO;
    }

    @GetMapping("/checkInitPassword")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "验证是否是初始密码", notes = "验证是否是初始密码")
    public ResultVO checkInitPassword(String token, String account) {
        ResultVO resultVO = bhTokenService.checkInitPassword(token, account);
        return resultVO;
    }

    @GetMapping("/changePassword")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public ResultVO changePassword(String token, String account, String oldPassword, String newPassword) {
        ResultVO resultVO = bhTokenService.changePassword(token, account, oldPassword, newPassword);
        return resultVO;
    }



    @GetMapping("/applicationList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "所有应用", notes = "所有应用")
    public ResultVO applicationList(String token) {
        ResultVO resultVO = bhTokenService.applicationList(token);
        return resultVO;
    }

    @GetMapping("/getAppUseRank")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取月应用访问排名", notes = "获取月应用访问排名")
    public ResultVO getAppUseRank(String token) {
        ResultVO resultVO = bhTokenService.getAppUseRank(token);
        return resultVO;
    }



    @GetMapping("/userApplication")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "用户应用", notes = "用户应用")
    public ResultVO getUserApplication(String token, String account, String keyword, Query query) {
        ResultVO resultVO = bhTokenService.getUserApplication(token, account, keyword, query);
        return resultVO;
    }

    @GetMapping("/applicationClassification")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "应用分类", notes = "应用分类")
    public ResultVO applicationClassification(String token) {
        ResultVO resultVO = bhTokenService.applicationClassification(token);
        return resultVO;
    }

    @GetMapping("/applicationDept")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "应用单位", notes = "应用单位")
    public ResultVO applicationDept(String token) {
        ResultVO resultVO = bhTokenService.applicationDept(token);
        return resultVO;
    }


    @GetMapping("/applicationByClassificationAndDept")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "根据应用分类应用单位查询应用", notes = "根据应用分类应用单位查询应用")
    public ResultVO applicationByClassificationAndDept(String token, Long applicationClassificationId, Long deptId, String keywords, Query query) {
        ResultVO resultVO = bhTokenService.applicationByClassificationAndDept(token, applicationClassificationId, deptId, keywords, Condition.getPage(query));
        return resultVO;
    }

    @GetMapping("/applicationActivation")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "应用活跃度", notes = "应用活跃度")
    public ResultVO applicationActivation(String token, Integer type, String month) {
        ResultVO resultVO = bhTokenService.applicationActivation(token, type, month);
        return resultVO;
    }

    @GetMapping("/baseSupermarket")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "底座超市", notes = "底座超市")
    public ResultVO baseSupermarket(String token) {
        ResultVO resultVO = bhTokenService.baseSupermarket(token);
        return resultVO;
    }

    @GetMapping("/appliationCategoryNum")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "应用分类数量", notes = "")
    public ResultVO appliationCategoryNum(String token) {
        ResultVO resultVO = bhTokenService.appliationCategoryNum(token);
        return resultVO;
    }

    @GetMapping("/smsNotice")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "短信通知查询", notes = "")
    public ResultVO smsNotice(String token, String account, String start, String end) {
        ResultVO resultVO = bhTokenService.smsNotice(token, account, start, end);
        return resultVO;
    }

    @GetMapping("/entProductList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "所有企业产品", notes = "所有企业产品")
    public ResultVO entProductList(String token) {
        ResultVO resultVO = bhTokenService.entProductList(token);
        return resultVO;
    }

    @GetMapping("/addApplicationClassification")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "添加时的应用分类", notes = "加时的应用分类")
    public ResultVO addApplicationClassification(String token) {
        ResultVO resultVO = bhTokenService.addApplicationClassification(token);
        return resultVO;
    }

    @GetMapping("/addApplicationDept")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "添加时的应用单位", notes = "添加时的应用单位")
    public ResultVO addApplicationDept(String token) {
        ResultVO resultVO = bhTokenService.addApplicationDept(token);
        return resultVO;
    }

    @PostMapping("/uploadFile")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "上传文件", notes = "上传文件")
    public ResultVO uploadFile(String token, MultipartFile multipartFile) throws Exception {
        ResultVO resultVO = bhTokenService.uploadFile(token, multipartFile);
        return resultVO;
    }

    @PostMapping("/addApplication")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "添加应用", notes = "添加应用")
    public ResultVO addApplication(@RequestBody BhApplicationManagementUpDTO applicationManagement) {
        ResultVO resultVO = bhTokenService.addApplication(applicationManagement);
        return resultVO;
    }

    @PostMapping("/addUserAuthority")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "添加用户权限", notes = "添加用户权限")
    public ResultVO addUserAuthority(@RequestBody BhUserAuthorityUpDTO bhUserAuthorityUpDTO) {
        ResultVO resultVO = bhTokenService.addUserAuthority(bhUserAuthorityUpDTO);
        return resultVO;
    }

    @GetMapping("/applicationApplyForList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "根据申请人查询应用申请列表", notes = "根据申请人查询应用申请列表")
    public ResultVO applicationApplyForList(String token, String applicant, Query query) {
        ResultVO resultVO = bhTokenService.applicationApplyForList(token, applicant, Condition.getPage(query));
        return resultVO;
    }

    @GetMapping("/authorityApplyForList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "根据申请人查询权限申请列表", notes = "根据申请人查询权限申请列表")
    public ResultVO authorityApplyForList(String token, String applicant, Query query) {
        ResultVO resultVO = bhTokenService.authorityApplyForList(token, applicant, Condition.getPage(query));
        return resultVO;
    }

    @GetMapping("/noticeList")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "通知公告", notes = "通知公告")
    public ResultVO noticeList(String token, Integer noticeType, Query query) {
        ResultVO resultVO = bhTokenService.noticeList(token, noticeType, Condition.getPage(query));
        return resultVO;
    }

    @GetMapping("/checkIsAdmin")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "验证是否管理员用户", notes = "验证是否管理员用户")
    public ResultVO checkIsAdmin(String token, String account) {
        ResultVO resultVO = bhTokenService.checkIsAdmin(token, account);
        return resultVO;
    }

    @PostMapping("/addAuthApplication")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "添加认证过的应用", notes = "添加认证过的应用")
    public ResultVO addAuthApplication(@RequestBody BhApplicationManagementUpDTO applicationManagement) {
        ResultVO resultVO = bhTokenService.addAuthApplication(applicationManagement);
        return resultVO;
    }

    @PostMapping("/onOffShelvesApp")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "上下架应用", notes = "上下架应用")
    public ResultVO onOffShelvesApp(String token, Long applicationManagementId, Integer status) {
        ResultVO resultVO = bhTokenService.onOffShelvesApp(token, applicationManagementId, status);
        return resultVO;
    }



    @GetMapping("/getHomePageAnalysis")
    @ApiOperationSupport(order = 21)
    @ApiOperation(value = "获取后台首页审批状态分析", notes = "获取后台首页审批状态分析")
    public ResultVO getHomePageAnalysis(String token) {
        ResultVO resultVO = bhTokenService.getHomePageAnalysis(token);
        return resultVO;
    }

    @GetMapping("/getAccessStatistics")
    @ApiOperationSupport(order = 22)
    @ApiOperation(value = "获取访问统计", notes = "获取后台首页审批状态分析")
    public ResultVO getAccessStatistics(String token,String start,String end) {
        ResultVO resultVO = bhTokenService.getAccessStatistics(token, start, end);
        return resultVO;
    }




    @GetMapping("/getApplyForStatistics")
    @ApiOperationSupport(order = 23)
    @ApiOperation(value = "获取申请统计", notes = "获取申请统计")
    public ResultVO getApplyForStatistics(String token,String start,String end) {
        ResultVO resultVO = bhTokenService.getApplyForStatistics(token, start, end);
        return resultVO;
    }


    @GetMapping("/getApplyForEcharts")
    @ApiOperationSupport(order = 24)
    @ApiOperation(value = "获取本月申请统计echats", notes = "获取本月申请统计echats")
    public ResultVO getApplyForEcharts(String token) {
        ResultVO resultVO = bhTokenService.getApplyForEcharts(token);
        return resultVO;
    }

    @GetMapping("/getAccessEcharts")
    @ApiOperationSupport(order = 25)
    @ApiOperation(value = "获取本月访问统计echats", notes = "获取本月访问统计echats")
    public ResultVO getAccessEcharts(String token) {
        ResultVO resultVO = bhTokenService.getAccessEcharts(token);
        return resultVO;
    }


    @GetMapping("/getApplicationList")
    @ApiOperationSupport(order = 26)
    @ApiOperation(value = "所有应用没有分类", notes = "所有应用没有分类")
    public ResultVO getApplicationList(String token) {
        ResultVO resultVO = bhTokenService.getApplicationList(token);
        return resultVO;
    }

    @GetMapping("/getDzcsUserNum")
    @ApiOperationSupport(order = 27)
    @ApiOperation(value = "底座超市用户数量", notes = "底座超市用户数量")
    public ResultVO getDzcsUserNum(String token) {
        ResultVO resultVO = bhTokenService.getDzcsUserNum(token);
        return resultVO;
    }



    /**====================================统一身份认证数据提供区城运=================================================**/


    @GetMapping("/getSmsEcharts")
    @ApiOperationSupport(order = 27)
    @ApiOperation(value = "获取短信echarts", notes = "获取短信统计 type 1 周 2 月")
    public ResultVO getSmsEcharts(String token,@RequestParam Integer type) {
        ResultVO resultVO = bhTokenService.getSmsEcharts(token,type);
        return resultVO;
    }




    @GetMapping("/getStatisticsNote")
    @ApiOperationSupport(order = 28)
    @ApiOperation(value = "获取短信总计今日发送", notes = "获取短信总计今日发送")
    public ResultVO getStatisticsNote(String token) {
        ResultVO resultVO = bhTokenService.getStatisticsNote(token);
        return resultVO;
    }


    @GetMapping("/getUserData")
    @ApiOperationSupport(order = 29)
    @ApiOperation(value = "获取用户活跃", notes = "获取用户活跃")
    public ResultVO getUserData(String token) {
        ResultVO resultVO = bhTokenService.getUserData(token);
        return resultVO;
    }


    @GetMapping("/getDeptRank")
    @ApiOperationSupport(order = 30)
    @ApiOperation(value = "获取部门访问排名", notes = "获取部门访问排名")
    public ResultVO getDeptRank(String token) {
        ResultVO resultVO = bhTokenService.getDeptRank(token);
        return resultVO;
    }

    /**====================都市圈5.31==============================**/

    @GetMapping("/getApplyForRank")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取月应用权限申请排名", notes = "获取月应用权限申请排名")
    public ResultVO getApplyForRank(String token) {
        ResultVO resultVO = bhTokenService.getApplyForRank(token);
        return resultVO;
    }

    /**====================都市圈7.9==============================**/

    /**
     * 都市圈撤回
     * @param token
     * @param applicant
     * @param query
     * @return
     */
    @PostMapping("/withdrawAuthorityApplyFor")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "都市圈撤回权限申请", notes = "都市圈撤回权限申请")
    public ResultVO withdrawAuthorityApplyFor(String token,@RequestParam Long id) {
        ResultVO resultVO = bhTokenService.withdrawAuthorityApplyFor(token, id);
        return resultVO;
    }

}
