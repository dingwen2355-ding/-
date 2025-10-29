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
package com.jslc.modules.szbh.controller;

import com.jslc.modules.szbh.entity.BhUser;
import com.jslc.modules.szbh.vo.BhUserVO;
import com.jslc.modules.szbh.vo.GroupUserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import com.jslc.common.annotation.LcSysLog;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.szbh.entity.ManageUserGroup;
import com.jslc.modules.szbh.vo.ManageUserGroupVO;
import com.jslc.modules.szbh.service.IManageUserGroupService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;

/**
 * 管理用户组 控制器
 *
 * @author BladeX
 * @since 2023-06-02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/szbh-back/manageusergroup")
@Api(value = "管理用户组", tags = "管理用户组接口")
public class ManageUserGroupController extends BladeController {

	private final IManageUserGroupService manageUserGroupService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "管理用户组详情", notes = "管理用户组详情")
	public R<ManageUserGroup> detail(Long id) {
		ManageUserGroup detail = manageUserGroupService.getDetail(id);
		return R.data(detail);
	}

	/**
	 * 获取 用户组已添加人员 分页
	 */
	@GetMapping("/getGroupUserPage")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "管理用户组获取 用户组已添加人员 分页", notes = "管理用户组获取 用户组已添加人员 分页")
	public R<IPage<GroupUserInfoVO>> getGroupUserPage(Long id,String userName,Query query) {
		IPage<GroupUserInfoVO> userList = manageUserGroupService.getGroupUser(Condition.getPage(query),id, userName);
		return R.data(userList);
	}
	@GetMapping("/getUserList")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "管理用户组获取 用户组已添加人员 分页", notes = "管理用户组获取 用户组已添加人员 分页")
	public R getUserList(Long id) {
		List<GroupUserInfoVO> userList = manageUserGroupService.getUserList(id,null);
		return R.data(userList);
	}

	/**
	 * 获取 用户组已添加人员 不分页
	 */
	@GetMapping("/getGroupUserList")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取 用户组已添加人员 不分页", notes = "获取 用户组已添加人员 不分页")
	public R<List<GroupUserInfoVO>> getGroupUserList(Long id) {
		List<GroupUserInfoVO> userList = manageUserGroupService.getGroupUserList(id);
		return R.data(userList);
	}



	/**
	 * 自定义分页 管理用户组
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = " 管理用户组分页", notes = "管理用户组分页")
	public R<IPage<ManageUserGroupVO>> page(String name, Query query) {
		IPage<ManageUserGroupVO> pages = manageUserGroupService.selectManageUserGroupPage(Condition.getPage(query), name);
		return R.data(pages);
	}


	/**
	 *  管理用户组不分页
	 */
	@GetMapping("/getList")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "管理用户组不分页 ", notes = "管理用户组不分页")
	public R<List<ManageUserGroup>> getList(String key) {
		List<ManageUserGroup> pages = manageUserGroupService.getList(key);
		return R.data(pages);
	}

	/**
	 * 删除 管理用户组 管理的用户
	 */
	@PostMapping("/removeUser")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除 管理用户组 管理的用户", notes = "删除 管理用户组 管理的用户")
	@LcSysLog("删除管理用户组管理的用户")
	public R removeUser(@ApiParam(value = "主键集合", required = true) Long manageId,@RequestParam String ids) {
		return R.status(manageUserGroupService.delUsers(manageId,Func.toLongList(ids)));
	}



	/**
	 * 新增或修改 管理用户组
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "管理用户组新增或修改", notes = "管理用户组新增或修改")
	@LcSysLog("管理用户组新增或修改")
	public R submit(@Valid @RequestBody ManageUserGroup manageUserGroup) {
		return R.status(manageUserGroupService.submit(manageUserGroup));
	}

	
	/**
	 * 删除 管理用户组
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除 管理用户组", notes = "删除 管理用户组")
	@LcSysLog("删除管理用户组")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(manageUserGroupService.dels(Func.toLongList(ids)));
	}




	/**
	 *  管理用户组
	 */
	@GetMapping("/downLoad")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "管理用户组 导出", notes = "管理用户组 导出")
	@LcSysLog("管理用户组导出")
	public void passCheck(HttpServletResponse response , String name,String ids) {
		manageUserGroupService.downLoad( response, name,ids);

	}
	
}
