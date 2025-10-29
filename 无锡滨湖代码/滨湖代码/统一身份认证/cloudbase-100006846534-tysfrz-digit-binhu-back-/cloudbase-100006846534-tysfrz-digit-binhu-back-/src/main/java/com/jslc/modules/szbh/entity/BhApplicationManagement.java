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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.tool.utils.DateUtil;

/**
 * 应用管理实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
@TableName("bh_application_management")
@ApiModel(value = "BhDept对象", description = "部门管理实体类")
public class BhApplicationManagement implements Serializable {

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
	* appID
	*/
	@ApiModelProperty(value = "appID")
	private String appId;
	/**
	* appSecret
	*/
	@ApiModelProperty(value = "appSecret")
	private String appSecret;
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
	* 用户信息
	*/
	@ApiModelProperty(value = "用户信息")
	private String userColumn;
	/**
	* 排序
	*/
	@ApiModelProperty(value = "排序")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer sort;
	/**
	* 是否已更新，0-未更新，1-已更新
	*/
	@ApiModelProperty(value = "是否已更新，0-未更新，1-已更新")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isUpdated;
	/**
	* 数据来源
	*/
	@ApiModelProperty(value = "数据来源")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer dataSource;

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
	 * 企业id
	 */
	@ApiModelProperty(value = "企业id")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long entId;

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

}
