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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 安全设置实体类
 *
 * @author BladeX
 * @since 2023-06-01
 */
@Data
@TableName("bh_security_setting")
@ApiModel(value = "安全设置实体类", description = "安全设置实体类")
public class SecuritySetting implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long id;
	/**
	* 密码强度,1-低，2-中，3-高
	*/
	@ApiModelProperty(value = "密码强度,1-低，2-中，3-高")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer secretStrength;
	/**
	* 密码最大长度
	*/
	@ApiModelProperty(value = "密码最大长度")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer secretMaxLength;
	/**
	* 密码最小长度
	*/
	@ApiModelProperty(value = "密码最小长度")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer secretMinLength;
	/**
	* 登录锁定设置,1-开，0-关
	*/
	@ApiModelProperty(value = "登录锁定设置,1-开，0-关")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isLoginLock;
	/**
	* 密码错误锁定次数
	*/
	@ApiModelProperty(value = "密码错误锁定次数")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer secretErrorLockTime;
	/**
	* 密码尝试时间范围
	*/
	@ApiModelProperty(value = "密码尝试时间范围")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long secretTryTimeRange;
	/**
	* 解锁时间
	*/
	@ApiModelProperty(value = "解锁时间")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long secretUnlockTime;
	/**
	* 验证码设置,1-开，0-关
	*/
	@ApiModelProperty(value = "验证码设置,1-开，0-关")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isCaptchaLock;
	/**
	* 密码错误次数显示验证码
	*/
	@ApiModelProperty(value = "密码错误次数显示验证码")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer sercetErrorTimeCaptcha;
	/**
	* 验证码尝试时间范围
	*/
	@ApiModelProperty(value = "验证码尝试时间范围")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long captchaTryTimeRange;


}
