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
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.modules.szbh.entity.SecuritySetting;
import com.jslc.modules.szbh.entity.SecurityUserRecord;
import com.jslc.modules.szbh.service.ISecuritySettingService;
import com.jslc.modules.szbh.vo.SecurityUserRecordVO;
import com.jslc.modules.szbh.mapper.SecurityUserRecordMapper;
import com.jslc.modules.szbh.service.ISecurityUserRecordService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * 安全设置记录表 服务实现类
 *
 * @author BladeX
 * @since 2023-06-01
 */
@Service
public class SecurityUserRecordServiceImpl extends ServiceImpl<SecurityUserRecordMapper, SecurityUserRecord> implements ISecurityUserRecordService {

	@Lazy
	@Autowired
	private ISecuritySettingService iSecuritySettingService;

	@Override
	public SecurityUserRecordVO getAccountDetail(String username) {
		SecurityUserRecordVO result = new SecurityUserRecordVO();
		if (Func.isNotEmpty(username)){
			List<SecuritySetting> list = iSecuritySettingService.list();
			SecuritySetting securitySetting = new SecuritySetting();
			//如果该用户是第一次登录创建一条 安全设置记录表数据
			LambdaQueryWrapper<SecurityUserRecord> queryUserRecord = Wrappers.lambdaQuery(SecurityUserRecord.class).eq(SecurityUserRecord::getAccount, username);
			SecurityUserRecord one = this.getOne(queryUserRecord);
			if (CollectionUtils.isNotEmpty(list)){

				if (one == null){
					securitySetting = list.get(0);
					SecurityUserRecord securityUserRecord = new SecurityUserRecord();
					securityUserRecord.setAccount(username);
					securityUserRecord.setSecretErrorResidueTime(securitySetting.getSecretErrorLockTime());
					securityUserRecord.setSecretUnlockTime(LocalDateTime.now());
					securityUserRecord.setSecretLastTryTime(LocalDateTime.now()); //下次密码尝试时间
					securityUserRecord.setSecretErrorResidueTimeCaptcha(securitySetting.getSercetErrorTimeCaptcha());
					this.save(securityUserRecord);
				}

			}
			LambdaQueryWrapper<SecurityUserRecord> query = Wrappers.lambdaQuery(SecurityUserRecord.class).eq(SecurityUserRecord::getAccount, username);
			SecurityUserRecord securityUserRecord = this.getOne(query);
			BeanUtil.copy(securityUserRecord,result);
		}
		if (Func.isNotEmpty(result)){
			long set = result.getCaptchaLastTryTime().toInstant(ZoneOffset.of("+8")).getEpochSecond();
			long time = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).getEpochSecond();
			if (set > time){
				result.setTime(set-time);
			}else {
				result.setTime(0L);
			}
		}

		return result;
	}

	@Override
	public IPage<SecurityUserRecordVO> selectSecurityUserRecordPage(IPage<SecurityUserRecordVO> page, SecurityUserRecordVO securityUserRecord) {
		return page.setRecords(baseMapper.selectSecurityUserRecordPage(page, securityUserRecord));
	}

}
