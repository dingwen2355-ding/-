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
package com.jslc.modules.szbh.mapper;

import com.jslc.modules.szbh.entity.AuthorityApplyFor;
import com.jslc.modules.szbh.vo.AuthorityApplyForIdsVO;
import com.jslc.modules.szbh.vo.AuthorityApplyForRestVO;
import com.jslc.modules.szbh.vo.AuthorityApplyForVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.szbh.vo.DeptUserVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限申请表 Mapper 接口
 *
 * @author BladeX
 * @since 2023-07-25
 */
public interface AuthorityApplyForMapper extends BaseMapper<AuthorityApplyFor> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param authorityApplyFor
	 * @return
	 */
	List<AuthorityApplyForVO> selectAuthorityApplyForPage(@Param("page") IPage page,
														  @Param("managementIds") List<Long> managementIds);

	AuthorityApplyForVO getDetail(@Param("id") Long id);

	List<AuthorityApplyForRestVO> authorityApplyForList(@Param("page") IPage page, @Param("applicant")String applicant);

    List<AuthorityApplyForVO> getUserApplyFor(@Param("page") IPage<AuthorityApplyForVO> page,
											  @Param("key")String key,
											  @Param("reviewStatus")Integer reviewStatus,
											  @Param("account")String account);


	List<AuthorityApplyForVO> getApplicationApplyForRanking(@Param("start") LocalDateTime start,@Param("end") LocalDateTime end);


	List<AuthorityApplyForVO> getApplyForStatistics(@Param("start") LocalDateTime start,@Param("end") LocalDateTime end);


	DeptUserVO getUserInfo(@Param("userAccount") String userAccount);



}
