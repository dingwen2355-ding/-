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

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 应用管理视图实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
public class BhApplicationActivationRestVO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "appId")
	private String appId;

	@ApiModelProperty(value = "应用名称")
	private String name;

	@ApiModelProperty(value = "活跃度")
	private Long recordNum;

	@ApiModelProperty(value = "排名变化")
	private Integer rankChange;
}
