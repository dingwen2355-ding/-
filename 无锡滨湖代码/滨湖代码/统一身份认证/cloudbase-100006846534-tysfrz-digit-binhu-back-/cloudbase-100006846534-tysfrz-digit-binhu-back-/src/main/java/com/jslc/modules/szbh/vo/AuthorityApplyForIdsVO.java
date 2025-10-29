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

import com.jslc.modules.szbh.entity.AuthorityApplyFor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限申请表视图实体类
 *
 * @author BladeX
 * @since 2023-07-25
 */
@Data
public class AuthorityApplyForIdsVO{
	private static final long serialVersionUID = 1L;

	/**
	 * 审核id
	 */
	@ApiModelProperty(value = "审核id")
	String ids;

	/**
	 * 审核意见
	 */
	@ApiModelProperty(value = "审核意见")
	String reviewOpinion;

	/**
	 * 审核状态
	 */
	@ApiModelProperty(value = "审核状态")
	Integer reviewStatus;

}
