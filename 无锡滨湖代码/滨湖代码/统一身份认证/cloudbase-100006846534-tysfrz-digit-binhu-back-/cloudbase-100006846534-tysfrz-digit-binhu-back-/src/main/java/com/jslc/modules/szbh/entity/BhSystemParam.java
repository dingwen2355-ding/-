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
 * 滨湖系统参数实体类
 *
 * @author BladeX
 * @since 2023-05-17
 */
@Data
@TableName("bh_system_param")
@ApiModel(value = "BhSystemParam对象", description = "滨湖系统参数实体类")
public class BhSystemParam implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	* 参数名
	*/
	@ApiModelProperty(value = "参数名")
	private String name;
	/**
	* 参数值
	*/
	@ApiModelProperty(value = "参数值")
	private String value;
	/**
	* 到期时间
	*/
	@ApiModelProperty(value = "到期时间")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long time;


}
