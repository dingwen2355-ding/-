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
package com.jslc.modules.szbh.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.core.tool.utils.DateUtil;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 应用管理实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
@ApiModel(value = "BhDept对象", description = "部门管理实体类")
public class BhApplicationManagementUpDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 应用名称
	*/
	@ApiModelProperty(value = "应用名称")
	private String name;
	/**
	* 应用类别
	*/
	@ApiModelProperty(value = "应用类别")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long categoryId;
	/**
	* 应用logo
	*/
	@ApiModelProperty(value = "应用logo")
	private String logo;
	/**
	* 应用描述
	*/
	@ApiModelProperty(value = "应用描述")
	private String description;
	/**
	* 所属部门
	*/
	@ApiModelProperty(value = "所属部门")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long deptId;
	/**
	* 联系人
	*/
	@ApiModelProperty(value = "联系人")
	private String contacts;
	/**
	* 电话
	*/
	@ApiModelProperty(value = "电话")
	private String telephone;
	/**
	* 应用URL
	*/
	@ApiModelProperty(value = "应用URL")
	private String url;
	/**
	* 是否上架 1上 0不上
	*/
	@ApiModelProperty(value = "是否上架")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isGround;
	/**
	* 是否启用
	*/
	@ApiModelProperty(value = "是否启用 1启 0关")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isEnable;
	/**
	* 是否推荐 1推 0不推
	*/
	@ApiModelProperty(value = "是否推荐 1推 0不推")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isRecommend;
	/**
	* 是否短信推送 1推 0不推
	*/
	@ApiModelProperty(value = "是否短信推送 1推 0不推")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isSendSms;
	/**
	* 协议类型
	*/
	@ApiModelProperty(value = "协议类型")
	private String protocol;
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
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	private LocalDateTime createTime;
	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long updateUser;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;
	/**
	 * 是否删除0：未删除，1：已删除
	 */
	@ApiModelProperty(value = "是否删除0：未删除，1：已删除")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isDeleted;
	/**
	 * 应用简介
	 */
	@ApiModelProperty(value = "应用简介")
	private String intro;
	/**
	 * 应用logo的id
	 */
	@ApiModelProperty(value = "应用logo的id")
	private String logoId;
	/**
	 * logo的id
	 */
	@ApiModelProperty(value = "logo的id")
	private String iconId;
	/**
	 * logo的地址
	 */
	@ApiModelProperty(value = "logo的地址")
	private String icon;
	/**
	 * token
	 */
	@ApiModelProperty(value = "token")
	private String token;
	/**
	 * 申请理由
	 */
	private String reason;
	/**
	 * 审核意见
	 */
	private String reviewOpinion;
	/**
	 * 审核状态
	 */
	private Integer reviewStatus;
	/**
	 * 申请人，关联用户账号account
	 */
	@ApiModelProperty(value = "申请人，关联用户账号account")
	private String applicant;
}
