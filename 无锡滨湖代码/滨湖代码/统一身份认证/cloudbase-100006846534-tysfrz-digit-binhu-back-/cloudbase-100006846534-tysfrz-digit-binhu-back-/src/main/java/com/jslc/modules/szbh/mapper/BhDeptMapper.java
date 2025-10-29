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

import com.jslc.modules.szbh.vo.SystemTreeNode;
import com.jslc.modules.szbh.entity.BhDept;
import com.jslc.modules.szbh.vo.BhDeptVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门管理 Mapper 接口
 *
 * @author BladeX
 * @since 2023-05-09
 */
public interface BhDeptMapper extends BaseMapper<BhDept> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bhDept
	 * @return
	 */
	List<BhDeptVO> selectBhDeptPage(IPage page, BhDeptVO bhDept);

    List<BhDeptVO> getDeptList(@Param("name") String name,
							   @Param("type")Integer type,
							   @Param("parentId")Long parentId,
							   @Param("ids")List<Long> ids,
							   @Param("isEnable")Integer isEnable);


	List<SystemTreeNode> getOrgTree2(@Param("id")Long id);

	BhDeptVO getDetail(@Param("id")Long id);

    List<BhDeptVO> getDeptList2(@Param("name") String name,
								@Param("type")Integer type,
								@Param("parentId")Long parentId,
								@Param("ids")List<Long> ids,
								@Param("isEnable")Integer isEnable);

	List<BhDept> getDeptListBySyncDeptAndField(@Param("syncDepts") List<String> syncDepts,@Param("syncFields") String syncFields);

	List<BhDeptVO> getRootList();



}
