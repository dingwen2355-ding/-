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

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.modules.szbh.entity.BhEnt;
import com.jslc.modules.szbh.vo.BhEntVO;
import com.jslc.modules.szbh.mapper.BhEntMapper;
import com.jslc.modules.szbh.service.IBhEntService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 企业表 服务实现类
 *
 * @author BladeX
 * @since 2023-06-06
 */
@Service
public class BhEntServiceImpl extends ServiceImpl<BhEntMapper, BhEnt> implements IBhEntService {

	@Override
	public BhEntVO getDetail(Long id) {
		BhEntVO detail= baseMapper.getDetail(id);
		return detail;
	}


	@Override
	public IPage<BhEntVO> selectBhEntPage(IPage<BhEntVO> page, String key) {
		List<BhEntVO> list = baseMapper.selectBhEntPage(page, key);
		return page.setRecords(list);
	}


	@Override
	public List<BhEntVO> getList(String entName) {
		List<BhEntVO> list = baseMapper.selectBhEntPage(null, entName);
		return list;
	}

	@Override
	public Boolean dels(List<Long> ids) {
		LambdaUpdateWrapper<BhEnt> set = Wrappers.lambdaUpdate(BhEnt.class).in(BhEnt::getId, ids).set(BhEnt::getIsDeleted, 1);
		return this.update(set);
	}
}
