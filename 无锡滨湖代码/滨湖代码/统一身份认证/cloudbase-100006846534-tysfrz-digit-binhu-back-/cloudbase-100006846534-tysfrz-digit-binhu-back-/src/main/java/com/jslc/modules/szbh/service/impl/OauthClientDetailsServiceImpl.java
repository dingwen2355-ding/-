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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.modules.szbh.entity.OauthClientDetails;
import com.jslc.modules.szbh.vo.OauthClientDetailsVO;
import com.jslc.modules.szbh.mapper.OauthClientDetailsMapper;
import com.jslc.modules.szbh.service.IOauthClientDetailsService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *  服务实现类
 *
 * @author BladeX
 * @since 2023-05-16
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {

	@Override
	public IPage<OauthClientDetailsVO> selectOauthClientDetailsPage(IPage<OauthClientDetailsVO> page, OauthClientDetailsVO oauthClientDetails) {
		return page.setRecords(baseMapper.selectOauthClientDetailsPage(page, oauthClientDetails));
	}

	@Override
	public Boolean submit(OauthClientDetails oauthClientDetails) {
		LambdaQueryWrapper<OauthClientDetails> query = Wrappers.lambdaQuery(OauthClientDetails.class).eq(OauthClientDetails::getClientId, oauthClientDetails.getClientId());
		OauthClientDetails one = this.getOne(query);
		if (Func.isEmpty(one)){
			return this.save(oauthClientDetails);
		}else {
			LambdaUpdateWrapper<OauthClientDetails> set = Wrappers.lambdaUpdate(OauthClientDetails.class).eq(OauthClientDetails::getClientId, oauthClientDetails.getClientId()).set(OauthClientDetails::getClientSecret, oauthClientDetails.getClientSecret())
					.set(OauthClientDetails::getWebServerRedirectUri, oauthClientDetails.getWebServerRedirectUri());
			return this.update(set);
		}
	}

	@Override
	public void delAll(List<String> clientIds) {
		baseMapper.del(clientIds);
	}
}
