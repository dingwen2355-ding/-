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

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 部门管理实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
@ApiModel(value = "BhDept对象", description = "部门管理实体类")
public class BhDeptRestVO {
	private static final long serialVersionUID = 1L;

	/**
	 * 部门id，原始id
	 */
	@ApiModelProperty(value = "部门id，原始id")
	private Long id;
	/**
	* 部门名称
	*/
	@ApiModelProperty(value = "部门名称")
	private String name;
	/**
	 * 父主键
	 */
	@ApiModelProperty(value = "父主键")
	private Long parentId;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Long sort;
	/**
	* 部门类型
	*/
	@ApiModelProperty(value = "部门类型")
	private Integer type;
	/**
	 * 成员上限
	 */
	@ApiModelProperty(value = "成员上限")
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
}
