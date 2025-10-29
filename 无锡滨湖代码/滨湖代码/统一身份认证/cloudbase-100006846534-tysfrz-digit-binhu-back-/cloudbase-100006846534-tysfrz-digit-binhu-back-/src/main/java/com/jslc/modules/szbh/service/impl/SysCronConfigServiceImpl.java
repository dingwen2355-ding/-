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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.common.utils.CommonUtil;
import com.jslc.modules.job.entiry.ScheduleJobEntity;
import com.jslc.modules.job.service.ScheduleJobService;
import com.jslc.modules.szbh.entity.BhUserSync;
import com.jslc.modules.szbh.entity.SyncRecord;
import com.jslc.modules.szbh.entity.SysCronConfig;
import com.jslc.modules.szbh.service.IBhUserSyncService;
import com.jslc.modules.szbh.service.ISyncRecordService;
import com.jslc.modules.szbh.vo.SyncRecordVO;
import com.jslc.modules.szbh.vo.SysCronConfigVO;
import com.jslc.modules.szbh.mapper.SysCronConfigMapper;
import com.jslc.modules.szbh.service.ISysCronConfigService;
import com.jslc.modules.szbh.vo.UpSynVO;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  服务实现类
 *
 * @author BladeX
 * @since 2023-05-15
 */
@Service
public class SysCronConfigServiceImpl extends ServiceImpl<SysCronConfigMapper, SysCronConfig> implements ISysCronConfigService {

	@Autowired
	private ISyncRecordService iSyncRecordService;
	@Autowired
	private IBhUserSyncService iBhUserSyncService;
	@Autowired
	private ScheduleJobService scheduleJobService;


	@Override
	public IPage<SysCronConfigVO> selectSysCronConfigPage(IPage<SysCronConfigVO> page, SysCronConfigVO sysCronConfig) {
		return page.setRecords(baseMapper.selectSysCronConfigPage(page, sysCronConfig));
	}

	@Override
	public Boolean submit(SysCronConfigVO sysCronConfigVO) {
		SysCronConfig sysCronConfig = new SysCronConfig();
		BeanUtil.copy(sysCronConfigVO,sysCronConfig);
		if (sysCronConfig.getSynIds() != null && sysCronConfig.getSynIds().length() > 0){
			sysCronConfig.setSynIds(Func.toStrList(sysCronConfig.getSynIds()).stream().distinct().collect(Collectors.joining(",")));
		}
		String cron = "0 0 0/time * * ?";
		if (sysCronConfigVO.getTime() != null){
			cron = cron.replaceAll("time", sysCronConfigVO.getTime().toString());
			sysCronConfig.setCron(cron);

		}
		if (sysCronConfig.getType() != null && sysCronConfig.getType() == 1){
			sysCronConfig.setDesc("人员表自动同步");
		}
		if (sysCronConfig.getType() != null && sysCronConfig.getType() == 2){
			sysCronConfig.setDesc("部门表自动同步");
		}

		boolean result = this.saveOrUpdate(sysCronConfig);

		//保存定时任务表
		if(result){

			Long id = sysCronConfig.getId();

//			String cronExpression = CommonUtil.getCronExpression(sysCronConfig.getRegularTime());
			JSONObject json = new JSONObject();
//			json.put("synId",sysCronConfig.getSynIds());
			json.put("bizId",id);

			ScheduleJobEntity scheduleJob = new ScheduleJobEntity();
			if (sysCronConfig.getType() != null && sysCronConfig.getType() == 1){
				scheduleJob.setBeanName("syncUserTask");
			}else if (sysCronConfig.getType() != null && sysCronConfig.getType() == 2){
				scheduleJob.setBeanName("syncDeptTask");
			}

			scheduleJob.setParams(json.toJSONString());
			scheduleJob.setCronExpression(sysCronConfig.getCron());
			scheduleJob.setBizId(id);
			if (sysCronConfig.getIsEnable() != null && sysCronConfig.getIsEnable() == 1){
				scheduleJob.setStatus(0);
			}else if (sysCronConfig.getIsEnable() != null && sysCronConfig.getIsEnable() == 0){
				scheduleJob.setStatus(1);
			}


			ScheduleJobEntity scheduleJobEntity = scheduleJobService.getOne(Wrappers.<ScheduleJobEntity>query().lambda().eq(ScheduleJobEntity::getBizId, id));
			if(scheduleJobEntity!=null && scheduleJobEntity.getJobId()!=null){
				scheduleJob.setJobId(scheduleJobEntity.getJobId());
				scheduleJob.setCreateTime(scheduleJobEntity.getCreateTime());
				scheduleJobService.update(scheduleJob);
			}else{
				scheduleJobService.saveJob(scheduleJob);
			}
		}
		return result;

	}


