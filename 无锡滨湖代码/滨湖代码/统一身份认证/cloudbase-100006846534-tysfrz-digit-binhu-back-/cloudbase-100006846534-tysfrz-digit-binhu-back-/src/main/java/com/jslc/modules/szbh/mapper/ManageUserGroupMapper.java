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

import com.jslc.modules.szbh.entity.ManageUserGroup;
import com.jslc.modules.szbh.vo.GroupUserInfoVO;
import com.jslc.modules.szbh.vo.ManageUserGroupVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管理用户组 Mapper 接口
 *
 * @author BladeX
 * @since 2023-06-02
 */
public interface ManageUserGroupMapper extends BaseMapper<ManageUserGroup> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param manageUserGroup
	 * @return
	 */
	List<ManageUserGroupVO> selectManageUserGroupPage(@Param("page") IPage page,
													  @Param("key") String key);

	List<GroupUserInfoVO> getGroupUserData(@Param("page") IPage<GroupUserInfoVO> page,
										   @Param("id")Long id,
										   @Param("userName")String userName);


}
