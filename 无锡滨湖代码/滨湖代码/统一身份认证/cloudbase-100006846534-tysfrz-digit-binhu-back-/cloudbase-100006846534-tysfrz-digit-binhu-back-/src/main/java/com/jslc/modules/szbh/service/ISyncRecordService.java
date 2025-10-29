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
package com.jslc.modules.szbh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jslc.modules.szbh.entity.SyncRecord;
import com.jslc.modules.szbh.vo.SyncRecordVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;

/**
 * 同步记录表 服务类
 *
 * @author BladeX
 * @since 2023-06-06
 */
public interface ISyncRecordService extends IService<SyncRecord> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param syncRecord
	 * @return
	 */
	IPage<SyncRecordVO> selectSyncRecordPage(IPage<SyncRecordVO> page, String key,String start ,String end,String type);

	IPage<SyncRecordVO> getDownpage(IPage<SyncRecordVO> page, String key,Long id);


	void downLoad(HttpServletResponse response, String key, String start, String end, String type, String ids);


}
