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

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 权限申请表视图实体类
 *
 * @author BladeX
 * @since 2023-07-25
 */
@Data
public class DSQAuthorityApplyStatusVO {
	private static final long serialVersionUID = 1L;

	/**
	 * 0:通过 1:待审核  2:驳回 4:撤销
	 */
	@ApiModelProperty(value = "审核状态: 0:通过 1:待审核  2:驳回 4:撤销", required = true)
	@NotNull(message = "审核状态不能为空")
	private int status;

	/**
	 * 应用申请人_用户id
	 */
	@ApiModelProperty(value = "应用申请人_用户id", required = true)
	@NotNull(message = "应用申请人_用户id不能为空")
	private String applyId;

	/**
	 * 应用申请人_用户姓名
	 */
	@ApiModelProperty(value = "应用申请人_用户姓名", required = true)
	@NotNull(message = "应用申请人_用户姓名不能为空")
	private String applyUserName;

	/**
	 * 审核人用户_id
	 */
	@ApiModelProperty(value = "审核人用户_id", required = true)
	@NotNull(message = "审核人用户_id不能为空")
	private String examineUserId;
	/**
	 * 审批人姓名
	 */
	@ApiModelProperty(value = "审批人姓名", required = true)
	@NotNull(message = "审批人姓名不能为空")
	private String examineUserName;
	/**
	 * 应用名称
	 */
	@ApiModelProperty(value = "应用名称", required = true)
	@NotNull(message = "应用名称不能为空")
	private String appName;


}
