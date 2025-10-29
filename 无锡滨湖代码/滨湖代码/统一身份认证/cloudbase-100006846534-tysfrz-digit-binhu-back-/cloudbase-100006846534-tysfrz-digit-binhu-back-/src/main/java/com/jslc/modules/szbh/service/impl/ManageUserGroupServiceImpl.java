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
import com.jslc.common.utils.DateCommonUtil;
import com.jslc.modules.szbh.entity.*;
import com.jslc.modules.szbh.service.IApplicationAuthorityService;
import com.jslc.modules.szbh.service.IManageUserService;
import com.jslc.modules.szbh.vo.BhUserVO;
import com.jslc.modules.szbh.vo.GroupUserInfoVO;
import com.jslc.modules.szbh.vo.ManageUserGroupVO;
import com.jslc.modules.szbh.mapper.ManageUserGroupMapper;
import com.jslc.modules.szbh.service.IManageUserGroupService;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.BeanUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理用户组 服务实现类
 *
 * @author BladeX
 * @since 2023-06-02
 */
@Service
public class ManageUserGroupServiceImpl extends ServiceImpl<ManageUserGroupMapper, ManageUserGroup> implements IManageUserGroupService {

	@Lazy
	@Autowired
	private IManageUserService iManageUserService;

	@Lazy
	@Autowired
	private IApplicationAuthorityService applicationAuthorityService;


	@Override
	public ManageUserGroup getDetail(Long id) {

		ManageUserGroup manageUserGroup = this.getById(id);

		return manageUserGroup;
	}

	@Override
	public IPage<GroupUserInfoVO> getGroupUser(IPage<GroupUserInfoVO> page, Long id, String userName) {
		List<GroupUserInfoVO> list = getGroupUserData( page, id, userName);
		return page.setRecords(list);
	}

	@Override
	public List<GroupUserInfoVO> getUserList(Long id, String userName) {
		List<GroupUserInfoVO> list = getGroupUserData( null, id, userName);
		return list;
	}

	@Override
	public List<GroupUserInfoVO> getGroupUserList(Long id) {
		List<GroupUserInfoVO> groupUserData = getGroupUserData(null, id, null);
		return groupUserData;
	}


	private List<GroupUserInfoVO> getGroupUserData(IPage<GroupUserInfoVO> page, Long id, String userName) {
		List<GroupUserInfoVO> list = baseMapper.getGroupUserData(page, id, userName);
		return list;
	}

	@Override
	public Boolean delUsers(Long manageId,List<Long> ids) {
		LambdaQueryWrapper<ManageUser> query = Wrappers.lambdaQuery(ManageUser.class).eq(ManageUser::getBizId,manageId).in(ManageUser::getBhUserId, ids);
		List<ManageUser> list = iManageUserService.list(query);
		List<Long> manageUserids = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)){
			manageUserids = list.stream().map(ManageUser::getId).collect(Collectors.toList());
		}

		boolean flag = iManageUserService.removeBatchByIds(manageUserids);
//		if (CollectionUtils.isNotEmpty(list)) {
			//更新用户组权限
			Integer type = 2;
			List<ApplicationAuthority> applicat = applicationAuthorityService.getAuthorityByType(manageId, type);
			applicationAuthorityService.submits(applicat);
