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

import com.jslc.common.constant.CommonConstant;
import com.jslc.modules.system.excel.UserExcel;
import com.jslc.modules.szbh.entity.BhUserSync;
import com.jslc.modules.szbh.vo.PassWordBitVO;
import com.jslc.modules.szbh.vo.ResultVO;
import io.swagger.annotations.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import oracle.jdbc.proxy.annotation.Post;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import com.jslc.common.annotation.LcSysLog;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.szbh.entity.BhUser;
import com.jslc.modules.szbh.vo.BhUserVO;
import com.jslc.modules.szbh.service.IBhUserService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 用户管理 控制器
 *
 * @author BladeX
 * @since 2023-05-09
 */
@RestController
@AllArgsConstructor
@RequestMapping(CommonConstant.SQUIRREL_SZBH + "/bhuser")
@Api(value = "用户管理", tags = "用户管理接口")
public class BhUserController extends BladeController {

	private final IBhUserService bhUserService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查看用户详情", notes = "查看用户详情")
//	@LcSysLog("查看用户详情")
	public R<BhUserVO> detail(Long id) {
		BhUserVO detail = bhUserService.getDetail(id);
		return R.data(detail);
	}



	/**
	 * 自定义分页 用户管理
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "查看用户列表分页", notes = "查看用户列表分页")
//	@LcSysLog("查看用户列表")
	public R<IPage<BhUserVO>> page(Long bhDeptId,String  code ,Integer isEnable,Integer isOnline,Integer isAuthorized ,String start,String end, Query query) {
		IPage<BhUserVO> pages = bhUserService.selectBhUserPage(Condition.getPage(query),bhDeptId,  code , isEnable, isOnline, isAuthorized , start, end);
		return R.data(pages);
	}



	/**
	 *  用户管理 列表
	 */
	@GetMapping("/getList")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = " 查看用户列表 不分页", notes = " 查看用户列表 不分页")
//	@LcSysLog("查看用户列表")
	public R<List<BhUser>> getList(String key) {
		List<BhUser> pages = bhUserService.getList(key);
		return R.data(pages);
	}


	/**
	 * 新增或修改 用户管理
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "用户新增或修改", notes = "用户新增或修改")
	@LcSysLog("用户新增或修改")
	public R submit(@Valid @RequestBody BhUser bhUser) {
		return R.status(bhUserService.submit(bhUser));
	}

	/**
	 * 新增或修改 用户管理
	 */
	@PostMapping("/submitList")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "用户新增或修改", notes = "用户新增或修改")
	@LcSysLog("用户新增或修改")
	public R submit(@Valid @RequestBody List<BhUser> bhUser) {
		return R.status(bhUserService.submitList(bhUser));
	}

	/**
	 * 新增或修改 用户管理
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改手机号", notes = "修改手机号")
	@LcSysLog("修改手机号")
	public R update(@Valid @RequestBody BhUser bhUser) {
		return R.status(bhUserService.updatePhone(bhUser));
	}
	
	/**
	 * 删除 用户管理
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "用户删除", notes = "用户删除")
	@LcSysLog("用户删除")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(bhUserService.del(Func.toLongList(ids)));
	}

	/**
	 * 停用或启用 用户管理
	 */
	@PostMapping("/changeEnable")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "用户停用或启用", notes = "用户停用或启用")
	@LcSysLog("用户停用或启用")
	public R changeEnable(@Valid @RequestParam Long id ) {
		return R.status(bhUserService.changeEnable(id));
	}

	/**
	 * 用户管理 踢下线
	 */
	@PostMapping("/changeOnline")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "用户踢下线", notes = "用户踢下线")
	@LcSysLog("用户踢下线")
	public R changeOnline(@Valid @RequestParam String ids ) {
		return R.status(bhUserService.changeOnline(ids));
	}


	/**
	 * 用户管理 重置密码
	 */
	@PostMapping("/changePassword")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "用户重置密码", notes = "用户重置密码")
	@LcSysLog("用户重置密码")
	public R changePassword(@Valid @RequestParam String ids ) {
		return R.status(bhUserService.changePassword(ids));
	}


	/**
	 *  重置所有的密码
	 */
	@PostMapping("/changeAllPassword")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "重置所有的密码", notes = "重置所有的密码")
	public R changeAllPassword() {
		return R.status(bhUserService.changeAllPassword());
	}

	@GetMapping("/downLoad")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "用户人员信息导出", notes = "用户人员信息导出")
	@LcSysLog("用户人员信息导出")
	public void downLoadRanking(HttpServletResponse response,Long bhDeptId,String  code ,String isEnable,String isOnline,String isAuthorized ,String start,String end,String ids) {
		Integer isEnable2 = null;
		Integer isOnline2= null;
		Integer isAuthorized2= null;
		String code2= null;
		String start2= null;
		String end2= null;
		List<Long> id = null;
		if (Func.isNotEmpty(isOnline) && !isEnable.equals("undefined")){
			isEnable2 = Integer.valueOf(isEnable);
		}
		if (Func.isNotEmpty(isOnline) && !isOnline.equals("undefined")){
			isOnline2 = Integer.valueOf(isEnable);
		}
		if (Func.isNotEmpty(isAuthorized) && !isAuthorized.equals("undefined")){
			isAuthorized2 = Integer.valueOf(isEnable);
		}
		if (Func.isNotEmpty(code) && code.equals("undefined")){
			code2 = null;
		}
		if (Func.isNotEmpty(start) && start.equals("undefined")){
			start2 = null;
		}
		if (Func.isNotEmpty(end) && end.equals("undefined")){
			end2 = null;
		}

		if (Func.isNotEmpty(ids) && ids.equals("undefined") && ids !="" ){
			id = null;
		}else {
			id = Func.toLongList(ids);
		}
		bhUserService.downLoad(response, bhDeptId,  code2 , isEnable2, isOnline2, isAuthorized2 , start2, end2,id);

	}


	/**
	 * 同步 人员
	 */
	@PostMapping("/syn")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "account", value = "账户集合", paramType = "query", dataType = "string"),
	})
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "用户人员信息同步", notes = "用户人员信息同步")
	@LcSysLog("用户人员信息同步")
	public R syn() throws Exception{
		return R.status(bhUserService.syn());
	}


	/**
	 * 应用授权 根据用户id获取应用和职位信息
	 */
	@GetMapping("/getDeptList")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "应用授权 根据用户id获取应用和职位信息", notes = "应用授权 根据用户id获取应用和职位信息")
