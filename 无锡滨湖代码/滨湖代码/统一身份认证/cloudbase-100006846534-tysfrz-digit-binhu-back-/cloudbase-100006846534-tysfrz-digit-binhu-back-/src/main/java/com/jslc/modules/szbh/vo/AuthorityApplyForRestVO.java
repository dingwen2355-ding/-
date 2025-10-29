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
package com.jslc.modules.szbh.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 权限申请表实体类
 *
 * @author BladeX
 * @since 2023-07-25
 */
@Data
public class AuthorityApplyForRestVO {

    private static final long serialVersionUID = 1L;


    /**
     * 记录id
     */
    private Long id;

    /**
     * 用户账号
     */
    private String account;
    /**
     * 部门id，用户所在的部门id，可不填，默认主部门
     */
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Long deptId;
    /**
     * 应用id
     */
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Long applicationManagementId;
    /**
     * 申请理由
     */
    private String reason;
    /**
     * 审核意见，0-待审核，1-通过，2-驳回
     */
    private String reviewOpinion;
    /**
     * 审核状态
     */
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Integer reviewStatus;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "申请时间")
    private LocalDateTime applicationTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "审批时间")
    private LocalDateTime approveTime;
    /**
     * 申请人，关联用户账号account
     */
    @ApiModelProperty(value = "申请人，关联用户账号account")
    private String applicant;
    /**
     * 应用所属部门
     */
    private String applicationDeptName;
    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 申请人手机号
     */
    @ApiModelProperty(value = "申请人手机号")
    private String applicantPhone;
    /**
     * 申请人名称
     */
    @ApiModelProperty(value = "申请人名称")
    private String applicantName;
    /**
     * 申请人单位名称
     */
    @ApiModelProperty(value = "申请人单位名称")
    private String deptName;
    /**
     * 单位类型
     */
    @ApiModelProperty(value = "单位类型")
    private String applicantDeptType;





}
