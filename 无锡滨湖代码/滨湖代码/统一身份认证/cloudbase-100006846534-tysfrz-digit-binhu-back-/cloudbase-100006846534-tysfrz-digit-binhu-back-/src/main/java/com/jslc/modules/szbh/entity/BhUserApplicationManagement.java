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
 * 用户应用管理表实体类
 *
 * @author BladeX
 * @since 2023-05-11
 */
@Data
@TableName("bh_user_application_management")
@ApiModel(value = "BhUserApplicationManagement对象", description = "用户应用管理表")
public class BhUserApplicationManagement  implements Serializable {

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
	@ApiModelProperty(value = "授权id")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long bizId;

	/**
	* 用户id
	*/
	@ApiModelProperty(value = "用户id")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long userId;

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
	 * 应用创建关系
	 */
	@ApiModelProperty(value = "应用创建关系")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private LocalDateTime createTime;



	/**
	 * 类型，组织1，用户组2，用户3
	 */
	@ApiModelProperty(value = "类型，组织1，用户组2，用户3")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer type;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer appOrder;


}
