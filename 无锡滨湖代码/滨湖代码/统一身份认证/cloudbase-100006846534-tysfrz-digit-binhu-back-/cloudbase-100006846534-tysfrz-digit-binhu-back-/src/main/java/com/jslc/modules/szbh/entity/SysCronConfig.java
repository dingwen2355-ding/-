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

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 实体类
 *
 * @author BladeX
 * @since 2023-05-15
 */
@Data
@TableName("sys_cron_config")
@ApiModel(value = "SysCronConfig对象", description = "定时任务表")
public class SysCronConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 类型1：人员
	*/
	@ApiModelProperty(value = "类型1：人员")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer type;
	/**
	* 执行任务的时间配置
	*/
	@ApiModelProperty(value = "执行任务的时间配置")
	private String cron;
	/**
	* 描述
	*/
	@ApiModelProperty(value = "描述")
	@TableField(value = "`desc`")
	private String desc;
	/**
	* 是否启用，1-是，0-否
	*/
	@ApiModelProperty(value = "是否启用，1-是，0-否")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isEnable;
	/**
	* 部门 deptId 或者用户的账户
	*/
	@ApiModelProperty(value = "部门 deptId 或者用户的账户")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String synIds;
	/**
	* 部门 deptId 或者用户的账户
	*/
	@ApiModelProperty(value = "人员对应的部门id")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String deptId;


}
