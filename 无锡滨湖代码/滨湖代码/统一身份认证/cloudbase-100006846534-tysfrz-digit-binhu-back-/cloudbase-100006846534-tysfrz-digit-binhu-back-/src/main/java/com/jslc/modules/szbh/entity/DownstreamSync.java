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
 * 下游同步配置表实体类
 *
 * @author BladeX
 * @since 2023-06-06
 */
@Data
@TableName("bh_downstream_sync")
@ApiModel(value = "下游同步配置表实体类", description = "下游同步配置表实体类")
public class DownstreamSync implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;


	/**
	* 应用id
	*/
	@ApiModelProperty(value = "应用id")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long appId;
	/**
	* 同步数据类型，部门信息/用户信息
	*/
	@ApiModelProperty(value = "同步数据类型，部门信息/用户信息")
	private String dataType;
	/**
	* 同步的部门，逗号隔开
	*/
	@ApiModelProperty(value = "同步的部门，逗号隔开")
	private String syncDept;
	/**
	* 同步的字段，逗号隔开
	*/
	@ApiModelProperty(value = "同步的字段，逗号隔开")
	private String syncField;


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
	 * 同步人员，逗号隔开
	 */
	@ApiModelProperty(value = "同步人员，逗号隔开")
	private String syncUser;


}
