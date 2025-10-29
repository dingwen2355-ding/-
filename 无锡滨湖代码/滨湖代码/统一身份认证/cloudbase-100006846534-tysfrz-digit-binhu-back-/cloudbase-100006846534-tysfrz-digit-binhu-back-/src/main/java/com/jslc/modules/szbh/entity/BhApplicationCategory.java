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
 * 应用类别实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
@TableName("bh_application_category")
@ApiModel(value = "BhApplicationCategory对象", description = "应用类别实体类")
public class BhApplicationCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 应用类别名称
	*/
	@ApiModelProperty(value = "应用类别名称")
	private String name;
	/**
	* 上级类别
	*/
	@ApiModelProperty(value = "上级类别")
	private Long parentId;
	/**
	* 应用描述
	*/
	@ApiModelProperty(value = "应用描述")
	private String description;
	/**
	* 启用状态,1-启用，0-停用
	*/
	@ApiModelProperty(value = "启用状态,1-启用，0-停用")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isEnable;
	/**
	* 排序
	*/
	@ApiModelProperty(value = "排序")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer sort;

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
	 * 应用类型，1-已建应用，2-企业产品
	 */
	@ApiModelProperty(value = "应用类型，1-已建应用，2-企业产品")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer type;


}
