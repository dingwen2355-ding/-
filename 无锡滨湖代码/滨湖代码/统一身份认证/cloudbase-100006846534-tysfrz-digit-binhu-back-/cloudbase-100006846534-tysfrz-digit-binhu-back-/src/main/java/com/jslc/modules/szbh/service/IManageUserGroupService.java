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
import com.jslc.modules.szbh.entity.ManageUser;
import com.jslc.modules.szbh.entity.ManageUserGroup;
import com.jslc.modules.szbh.vo.BhUserVO;
import com.jslc.modules.szbh.vo.GroupUserInfoVO;
import com.jslc.modules.szbh.vo.ManageUserGroupVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 管理用户组 服务类
 *
 * @author BladeX
 * @since 2023-06-02
 */
public interface IManageUserGroupService extends IService<ManageUserGroup> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param manageUserGroup
	 * @return
	 */
	IPage<ManageUserGroupVO> selectManageUserGroupPage(IPage<ManageUserGroupVO> page, String key);

	Boolean submit(ManageUserGroup manageUserGroup);

	Boolean dels(List<Long> ids);

	ManageUserGroup getDetail(Long id);


	IPage<GroupUserInfoVO> getGroupUser(IPage<GroupUserInfoVO> page, Long id, String userName);

	List<GroupUserInfoVO> getGroupUserList(Long id);

	Boolean delUsers(Long manageId,List<Long> toLongList);

	void downLoad(HttpServletResponse response, String key,String ids);

    List<ManageUserGroup> getUserGroup(String bizId);

	List<ManageUserGroup> getList(String key);


    List<GroupUserInfoVO> getUserList(Long id, String userName);



}
