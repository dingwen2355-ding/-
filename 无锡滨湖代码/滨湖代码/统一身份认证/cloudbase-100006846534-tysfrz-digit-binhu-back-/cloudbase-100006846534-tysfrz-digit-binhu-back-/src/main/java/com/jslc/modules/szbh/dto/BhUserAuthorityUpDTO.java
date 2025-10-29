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
package com.jslc.modules.szbh.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.tool.utils.DateUtil;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 应用管理实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
@ApiModel(value = "BhDept对象", description = "部门管理实体类")
public class BhUserAuthorityUpDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long id;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "用户账号")
	private String account;
	/**
	 * 部门id
	 */
	@ApiModelProperty(value = "部门id")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long deptId;
	/**
	 * 应用id
	 */
	@ApiModelProperty(value = "应用id")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long applicationManagementId;
	/**
	 * 申请理由
	 */
	@ApiModelProperty(value = "申请理由")
	private String reason;
	/**
	 * 申请人
	 */
	@ApiModelProperty(value = "申请人")
	private String applicant;

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

	/**
	 * token
	 */
	@ApiModelProperty(value = "token")
	private String token;
}
