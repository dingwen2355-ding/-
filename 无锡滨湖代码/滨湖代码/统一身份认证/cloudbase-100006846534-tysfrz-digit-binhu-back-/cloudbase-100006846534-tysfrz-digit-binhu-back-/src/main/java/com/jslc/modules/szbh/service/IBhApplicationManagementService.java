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
import com.jslc.modules.szbh.entity.BhApplicationManagement;
import com.jslc.modules.szbh.entity.BhDept;
import com.jslc.modules.szbh.vo.ApplicaitonManagementCountVO;
import com.jslc.modules.szbh.vo.BhApplicationManagementVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 应用管理 服务类
 *
 * @author BladeX
 * @since 2023-05-09
 */
public interface IBhApplicationManagementService extends IService<BhApplicationManagement> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bhApplicationManagement
	 * @return
	 */
	IPage<BhApplicationManagementVO> selectBhApplicationManagementPage(IPage<BhApplicationManagementVO> page, String name,String appCategory,Integer isGround, Integer isRecommend );

	Boolean changeEnable(Long id);

	Boolean del(List<Long> toLongList);

	Boolean submit(BhApplicationManagementVO bhApplicationManagement);


	BhApplicationManagementVO getDetail(Long id);


    String getSecret();

    List<BhApplicationManagement> getApplication(String applicationId);

	List<BhApplicationManagement> getList(String key);


	Boolean changeGround(Long id);


    List<BhApplicationManagementVO> getUserApp(String name);


	List<BhDept> getUserDept();

	IPage<BhApplicationManagementVO> applicationByClassificationAndDept(IPage<BhApplicationManagementVO> page,Long applicationClassificationId, Long deptId, Integer isAuthorization,String appName);


	List<BhApplicationManagementVO> getUserAppById(Long userId);

	List<String> getUserAppId(String account);


	List<BhApplicationManagementVO> getAppSumData(LocalDateTime start, LocalDateTime end);

	Long submitV1(BhApplicationManagementVO bhApplicationManagement);
}
