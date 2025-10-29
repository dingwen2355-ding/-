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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.tool.node.INode;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApplicationDSQVO {

	/**
	 * 应用秘钥
	 */
	@ApiModelProperty(value = "应用秘钥")
	private String appSecret;

	/**
	 * 应用id
	 */
	@ApiModelProperty(value = "id")
	private String id;

	/**
	 * 访问数量
	 */
	@ApiModelProperty(value = "访问数量")
	private String viewNum;

	/**
	 * 申请数量
	 */
	@ApiModelProperty(value = "申请数量")
	private String applyForNum;



}
