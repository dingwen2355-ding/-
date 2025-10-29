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

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.modules.szbh.dto.DeptMap;
import com.jslc.modules.szbh.dto.DeptPositionJson;
import com.jslc.modules.szbh.entity.BhUser;
import com.jslc.modules.szbh.entity.BhUserApplicationManagement;
import com.jslc.modules.szbh.service.IBhUserService;
import com.jslc.modules.szbh.vo.BhUserApplicationManagementOrderVO;
import com.jslc.modules.szbh.vo.BhUserApplicationManagementVO;
import com.jslc.modules.szbh.mapper.BhUserApplicationManagementMapper;
import com.jslc.modules.szbh.service.IBhUserApplicationManagementService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户应用管理表 服务实现类
 *
 * @author BladeX
 * @since 2023-05-11
 */
@Service
public class BhUserApplicationManagementServiceImpl extends ServiceImpl<BhUserApplicationManagementMapper, BhUserApplicationManagement> implements IBhUserApplicationManagementService {


	@Autowired
	@Lazy
	private IBhUserService iBhUserService;


	@Override
	public IPage<BhUserApplicationManagementVO> selectBhUserApplicationManagementPage(IPage<BhUserApplicationManagementVO> page, BhUserApplicationManagementVO bhUserApplicationManagement) {
		return page.setRecords(baseMapper.selectBhUserApplicationManagementPage(page, bhUserApplicationManagement));
	}

	@Override
	public List<DeptMap> getList(Long id,Long deptId) {
		List<DeptMap> result  = new ArrayList<>();
		LambdaQueryWrapper<BhUserApplicationManagement> query = Wrappers.lambdaQuery(BhUserApplicationManagement.class).eq(BhUserApplicationManagement::getUserId, id).eq(BhUserApplicationManagement::getDeptId, deptId);
		List<BhUserApplicationManagement> list = this.list(query);
		if (CollectionUtils.isNotEmpty(list)){
			Map<Long, List<BhUserApplicationManagement>> collect = list.stream().filter(f->f.getDeptId() != null).collect(Collectors.groupingBy(BhUserApplicationManagement::getDeptId));
			for (Map.Entry<Long, List<BhUserApplicationManagement>> entry : collect.entrySet()) {
				DeptMap deptMap = new DeptMap();
				deptMap.setDeptId(entry.getKey().toString());
				deptMap.setAppIds(entry.getValue().stream().map(BhUserApplicationManagement::getApplicationManagementId).collect(Collectors.toList()));
				result.add(deptMap);
			}
		}
		return result;
	}

	@Override
	public Boolean submit(String jsonStr) {

		Boolean flag= false;
		List<DeptPositionJson> deptPositionJsons = JSONArray.parseArray(jsonStr, DeptPositionJson.class);
		List<Long> users = deptPositionJsons.stream().map(DeptPositionJson::getUserId).collect(Collectors.toList());
		List<Long> deptId = deptPositionJsons.stream().map(DeptPositionJson::getDeptId).collect(Collectors.toList());
//		List<Long> apps = Func.toLongList(applicationIds);
		//需要添加的结果集
		List<BhUserApplicationManagement> result  =new ArrayList<>();
		LambdaQueryWrapper<BhUserApplicationManagement> queryWrapper = Wrappers.lambdaQuery(BhUserApplicationManagement.class).in(BhUserApplicationManagement::getUserId, users).in(BhUserApplicationManagement::getDeptId,deptId);
		List<BhUserApplicationManagement> list = this.list(queryWrapper);
		//现将原来的删除 真删除
		if (CollectionUtils.isNotEmpty(list)){
			this.removeByIds(list.stream().map(BhUserApplicationManagement::getId).collect(Collectors.toList()));
		}
			//将bhUserApplicationManagementVO.getApplicationIds() 应用存入
//			List<Long> applicationIds = Func.toLongList(bhUserApplicationManagementVO.getApplicationIds());

		for (DeptPositionJson f : deptPositionJsons) {
			if (f.getAppIds() != null){
				List<Long> appIds = Func.toLongList(f.getAppIds());
				for (Long appId : appIds) {
					BhUserApplicationManagement bhUserApplicationManagement = new BhUserApplicationManagement();
					bhUserApplicationManagement.setApplicationManagementId(appId);
					bhUserApplicationManagement.setUserId(f.getUserId());
					bhUserApplicationManagement.setDeptId(f.getDeptId());
					result.add(bhUserApplicationManagement);
				}
			}
		}

		if (CollectionUtils.isEmpty(result)){
			flag=true;
		}else {
			flag = this.saveOrUpdateBatch(result);
			if (flag){
				LambdaUpdateWrapper<BhUser> set = Wrappers.lambdaUpdate(BhUser.class).in(BhUser::getId, users).set(BhUser::getIsAuthorized, 1);
				iBhUserService.update(set);
			}
		}
		return flag;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean submitOrder(List<BhUserApplicationManagementOrderVO> param) {
		for (BhUserApplicationManagementOrderVO f : param) {
			LambdaUpdateWrapper<BhUserApplicationManagement> set = Wrappers.lambdaUpdate(BhUserApplicationManagement.class).eq(BhUserApplicationManagement::getApplicationManagementId, f.getId())
					.eq(BhUserApplicationManagement::getUserId, f.getUserId()).set(BhUserApplicationManagement::getAppOrder, f.getAppOrder());
			this.update(set);
		}

		return true;
	}

	@Override
	public List<BhUserApplicationManagement> getUserApp(String userAccount, Long id) {
		List<BhUserApplicationManagement> list = baseMapper.getUserApp(userAccount,id);
		return list;
	}

	@Override
	public Integer getDzcsUserNum() {
		Integer iCount = baseMapper.getDzcsUserNum();
		return iCount;
	}
}