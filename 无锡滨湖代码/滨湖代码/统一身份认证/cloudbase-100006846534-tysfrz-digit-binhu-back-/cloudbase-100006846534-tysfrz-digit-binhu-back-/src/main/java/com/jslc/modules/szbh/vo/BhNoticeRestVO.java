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

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.jslc.modules.szbh.entity.BhFile;
import com.jslc.modules.szbh.entity.BhNotice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知公告视图实体类
 *
 * @author BladeX
 * @since 2023-08-10
 */
@Data
public class BhNoticeRestVO  {
	private static final long serialVersionUID = 1L;

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
	 * 上传附件
	 */
	private String fileList;
}
