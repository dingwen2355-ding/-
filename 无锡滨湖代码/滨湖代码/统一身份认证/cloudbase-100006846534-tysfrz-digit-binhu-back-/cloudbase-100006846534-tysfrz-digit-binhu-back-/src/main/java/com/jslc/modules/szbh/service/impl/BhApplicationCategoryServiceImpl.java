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
package com.jslc.modules.szbh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.modules.system.wrapper.DeptWrapper;
import com.jslc.modules.szbh.entity.*;
import com.jslc.modules.szbh.service.IBhApplicationManagementService;
import com.jslc.modules.szbh.service.IOauthClientDetailsService;
import com.jslc.modules.szbh.vo.*;
import com.jslc.modules.szbh.mapper.BhApplicationCategoryMapper;
import com.jslc.modules.szbh.service.IBhApplicationCategoryService;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用类别 服务实现类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Service
public class BhApplicationCategoryServiceImpl extends ServiceImpl<BhApplicationCategoryMapper, BhApplicationCategory> implements IBhApplicationCategoryService {

	@Lazy
	@Autowired
	private IBhApplicationManagementService applicationManagementService;


	@Override
	public BhApplicationCategoryVO getDetail(Long id) {
		BhApplicationCategoryVO detail = baseMapper.getDetail(id);
		return detail;
	}

	@Override
	public List<BhApplicationCategoryVO> getList(String name, Integer isEnable, Long parentId,Integer type) {
		List<BhApplicationCategoryVO> list = getAppCategoryList(name,isEnable,  parentId, type);
		return list;
	}

