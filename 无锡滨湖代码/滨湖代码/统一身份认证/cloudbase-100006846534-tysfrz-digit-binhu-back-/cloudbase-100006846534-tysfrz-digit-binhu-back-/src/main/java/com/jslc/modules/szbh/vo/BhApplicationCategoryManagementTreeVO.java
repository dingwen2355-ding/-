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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springblade.core.tool.node.INode;

import java.util.ArrayList;
import java.util.List;

@Data
public class BhApplicationCategoryManagementTreeVO implements INode<BhApplicationCategoryManagementTreeVO> {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;
	@JsonSerialize(using = ToStringSerializer.class)
	private boolean hasChildren;
	@JsonSerialize(using = ToStringSerializer.class)
	private String title;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long nodeType;
	@JsonSerialize(using = ToStringSerializer.class)
	private Integer isGround;
	@JsonSerialize(using = ToStringSerializer.class)
	private Integer isRecommend;
	@JsonSerialize(using = ToStringSerializer.class)
	private String url;
	@JsonSerialize(using = ToStringSerializer.class)
	private String intro;
	@JsonSerialize(using = ToStringSerializer.class)
	private String description;
	@JsonSerialize(using = ToStringSerializer.class)
	private String deptId;
    @JsonSerialize(using = ToStringSerializer.class)
    private String deptName;
	@JsonSerialize(using = ToStringSerializer.class)
	private String logo;
	@JsonSerialize(using = ToStringSerializer.class)
	private String contacts;
	@JsonSerialize(using = ToStringSerializer.class)
	private String telephone;
	@JsonSerialize(using = ToStringSerializer.class)
	private String protocol;
	@JsonSerialize(using = ToStringSerializer.class)
	private String userColumn;
	@JsonSerialize(using = ToStringSerializer.class)
	private String createTime;
	@JsonSerialize(using = ToStringSerializer.class)
	private String icon;
	@JsonSerialize(using = ToStringSerializer.class)
	private Integer viewNum;
	@JsonSerialize(using = ToStringSerializer.class)
	private Integer applyForNum;


	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<BhApplicationCategoryManagementTreeVO> children;
	public BhApplicationCategoryManagementTreeVO() {
	}


	protected boolean canEqual(final Object other) {
		return other instanceof BhApplicationCategoryManagementTreeVO;
	}

	@Override
	public Boolean getHasChildren() {
		return this.hasChildren;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public Long getParentId() {
		return this.parentId;
	}

	@Override
	public List<BhApplicationCategoryManagementTreeVO> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}
}