//	@LcSysLog("应用授权 根据用户id获取应用和职位信息")
	public R getDeptList(Long id) {
		return R.data(bhUserService.getDeptList(id));
	}

	/**
	 * 多用户获取主部门信息
	 */
	@GetMapping("/getMajorDeptList")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "多用户获取主部门信息", notes = "多用户获取主部门信息")
//	@LcSysLog("应用授权 根据用户id获取应用和职位信息")
	public R getMajorDeptList(String ids) {
		return R.data(bhUserService.getMajorDeptList(ids));
	}

	@GetMapping("/getUser")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "部门下的人员", notes = "部门下的人员 传入部门 id")
	public R<List<BhUser>> getUser(String ids) {
		List<BhUser> tree = bhUserService.getUserSyn(ids);
		return R.data(tree);
	}

	@PostMapping("/getUsers")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "部门下的人员", notes = "部门下的人员 传入部门 id")
	public R<List<BhUser>> getUsers(@RequestBody Map<String,String> param) {
		List<BhUser> tree = bhUserService.getUserSyn(param.get("ids"));
		return R.data(tree);
	}

	@GetMapping("/sendMsg")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "测试用户发短信", notes = "测试用户发短信")
	public ResultVO sendMsg(String userIds) {
		return bhUserService.sendMsg(userIds);
	}

	/***********************************前台*****************************/
	@GetMapping("/getUserRole")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "获取当前登录的角色 0是管理员 1是普通用户", notes = "获取当前登录的角色 0是管理员 1是普通用户")
	public R getUserRole() {
		return R.data(bhUserService.getUserRole());
	}


	@GetMapping("/getPasswordType")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "获取是否是初始密码  0-是初始密码，未修改过，1-不是初始密码，已修改", notes = "获取是否是初始密码  0-是初始密码，未修改过，1-不是初始密码，已修改")
	public R getPasswordType() {
		return R.data(bhUserService.getPasswordType());
	}


	@GetMapping("/updatePassword")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "修改初始密码", notes = "修改密码")
	@LcSysLog("修改初始密码")
	public R updatePassword(String oldPassword, String newPassword) {
		return R.data(bhUserService.updatePassword(AuthUtil.getUserAccount(), oldPassword, newPassword));
	}




	/**
	 * 详情
	 */
	@GetMapping("/getAccountDetail")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "查看用户详情", notes = "查看用户详情")
//	@LcSysLog("查看用户详情")
	public R<BhUserVO> getAccountDetail() {
		BhUserVO detail = bhUserService.getAccountDetail(AuthUtil.getUserAccount());
		return R.data(detail);
	}

	/**
	 * 详情
	 */
	@GetMapping("/getPasswordBit")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "查看用户详情", notes = "查看用户详情")
//	@LcSysLog("查看用户详情")
	public R getPasswordBit() {
		PassWordBitVO detail = bhUserService.getPasswordBit();
		return R.data(detail);
	}



	/**
	 * @return org.springblade.core.tool.api.R
	 * @description 导入企业、点位、机组、数采仪、设备
	 * @Param [multipartFile]
	 * @author lj
	 * @date 2021/1/19
	 */
	@PostMapping("/import")
	@ApiOperationSupport(order = 18)
	@ApiOperation(value = "导入", notes = "导入")
	@LcSysLog("导入用户")
	public R importExcel(MultipartFile multipartFile,Long deptId) {
		return R.status(bhUserService.importExcel(multipartFile, deptId));
	}

	@GetMapping("/downTemplate")
	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "下载导入人员模板", notes = "下载导入人员模板")
	@LcSysLog("下载导入人员模板")
	public void downTemplate(HttpServletResponse response) {
		bhUserService.downTemplate(response);

	}

}
