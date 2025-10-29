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
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springblade.core.tool.node.INode;

import java.util.ArrayList;
import java.util.List;

@Data
public class SystemTreeNode implements INode<SystemTreeNode> {
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
	private Long key;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long value;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long nodeType;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentKey;
	@JsonSerialize(using = ToStringSerializer.class)
	private String parentTitle;
	@JsonSerialize(using = ToStringSerializer.class)
	private Integer deptType;
	@JsonSerialize(using = ToStringSerializer.class)
	private String tenantId;
//	@JsonSerialize(using = ToStringSerializer.class)
//	private List<SiteInfo> siteInfoList;

	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<SystemTreeNode> children;
	public SystemTreeNode() {
	}

	public String toString() {
		return "SystemTreeNode(title=" + this.getTitle() + ", key=" + this.getKey() + ", value=" + this.getValue() + ")";
	}

	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof SystemTreeNode)) {
			return false;
		} else {
			SystemTreeNode other = (SystemTreeNode) o;
			if (!other.canEqual(this)) {
				return false;
			} else {
				label47:
				{
					Object this$title = this.getTitle();
					Object other$title = other.getTitle();
					if (this$title == null) {
						if (other$title == null) {
							break label47;
						}
					} else if (this$title.equals(other$title)) {
						break label47;
					}

					return false;
				}

				Object this$key = this.getKey();
				Object other$key = other.getKey();
				if (this$key == null) {
					if (other$key != null) {
						return false;
					}
				} else if (!this$key.equals(other$key)) {
					return false;
				}

				Object this$value = this.getValue();
				Object other$value = other.getValue();
				if (this$value == null) {
					if (other$value != null) {
						return false;
					}
				} else if (!this$value.equals(other$value)) {
					return false;
				}

				return true;
			}
		}
	}

	protected boolean canEqual(final Object other) {
		return other instanceof SystemTreeNode;
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
	public List<SystemTreeNode> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}
}
