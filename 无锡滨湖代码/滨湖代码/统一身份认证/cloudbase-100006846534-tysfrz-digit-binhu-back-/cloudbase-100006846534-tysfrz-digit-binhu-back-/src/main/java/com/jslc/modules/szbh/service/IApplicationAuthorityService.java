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
import com.jslc.modules.szbh.entity.ApplicationAuthority;
import com.jslc.modules.szbh.vo.ApplicationAuthorityVO;
import com.jslc.modules.szbh.vo.UserAppVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 应用授权 服务类
 *
 * @author BladeX
 * @since 2023-06-07
 */
public interface IApplicationAuthorityService extends IService<ApplicationAuthority> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param applicationAuthority
	 * @return
	 */
	IPage<ApplicationAuthorityVO> selectApplicationAuthorityPage(IPage<ApplicationAuthorityVO> page, String appNames,String deptNames,Integer type);

	Boolean submit(ApplicationAuthority applicationAuthority);

	Boolean submits(List<ApplicationAuthority> applicationAuthoritys);


	Boolean dels(List<Long> ids);

	IPage<UserAppVO> getUserAppPage(IPage<UserAppVO> page, String account,String name, String deptIds, String appIds);


    void downLoad(HttpServletResponse response, String account,String name, String deptIds, String appIds);


	Boolean submitApplyFor(List<ApplicationAuthority> applicationAuthority);


	List<ApplicationAuthority> getAuthorityByType(Long majorDept, Integer type);


}
