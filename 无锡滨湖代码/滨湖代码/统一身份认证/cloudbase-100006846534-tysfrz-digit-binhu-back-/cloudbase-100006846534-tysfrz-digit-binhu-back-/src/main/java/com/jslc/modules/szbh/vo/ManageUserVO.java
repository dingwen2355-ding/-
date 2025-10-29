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

import com.jslc.modules.szbh.entity.ManageUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理用户组用户表视图实体类
 *
 * @author BladeX
 * @since 2023-06-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ManageUserVO extends ManageUser {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ids
	 */
	@ApiModelProperty(value = "用户ids")
	String userIds;

}
