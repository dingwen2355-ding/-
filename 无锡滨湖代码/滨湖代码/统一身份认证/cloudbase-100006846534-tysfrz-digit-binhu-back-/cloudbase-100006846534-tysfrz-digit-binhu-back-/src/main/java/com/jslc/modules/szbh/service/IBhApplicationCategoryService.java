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
package com.jslc.modules.szbh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jslc.modules.szbh.entity.BhApplicationCategory;
import com.jslc.modules.szbh.vo.ApplicationDSQVO;
import com.jslc.modules.szbh.vo.BhApplicationCategoryManagementTreeVO;
import com.jslc.modules.szbh.vo.BhApplicationCategoryVO;
import com.jslc.modules.szbh.vo.SystemTreeNode;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 应用类别 服务类
 *
 * @author BladeX
 * @since 2023-05-09
 */
public interface IBhApplicationCategoryService extends IService<BhApplicationCategory> {

	BhApplicationCategoryVO getDetail(Long id);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bhApplicationCategory
	 * @return
	 */
//	IPage<BhApplicationCategoryVO> selectBhApplicationCategoryPage(IPage<BhApplicationCategoryVO> page, BhApplicationCategoryVO bhApplicationCategory);

	List<BhApplicationCategoryVO> getList(String name, Integer isEnable, Long parentId,Integer type);


	List<SystemTreeNode> getAppCategoryTree(String name,Integer treeType,Integer type);


	Boolean submit(BhApplicationCategory bhApplicationCategory);


	Boolean del(List<Long> ids);

	Boolean changeEnable(Long id);

	List<BhApplicationCategoryManagementTreeVO> getAppCategoryManagementTree();

	List<BhApplicationCategoryManagementTreeVO> getAppProductCategoryManagementTree();

    List<BhApplicationCategoryVO> getUserApplicationCategory();


	List<SystemTreeNode> getUserAppCategoryTree(String name, Integer treeType);

	List<ApplicationDSQVO> getApplicationList();




}



