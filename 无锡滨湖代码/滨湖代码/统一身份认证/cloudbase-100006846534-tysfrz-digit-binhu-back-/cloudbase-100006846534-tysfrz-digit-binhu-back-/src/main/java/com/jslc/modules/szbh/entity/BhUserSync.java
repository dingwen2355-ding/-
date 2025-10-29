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
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户管理实体类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Data
@TableName("bh_user_sync")
@ApiModel(value = "BhUserSync对象", description = "用户管理实体类")
public class BhUserSync implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	* 用户名称
	*/
	@ApiModelProperty(value = "用户名称")
	private String name;
	/**
	* 用户账号
	*/
	@ApiModelProperty(value = "用户账号")
	private String userid;
//	/**
//	* 密码
//	*/
//	@ApiModelProperty(value = "密码")
//	private String password;
	/**
	* 成员所属部门id列表。排序在第一个的部门id为主岗部门
	*/
	@ApiModelProperty(value = "成员所属部门id列表。排序在第一个的部门id为主岗部门")
	private String department;
	/**
	* 所在的部门内是否为上级
	*/
	@ApiModelProperty(value = "所在的部门内是否为上级")
	private String isLeaderInDept;
	/**
	* 主部门职位
	*/
	@ApiModelProperty(value = "主部门职位")
	private String position;
	/**
	* 主部门手机
	*/
	@ApiModelProperty(value = "主部门手机")
	private String mobile;
	/**
	* 是否启用
	*/
	@ApiModelProperty(value = "启用/禁用成员。1表示启用成员，0表示禁用成员")
	private Integer enable;
	/**
	* 职位信息，多部门多职位，跟随部门
	*/
	@ApiModelProperty(value = "职位信息，多部门多职位，跟随部门")
	private String positions;
	/**
	* 性别，1-男，2-女
	*/
	@ApiModelProperty(value = "性别，1-男，2-女")
	private String gender;
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
	* 座机
	*/
	@ApiModelProperty(value = "座机")
	private String telephone;
	/**
	* 排序,原始字段为order
	*/
	@ApiModelProperty(value = "排序,原始字段为order")
	private String sort;
	/**
	* 是否隐藏手机号
	*/
	@ApiModelProperty(value = "是否隐藏手机号")
	private Integer hideMobile;
	/**
	 * 激活状态
	 */
	@ApiModelProperty(value = "激活状态: 1=已激活，2=已禁用，4=未激活")
	private Integer status;
	/**
	* 扩展属性，json格式
	*/
	@ApiModelProperty(value = "扩展属性，json格式")
	private String extattr;
	/**
	* 成员二维码图片地址
	*/
	@ApiModelProperty(value = "成员二维码图片地址")
	private String qrCode;
	/**
	* 手机区号
	*/
	@ApiModelProperty(value = "手机区号")
	private String countryCode;
	/**
	 * 对外简称
	 */
	@ApiModelProperty(value = "对外简称")
	private String externalCorpName;
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
	private LocalDateTime createTime;
	/**
	 * 用户所在主部门
	 */
	@ApiModelProperty(value = "用户所在主部门")
	private Long majorDepartment;



}
