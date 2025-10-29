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
import com.jslc.modules.szbh.vo.ResultVO;
import com.jslc.modules.szbh.vo.SystemTreeNode;
import com.jslc.modules.szbh.entity.BhDept;
import com.jslc.modules.szbh.vo.BhDeptVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 部门管理 服务类
 *
 * @author BladeX
 * @since 2023-05-09
 */
public interface IBhDeptService extends IService<BhDept> {

	BhDeptVO getDetail(Long id);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bhDept
	 * @return
	 */
	IPage<BhDeptVO> selectBhDeptPage(IPage<BhDeptVO> page, BhDeptVO bhDept);

    List<BhDeptVO> getList(String name, Integer type, Long parentId,Integer isEnable);


	List<BhDeptVO> getRootDepts();


	Boolean del(List<Long> ids);

	Boolean changeEnable(Long id);

	void downLoad(HttpServletResponse response, String name, Integer type,List<Long> ids,Integer isEnable);

	List<SystemTreeNode> getBhDeptTree(Long id);


    Boolean submit(BhDept bhDept);


	Boolean syn();

	/**
	 * 查询部门
	 */
	List<BhDept> getDeptListByDeptId(Long deptId, Integer noFetchChild, List<String> syncDepts,List<String> syncFields);


    ResultVO initUserSendSms(String phones, String msg);

	String getAppIds(Long userId,Long deptId);

	List<BhDept> getDepts(String bizId);

	List<BhDeptVO> getList2(String name, Integer type, Long parentId, Integer isEnable);


}
