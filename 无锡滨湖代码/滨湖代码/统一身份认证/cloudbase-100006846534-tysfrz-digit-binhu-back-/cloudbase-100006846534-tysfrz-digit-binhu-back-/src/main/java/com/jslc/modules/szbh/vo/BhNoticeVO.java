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

import com.jslc.modules.szbh.entity.BhFile;
import com.jslc.modules.szbh.entity.BhNotice;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 通知公告视图实体类
 *
 * @author BladeX
 * @since 2023-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BhNoticeVO extends BhNotice {
	private static final long serialVersionUID = 1L;
	/**
	 * 0待发布 1已发布
	 */
	Integer type;
	/**
	 * 0未过期 1 已过期
	 */
	Integer isExpire;
	/**
	 * 0待发布 1已发布
	 */
	String account;
	/**
	 * 0待发布 1已发布
	 */
	String noticeTypeName;

	/**
	 * 文件集合
	 */
	private List<BhFile> fileList;
}
