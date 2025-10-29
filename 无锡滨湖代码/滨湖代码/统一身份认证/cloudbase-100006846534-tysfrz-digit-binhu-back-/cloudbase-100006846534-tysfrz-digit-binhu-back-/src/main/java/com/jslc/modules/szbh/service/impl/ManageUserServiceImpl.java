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

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.modules.szbh.entity.ApplicationAuthority;
import com.jslc.modules.szbh.entity.BhApplicationManagement;
import com.jslc.modules.szbh.entity.ManageUser;
import com.jslc.modules.szbh.service.IApplicationAuthorityService;
import com.jslc.modules.szbh.vo.ManageUserVO;
import com.jslc.modules.szbh.mapper.ManageUserMapper;
import com.jslc.modules.szbh.service.IManageUserService;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理用户组用户表 服务实现类
 *
 * @author BladeX
 * @since 2023-06-02
 */
@Service
public class ManageUserServiceImpl extends ServiceImpl<ManageUserMapper, ManageUser> implements IManageUserService {

	@Lazy
	@Autowired
	private IApplicationAuthorityService applicationAuthorityService;

	@Override
	public IPage<ManageUserVO> selectManageUserPage(IPage<ManageUserVO> page, ManageUserVO manageUser) {
		return page.setRecords(baseMapper.selectManageUserPage(page, manageUser));
	}

	@Override
	public ManageUser getDetail(Long id) {
		ManageUser one = this.getById(id);
		return one;
	}



	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean submit(ManageUserVO manageUser) {
		if (Func.isNotEmpty(manageUser.getUserIds()) && manageUser.getUserIds().length() == 0){
			throw new ServiceException("添加组内人员为空");
		}
		List<ManageUser> result  =new ArrayList<>();
		LambdaQueryWrapper<ManageUser> query = Wrappers.lambdaQuery(ManageUser.class).eq(ManageUser::getBizId, manageUser.getBizId());
		List<ManageUser> list = this.list(query);
		if (CollectionUtils.isNotEmpty(list)){//先删除
			List<Long> ids = list.stream().map(ManageUser::getId).collect(Collectors.toList());
			this.removeByIds(ids);
		}

		if (manageUser.getUserIds() != null){
			List<Long> userIds = Func.toLongList(manageUser.getUserIds());
			for (Long id : userIds) {
				ManageUser manageUser1 = new ManageUser();
				manageUser1.setBhUserId(id);
				manageUser1.setBizId(manageUser.getBizId());
				result.add(manageUser1);
			}
		}
		boolean flag = this.saveOrUpdateBatch(result);
		Integer type = 2;
		List<ApplicationAuthority> authorityByType = applicationAuthorityService.getAuthorityByType(manageUser.getBizId(), type);
		applicationAuthorityService.submits(authorityByType);
		return flag;
	}
}
