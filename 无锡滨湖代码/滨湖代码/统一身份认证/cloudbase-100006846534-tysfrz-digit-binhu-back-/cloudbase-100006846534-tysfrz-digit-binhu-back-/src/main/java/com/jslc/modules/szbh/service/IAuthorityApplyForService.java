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
import com.jslc.modules.szbh.entity.AuthorityApplyFor;
import com.jslc.modules.szbh.vo.*;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 权限申请表 服务类
 *
 * @author BladeX
 * @since 2023-07-25
 */
public interface IAuthorityApplyForService extends IService<AuthorityApplyFor> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param authorityApplyFor
	 * @return
	 */
	IPage<AuthorityApplyForVO> selectAuthorityApplyForPage(IPage<AuthorityApplyForVO> page );

	Boolean submit(AuthorityApplyFor authorityApplyFor);


	AuthorityApplyForVO getDetail(Long id);

	IPage<AuthorityApplyForRestVO> authorityApplyForList(IPage<AuthorityApplyForRestVO> page, String applicant );

    IPage<AuthorityApplyForVO> getUserApplyFor(IPage<AuthorityApplyForVO> page, String key, Integer reviewStatus);


	Boolean submitList(AuthorityApplyForIdsVO authorityApplyFor);


    List<HeadPageVO> getApplicationApplyForRanking();


	HeadPageDSQVO getHomePageAnalysis();


    List<ApplyForStatisticsDSQVO> getApplyForStatistics();


	DeptUserVO getUserInfo();

	EchartsDSQVO getApplyForEcharts();


	List<NVMapVO> getDeptRank();


	String getToken();

}
