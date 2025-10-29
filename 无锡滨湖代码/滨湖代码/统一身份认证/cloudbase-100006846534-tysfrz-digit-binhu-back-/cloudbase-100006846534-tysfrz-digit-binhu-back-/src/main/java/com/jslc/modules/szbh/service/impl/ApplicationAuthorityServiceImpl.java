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

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jslc.common.utils.CommonUtil;
import com.jslc.common.utils.DateCommonUtil;
import com.jslc.modules.szbh.entity.*;
import com.jslc.modules.szbh.service.*;
import com.jslc.modules.szbh.vo.ApplicationAuthorityVO;
import com.jslc.modules.szbh.mapper.ApplicationAuthorityMapper;
import com.jslc.modules.szbh.vo.SyncRecordVO;
import com.jslc.modules.szbh.vo.UserAppVO;
import org.apache.logging.log4j.util.PropertySource;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.DateTimeUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 应用授权 服务实现类
 *
 * @author BladeX
 * @since 2023-06-07
 */
@Service
public class ApplicationAuthorityServiceImpl extends ServiceImpl<ApplicationAuthorityMapper, ApplicationAuthority> implements IApplicationAuthorityService {

	@Autowired
	private IBhApplicationManagementService bhApplicationManagementService;
	@Autowired
	private IBhDeptService bhDeptService;
	@Autowired
	private IManageUserGroupService manageUserGroupService;

	@Lazy
	@Autowired
	private IBhUserService bhUserService;

//	@Autowired
//	private IBhDeptSyncService iBhDeptSyncService;

	@Autowired
	private  IBhUserApplicationManagementService bhUserApplicationManagementService;

	@Override
	public IPage<ApplicationAuthorityVO> selectApplicationAuthorityPage(IPage<ApplicationAuthorityVO> page,String appNames,String deptNames,Integer type) {
		List<ApplicationAuthorityVO> list = getApplication(appNames,deptNames, type);
		IPage iPage = CommonUtil.manualPage(page, list);
		return iPage;
	}

	private List<ApplicationAuthorityVO> getApplication(String appNames,String deptNames,Integer type) {
		List<ApplicationAuthorityVO> list = baseMapper.getApplication(type);
		if (CollectionUtils.isNotEmpty(list)){
			for (ApplicationAuthorityVO applicationAuthorityVO : list) {
				applicationAuthorityVO.setAppSum(0);
				applicationAuthorityVO.setDeptSums(0);
				if (Func.isNotEmpty(applicationAuthorityVO.getApplicationId())){
					List<BhApplicationManagement> list1 = bhApplicationManagementService.getApplication(applicationAuthorityVO.getApplicationId());
					if (CollectionUtils.isNotEmpty(list1)){
						applicationAuthorityVO.setAppSum(list1.size());
						applicationAuthorityVO.setAppNames(list1.stream().map(BhApplicationManagement::getName).collect(Collectors.joining(",")));
					}
				}
				if (type == 1) {
					if (Func.isNotEmpty(applicationAuthorityVO.getBizId())) {
						List<BhDept> list1 = bhDeptService.getDepts(applicationAuthorityVO.getBizId());
						if (CollectionUtils.isNotEmpty(list1)) {
							applicationAuthorityVO.setDeptSums(list1.size());
							applicationAuthorityVO.setDeptNames(list1.stream().map(BhDept::getName).collect(Collectors.joining(",")));
						}
					}
				}else if (type == 2){
					if (Func.isNotEmpty(applicationAuthorityVO.getBizId())) {
						List<ManageUserGroup> list1 = manageUserGroupService.getUserGroup(applicationAuthorityVO.getBizId());
						if (CollectionUtils.isNotEmpty(list1)) {
							applicationAuthorityVO.setDeptSums(list1.size());
							applicationAuthorityVO.setDeptNames(list1.stream().map(ManageUserGroup::getName).collect(Collectors.joining(",")));
						}
					}
				}else {
					if (Func.isNotEmpty(applicationAuthorityVO.getBizId())) {
						List<BhUser> list1 = bhUserService.getUserGroup(applicationAuthorityVO.getBizId());
						if (CollectionUtils.isNotEmpty(list1)) {
							applicationAuthorityVO.setDeptSums(list1.size());
							applicationAuthorityVO.setDeptNames(list1.stream().map(BhUser::getName).collect(Collectors.joining(",")));
						}
					}
				}
			}
		}
//		appNames deptNames
		if (CollectionUtils.isNotEmpty(list) && appNames != null){
			list = list.stream().filter(f->f.getAppNames() != null).filter(f->f.getAppNames().contains(appNames)).collect(Collectors.toList());
		}

		if (CollectionUtils.isNotEmpty(list) && deptNames != null){
			list = list.stream().filter(f->f.getDeptNames() != null).filter(f-> f.getDeptNames().contains(deptNames)).collect(Collectors.toList());
		}


		return list;
	}