	private List<BhApplicationCategoryVO> getAppCategoryList(String name, Integer isEnable, Long parentId,Integer type) {
		List<BhApplicationCategoryVO> list = baseMapper.getAppCategoryList(name,isEnable,parentId, type);
		List<BhApplicationCategoryVO> result  = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)){
			result = DeptWrapper.build().listNodeLazyVO3(list);
		}
		return result;
	}

	@Override
	public List<SystemTreeNode> getAppCategoryTree(String name,Integer treeType,Integer type) {
		List<SystemTreeNode> result = new ArrayList<>();
		List<SystemTreeNode> list = baseMapper.getOrgTree(name, treeType,type);
		result = ForestNodeMerger.merge(list);
		return result;
	}

	@Override
	public List<SystemTreeNode> getUserAppCategoryTree(String name, Integer treeType) {
		List<SystemTreeNode> result = new ArrayList<>();
		List<SystemTreeNode> list = baseMapper.getUserAppCategoryTree(name, treeType);
		result = ForestNodeMerger.merge(list);
		return result;
	}

	@Override
	public Boolean submit(BhApplicationCategory bhApplicationCategory) {
		Integer sort = 1;
		LambdaQueryWrapper<BhApplicationCategory> query = Wrappers.lambdaQuery(BhApplicationCategory.class)
				.eq(BhApplicationCategory::getIsDeleted,0)
				.eq(BhApplicationCategory::getType,bhApplicationCategory.getType())
				.orderByDesc(BhApplicationCategory::getSort);
		List<BhApplicationCategory> list = this.list(query);
		if (CollectionUtils.isNotEmpty(list)){
			sort = list.get(0).getSort()+1;
		}
		if (Func.isEmpty(bhApplicationCategory.getParentId())){
			bhApplicationCategory.setParentId(0L);
		}
		if (Func.isEmpty(bhApplicationCategory.getId())){ // 新增
			bhApplicationCategory.setCreateUser(AuthUtil.getUserId());
			bhApplicationCategory.setIsDeleted(0);
			bhApplicationCategory.setCreateTime(LocalDateTime.now());
			bhApplicationCategory.setUpdateUser(AuthUtil.getUserId());
			bhApplicationCategory.setUpdateTime(LocalDateTime.now());
			if (Func.isEmpty(bhApplicationCategory.getSort())){
				bhApplicationCategory.setSort(sort);
			}
		}else {//修改
			bhApplicationCategory.setUpdateUser(AuthUtil.getUserId());
			bhApplicationCategory.setUpdateTime(LocalDateTime.now());
		}
		return this.saveOrUpdate(bhApplicationCategory);

	}


	@Override
	public Boolean del(List<Long> ids) {
		//删除的时候把父节点下的子节点也删除
		List<Long> hdDeptIds  = new ArrayList<>();
		for (Long id : ids) {
			LambdaQueryWrapper<BhApplicationCategory> query = Wrappers.lambdaQuery(BhApplicationCategory.class).eq(BhApplicationCategory::getIsDeleted, 0).eq(BhApplicationCategory::getParentId, id);
			List<BhApplicationCategory> list = this.list(query);
			if (CollectionUtils.isNotEmpty(list)){
				hdDeptIds.addAll(list.stream().map(BhApplicationCategory::getId).collect(Collectors.toList()));
			}
			hdDeptIds.add(id);
		}
		LambdaUpdateWrapper<BhApplicationManagement> queryApp = Wrappers.lambdaUpdate(BhApplicationManagement.class).in(BhApplicationManagement::getCategoryId, hdDeptIds).eq(BhApplicationManagement::getIsDeleted, 0);
		List<BhApplicationManagement> list = applicationManagementService.list(queryApp);
		if (CollectionUtils.isNotEmpty(list)){
			throw new ServiceException("该应用类别下有应用，请删除应用后再删除应用类别");
		}

		LambdaUpdateWrapper<BhApplicationCategory> set = Wrappers.lambdaUpdate(BhApplicationCategory.class).in(BhApplicationCategory::getId, hdDeptIds).set(BhApplicationCategory::getIsDeleted, 1);
		return this.update(set);
	}

	@Override
	public Boolean changeEnable(Long id) {
		Integer state = 0;
		BhApplicationCategory one = this.getById(id);
		if (Func.isEmpty(one.getIsEnable()) || one.getIsEnable() == 0){
			state = 1;
		}
		LambdaUpdateWrapper<BhApplicationCategory> set = Wrappers.lambdaUpdate(BhApplicationCategory.class).eq(BhApplicationCategory::getId, id).set(BhApplicationCategory::getIsEnable, state);
		return  this.update(set);
	}

	@Override
	public List<BhApplicationCategoryManagementTreeVO> getAppCategoryManagementTree() {
		List<BhApplicationCategoryManagementTreeVO> result = new ArrayList<>();
		LocalDateTime start = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
		LocalDateTime end = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

		List<BhApplicationCategoryManagementTreeVO> list = baseMapper.getAppCategoryManagementTree(start,end);
		result = ForestNodeMerger.merge(list);
		return result;
	}

	@Override
	public List<ApplicationDSQVO> getApplicationList() {
		List<ApplicationDSQVO> list = baseMapper.getApplicationList();
		return list;
	}

	@Override
	public List<BhApplicationCategoryManagementTreeVO> getAppProductCategoryManagementTree() {
		List<BhApplicationCategoryManagementTreeVO> result = new ArrayList<>();
		List<BhApplicationCategoryManagementTreeVO> list = baseMapper.getAppProductCategoryManagementTree();
		result = ForestNodeMerger.merge(list);
		return result;
	}

	/***********************************前台*****************************/
	@Override
	public List<BhApplicationCategoryVO> getUserApplicationCategory() {
		List<BhApplicationCategoryVO> appList = baseMapper.applicationClassification();

//		List<BhApplicationCategoryRestVO> resultList = new ArrayList<>();
//		if (Func.isNotEmpty(appList)) {
//			appList.forEach(o -> {
//				BhApplicationCategoryRestVO bhApplicationCategoryRestVO = new BhApplicationCategoryRestVO();
//				bhApplicationCategoryRestVO.setId(o.getId());
//				bhApplicationCategoryRestVO.setName(o.getName());
//				resultList.add(bhApplicationCategoryRestVO);
//			});
//		}
		return appList;
	}
}
