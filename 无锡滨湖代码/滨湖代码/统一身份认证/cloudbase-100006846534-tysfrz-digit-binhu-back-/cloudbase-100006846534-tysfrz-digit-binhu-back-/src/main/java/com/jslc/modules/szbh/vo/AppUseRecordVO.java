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

import com.jslc.modules.szbh.entity.AppUseRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用使用记录视图实体类
 *
 * @author BladeX
 * @since 2023-06-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppUseRecordVO extends AppUseRecord {
	private static final long serialVersionUID = 1L;

	/**
	 * 应用id
	 */
	@ApiModelProperty(value = "应用id")
	private Long appId;
	/**
	 * 应用id
	 */
	@ApiModelProperty(value = "应用id")
	private String appSecret;

	/**
	 * 部门id
	 */
	@ApiModelProperty(value = "部门id")
	private Long deptId;


	/**
	 * 应用名称
	 */
	@ApiModelProperty(value = "应用名称")
	private String appName;


	/**
	 * 应用类别
	 */
	@ApiModelProperty(value = "应用类别")
	private String categoryName;




}