	@Override
	public Boolean submit(ApplicationAuthority applicationAuthority) {
		if (Func.isEmpty(applicationAuthority.getId())){ // 新增
			applicationAuthority.setCreateUser(AuthUtil.getUserId());
			applicationAuthority.setIsDeleted(0);
			applicationAuthority.setCreateTime(LocalDateTime.now());
			applicationAuthority.setUpdateUser(AuthUtil.getUserId());
			applicationAuthority.setUpdateTime(LocalDateTime.now());
		}else {//修改
			applicationAuthority.setUpdateUser(AuthUtil.getUserId());
			applicationAuthority.setUpdateTime(LocalDateTime.now());
		}
		Boolean flag=  this.saveOrUpdate(applicationAuthority);

		if (flag){
			List<BhUserApplicationManagement> appUseList = new ArrayList<>();
			List<BhUserApplicationManagement> list = bhUserApplicationManagementService.list(Wrappers.lambdaQuery(BhUserApplicationManagement.class).eq(BhUserApplicationManagement::getBizId, applicationAuthority.getId()));

			if (CollectionUtils.isNotEmpty(list)){
				List<Long> ids = list.stream().map(BhUserApplicationManagement::getId).collect(Collectors.toList());
				bhUserApplicationManagementService.removeBatchByIds(ids);
			}
			if (applicationAuthority.getType() == 1){//组织
				List<Long> appIds = null;
				if (Func.isNotEmpty(applicationAuthority.getApplicationId())){
					appIds = Func.toLongList(applicationAuthority.getApplicationId());
				}
				List<BhUser> userIds = null;
				if (Func.isNotEmpty(applicationAuthority.getBizId())){
					//获取所有部门
					List<Long> deptId = Func.toLongList(applicationAuthority.getBizId());
//					List<Long> deptIds = queryParentId(deptId);
					List<Long> dept = new ArrayList<>();
					dept.addAll(deptId);
//					dept.addAll(deptIds);
					//获取部门下的人员
//					LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).in(BhUser::getMajorDept, dept);
//					List<BhUser> userList = bhUserService.list(query);

//					LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).in(BhUser::getMajorDept, dept);
					List<BhUser> userList = bhUserService.getUserList(dept);

					if (CollectionUtils.isNotEmpty(userList)){
						userIds = userList.stream().distinct().collect(Collectors.toList());
					}


				}
				if (CollectionUtils.isNotEmpty(userIds) && CollectionUtils.isNotEmpty(appIds)){
					for (BhUser user : userIds) {
						for (Long appId : appIds) {

							BhUserApplicationManagement bhUserApplicationManagement = new BhUserApplicationManagement();
							bhUserApplicationManagement.setUserId(user.getId());
							LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, user.getMajorDept());
							BhDept one = bhDeptService.getOne(query);
							if (Func.isNotEmpty(one)){
								bhUserApplicationManagement.setDeptId(one.getId());
							}

							bhUserApplicationManagement.setApplicationManagementId(appId);
							bhUserApplicationManagement.setCreateTime(LocalDateTime.now());
							bhUserApplicationManagement.setType(1);
							bhUserApplicationManagement.setBizId(applicationAuthority.getId());
							appUseList.add(bhUserApplicationManagement);
						}
					}
				}

			}else if(applicationAuthority.getType() == 2){//用户组
				List<Long> appIds = null;
				if (Func.isNotEmpty(applicationAuthority.getApplicationId())){
					appIds = Func.toLongList(applicationAuthority.getApplicationId());
				}
				List<BhUser> userIds = null;
				if (Func.isNotEmpty(applicationAuthority.getBizId())){
					//获取所有组
					List<Long> group = Func.toLongList(applicationAuthority.getBizId());

					//获取组下的人员
//					LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).in(BhUser::getMajorDept, deptIds);
					userIds = bhUserService.getUserGroupData(group);

				}
//				String majorDept = null;
//				//查询所有的用户的主岗部门或者其他
//				if (applicationAuthority.getUserGroupPostType() != null && applicationAuthority.getUserGroupPostType() == 1){
//					//仅主岗
//					List<Long> majorDeptList = userIds.stream().map(BhUser::getMajorDept).collect(Collectors.toList());
//
//				}else if (applicationAuthority.getUserGroupPostType() != null && applicationAuthority.getUserGroupPostType() == 1){
//
//				}
				if (CollectionUtils.isNotEmpty(userIds) && CollectionUtils.isNotEmpty(appIds)){
					for (BhUser user : userIds) {
						List<BhDept> bhDeptList = null;
						//查询所有的用户的主岗部门或者其他
						if (applicationAuthority.getUserGroupPostType() != null &&  user.getMajorDept() != null && applicationAuthority.getUserGroupPostType() == 1){

							LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, user.getMajorDept());
							bhDeptList = bhDeptService.list(query);
						}else if (applicationAuthority.getUserGroupPostType() != null &&  user.getMajorDept() != null && applicationAuthority.getUserGroupPostType() == 2){
							List<Long> depts = new ArrayList<>();
							depts.add(user.getMajorDept());
							depts.addAll(Func.toLongList(user.getOtherDept()));
							if (CollectionUtils.isNotEmpty(depts)){
								LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).in(BhDept::getDeptId, user.getMajorDept());
								bhDeptList = bhDeptService.list(query);
							}
						}
						if (bhDeptList != null) {
							for (BhDept bhDept : bhDeptList) {
								for (Long appId : appIds) {

									BhUserApplicationManagement bhUserApplicationManagement = new BhUserApplicationManagement();
									bhUserApplicationManagement.setUserId(user.getId());
									bhUserApplicationManagement.setApplicationManagementId(appId);
									bhUserApplicationManagement.setCreateTime(LocalDateTime.now());
									bhUserApplicationManagement.setType(2);
									bhUserApplicationManagement.setBizId(applicationAuthority.getId());
									appUseList.add(bhUserApplicationManagement);
									bhUserApplicationManagement.setDeptId(bhDept.getId());
								}
							}

						}
					}
				}
			}else {//用户
				List<Long> appIds = null;
				if (Func.isNotEmpty(applicationAuthority.getApplicationId())){
					appIds = Func.toLongList(applicationAuthority.getApplicationId());
				}
				List<BhUser> userIds = null;
				if (Func.isNotEmpty(applicationAuthority.getBizId())){
					//获取所有人员
					List<Long> user = Func.toLongList(applicationAuthority.getBizId());

					//获取组下的人员
					LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).in(BhUser::getId, user);
					userIds = bhUserService.list(query);
				}
				if (CollectionUtils.isNotEmpty(userIds) && CollectionUtils.isNotEmpty(appIds)){
					for (BhUser user : userIds) {
						for (Long appId : appIds) {

							BhUserApplicationManagement bhUserApplicationManagement = new BhUserApplicationManagement();
							bhUserApplicationManagement.setUserId(user.getId());
							LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, user.getMajorDept());
							BhDept one = bhDeptService.getOne(query);
							if (Func.isNotEmpty(one)){
								bhUserApplicationManagement.setDeptId(one.getId());
							}
							bhUserApplicationManagement.setApplicationManagementId(appId);
							bhUserApplicationManagement.setCreateTime(LocalDateTime.now());
							bhUserApplicationManagement.setType(applicationAuthority.getType());
							bhUserApplicationManagement.setBizId(applicationAuthority.getId());
							appUseList.add(bhUserApplicationManagement);
						}
					}
				}
			}
			bhUserApplicationManagementService.saveOrUpdateBatch(appUseList);
		}
		return flag;
	}


	@Override
	public Boolean submits(List<ApplicationAuthority> applicationAuthoritys) {
		Boolean result = true;
		if (CollectionUtils.isNotEmpty(applicationAuthoritys)){
			for (ApplicationAuthority applicationAuthority : applicationAuthoritys) {


				if (Func.isEmpty(applicationAuthority.getId())) { // 新增
					applicationAuthority.setCreateUser(AuthUtil.getUserId());
					applicationAuthority.setIsDeleted(0);
					applicationAuthority.setCreateTime(LocalDateTime.now());
					applicationAuthority.setUpdateUser(AuthUtil.getUserId());
					applicationAuthority.setUpdateTime(LocalDateTime.now());
				} else {//修改
					applicationAuthority.setUpdateUser(AuthUtil.getUserId());
					applicationAuthority.setUpdateTime(LocalDateTime.now());
				}
				Boolean flag = this.saveOrUpdate(applicationAuthority);

				if (flag) {
					List<BhUserApplicationManagement> appUseList = new ArrayList<>();
					List<BhUserApplicationManagement> list = bhUserApplicationManagementService.list(Wrappers.lambdaQuery(BhUserApplicationManagement.class).eq(BhUserApplicationManagement::getBizId, applicationAuthority.getId()));

					if (CollectionUtils.isNotEmpty(list)) {
						List<Long> ids = list.stream().map(BhUserApplicationManagement::getId).collect(Collectors.toList());
						bhUserApplicationManagementService.removeBatchByIds(ids);
					}
					if (applicationAuthority.getType() == 1) {//组织
						List<Long> appIds = null;
						if (Func.isNotEmpty(applicationAuthority.getApplicationId())) {
							appIds = Func.toLongList(applicationAuthority.getApplicationId());
						}
						List<BhUser> userIds = null;
						if (Func.isNotEmpty(applicationAuthority.getBizId())) {
							//获取所有部门
							List<Long> deptId = Func.toLongList(applicationAuthority.getBizId());
//							List<Long> deptIds = queryParentId(deptId);
							List<Long> dept = new ArrayList<>();
							dept.addAll(deptId);
//							dept.addAll(deptIds);
							//获取部门下的人员
//							LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).in(BhUser::getMajorDept, dept);
//							List<BhUser> userList = bhUserService.list(query);
							List<BhUser> userList = bhUserService.getUserList(dept);
							if (CollectionUtils.isNotEmpty(userList)) {
								userIds = userList.stream().distinct().collect(Collectors.toList());
							}


						}
						if (CollectionUtils.isNotEmpty(userIds) && CollectionUtils.isNotEmpty(appIds)) {
							for (BhUser user : userIds) {
								for (Long appId : appIds) {

									BhUserApplicationManagement bhUserApplicationManagement = new BhUserApplicationManagement();
									bhUserApplicationManagement.setUserId(user.getId());
									LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, user.getMajorDept());
									BhDept one = bhDeptService.getOne(query);
									if (Func.isNotEmpty(one)) {
										bhUserApplicationManagement.setDeptId(one.getId());
									}

									bhUserApplicationManagement.setApplicationManagementId(appId);
									bhUserApplicationManagement.setCreateTime(LocalDateTime.now());
									bhUserApplicationManagement.setType(1);
									bhUserApplicationManagement.setBizId(applicationAuthority.getId());
									appUseList.add(bhUserApplicationManagement);
								}
							}
						}

					} else if (applicationAuthority.getType() == 2) {//用户组
						List<Long> appIds = null;
						if (Func.isNotEmpty(applicationAuthority.getApplicationId())) {
							appIds = Func.toLongList(applicationAuthority.getApplicationId());
						}
						List<BhUser> userIds = null;
						if (Func.isNotEmpty(applicationAuthority.getBizId())) {
							//获取所有组
							List<Long> group = Func.toLongList(applicationAuthority.getBizId());

							//获取组下的人员
//					LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).in(BhUser::getMajorDept, deptIds);
							userIds = bhUserService.getUserGroupData(group);

						}

						if (CollectionUtils.isNotEmpty(userIds) && CollectionUtils.isNotEmpty(appIds)) {
							for (BhUser user : userIds) {
								List<BhDept> bhDeptList = null;
								//查询所有的用户的主岗部门或者其他
								if (applicationAuthority.getUserGroupPostType() != null && user.getMajorDept() != null && applicationAuthority.getUserGroupPostType() == 1) {

									LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, user.getMajorDept());
									bhDeptList = bhDeptService.list(query);
								} else if (applicationAuthority.getUserGroupPostType() != null && user.getMajorDept() != null && applicationAuthority.getUserGroupPostType() == 2) {
									List<Long> depts = new ArrayList<>();
									depts.add(user.getMajorDept());
									depts.addAll(Func.toLongList(user.getOtherDept()));
									if (CollectionUtils.isNotEmpty(depts)) {
										LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).in(BhDept::getDeptId, user.getMajorDept());
										bhDeptList = bhDeptService.list(query);
									}
								}
								if (bhDeptList != null) {
									for (BhDept bhDept : bhDeptList) {
										for (Long appId : appIds) {

											BhUserApplicationManagement bhUserApplicationManagement = new BhUserApplicationManagement();
											bhUserApplicationManagement.setUserId(user.getId());
											bhUserApplicationManagement.setApplicationManagementId(appId);
											bhUserApplicationManagement.setCreateTime(LocalDateTime.now());
											bhUserApplicationManagement.setType(2);
											bhUserApplicationManagement.setBizId(applicationAuthority.getId());
											appUseList.add(bhUserApplicationManagement);
											bhUserApplicationManagement.setDeptId(bhDept.getId());
										}
									}

								}
							}
						}
					} else {//用户
						List<Long> appIds = null;
						if (Func.isNotEmpty(applicationAuthority.getApplicationId())) {
							appIds = Func.toLongList(applicationAuthority.getApplicationId());
						}
						List<BhUser> userIds = null;
						if (Func.isNotEmpty(applicationAuthority.getBizId())) {
							//获取所有人员
							List<Long> user = Func.toLongList(applicationAuthority.getBizId());

							//获取组下的人员
							LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).in(BhUser::getId, user);
							userIds = bhUserService.list(query);
						}
						if (CollectionUtils.isNotEmpty(userIds) && CollectionUtils.isNotEmpty(appIds)) {
							for (BhUser user : userIds) {
								for (Long appId : appIds) {

									BhUserApplicationManagement bhUserApplicationManagement = new BhUserApplicationManagement();
									bhUserApplicationManagement.setUserId(user.getId());
									LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, user.getMajorDept());
									BhDept one = bhDeptService.getOne(query);
									if (Func.isNotEmpty(one)) {
										bhUserApplicationManagement.setDeptId(one.getId());
									}
									bhUserApplicationManagement.setApplicationManagementId(appId);
									bhUserApplicationManagement.setCreateTime(LocalDateTime.now());
									bhUserApplicationManagement.setType(applicationAuthority.getType());
									bhUserApplicationManagement.setBizId(applicationAuthority.getId());
									appUseList.add(bhUserApplicationManagement);
								}
							}
						}
					}
					bhUserApplicationManagementService.saveOrUpdateBatch(appUseList);
				}
			}

		}
		return result;
	}


	@Override
	public List<ApplicationAuthority> getAuthorityByType(Long bizId, Integer type) {
		List<ApplicationAuthority> result = new ArrayList<>();
		List<ApplicationAuthority> list = baseMapper.getAuthorityByType(bizId,type);
		if (CollectionUtils.isNotEmpty(list)){
			for (ApplicationAuthority applicationAuthority : list) {
				List<Long> bizIds = Func.toLongList(applicationAuthority.getBizId());
				if (bizIds.contains(bizId)){
					result.add(applicationAuthority);
				}
			}
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean submitApplyFor(List<ApplicationAuthority> applicationAuthority) {
		Boolean result = true;
		List<BhUserApplicationManagement> appUseList = new ArrayList<>();
		LocalDateTime end  = LocalDateTime.now();
		LocalDateTime start = end.with(LocalTime.MIN);
		if (CollectionUtils.isNotEmpty(applicationAuthority)){
			for (ApplicationAuthority authority : applicationAuthority) {
				LambdaQueryWrapper<ApplicationAuthority> query = Wrappers.lambdaQuery(ApplicationAuthority.class)
						.eq(ApplicationAuthority::getBizId, authority.getBizId())
						.eq(ApplicationAuthority::getIsDeleted,0)
						.eq(ApplicationAuthority::getType,authority.getType())
						.between(ApplicationAuthority::getCreateTime, start, end);
				ApplicationAuthority one = this.getOne(query);
				if (Func.isEmpty(one)){
					authority.setCreateUser(AuthUtil.getUserId());
					authority.setIsDeleted(0);
					authority.setCreateTime(LocalDateTime.now());
					authority.setUpdateUser(AuthUtil.getUserId());
					authority.setUpdateTime(LocalDateTime.now());
				}else {
					authority.setId(one.getId());
					authority.setUpdateUser(AuthUtil.getUserId());
					authority.setUpdateTime(LocalDateTime.now());
					authority.setApplicationId(one.getApplicationId()+","+authority.getApplicationId());
				}
				boolean flag = this.saveOrUpdate(authority);
				if (!flag){
					result = false;
				}
				if (flag){

					//先删除应用访问关系
					List<BhUserApplicationManagement> list = bhUserApplicationManagementService.list(Wrappers.lambdaQuery(BhUserApplicationManagement.class).eq(BhUserApplicationManagement::getBizId, authority.getId()));
					if (CollectionUtils.isNotEmpty(list)){
						List<Long> ids = list.stream().map(BhUserApplicationManagement::getId).collect(Collectors.toList());
						bhUserApplicationManagementService.removeBatchByIds(ids);
					}
					//获取对应的应用
					List<Long> appIds = null;
					if (Func.isNotEmpty(authority.getApplicationId())){
						appIds = Func.toLongList(authority.getApplicationId());
					}
					//获取对应的人员
					List<BhUser> userIds = null;
					if (Func.isNotEmpty(authority.getBizId())){
						//获取所有人员
						List<Long> user = Func.toLongList(authority.getBizId());

						//获取组下的人员
						LambdaQueryWrapper<BhUser> queryUser = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).in(BhUser::getId, user);
						userIds = bhUserService.list(queryUser);
					}
					//遍历添加应用访问关系
					if (CollectionUtils.isNotEmpty(userIds) && CollectionUtils.isNotEmpty(appIds)){
						for (BhUser user : userIds) {
							for (Long appId : appIds) {

								BhUserApplicationManagement bhUserApplicationManagement = new BhUserApplicationManagement();
								bhUserApplicationManagement.setUserId(user.getId());
								LambdaQueryWrapper<BhDept> queryBhdept = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, user.getMajorDept());
								BhDept bhDept = bhDeptService.getOne(queryBhdept);
								if (Func.isNotEmpty(bhDept)){
									bhUserApplicationManagement.setDeptId(bhDept.getId());
								}
								bhUserApplicationManagement.setApplicationManagementId(appId);
								bhUserApplicationManagement.setCreateTime(LocalDateTime.now());
								bhUserApplicationManagement.setType(4);
								bhUserApplicationManagement.setBizId(authority.getId());
								appUseList.add(bhUserApplicationManagement);
							}
						}
					}
				}
			}
		}
		bhUserApplicationManagementService.saveBatch(appUseList);

		return result;
	}

	public List<Long> queryParentId(List<Long> ids){
			LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).in(BhDept::getDeptId, ids);
			List<Long> result = new ArrayList<>();
			//根据父ID查询部门
			List<BhDept> departments = bhDeptService.list(query);
			if (CollectionUtils.isNotEmpty(departments)) {
				//拿到当前所有部门ID
				List<Long> parentIds = departments.stream().map(item -> item.getParentId()).distinct().collect(Collectors.toList());
				//拼接子部门查询结果
				result.addAll(queryParentId(parentIds));
				return result;
			} else {
				//如果没有下级部门那么我们就返回空集合，结束递归。
				return Lists.newArrayList();
			}
		}


	@Override
	public Boolean dels(List<Long> ids) {
		LambdaUpdateWrapper<ApplicationAuthority> set = Wrappers.lambdaUpdate(ApplicationAuthority.class).in(ApplicationAuthority::getId, ids).set(ApplicationAuthority::getIsDeleted, 1);
		boolean update = this.update(set);
		if (update){
			LambdaQueryWrapper<BhUserApplicationManagement> in = Wrappers.lambdaQuery(BhUserApplicationManagement.class).in(BhUserApplicationManagement::getBizId, ids);
			List<BhUserApplicationManagement> list = bhUserApplicationManagementService.list(in);
			if (CollectionUtils.isNotEmpty(list)){
				bhUserApplicationManagementService.removeBatchByIds(list.stream().map(BhUserApplicationManagement::getId).collect(Collectors.toList()));
			}

		}
		//同时删除应用访问权限

		return update;
	}


	@Override
	public IPage<UserAppVO> getUserAppPage(IPage<UserAppVO> page, String account,String name, String deptIds, String appIds) {
		List<UserAppVO> list = getUserAppList(page,account, name,deptIds,appIds);
		if (CollectionUtils.isNotEmpty(list)){
			list = list.stream().sorted(Comparator.comparing(UserAppVO::getCreateTime, Comparator.nullsFirst(Comparator.naturalOrder())).reversed()).collect(Collectors.toList());
		}
		IPage iPage = CommonUtil.manualPage(page, list);
		return  iPage;
	}


	@Override
	public void downLoad(HttpServletResponse response, String account,String name, String deptIds, String appIds) {
		OutputStream out = null;
		List<UserAppVO> list = getUserAppList(null,account, name,deptIds,appIds);

		try {
			String fileName = "应用访问关系.xlsx";

			out = response.getOutputStream();
			//设置返回头
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");


			//动态添加 表头 headList --> 所有表头行集合
			List<List<String>> headList = new ArrayList<List<String>>();
			List<String> headTitle = new ArrayList<String>();
			headTitle.add("账号");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("姓名");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("部门");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("职位");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("是否可登录");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("应用");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("同步时间");
			headList.add(headTitle);


			// 所有行的集合
			List<List<Object>> resultList = new ArrayList<List<Object>>();

			for (UserAppVO userAppVO : list) {
				List<Object> row = new ArrayList<Object>();
				row.add(userAppVO.getAccount());
				row.add(userAppVO.getName());
				row.add(userAppVO.getDeptName());
				row.add(userAppVO.getPositionName());
				row.add(userAppVO.getIsEnableLoginName());
				row.add(userAppVO.getAppName());
				row.add(Func.isNotEmpty(userAppVO.getCreateTime()) ? DateTimeUtil.format(userAppVO.getCreateTime(), DateCommonUtil.SECODE_FORMAT) : null);
				resultList.add(row);
			}

			EasyExcel.write(response.getOutputStream())
					.head(headList)
					.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
					.sheet("应用访问关系")
					.doWrite(resultList);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private List<UserAppVO> getUserAppList(IPage<UserAppVO> page, String account,String name, String deptIds, String appIds) {
		List<UserAppVO> result = new ArrayList<>();
		List<Long> dept = null;
		List<Long> appId = null;
		if (Func.isNotEmpty(deptIds)){
			dept = Func.toLongList(deptIds);
		}
		if (Func.isNotEmpty(appIds)){
			appId = Func.toLongList(appIds);
		}

		List<UserAppVO> list = baseMapper.getUserAppList(null,account, name,dept,appId);
		if (CollectionUtils.isNotEmpty(list)){//去重
//			Map<Integer, List<UserAppVO>> collect = list.stream().collect(Collectors.groupingBy(UserAppVO::getType));
//			for (Map.Entry<Integer, List<UserAppVO>> entry : collect.entrySet()) {
//				result.addAll(entry.getValue().stream().distinct().collect(Collectors.toList()));
//			}
			Map<String, List<UserAppVO>> collect = list.stream().collect(Collectors.groupingBy(f -> f.getAccount() + f.getAppName()));

			for (Map.Entry<String, List<UserAppVO>> entry : collect.entrySet()) {
				UserAppVO userAppVO = entry.getValue().stream().filter(f -> f.getCreateTime() != null).max(Comparator.comparing(UserAppVO::getCreateTime)).orElse(null);
				if (Func.isNotEmpty(userAppVO)){
					result.add(userAppVO);
				}
			}

		}

		return result;

	}
}
