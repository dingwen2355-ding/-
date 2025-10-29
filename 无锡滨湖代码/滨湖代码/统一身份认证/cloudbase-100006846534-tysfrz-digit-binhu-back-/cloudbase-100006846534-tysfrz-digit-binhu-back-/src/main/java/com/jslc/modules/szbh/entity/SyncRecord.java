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
 * 同步记录表实体类
 *
 * @author BladeX
 * @since 2023-06-06
 */
@Data
@TableName("bh_sync_record")
@ApiModel(value = "同步记录表实体类", description = "同步记录表实体类")
public class SyncRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 同步类型,上下游
	*/
	@ApiModelProperty(value = "同步类型,上下游")
	private String upOrDown;
	/**
	* 数据源/应用
	*/
	@ApiModelProperty(value = "数据源/应用")
	private String appName;
	/**
	* 同步数据类型
	*/
	@ApiModelProperty(value = "同步数据类型")
	private String dataType;
	/**
	* 同步结果
	*/
	@ApiModelProperty(value = "同步结果")
	private String result;
	/**
	* 异常原因
	*/
	@ApiModelProperty(value = "异常原因")
	private String cause;
	/**
	* 是否自动，1-自动，0-手动
	*/
	@ApiModelProperty(value = "是否自动，1-自动，0-手动")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isAutomatic;

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
	 * 下游同步id
	 */
	@ApiModelProperty(value = "下游同步id")
	private Long downstreamId;

	/**
	 * ip地址
	 */
	@ApiModelProperty(value = "ip地址")
	private String ipAddress;


}
