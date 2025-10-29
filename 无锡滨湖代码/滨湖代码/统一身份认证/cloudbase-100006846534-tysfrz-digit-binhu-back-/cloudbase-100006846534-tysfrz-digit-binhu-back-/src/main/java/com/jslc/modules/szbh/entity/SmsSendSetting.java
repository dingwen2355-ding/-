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
 * 短信发送配置实体类
 *
 * @author BladeX
 * @since 2023-06-07
 */
@Data
@TableName("bh_sms_send_setting")
@ApiModel(value = "短信发送配置实体类", description = "短信发送配置实体类")
public class SmsSendSetting implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;



	/**
	* 标题
	*/
	@ApiModelProperty(value = "标题")
	private String title;
	/**
	* 接收号码，用逗号隔开
	*/
	@ApiModelProperty(value = "接收号码，用逗号隔开")
	private String mobile;
	/**
	* 接收号码类型，1-批量导入，2-系统用户，3-手动添加
	*/
	@ApiModelProperty(value = "接收号码类型，1-批量导入，2-系统用户，3-手动添加")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer mobileType;
	/**
	* 短信内容
	*/
	@ApiModelProperty(value = "短信内容")
	private String content;
	/**
	* 是否定时发送，1-定时，0-即时
	*/
	@ApiModelProperty(value = "是否定时发送，1-定时，0-即时")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isRegularTime;

	/**
	* 发送状态，1-已发送，0-未发送
	*/
	@ApiModelProperty(value = "发送状态，1-已发送，0-未发送")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer sendStatus;
	/**
	* 定时发送时间
	*/
	@ApiModelProperty(value = "定时发送时间")
	private String regularTime;


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



}
