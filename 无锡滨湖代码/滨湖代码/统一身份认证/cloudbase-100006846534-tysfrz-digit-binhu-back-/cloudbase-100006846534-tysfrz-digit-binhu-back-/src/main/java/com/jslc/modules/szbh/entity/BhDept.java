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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 部门管理实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
@TableName("bh_dept")
@ApiModel(value = "BhDept对象", description = "部门管理实体类")
public class BhDept implements Serializable {


	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 部门id，原始id
	 */
	@ApiModelProperty(value = "部门id，原始id")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long deptId;

	/**
	* 部门名称
	*/
	@ApiModelProperty(value = "部门名称")
	private String name;
	/**
	* 部门类型
	*/
	@ApiModelProperty(value = "部门类型")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Integer type;
	/**
	* 部门描述
	*/
	@ApiModelProperty(value = "部门描述")
	private String description;
	/**
	* 排序
	*/
	@ApiModelProperty(value = "排序")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long sort;
	/**
	* 启用状态,1-启用，0-停用
	*/
	@ApiModelProperty(value = "启用状态,1-启用，0-停用")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isEnable;
	/**
	* 父主键
	*/
	@ApiModelProperty(value = "父主键")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long parentId;
	/**
	* 祖级列表
	*/
	@ApiModelProperty(value = "祖级列表")
	private String ancestors;
	/**
	* 成员上限
	*/
	@ApiModelProperty(value = "成员上限")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer partyUserLimit;
	/**
	* 单位全称
	*/
	@ApiModelProperty(value = "单位全称")
	private String fullName;
	/**
	* 单位简称
	*/
	@ApiModelProperty(value = "单位简称")
	private String shortName;
	/**
	* 单位简介
	*/
	@ApiModelProperty(value = "单位简介")
	private String briefIntroduction;
	/**
	* 单位网站
	*/
	@ApiModelProperty(value = "单位网站")
	private String domainName;
	/**
	* 单位地址
	*/
	@ApiModelProperty(value = "单位地址")
	private String address;
	/**
	* 单位电话
	*/
	@ApiModelProperty(value = "单位电话")
	private String telephone;
	/**
	* 社会信用代码
	*/
	@ApiModelProperty(value = "社会信用代码")
	private String organizationCode;
	/**
	* 是否已更新，0-未更新，1-已更新
	*/
	@ApiModelProperty(value = "是否已更新，0-未更新，1-已更新")
	private Integer isUpdated;
	/**
	* 数据来源  1-同步，2-新建
	*/
	@ApiModelProperty(value = "数据来源  1-同步，2-新建")
	private Integer dataSource;

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
