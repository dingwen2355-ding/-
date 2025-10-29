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
import com.jslc.modules.szbh.dto.DeptPositionDto;
import com.jslc.modules.szbh.entity.BhDept;
import com.jslc.modules.szbh.entity.BhUser;
import com.jslc.modules.szbh.vo.BhApplicationManagementVO;
import com.jslc.modules.szbh.vo.BhUserVO;
import com.jslc.modules.szbh.vo.PassWordBitVO;
import com.jslc.modules.szbh.vo.ResultVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.core.tool.api.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 用户管理 服务类
 *
 * @author BladeX
 * @since 2023-05-09
 */
public interface IBhUserService extends IService<BhUser> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bhUser
	 * @return
	 */
	IPage<BhUserVO> selectBhUserPage(IPage<BhUserVO> page, Long bhDeptId,String  code ,Integer isEnable,Integer isOnline,Integer isAuthorized ,String start,String end);

	Boolean del(List<Long> toLongList);

	BhUserVO getDetail(Long id);


	Boolean changeEnable(Long id);

	Boolean changeOnline(String ids);

	void downLoad(HttpServletResponse response, Long bhDeptId, String code, Integer isEnable, Integer isOnline, Integer isAuthorized, String start, String end,List<Long> id);

	Boolean submit(BhUser bhUser);

	Boolean changePassword(String ids);


	Boolean syn() throws Exception;


    List<BhApplicationManagementVO> getDeptList(Long id);

	ResultVO sendMsg(String userIds);


	List<DeptPositionDto> getMajorDeptList(String ids);

    List<BhUser> getUserGroup(String bizId);

    List<BhUser> getList(String key);


	List<BhUser> getUserGroupData(List<Long> ids);

	List<BhUser> getUserSyn(String ids);

	/**
	 * 查询用户
	 */
	List<BhUser> getUserListByDeptId(List<String> syncFields, List<String> syncUsers);


    Integer getUserRole();

    Integer getPasswordType();



	Boolean updatePassword(String userAccount, String oldPassword, String newPassword);

    BhUserVO getAccountDetail(String account);


	Boolean updatePhone(BhUser bhUser);


    Boolean importExcel(MultipartFile multipartFile,Long deptId);

	Boolean submitList(List<BhUser> bhUser);


	void downTemplate(HttpServletResponse response);


	PassWordBitVO getPasswordBit();

	BhUserVO getUserDetail(String account);


	List<BhUser> getUserList(List<Long> dept);


	Boolean changeAllPassword();


    String getLoginPhones();


}
