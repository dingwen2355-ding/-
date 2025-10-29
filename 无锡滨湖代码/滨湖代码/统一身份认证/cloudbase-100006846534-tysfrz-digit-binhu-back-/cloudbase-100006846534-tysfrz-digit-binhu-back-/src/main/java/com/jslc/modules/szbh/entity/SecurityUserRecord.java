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
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 安全设置记录表实体类
 *
 * @author BladeX
 * @since 2023-06-01
 */
@Data
@TableName("bh_security_user_record")
@ApiModel(value = "安全设置记录表实体类", description = "安全设置记录表实体类")
public class SecurityUserRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 用户账号
	*/
	@ApiModelProperty(value = "用户账号")
	private String account;
	/**
	* 密码错误锁定剩余次数
	*/
	@ApiModelProperty(value = "密码错误锁定剩余次数")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer secretErrorResidueTime;
	/**
	* 密码下次尝试时间
	*/
	@ApiModelProperty(value = "密码下次尝试时间")
	private LocalDateTime secretLastTryTime;
	/**
	* 解锁时间
	*/
	@ApiModelProperty(value = "解锁时间")
	private LocalDateTime secretUnlockTime;
	/**
	* 密码错误剩余次数显示验证码
	*/
	@ApiModelProperty(value = "密码错误剩余次数显示验证码")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer secretErrorResidueTimeCaptcha;
	/**
	* 验证码下次尝试时间
	*/
	@ApiModelProperty(value = "验证码下次尝试时间")
	private LocalDateTime captchaLastTryTime;


}
