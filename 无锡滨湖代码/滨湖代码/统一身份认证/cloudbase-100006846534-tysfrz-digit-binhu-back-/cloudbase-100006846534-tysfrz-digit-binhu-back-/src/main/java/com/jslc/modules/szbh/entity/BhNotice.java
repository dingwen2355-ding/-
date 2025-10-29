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

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Delegate;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知公告实体类
 *
 * @author BladeX
 * @since 2023-08-10
 */
@Data
@TableName("bh_notice")
public class BhNotice implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 标题
	*/
		private String title;
	/**
	* 公告内容
	*/
		private String content;
	/**
	* 发布时间
	*/
	@JsonFormat(pattern = "yyyy-MM-dd")
		private LocalDate releaseTime;
	/**
	* 通知类型，1-服务指南 2-通知公告
	*/
		private Integer noticeType;
	/**
	* 信息来源
	*/
		private String infoSource;
	/**
	* 下架时间
	*/
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
		private LocalDate offShelfTime;
	/**
	* 是否置顶,1-是，0-否
	*/

		private Integer isTop;
	/**
	* 上传附件
	*/
		private String files;

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
	@TableLogic
	private Integer isDeleted;


}
