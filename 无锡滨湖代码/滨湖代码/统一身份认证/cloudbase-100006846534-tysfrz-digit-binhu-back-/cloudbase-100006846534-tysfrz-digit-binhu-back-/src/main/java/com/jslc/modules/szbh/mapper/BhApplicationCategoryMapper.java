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
package com.jslc.modules.szbh.mapper;

import com.jslc.modules.szbh.entity.BhApplicationCategory;
import com.jslc.modules.szbh.vo.ApplicationDSQVO;
import com.jslc.modules.szbh.vo.BhApplicationCategoryManagementTreeVO;
import com.jslc.modules.szbh.vo.BhApplicationCategoryVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.szbh.vo.SystemTreeNode;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 应用类别 Mapper 接口
 *
 * @author BladeX
 * @since 2023-05-09
 */
public interface BhApplicationCategoryMapper extends BaseMapper<BhApplicationCategory> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bhApplicationCategory
	 * @return
	 */
	List<BhApplicationCategoryVO> selectBhApplicationCategoryPage(IPage page, BhApplicationCategoryVO bhApplicationCategory);

    BhApplicationCategoryVO getDetail(@Param("id") Long id);

	List<BhApplicationCategoryVO> getAppCategoryList(@Param("name")String name,
													 @Param("isEnable")Integer isEnable,
													 @Param("parentId")Long parentId,
													 @Param("type")Integer type);


	List<SystemTreeNode> getOrgTree(@Param("name")String name,
									@Param("treeType")Integer treeType,
									@Param("type")Integer type);

	/**
	 * 所有应用
	 */
	List<BhApplicationCategoryManagementTreeVO> getAppCategoryManagementTree(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

	/**
	 * 应用分类
	 */
	List<BhApplicationCategoryVO> applicationClassification();

	/**
	 * 所有企业产品
	 */
	List<BhApplicationCategoryManagementTreeVO> getAppProductCategoryManagementTree();

	List<SystemTreeNode> getUserAppCategoryTree(@Param("name") String name,@Param("treeType") Integer treeType);

	List<ApplicationDSQVO> getApplicationList();



}
