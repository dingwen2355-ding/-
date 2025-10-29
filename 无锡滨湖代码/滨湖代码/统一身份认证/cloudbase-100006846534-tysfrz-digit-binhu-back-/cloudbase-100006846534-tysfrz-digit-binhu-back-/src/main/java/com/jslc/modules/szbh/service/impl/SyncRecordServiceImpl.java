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
package com.jslc.modules.szbh.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.common.utils.DateCommonUtil;
import com.jslc.modules.szbh.entity.SyncRecord;
import com.jslc.modules.szbh.vo.ManageUserGroupVO;
import com.jslc.modules.szbh.vo.SyncRecordVO;
import com.jslc.modules.szbh.mapper.SyncRecordMapper;
import com.jslc.modules.szbh.service.ISyncRecordService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.DateTimeUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 同步记录表 服务实现类
 *
 * @author BladeX
 * @since 2023-06-06
 */
@Service
public class SyncRecordServiceImpl extends ServiceImpl<SyncRecordMapper, SyncRecord> implements ISyncRecordService {



	@Override
	public IPage<SyncRecordVO> selectSyncRecordPage(IPage<SyncRecordVO> page, String key, String start, String end, String type) {
		List<SyncRecordVO> list = getSyncRecordList(page, key,  start, end, type);
		return page.setRecords(list);
	}

	private List<SyncRecordVO> getSyncRecordList(IPage<SyncRecordVO> page, String key, String start, String end, String type) {
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		if (Func.isNotEmpty(start) && Func.isNotEmpty(end)){
			startTime = LocalDateTime.of(LocalDate.parse(start,DateTimeUtil.DATE_FORMAT), LocalTime.MIN);
			endTime = LocalDateTime.of(LocalDate.parse(end,DateTimeUtil.DATE_FORMAT), LocalTime.MAX);
		}
		if (Func.isEmpty(type)){
			type = null;
		}
		List<SyncRecordVO> list = baseMapper.getSyncRecordList(page,key,startTime,endTime,type);

		return list;
	}


	@Override
	public IPage<SyncRecordVO> getDownpage(IPage<SyncRecordVO> page, String key,Long id) {
		List<SyncRecordVO> list = baseMapper.getDownList(page,key,id);
		return page.setRecords(list);
	}


	@Override
	public void downLoad(HttpServletResponse response, String key, String start, String end, String type, String ids) {
		OutputStream out = null;

		List<SyncRecordVO> list = getSyncRecordList(null, key, start, end, type);
		if (CollectionUtils.isNotEmpty(list) && Func.isNotEmpty(ids) && ids.length() >0){
			list = list.stream().filter(f->Func.toLongList(ids).contains(f.getId())).collect(Collectors.toList());
		}
		try {
			String fileName = "同步记录.xlsx";

			out = response.getOutputStream();
			//设置返回头
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");


			//动态添加 表头 headList --> 所有表头行集合
			List<List<String>> headList = new ArrayList<List<String>>();
			List<String> headTitle = new ArrayList<String>();
			headTitle.add("数据源/应用名称");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("数据类型");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("同步类型");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("同步结果");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("异常原因");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("同步时间");
			headList.add(headTitle);


			// 所有行的集合
			List<List<Object>> resultList = new ArrayList<List<Object>>();

			for (SyncRecordVO syncRecordVO : list) {
				List<Object> row = new ArrayList<Object>();
				row.add(syncRecordVO.getAppName());
				row.add(syncRecordVO.getDataType());
				row.add(syncRecordVO.getUpOrDown());
				row.add(syncRecordVO.getResult());
				row.add(syncRecordVO.getCause());
				row.add(Func.isNotEmpty(syncRecordVO.getCreateTime()) ? DateTimeUtil.format(syncRecordVO.getCreateTime(), DateCommonUtil.SECODE_FORMAT) : null);
				resultList.add(row);
			}

			EasyExcel.write(response.getOutputStream())
					.head(headList)
					.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
					.sheet("同步记录")
					.doWrite(resultList);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
