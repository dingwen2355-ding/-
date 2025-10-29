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
import com.jslc.modules.szbh.entity.DownstreamSync;
import com.jslc.modules.szbh.entity.SyncRecord;
import com.jslc.modules.szbh.mapper.SyncRecordMapper;
import com.jslc.modules.szbh.vo.DownstreamSyncVO;
import com.jslc.modules.szbh.mapper.DownstreamSyncMapper;
import com.jslc.modules.szbh.service.IDownstreamSyncService;
import com.jslc.modules.szbh.vo.SyncRecordVO;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 下游同步配置表 服务实现类
 *
 * @author BladeX
 * @since 2023-06-06
 */
@Service
public class DownstreamSyncServiceImpl extends ServiceImpl<DownstreamSyncMapper, DownstreamSync> implements IDownstreamSyncService {

	@Autowired
	private SyncRecordMapper syncRecordMapper;



	@Override
	public DownstreamSyncVO getDetail(Long id) {
		DownstreamSyncVO downstreamSyncVO = baseMapper.getDetail(id);
		return downstreamSyncVO;
	}

	@Override
	public IPage<DownstreamSyncVO> selectDownstreamSyncPage(IPage<DownstreamSyncVO> page, String key) {
		List<DownstreamSyncVO> list = baseMapper.selectDownstreamSyncPage(page, key);
		if (CollectionUtils.isNotEmpty(list)){
			for (DownstreamSyncVO downstreamSyncVO : list) {
				List<SyncRecordVO> downList = syncRecordMapper.getDownList(null, downstreamSyncVO.getAppName(),downstreamSyncVO.getId());
				downstreamSyncVO.setSynTypeName(CollectionUtils.isEmpty(downList) ? "已同步" : "未同步");
			}

		}
		return page.setRecords(list);
	}


	@Override
	public Boolean del(List<Long> ids) {
		LambdaUpdateWrapper<DownstreamSync> set = Wrappers.lambdaUpdate(DownstreamSync.class).in(DownstreamSync::getId, ids).set(DownstreamSync::getIsDeleted, 1);

		return this.update(set);
	}

	@Scheduled(cron = "0 0 0/1 * * ?")
	@Transactional(rollbackFor = Exception.class)
	public void refreshDownstreamInfo(){
		//获取所有的人员信息
		LambdaQueryWrapper<DownstreamSync> query = Wrappers.lambdaQuery(DownstreamSync.class).eq(DownstreamSync::getDataType,"人员信息").eq(DownstreamSync::getIsDeleted, 0);
		List<DownstreamSync> list = this.list(query);
		//刷新人员信息
		if (CollectionUtils.isNotEmpty(list)){
			for (DownstreamSync downstreamSync : list) {
				String syncDept = downstreamSync.getSyncDept();
				List<Long> deptIds = Func.toLongList(syncDept);
				List<String> accounts = baseMapper.getUserAccount(deptIds);
				if (CollectionUtils.isNotEmpty(accounts)){
					downstreamSync.setSyncUser(Func.join(accounts,","));
				}
			}
		}
		this.updateBatchById(list);

	}


	@Override
	public void updateDown(Long majorDept) {
		if (majorDept != null){
			List<DownstreamSync> list = baseMapper.getDownByDeptId(majorDept);

			if (CollectionUtils.isNotEmpty(list)){
				for (DownstreamSync downstreamSync : list) {
					this.submit(downstreamSync);
				}
			}

		}



	}

	@Override
	public Boolean submit(DownstreamSync downstreamSync) {
		//一个应用只能添加一个部门和人员类型
		LambdaQueryWrapper<DownstreamSync> query = Wrappers.lambdaQuery(DownstreamSync.class).eq(DownstreamSync::getAppId, downstreamSync.getAppId()).eq(DownstreamSync::getIsDeleted, 0);
		List<DownstreamSync> list = this.list(query);
		if (CollectionUtils.isNotEmpty(list)){
			//新增的情况
			List<String> typeStr = list.stream().map(DownstreamSync::getDataType).collect(Collectors.toList());
			if (downstreamSync.getId() == null){//新增
				if (typeStr.contains(downstreamSync.getDataType())) {
					throw new ServiceException("每个应用只能有一个"+downstreamSync.getDataType()+"数据");
				}

			}else {//修改
				List<DownstreamSync> collect = list.stream().filter(f -> f.getDataType().equals(downstreamSync.getDataType())).collect(Collectors.toList());

				if (CollectionUtils.isNotEmpty(collect) && collect.get(0).getId().longValue() != downstreamSync.getId().longValue()) {
					throw new ServiceException("每个应用只能有一个"+downstreamSync.getDataType()+"数据");
				}

			}
			//修改的情况
		}

		if (Func.isNotEmpty(downstreamSync.getSyncUser())){
			downstreamSync.setSyncUser(Func.toStrList(downstreamSync.getSyncUser()).stream().distinct().collect(Collectors.joining(",")));

		}
		Boolean flag = false;
		if (Func.isEmpty(downstreamSync.getId())){ // 新增
			downstreamSync.setCreateUser(AuthUtil.getUserId());
			downstreamSync.setIsDeleted(0);
			downstreamSync.setCreateTime(LocalDateTime.now());
			downstreamSync.setUpdateUser(AuthUtil.getUserId());
			downstreamSync.setUpdateTime(LocalDateTime.now());
			flag = true;
		}else {//修改
			downstreamSync.setUpdateUser(AuthUtil.getUserId());
			downstreamSync.setUpdateTime(LocalDateTime.now());
		}
		if (downstreamSync.getDataType().equals("人员信息")){
			String syncDept = downstreamSync.getSyncDept();
			List<Long> deptIds = Func.toLongList(syncDept);
			List<String> accounts = baseMapper.getUserAccount(deptIds);
			if (CollectionUtils.isNotEmpty(accounts)){
				downstreamSync.setSyncUser(Func.join(accounts,","));
			}

		}
		boolean result  = this.saveOrUpdate(downstreamSync);
		if (result){
			String appName = baseMapper.getAppName(downstreamSync.getAppId());
			if (flag){
				SyncRecord syncRecord = new SyncRecord();
				syncRecord.setUpOrDown("下游同步");
				syncRecord.setAppName(appName);
				syncRecord.setDataType(downstreamSync.getDataType());
				syncRecord.setResult("新增1条数据");
				syncRecord.setCause("无异常");
				Long userId = AuthUtil.getUserId();
				syncRecord.setIsAutomatic(Func.isNotEmpty(userId) ? 0:1);
				syncRecord.setCreateTime(LocalDateTime.now());
				syncRecord.setCreateUser(userId);
				syncRecord.setDownstreamId(downstreamSync.getId());
				syncRecordMapper.insert(syncRecord);
			}else {
				SyncRecord syncRecord = new SyncRecord();
				syncRecord.setUpOrDown("下游同步");
				syncRecord.setAppName(appName);
				syncRecord.setDataType(downstreamSync.getDataType());
				syncRecord.setResult("修改1条数据");
				syncRecord.setCause("无异常");
				Long userId = AuthUtil.getUserId();
				syncRecord.setIsAutomatic(Func.isNotEmpty(userId) ? 0:1);
				syncRecord.setCreateTime(LocalDateTime.now());
				syncRecord.setCreateUser(userId);
				syncRecord.setDownstreamId(downstreamSync.getId());
				syncRecordMapper.insert(syncRecord);
			}
		}

		return result;
	}
}