	@Override
	public SysCronConfigVO getDetail(Integer type) {
		SysCronConfigVO sysCronConfigVO = new SysCronConfigVO();

		LambdaQueryWrapper<SysCronConfig> query = Wrappers.lambdaQuery(SysCronConfig.class).eq(SysCronConfig::getType, type);
		SysCronConfig one = this.getOne(query);
		if (Func.isNotEmpty(one)){
			BeanUtil.copy(one,sysCronConfigVO);
			if (one.getCron() != null){
				//截取小时
				String str = one.getCron().substring(one.getCron().indexOf("/") + 1);
				String time = str.substring(0, str.indexOf(" "));
				sysCronConfigVO.setTime(Integer.valueOf(time));
			}

//			if (one.getType() == 1 && one.getSynIds() != null && one.getSynIds().length() >0 ){
//				LambdaQueryWrapper<BhUserSync> queryUser = Wrappers.lambdaQuery(BhUserSync.class).in(BhUserSync::getUserid, Func.toStrList(one.getSynIds()));
//				List<BhUserSync> userSyn = iBhUserSyncService.list(queryUser);
//				if (CollectionUtils.isNotEmpty(userSyn)){
//					String join = Func.join(userSyn.stream().map(BhUserSync::getMajorDepartment).distinct().collect(Collectors.toList()), ",");
//					sysCronConfigVO.setDeptIds(join);
//				}
//			}
		}
		return sysCronConfigVO;
	}


	@Override
	public IPage<UpSynVO> getUpSyn(IPage<UpSynVO> page,String key) {
		LambdaQueryWrapper<SysCronConfig> query = Wrappers.lambdaQuery(SysCronConfig.class).eq(SysCronConfig::getType, 1);
		SysCronConfig userConfig = this.getOne(query);

		LambdaQueryWrapper<SysCronConfig> query2 = Wrappers.lambdaQuery(SysCronConfig.class).eq(SysCronConfig::getType, 2);
		SysCronConfig deptConfig = this.getOne(query2);

		String str = null;
		if (Func.isNotEmpty(userConfig)){
			str = userConfig.getIsEnable() == 1 ? "启用" : "关闭";
		}

		String str2 = null;
		if (Func.isNotEmpty(deptConfig)){
			str2 = deptConfig.getIsEnable() == 1 ? "启用" : "关闭";
		}else {
			str2 = "关闭";
		}
		LambdaQueryWrapper<SyncRecord> query1 = Wrappers.lambdaQuery(SyncRecord.class).eq(SyncRecord::getDataType, "人员信息");
		LambdaQueryWrapper<SyncRecord> queryWrapper = Wrappers.lambdaQuery(SyncRecord.class).eq(SyncRecord::getDataType, "部门信息");
		List<SyncRecord> list1 = iSyncRecordService.list(query1);
		List<SyncRecord> list2 = iSyncRecordService.list(queryWrapper);

		List<UpSynVO> result = new ArrayList<>();
		UpSynVO upSynVO1 = new UpSynVO();
		UpSynVO upSynVO2 = new UpSynVO();
		result.add(upSynVO1);
		result.add(upSynVO2);
		upSynVO1.setDataSource("政务微信");
		upSynVO1.setDataType(1);
		upSynVO1.setDataTypeName("人员信息");
		upSynVO1.setIsEnableName(str);
		upSynVO1.setIsSynName(CollectionUtils.isEmpty(list1) ? "未同步" : "已同步");
		if (CollectionUtils.isNotEmpty(list1)){
			upSynVO1.setCreateTime(list1.stream().filter(f->f.getCreateTime() != null).max(Comparator.comparing(SyncRecord::getCreateTime)).get().getCreateTime());
		}


		upSynVO2.setDataSource("政务微信");
		upSynVO2.setDataType(2);
		upSynVO2.setDataTypeName("部门信息");
		upSynVO2.setIsEnableName(str2);
		upSynVO2.setIsSynName(CollectionUtils.isEmpty(list2) ? "未同步" : "已同步");
		if (CollectionUtils.isNotEmpty(list2)){
			upSynVO2.setCreateTime(list2.stream().filter(f->f.getCreateTime() != null).max(Comparator.comparing(SyncRecord::getCreateTime)).get().getCreateTime());
		}
		if (CollectionUtils.isNotEmpty(result) && Func.isNotEmpty(key)){
			result = result.stream().filter(f->f.getDataSource().contains(key)).collect(Collectors.toList());
		}
		IPage iPage = CommonUtil.manualPage(page, result);
		return iPage;
	}
}
