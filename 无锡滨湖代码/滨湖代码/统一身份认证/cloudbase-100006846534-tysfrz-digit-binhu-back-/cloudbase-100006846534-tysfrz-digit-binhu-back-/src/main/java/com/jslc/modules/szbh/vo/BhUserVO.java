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

import com.jslc.modules.szbh.dto.DeptPositionDto;
import com.jslc.modules.szbh.entity.BhUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户管理视图实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BhUserVO extends BhUser {
	private static final long serialVersionUID = 1L;


	/**
	 * 主部门名称
	 */
	@ApiModelProperty(value = "主部门名称")
	private String majorBhDeptName;

	/**
	 * 其他部门名称
	 */
	@ApiModelProperty(value = "其他部门名称")
	private String otherBhDeptName;
	/**
	 * 部门名称
	 */
	@ApiModelProperty(value = "其他部门名称")
	private String bhDeptName;
	/**
	 * 是否启用
	 */
	@ApiModelProperty(value = "是否启用")
	private String isEnableName;
	/**
	 * 是否在线
	 */
	@ApiModelProperty(value = "是否在线")
	private String isOnlineName;
	/**
	 * 是否授权
	 */
	@ApiModelProperty(value = "是否授权")
	private String isAuthorizedName;


	/**
	 * 排序字段
	 */
	@ApiModelProperty(value = "是否授权")
	private Long order;

	/**
	 * 其他部门 职位
	 */
	@ApiModelProperty(value = "其他部门 职位")
	List<DeptPositionDto> deptPositions;

	@ApiModelProperty(value = "所有部门名称层级")
	String depts;

	/**
	 * 文件集合
	 */
//	private List<BladeFileVO> files;

}
