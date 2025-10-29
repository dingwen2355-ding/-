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

import com.jslc.modules.szbh.entity.BhNotice;
import com.jslc.modules.szbh.vo.BhNoticeRestVO;
import com.jslc.modules.szbh.vo.BhNoticeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 通知公告 Mapper 接口
 *
 * @author BladeX
 * @since 2023-08-10
 */
public interface BhNoticeMapper extends BaseMapper<BhNotice> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bhNotice
	 * @return
	 */
	List<BhNoticeVO> selectBhNoticePage(@Param("page") IPage page,
										@Param("title") String title,
										@Param("noticeType") Integer noticeType,
										@Param("type") Integer type,
										@Param("start") String start,
										@Param("end") String end,
										@Param("downStart")String downStart,
										@Param("downEnd")String downEnd,
										@Param("isTop")Integer isTop);

	List<BhNoticeRestVO> noticeList(@Param("page") IPage page,
									@Param("noticeType") Integer noticeType,
									@Param("releaseTime") LocalDate releaseTime,
									@Param("offShelfTime") LocalDate offShelfTime);

	List<BhNoticeVO> getNoticeList(@Param("page")IPage<BhNoticeVO> page,
								   @Param("noticeType")Integer noticeType,
								   @Param("releaseTime")LocalDate releaseTime,
								   @Param("offShelfTime")LocalDate offShelfTime,
								   @Param("title")String title);
}