//		}
		return flag;
	}

	@Override
	public IPage<ManageUserGroupVO> selectManageUserGroupPage(IPage<ManageUserGroupVO> page, String key) {
		List<ManageUserGroupVO> list = getUserGroup(page, key);
		return page.setRecords(list);
	}

	private List<ManageUserGroupVO> getUserGroup(IPage<ManageUserGroupVO> page, String key) {
		List<ManageUserGroupVO> list = baseMapper.selectManageUserGroupPage(page,key);
		return list;
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean submit(ManageUserGroup manageUserGroup) {
		Boolean addFlag = false;
		if (Func.isEmpty(manageUserGroup.getId())){ // 新增
			manageUserGroup.setCreateUser(AuthUtil.getUserId());
			manageUserGroup.setIsDeleted(0);
			manageUserGroup.setCreateTime(LocalDateTime.now());
			manageUserGroup.setUpdateUser(AuthUtil.getUserId());
			manageUserGroup.setUpdateTime(LocalDateTime.now());
			addFlag = true;
		}else {//修改
			manageUserGroup.setUpdateUser(AuthUtil.getUserId());
			manageUserGroup.setUpdateTime(LocalDateTime.now());
		}

		boolean result = this.saveOrUpdate(manageUserGroup);
		//新增更新用户组权限
		if (addFlag) {
			Integer type = 2;
			List<ApplicationAuthority> applicat = applicationAuthorityService.getAuthorityByType(manageUserGroup.getId(), type);
			applicationAuthorityService.submits(applicat);
		}
		return result;
	}

	@Override
	public Boolean dels(List<Long> ids) {
		//先删除人员
		LambdaQueryWrapper<ManageUser> queryUser = Wrappers.lambdaQuery(ManageUser.class).in(ManageUser::getBizId, ids);
		List<ManageUser> userList = iManageUserService.list(queryUser);
		if (CollectionUtils.isNotEmpty(userList)){
			throw new ServiceException("请先删除用户组下的人员");
		}


		//删除组的时候把组下的人员也删了
		LambdaUpdateWrapper<ManageUserGroup> updateGroup = Wrappers.lambdaUpdate(ManageUserGroup.class).in(ManageUserGroup::getId, ids).set(ManageUserGroup::getIsDeleted, 1);
		boolean flag = this.update(updateGroup);
		if (flag){//删除成功 删除组下的用户
			LambdaQueryWrapper<ManageUser> query = Wrappers.lambdaQuery(ManageUser.class).in(ManageUser::getBizId, ids);
			List<ManageUser> list = iManageUserService.list(query);
			if (CollectionUtils.isNotEmpty(list)){
				List<Long> userIds = list.stream().map(ManageUser::getId).collect(Collectors.toList());
				iManageUserService.removeBatchByIds(userIds);
			}
		}
		return flag;
	}


	@Override
	public void downLoad(HttpServletResponse response, String key,String ids) {
		OutputStream out = null;
		List<ManageUserGroupVO> list = getUserGroup(null, key);
		if (Func.isNotEmpty(ids) && CollectionUtils.isNotEmpty(list)){
			List<Long> id = Func.toLongList(ids);
			list= list.stream().filter(f->id.contains(f.getId())).collect(Collectors.toList());
		}
		try {
			String fileName = "管理用户组.xlsx";

			out = response.getOutputStream();
			//设置返回头
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");


			//动态添加 表头 headList --> 所有表头行集合
			List<List<String>> headList = new ArrayList<List<String>>();
			List<String> headTitle = new ArrayList<String>();
			headTitle.add("用户组名称");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("备注");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("创建时间");
			headList.add(headTitle);

			// 所有行的集合
			List<List<Object>> resultList = new ArrayList<List<Object>>();

			for (ManageUserGroupVO manageUserGroupVO : list) {
				List<Object> row = new ArrayList<Object>();
				row.add(manageUserGroupVO.getName());
				row.add(manageUserGroupVO.getRemark());
				row.add(Func.isNotEmpty(manageUserGroupVO.getCreateTime()) ? DateTimeUtil.format(manageUserGroupVO.getCreateTime(), DateCommonUtil.SECODE_FORMAT) : null);
				resultList.add(row);

			}

			EasyExcel.write(response.getOutputStream())
					.head(headList)
					.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
					.sheet("管理用户组")
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


	@Override
	public List<ManageUserGroup> getUserGroup(String bizId) {
		List<Long> ids = Func.toLongList(bizId);
		LambdaUpdateWrapper<ManageUserGroup> query = Wrappers.lambdaUpdate(ManageUserGroup.class).in(ManageUserGroup::getId, ids);
		List<ManageUserGroup> list = this.list(query);
		return list;

	}


	@Override
	public List<ManageUserGroup> getList(String key) {
		LambdaUpdateWrapper<ManageUserGroup> query = Wrappers.lambdaUpdate(ManageUserGroup.class).eq(ManageUserGroup::getIsDeleted,0);
		if (Func.isNotEmpty(key)){
			query = Wrappers.lambdaUpdate(ManageUserGroup.class).eq(ManageUserGroup::getIsDeleted,0).like(ManageUserGroup::getName,key);
		}
		return this.list(query);

	}
}
