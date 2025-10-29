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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.jslc.modules.szbh.entity.BhApplicationManagement;
import com.jslc.modules.szbh.entity.BhFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.oss.model.BladeFile;
import org.springblade.core.tool.utils.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 应用管理视图实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BhApplicationManagementVO extends BhApplicationManagement {
	private static final long serialVersionUID = 1L;

	/**
	 * 类别名称
	 */
	@ApiModelProperty(value = "类别名称")
	private String categoryName;
	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "类别id")
	private Long categoryId;
	/**
	 * 是否上架
	 */
	@ApiModelProperty(value = "是否上架")
	private String isGroundName;
	/**
	 * 是否推荐
	 */
	@ApiModelProperty(value = "是否推荐")
	private String isRecommendName;
	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名称")
	private String userName;
	/**
	 * 用户账号
	 */
	@ApiModelProperty(value = "用户账号")
	private String account;
	/**
	 * bhuser的id
	 */
	@ApiModelProperty(value = "bhuser的id")
	private Long userId;
	/**
	 * 职位所在部门
	 */
	@ApiModelProperty(value = "职位所在部门")
	private Long positionDeptId;
	/**
	 * 职位所在部门名称
	 */
	@ApiModelProperty(value = "职位所在部门名称")
	private String positionDeptName;
	/**
	 * 应用部门
	 */
	@ApiModelProperty(value = "应用部门")
	private Long manageDeptId;
	/**
	 * 应用部门名称
	 */
	@ApiModelProperty(value = "应用部门名称")
	private String manageDeptName;

	/**
	 * 文件集合
	 */
	private List<BhFile> files;

	/**
	 * 应用类型，1-已建应用，2-企业产品
	 */
	@ApiModelProperty(value = "应用类型，1-已建应用，2-企业产品")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer type;

	/**
	 * 文件集合
	 */
	private List<BhFile> iconFiles;


	/**
	 * 是否授权 0未授权 1审核中 2已授权
	 */
	@ApiModelProperty(value = "是否授权 0未授权 1审核中 2已授权")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isAuthorization;

	/**
	 * 用户分配权限时间
	 */
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private LocalDateTime userTime;
	/**
	 * 是否为新的 1不是新的 0新的
	 */
	@ApiModelProperty(value = "是否为新的 1不是新的 0新的")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isNew;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer appOrder;


	/**
	 * 应用访问链接
	 */
	@ApiModelProperty(value = "应用访问链接")
	private String webUri;


}
