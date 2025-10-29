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
 * 管理用户组用户表实体类
 *
 * @author BladeX
 * @since 2023-06-02
 */
@Data
@TableName("bh_manage_user")
@ApiModel(value = "管理用户组用户表实体类", description = "管理用户组用户表实体类")
public class ManageUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;


	/**
	* 父级用户组
	*/
	@ApiModelProperty(value = "父级用户组")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long bizId;
	/**
	* 用户id
	*/
	@ApiModelProperty(value = "用户id")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long bhUserId;


}
