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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.modules.szbh.entity.SecuritySetting;
import com.jslc.modules.szbh.entity.SecurityUserRecord;
import com.jslc.modules.szbh.service.ISecurityUserRecordService;
import com.jslc.modules.szbh.vo.SecuritySettingVO;
import com.jslc.modules.szbh.mapper.SecuritySettingMapper;
import com.jslc.modules.szbh.service.ISecuritySettingService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 安全设置 服务实现类
 *
 * @author BladeX
 * @since 2023-06-01
 */
@Service
public class SecuritySettingServiceImpl extends ServiceImpl<SecuritySettingMapper, SecuritySetting> implements ISecuritySettingService {

	@Lazy
	@Autowired
	private ISecurityUserRecordService iSecurityUserRecordService;

	@Override
	public SecuritySetting getDetail() {
		SecuritySetting securitySetting = new SecuritySetting();
		List<SecuritySetting> list = this.list();
		if (CollectionUtils.isNotEmpty(list)){
			securitySetting = list.get(0);
		}
		return securitySetting;
	}

	@Override
	public IPage<SecuritySettingVO> selectSecuritySettingPage(IPage<SecuritySettingVO> page, SecuritySettingVO securitySetting) {
		return page.setRecords(baseMapper.selectSecuritySettingPage(page, securitySetting));
	}


	@Override
	public Boolean submit(SecuritySetting securitySetting) {
		Boolean result = this.saveOrUpdate(securitySetting);
		if (result){
			//设置成功后清除用户记录表
			List<SecurityUserRecord> list = iSecurityUserRecordService.list();
			if (CollectionUtils.isNotEmpty(list)){
				List<Long> collect = list.stream().map(SecurityUserRecord::getId).collect(Collectors.toList());
				iSecurityUserRecordService.removeBatchByIds(collect);
			}
		}
		return result;
	}
}
