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

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户管理实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
@ApiModel(value = "BhUser对象", description = "用户管理实体类")
public class BhUserRestVO  {

	private static final long serialVersionUID = 1L;
	/**
	* 用户名称
	*/
	@ApiModelProperty(value = "用户名称")
	private String name;
	/**
	* 用户账号
	*/
	@ApiModelProperty(value = "用户账号")
	private String account;
//	/**
//	* 密码
//	*/
//	@ApiModelProperty(value = "密码")
//	private String password;
	/**
	* 所属主部门
	*/
	@ApiModelProperty(value = "所属主部门")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Long majorDept;
	/**
	* 所在的主部门内是否为上级
	*/
	@ApiModelProperty(value = "所在的主部门内是否为上级")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isLeaderInDept;
	/**
	* 主部门职位
	*/
	@ApiModelProperty(value = "主部门职位")
	private String majorPosition;
	/**
	* 主部门手机
	*/
	@ApiModelProperty(value = "主部门手机")
	private String majorMobile;
	/**
	* 其他所属部门
	*/
	@ApiModelProperty(value = "其他所属部门")
	private String otherDept;
	/**
	* 所在的其他部门内是否为上级
	*/
	@ApiModelProperty(value = "所在的其他部门内是否为上级")
	private String isLeaderInOtherDept;
	/**
	* 其他部门职位
	*/
	@ApiModelProperty(value = "其他部门职位")
	private String otherPosition;
	/**
	* 性别，1-男，2-女
	*/
	@ApiModelProperty(value = "性别，1-男，2-女")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer gender;
	/**
	* 用户头像
	*/
	@ApiModelProperty(value = "用户头像")
	private String avatar;
	/**
	* 别名
	*/
	@ApiModelProperty(value = "别名")
	private String englishName;
	/**
	* 邮箱
	*/
	@ApiModelProperty(value = "邮箱")
	private String email;
	/**
	* 电话
	*/
	@ApiModelProperty(value = "电话")
	private String telephone;
	/**
	* 对外简称
	*/
	@ApiModelProperty(value = "对外简称")
	private String externalCorpName;
	/**
	* 排序
	*/
	@ApiModelProperty(value = "排序")
	private String sort;
	/**
	 * 是否在线1-在线，0-离线
	 */
	@ApiModelProperty(value = "密码是否已更新，0-未更新，1-已更新")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isPasswordUpdated;
	/**
	 * 是否短信通知 1发 0 不发
	 */
	@ApiModelProperty(value = "是否短信通知 1发 0 不发")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isSendSms;
	/**
	 * 是否启用
	 */
	@ApiModelProperty(value = "是否启用")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private Integer isEnable;
	/**
	 * 主岗部门名称
	 */
	@ApiModelProperty(value = "主岗部门名称")
	private String majorDeptName;
	/**
	 * 祖级部门名称
	 */
	@ApiModelProperty(value = "祖级部门名称")
	private String ancestorNames;
}
