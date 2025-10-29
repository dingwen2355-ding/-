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
package com.jslc.modules.szbh.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 应用申请表实体类
 *
 * @author BladeX
 * @since 2023-07-25
 */
@Data
@TableName("bh_application_apply_for")
public class ApplicationApplyFor implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;

    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用类别
     */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long categoryId;
    /**
     * 系统截图的id
     */
    private String logoId;
    /**
     * 系统截图的地址
     */
    private String logo;
    /**
     * 应用描述
     */
    private String description;
    /**
     * 所属部门
     */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long deptId;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 应用URL
     */
    private String url;
    /**
     * 是否上架
     */
    private Integer isGround;
    /**
     * 是否启用
     */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isEnable;
    /**
     * 是否推荐
     */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isRecommend;
    /**
     * 是否短信推送
     */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isSendSms;
    /**
     * 协议类型
     */
	private String protocol;
    /**
     * 数据来源
     */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer dataSource;
    /**
     * 应用简介
     */
    private String intro;
    /**
     * logo的id
     */
    private String iconId;
    /**
     * logo的地址
     */
    private String icon;
    /**
     * 申请理由
     */
    private String reason;
    /**
     * 审核意见
     */
    private String reviewOpinion;
    /**
     * 审核状态
     */
    private Integer reviewStatus;


    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Long createUser;


    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Long updateUser;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
    /**
     * 是否删除0：未删除，1：已删除
     */
    @ApiModelProperty(value = "是否删除0：未删除，1：已删除")
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Integer isDeleted;


    /**
     * 申请人，关联用户账号account
     */
    @ApiModelProperty(value = "申请人，关联用户账号account")
    private String applicant;


}
