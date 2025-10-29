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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.modules.szbh.entity.IpAbnormalLog;
import com.jslc.modules.szbh.vo.IpAbnormalLogVO;
import com.jslc.modules.szbh.mapper.IpAbnormalLogMapper;
import com.jslc.modules.szbh.service.IIpAbnormalLogService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * ip相同动作异常记录表 服务实现类
 *
 * @author BladeX
 * @since 2023-10-09
 */
@Service
public class IpAbnormalLogServiceImpl extends ServiceImpl<IpAbnormalLogMapper, IpAbnormalLog> implements IIpAbnormalLogService {

	@Override
	public IPage<IpAbnormalLogVO> selectIpAbnormalLogPage(IPage<IpAbnormalLogVO> page, IpAbnormalLogVO ipAbnormalLog) {
		return page.setRecords(baseMapper.selectIpAbnormalLogPage(page, ipAbnormalLog));
	}

	@Override
	public boolean submit(IpAbnormalLog ipAbnormalLog) {
		return this.saveOrUpdate(ipAbnormalLog);
	}
}
