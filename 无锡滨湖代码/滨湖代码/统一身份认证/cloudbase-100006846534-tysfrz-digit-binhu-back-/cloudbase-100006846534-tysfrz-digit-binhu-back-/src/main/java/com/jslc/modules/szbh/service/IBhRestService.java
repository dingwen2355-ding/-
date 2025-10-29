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
package com.jslc.modules.szbh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jslc.modules.szbh.dto.BhApplicationManagementUpDTO;
import com.jslc.modules.szbh.dto.BhUserAuthorityUpDTO;
import com.jslc.modules.szbh.entity.BhDeptSync;
import com.jslc.modules.szbh.vo.ResultVO;
import org.springblade.core.mp.support.Query;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 部门管理同步原始表 服务类
 *
 * @author BladeX
 * @since 2023-05-09
 */
public interface IBhRestService extends IService<BhDeptSync> {

    /**
     * 获取token
     */
    ResultVO getToken(String appId, String appSecret);

    /**
     * 校验token
     */
    String checkToken(String appId, String token);

    /**
     * 部门表
     */
    ResultVO getDepartmentList(HttpServletRequest request, String appId, String token, String id, Integer noFetchChild);

    /**
     * 用户表
     */
    ResultVO getUserList(HttpServletRequest request, String appId, String token, String id);

    /**
     * 用户信息
     */
    ResultVO getUserInfo(String appId, String token, String account);

    /**
     * 登录验证
     */
    ResultVO checkInitPassword(String token, String account);

    /**
     * 修改密码
     */
    ResultVO changePassword(String token, String account, String oldPassword, String newPassword);

    /**
     * 所有应用
     */
    ResultVO applicationList(String token);

    /**
     * 用户应用
     */
    ResultVO getUserApplication(String token, String account, String keyword, Query query);

    /**
     * 应用分类
     */
    ResultVO applicationClassification(String token);

    /**
     * 应用单位
     */
    ResultVO applicationDept(String token);

    /**
     * 根据应用分类应用单位查询应用
     */
    ResultVO applicationByClassificationAndDept(String token, Long applicationClassificationId, Long deptId, String keywords, IPage page);

    /**
     * 应用活跃度
     */
    ResultVO applicationActivation(String token, Integer type, String month);

    /**
     * 底座超市
     */
    ResultVO baseSupermarket(String token);

    /**
     * 应用分类数量
     */
    ResultVO appliationCategoryNum(String token);

    /**
     * 短信通知查询
     */
    ResultVO smsNotice(String token, String account, String start, String end);

    /**
     * 所有企业产品
     */
    ResultVO entProductList(String token);

    /**
     * 添加应用
     */
    ResultVO addApplication(BhApplicationManagementUpDTO applicationManagement);

    /**
     * 添加时的应用分类
     */
    ResultVO addApplicationClassification(String token);

    /**
     * 添加时的应用单位
     */
    ResultVO addApplicationDept(String token);

    /**
     * 上传文件
     */
    ResultVO uploadFile(String token, MultipartFile file) throws Exception;

    /**
     * 添加用户权限
     */
    ResultVO addUserAuthority(BhUserAuthorityUpDTO bhUserAuthorityUpDTO);

    /**
     * 根据申请人查询应用申请列表
     */
    ResultVO applicationApplyForList(String token, String applicant, IPage page);

    /**
     * 根据申请人查询权限申请列表
     */
    ResultVO authorityApplyForList(String token, String applicant, IPage page);

    /**
     * 根据申请人查询权限申请列表
     */
    ResultVO noticeList(String token, Integer noticeType, IPage page);

    /**
     * 验证是否管理员用户
     */
    ResultVO checkIsAdmin(String token, String account);

    /**
     * 添加应用
     */
    ResultVO addAuthApplication(BhApplicationManagementUpDTO applicationManagement);

    /**
     * 上下架应用
     */
    ResultVO onOffShelvesApp(String token, Long applicationManagementId, Integer status);

    ResultVO getHomePageAnalysis(String token);

    ResultVO getAccessStatistics(String token, String start, String end);

    ResultVO getApplyForStatistics(String token, String start, String end);

    ResultVO getApplyForEcharts(String token);

    ResultVO getAccessEcharts(String token);

    ResultVO getApplicationList(String token);

    ResultVO getDzcsUserNum(String token);

    ResultVO getAppUseRank(String token);


    ResultVO getSmsEcharts(String token, Integer type);


    ResultVO getStatisticsNote(String token);


    ResultVO getUserData(String token);


    ResultVO getDeptRank(String token);


    ResultVO getApplyForRank(String token);


    ResultVO withdrawAuthorityApplyFor(String token, Long id);



}
