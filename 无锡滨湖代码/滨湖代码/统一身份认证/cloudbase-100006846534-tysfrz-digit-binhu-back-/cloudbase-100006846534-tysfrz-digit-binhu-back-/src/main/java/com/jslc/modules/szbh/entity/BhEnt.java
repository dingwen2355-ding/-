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
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 企业表实体类
 *
 * @author BladeX
 * @since 2023-06-06
 */
@Data
@TableName("bh_ent")
@ApiModel(value = "企业表实体类", description = "企业表实体类")
public class BhEnt implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	* 企业名称
	*/
	@ApiModelProperty(value = "企业名称")
	private String entName;
	/**
	* 所属行业
	*/
	@ApiModelProperty(value = "所属行业")
	private String industry;
	/**
	* 是否本地公司,1-是，0-否
	*/
	@ApiModelProperty(value = "是否本地公司,1-是，0-否")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isLocal;
	/**
	* 联系人
	*/
	@ApiModelProperty(value = "联系人")
	private String contacts;
	/**
	* 电话
	*/
	@ApiModelProperty(value = "电话")
	private String mobile;
	/**
	* 企业简介
	*/
	@ApiModelProperty(value = "企业简介")
	private String intro;


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
