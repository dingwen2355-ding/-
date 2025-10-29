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
import com.jslc.modules.szbh.entity.ApplicationApplyFor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 应用申请表视图实体类
 *
 * @author BladeX
 * @since 2023-07-25
 */
@Data
public class ApplicationApplyForRestVO  {
	private static final long serialVersionUID = 1L;

	/**
	 * 应用名称
	 */
	private String name;
	/**
	 * 应用类别
	 */
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
	private Integer isEnable;
	/**
	 * 是否推荐
	 */
	private Integer isRecommend;
	/**
	 * 是否短信推送
	 */
	private Integer isSendSms;
	/**
	 * 协议类型
	 */
	private String protocol;
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

}
