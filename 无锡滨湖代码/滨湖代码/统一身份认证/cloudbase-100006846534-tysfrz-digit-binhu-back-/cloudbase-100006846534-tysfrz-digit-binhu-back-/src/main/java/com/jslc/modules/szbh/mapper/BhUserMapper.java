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

import com.jslc.modules.szbh.entity.BhUser;
import com.jslc.modules.szbh.vo.BhUserVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 用户管理 Mapper 接口
 *
 * @author BladeX
 * @since 2023-05-09
 */
public interface BhUserMapper extends BaseMapper<BhUser> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bhUser
	 * @return
	 */
	List<BhUserVO> selectBhUserPage(IPage page, BhUserVO bhUser);


	List<BhUserVO> getBhUserList(@Param("page") IPage<BhUserVO> page,
								 @Param("bhDeptIds")List<Long> bhDeptIds,
								 @Param("code")String code,
								 @Param("isEnable")Integer isEnable,
								 @Param("isOnline")Integer isOnline,
								 @Param("isAuthorized")Integer isAuthorized,
								 @Param("startTime")LocalDateTime startTime,
								 @Param("endTime")LocalDateTime endTime,
								 @Param("ids")List<Long> ids);

    List<BhUser> getUserGroupData(List<Long> ids);

	List<BhUser> getUserListBySyncUserAndField(@Param("syncUsers") List<String> syncUsers,@Param("syncFields") String syncFields);


	List<BhUser> getUserS(@Param("ids") List<Long> deptSynIds);

    String getRole(Long userId);

	BhUserVO getUserDetail(String account);

	List<BhUser> getUserList(@Param("bhDeptIds") List<Long> bhDeptIds);


    List<String> getLoginUserList();


    Integer getAdminRole(@Param("roleId") String roleId);



}
