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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.tool.utils.DateUtil;

import java.time.LocalDateTime;

/**
 * 应用管理视图实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
public class BhUserApplicationManagementRestVO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String name;
    /**
     * appID
     */
    @ApiModelProperty(value = "appID")
    private String appId;
    /**
     * appSecret
     */
    @ApiModelProperty(value = "appSecret")
    private String appSecret;
    /**
     * 应用描述
     */
    @ApiModelProperty(value = "应用描述")
    private String description;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private Long deptId;
    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    private String contacts;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String telephone;
    /**
     * 应用URL
     */
    @ApiModelProperty(value = "应用URL")
    private String url;
    /**
     * 协议类型
     */
    @ApiModelProperty(value = "协议类型")
    private String protocol;
    /**
     * 用户信息
     */
    @ApiModelProperty(value = "用户信息")
    private String userColumn;
    /**
     * 应用简介
     */
    @ApiModelProperty(value = "应用简介")
    private String intro;
    /**
     * 应用部门
     */
    @ApiModelProperty(value = "应用部门")
    private Long manageDeptId;
    /**
     * 应用部门名称
     */
    @ApiModelProperty(value = "应用部门名称")
    private String manageDeptName;
    /**
     * 登录次数
     */
    @ApiModelProperty(value = "登录次数")
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Long loginNumber;
    /**
     * 是否上架
     */
    @ApiModelProperty(value = "是否上架")
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Integer isGround;
    /**
     * 是否推荐
     */
    @ApiModelProperty(value = "是否推荐")
    @JsonSerialize(nullsUsing = NullSerializer.class)
    private Integer isRecommend;
    /**
     * 应用logo
     */
    @ApiModelProperty(value = "应用logo")
    private String logo;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
    private LocalDateTime createTime;
    /**
     * 应用logo
     */
    @ApiModelProperty(value = "应用logo")
    private String icon;
}
