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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jslc.common.constant.SwapConstant;
import com.jslc.common.utils.CommonUtil;
import com.jslc.common.utils.DateCommonUtil;
import com.jslc.common.utils.DateUtil;
import com.jslc.modules.szbh.entity.AppUseRecord;
import com.jslc.modules.szbh.entity.BhDept;
import com.jslc.modules.szbh.entity.BhDeptSync;
import com.jslc.modules.szbh.entity.BhUser;
import com.jslc.modules.szbh.service.IBhApplicationManagementService;
import com.jslc.modules.szbh.service.IBhDeptService;
import com.jslc.modules.szbh.vo.*;
import com.jslc.modules.szbh.mapper.AppUseRecordMapper;
import com.jslc.modules.szbh.service.IAppUseRecordService;
import io.swagger.models.Swagger;
import org.apache.ibatis.annotations.Param;
import org.checkerframework.checker.units.qual.A;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.DateTimeUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 应用使用记录 服务实现类
 *
 * @author BladeX
 * @since 2023-06-02
 */
@Service
public class AppUseRecordServiceImpl extends ServiceImpl<AppUseRecordMapper, AppUseRecord> implements IAppUseRecordService {

	@Autowired
	private IBhDeptService bhDeptService;

	@Autowired
	private IBhApplicationManagementService bhApplicationManagementService;


	@Override
	public IPage<UserOnlineVO> selectAppUseRecordPage(IPage<UserOnlineVO> page, String key, String start, String end , String deptIds, String appIds) {
		List<UserOnlineVO> list = getAppUseRecordData(page,key, start, end , deptIds, appIds);
		return page.setRecords(list);
	}

	private List<UserOnlineVO> getAppUseRecordData(IPage<UserOnlineVO> page ,String key, String start, String end, String deptIds, String appIds) {
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		List<Long> deptId = null;
		List<Long> appId = null;
//
//		if (Func.isNotEmpty(start) && Func.isNotEmpty(end)){
//			startTime = LocalDateTime.of(LocalDate.parse(start,DateTimeUtil.DATE_FORMAT), LocalTime.MIN);
//			endTime = LocalDateTime.of(LocalDate.parse(end,DateTimeUtil.DATE_FORMAT), LocalTime.MAX);
//		}
		if (Func.isNotEmpty(start)){
			startTime = LocalDateTime.of(LocalDate.parse(start, DateTimeUtil.DATE_FORMAT), LocalTime.MIN);
		}
		if (Func.isNotEmpty(end)){
			endTime = LocalDateTime.of(LocalDate.parse(end, DateTimeUtil.DATE_FORMAT), LocalTime.MAX);
		}

		if (Func.isNotEmpty(deptIds)){
			deptId = Func.toLongList(deptIds);
		}

		if (Func.isNotEmpty(appIds)){
			appId = Func.toLongList(appIds);
		}

		List<UserOnlineVO> list = baseMapper.getAppUseRecordData(page,key,startTime,endTime,deptId,appId);

		if (CollectionUtils.isNotEmpty(list)){
			Map<String,UserOnlineVO> map = new HashMap<>();
			List<String> account = list.stream().map(UserOnlineVO::getUserAccount).collect(Collectors.toList());
			List<UserOnlineVO> userInfoS =  baseMapper.getUserInfo(account);
			if (CollectionUtils.isNotEmpty(userInfoS)){
				map = userInfoS.stream().collect( Collectors.toMap(
						UserOnlineVO::getUserAccount,
						a -> a,(k1, k2) -> k1));

				for (UserOnlineVO f : list) {
					UserOnlineVO userOnlineVO = map.get(f.getUserAccount());
					if (Func.isNotEmpty(userOnlineVO)) {
//						f.setDeptId(userOnlineVO.getDeptId());
//						f.setBhDeptName(userOnlineVO.getBhDeptName());
						f.setAvatar(userOnlineVO.getAvatar());
						f.setUserName(userOnlineVO.getUserName());
						f.setPositionName(userOnlineVO.getPositionName());
						f.setUserId(userOnlineVO.getUserId());
					}
				}
			}

		}


		if (CollectionUtils.isNotEmpty(list)){
			for (UserOnlineVO userOnlineVO : list) {
				if (Func.isEmpty(userOnlineVO.getIp())){
					userOnlineVO.setIp("-");
				}
				if (Func.isEmpty(userOnlineVO.getDevice())){
					userOnlineVO.setDevice("-");
				}

				if (Func.isEmpty(userOnlineVO.getToken())){
					userOnlineVO.setToken("-");
				}
				if (Func.isEmpty(userOnlineVO.getBrowser())){
					userOnlineVO.setBrowser("-");
				}

			}
		}

		return list;

	}


	@Override
	public Boolean dels(List<Long> ids) {
		boolean flag = this.removeBatchByIds(ids);
		return flag;

	}

	@Override
	public void downLoad(HttpServletResponse response, String key, String start, String end, String deptIds, String appIds,String ids) {
		OutputStream out = null;
		List<UserOnlineVO> list = getAppUseRecordData(null, key, start, end, deptIds, appIds);
		if (Func.isNotEmpty(ids) && CollectionUtils.isNotEmpty(list)){
			List<Long> id = Func.toLongList(ids);
			list= list.stream().filter(f->id.contains(f.getId())).collect(Collectors.toList());
		}
		try {
			String fileName = "登录日志.xlsx";

			out = response.getOutputStream();
			//设置返回头
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");


			//动态添加 表头 headList --> 所有表头行集合
			List<List<String>> headList = new ArrayList<List<String>>();
			List<String> headTitle = new ArrayList<String>();
			headTitle.add("登录账户");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("部门");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("登录应用");
			headList.add(headTitle);

//			headTitle = new ArrayList<String>();
//			headTitle.add("登录token");
//			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("登录ip");
			headList.add(headTitle);



//			headTitle = new ArrayList<String>();
//			headTitle.add("设备信息");
//			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("登录时间");
			headList.add(headTitle);




			// 所有行的集合
			List<List<Object>> resultList = new ArrayList<List<Object>>();

			for (UserOnlineVO userOnlineVO : list) {
				List<Object> row = new ArrayList<Object>();
				row.add(userOnlineVO.getUserAccount());
				row.add(userOnlineVO.getBhDeptName());
				row.add(userOnlineVO.getAppName());
//				row.add(userOnlineVO.getToken());
				row.add(userOnlineVO.getIp());
//				row.add(userOnlineVO.getDevice());
				row.add(Func.isNotEmpty(userOnlineVO.getCreateTime()) ? DateTimeUtil.format(userOnlineVO.getCreateTime(), DateCommonUtil.SECODE_FORMAT) : null);
				resultList.add(row);

			}

			EasyExcel.write(response.getOutputStream())
					.head(headList)
					.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
					.sheet("登录日志")
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


	@Override
	public List<HeadAppInfoVO> getHead(Integer type, String start, String end, String deptIds, String appIds) {
		List<HeadAppInfoVO> result = new ArrayList<>();
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		List<Long> deptId = null;
		List<Long> appId = null;
		if (Func.isNotEmpty(deptIds)){
			deptId = Func.toLongList(deptIds);
		}
		if (Func.isNotEmpty(appIds)){
			appId = Func.toLongList(appIds);
		}

		if (type != null && type ==1 && Func.isNotEmpty(start)){//yyyy-MM-dd
			startTime = LocalDateTime.of(LocalDate.parse(start,DateTimeUtil.DATE_FORMAT), LocalTime.MIN);
			endTime = LocalDateTime.of(LocalDate.parse(end,DateTimeUtil.DATE_FORMAT), LocalTime.MAX);
		}else if (type != null && type == 2 && Func.isNotEmpty(start)){//yyyy-MM
			startTime = LocalDateTime.of(DateTimeUtil.parseDate(DateUtil.getMinDateMonth(start), DateCommonUtil.DAY_FORMAT), LocalTime.MIN);
			endTime = LocalDateTime.of(DateTimeUtil.parseDate(DateUtil.getMaxDateMonth(end), DateCommonUtil.DAY_FORMAT), LocalTime.MAX);
		}

		result.add(new HeadAppInfoVO("times","日期"));
		result.add(new HeadAppInfoVO("recordSum","总访问次数"));
		if (Func.isEmpty(appIds)){
			//为空全查
			result.addAll(baseMapper.getAppIds());
		}else {//不为空 查指定应用
			result.addAll(baseMapper.getAppId(appId));
		}
		return result;
	}

	@Override
	public IPage<AppRecordVO> getRecordPage(IPage<AppRecordVO> page, Integer type, String start, String end, String deptIds, String appIds) {
		List<AppRecordVO> list = getRecordDate(type,start,end,deptIds,appIds);
		IPage iPage = CommonUtil.manualPage(page, list);
		return iPage;
	}

	@Override
	public List<LoginRecordEcharsVO> getRecordEchars(Integer type, String start, String end, String deptIds, String appIds) {
		List<LoginRecordEcharsVO> result = new ArrayList<>();
		Map<String,List> map = new HashMap<>();

		List<AppRecordVO> recordDate = getRecordDate(type, start, end, null, null);
//		LocalDateTime startTime = null;
//		LocalDateTime endTime = null;
//		if (type == null && Func.isEmpty(start) && Func.isEmpty(end)){//近7天
//			endTime = LocalDateTime.now();
//			startTime = endTime.plusDays(-7L);
//		}else if (type != null && type ==1 && Func.isEmpty(start)){//yyyy-MM-dd
//			//获取本周开始结束时间
//			LocalDate now = LocalDate.now();
//			startTime = LocalDateTime.of(now.minusDays(now.getDayOfWeek().getValue() - 1), LocalTime.MIN);
//			endTime = LocalDateTime.of(now.plusDays(7 - now.getDayOfWeek().getValue()), LocalTime.MAX);
//		}else if (type != null && type == 2 && Func.isEmpty(start)){//yyyy-MM
//			//获取本月开始结束时间
//			startTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
//			endTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
//		}else if (type != null && type == 3 && Func.isEmpty(start)){//yyyy
//			//获取本年开始结束时间
//			startTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
//			endTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX);
//		}else {
//			startTime = LocalDateTime.of(LocalDate.parse(start,DateTimeUtil.DATE_FORMAT), LocalTime.MIN);
//			endTime = LocalDateTime.of(LocalDate.parse(end,DateTimeUtil.DATE_FORMAT), LocalTime.MAX);
//		}


//		List<String> time = new ArrayList<>();
//		do{
//			String str = "";
//			map.put("time",time);
//			if (type != null && type ==3){
//				str =startTime.format(DateTimeFormatter.ofPattern("yyyy/MM"));
//			}else {
//				str =startTime.format(DateTimeFormatter.ofPattern("MM/dd"));
//			}
////				List<LoginRecordVO> loginRecordVOS = collect.get(str);
//
//			if (type != null && type ==3){
//				time.add(startTime.format(DateTimeFormatter.ofPattern("yyyy/MM")));
//			}else {
//				time.add(startTime.format(DateTimeFormatter.ofPattern("MM/dd")));
//			}
//
//
//
//
//		}while (startTime.isBefore(endTime));
		if (CollectionUtils.isNotEmpty(recordDate)){
			List<String> time = new ArrayList<>();
			map.put("time",time);
//			result.add(new LoginRecordEcharsVO("time",time));

			for (int i = 0; i < recordDate.size(); i++) {
				if (i ==0){
					if (type != null && type == 2){
						String date = recordDate.get(i).getTimes().replaceAll("-","/");
						time.add(date);
					}else {
//						Date date1 = new Date(recordDate.get(i).getTimes());
//						String date = DateTimeUtil.format(DateTimeUtil.parseDate(recordDate.get(i).getTimes()), "MM/dd");
//						String date = DateTimeUtil.parseDate(recordDate.get(i).getTimes()).format(pattern);
						String date =recordDate.get(i).getTimes().substring(recordDate.get(i).getTimes().indexOf("-")+1).replaceAll("-","/");
						time.add(date);
					}
					List<AppMapVO> appInfo = recordDate.get(i).getAppInfo();
					if (CollectionUtils.isNotEmpty(appInfo)){
						for (AppMapVO appMapVO : appInfo) {
							List<String> k = new ArrayList<>();
							k.add(appMapVO.getAppRecordSum()+"");
							map.put(appMapVO.getAppName(),k);
//							result.add(new LoginRecordEcharsVO(appMapVO.getAppName(),k));
						}
					}

				}else {
					if (type != null && type == 2){
						String date = recordDate.get(i).getTimes().replaceAll("-","/");
						time.add(date);
					}else {
//						String date = DateTimeUtil.parseDate(recordDate.get(i).getTimes()).format(pattern);
						String date =recordDate.get(i).getTimes().substring(recordDate.get(i).getTimes().indexOf("-")+1).replaceAll("-","/");
//						String date = DateTimeUtil.format(DateTimeUtil.parseDate(recordDate.get(i).getTimes()), "MM/dd");
						time.add(date);
					}

					List<AppMapVO> appInfo = recordDate.get(i).getAppInfo();
					if (CollectionUtils.isNotEmpty(appInfo)){
						for (AppMapVO appMapVO : appInfo) {
//							List<String> k = new ArrayList<>();
							map.get(appMapVO.getAppName()).add(appMapVO.getAppRecordSum()+"");


						}
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(map)){
			for (Map.Entry<String, List> entry : map.entrySet()) {
				LoginRecordEcharsVO loginRecordEcharsVO = new LoginRecordEcharsVO();
				loginRecordEcharsVO.setName(entry.getKey());
				loginRecordEcharsVO.setX(entry.getValue());
				result.add(loginRecordEcharsVO);
			}
		}
		return result;
	}


	@Override
	public void appRecordLoad(HttpServletResponse response, Integer type, String start, String end, String deptIds, String appIds) {
		OutputStream out = null;
		List<AppRecordVO> list = getRecordDate(type, start, end, deptIds, appIds);
		try {
			String fileName = "应用登录统计.xlsx";

			out = response.getOutputStream();
			//设置返回头
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");

			List<HeadAppInfoVO> head = getHead(type, start, end, deptIds, appIds);
			List<List<String>> headList = head.stream().map(f->{
				List<String> lists = new ArrayList<>();
				lists.add(f.getAppName());
				return lists;
			}).collect(Collectors.toList());

			// 所有行的集合
			List<List<Object>> resultList = new ArrayList<List<Object>>();

			for (AppRecordVO appRecordVO : list) {
				List<Object> row = new ArrayList<Object>();
				row.add(appRecordVO.getTimes());
				row.add(appRecordVO.getRecordSum());
				if (CollectionUtils.isNotEmpty(appRecordVO.getAppInfo())){
					for (AppMapVO appMapVO : appRecordVO.getAppInfo()) {
						row.add(appMapVO.getAppRecordSum());
					}
				}
				resultList.add(row);

			}

			EasyExcel.write(response.getOutputStream())
					.head(headList)
					.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
					.sheet("应用登录统计")
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

	private List<AppRecordVO> getRecordDate(Integer type, String start, String end, String deptIds, String appIds) {

		List<AppRecordVO> result = new ArrayList<>();

		List<HeadAppInfoVO> appList = new ArrayList<>();
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		List<Long> deptId = null;
		List<Long> appId = null;
		if (Func.isNotEmpty(deptIds)){
			deptId = Func.toLongList(deptIds);
		}
		if (Func.isNotEmpty(appIds)){
			appId = Func.toLongList(appIds);
		}

		//本周 本月 本年 自定义时间范围
		if (type == null && Func.isEmpty(start) && Func.isEmpty(end)){//近7天
			endTime = LocalDateTime.now();
			startTime = endTime.plusDays(-7L);
		}else if (type != null && type ==1 && Func.isEmpty(start)){//yyyy-MM-dd
			//获取本周开始结束时间
			LocalDate now = LocalDate.now();
			startTime = LocalDateTime.of(now.minusDays(now.getDayOfWeek().getValue() - 1), LocalTime.MIN);
			endTime = LocalDateTime.of(now.plusDays(7 - now.getDayOfWeek().getValue()), LocalTime.MAX);
		}else if (type != null && type == 2 && Func.isEmpty(start)){//yyyy-MM
			//获取本月开始结束时间
			startTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
			endTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
		}else if (type != null && type == 3 && Func.isEmpty(start)){//yyyy
			//获取本年开始结束时间
			startTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
			endTime = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX);
		}else {
			startTime = LocalDateTime.of(LocalDate.parse(start,DateTimeUtil.DATE_FORMAT), LocalTime.MIN);
			endTime = LocalDateTime.of(LocalDate.parse(end,DateTimeUtil.DATE_FORMAT), LocalTime.MAX);
		}


		List<AppUseRecordVO> list = baseMapper.getRecordDate(startTime,endTime,deptId,appId);

		//根据时间进行分组
		Map<String, List<AppUseRecordVO>> collect = new HashMap<>();
		if (CollectionUtils.isNotEmpty(list)){
			if (type != null && type == 3) {
				collect = list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy/MM"))));
			} else {
				collect = list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("MM/dd"))));
			}
		}
		//最大时间
		LocalDateTime maxTime = endTime;
		//最小时间
		LocalDateTime minTime = startTime;

		//获取应用列表
			if (Func.isEmpty(appIds)){
				//为空全查
				appList.addAll(baseMapper.getAppIds());
			}else {//不为空 查指定应用
				appList.addAll(baseMapper.getAppId(appId));
			}
		do {
			String str = null;
//				String time = null;
			String time = null;
			if (type != null && type ==3){
				str =minTime.format(DateTimeFormatter.ofPattern("yyyy/MM"));
			}else {
				str =minTime.format(DateTimeFormatter.ofPattern("MM/dd"));
			}


			AppRecordVO appRecordVO = new AppRecordVO();
			appRecordVO.setTimes(str);
//			appRecordVO.setRecordSum(entry.getValue().size());
			List<AppMapVO> app = new ArrayList<>();
			appRecordVO.setAppInfo(app);
			Map<Long, List<AppUseRecordVO>> appCollect = new HashMap<>();
			if (CollectionUtils.isNotEmpty(collect.get(str))){
				appCollect = collect.get(str).stream().collect(Collectors.groupingBy(AppUseRecordVO::getAppId));
			}
			for (HeadAppInfoVO headAppInfoVO : appList) {
				AppMapVO appMapVO = new AppMapVO();
				List<AppUseRecordVO> appUseRecordVOS = appCollect.get(Long.valueOf(headAppInfoVO.getAppId()));
				appMapVO.setAppId(headAppInfoVO.getAppId());
				appMapVO.setAppName(headAppInfoVO.getAppName());
				appMapVO.setAppRecordSum(0);
				if (CollectionUtils.isNotEmpty(appUseRecordVOS)){
					appMapVO.setAppRecordSum(appUseRecordVOS.size());
				}
				app.add(appMapVO);
			}
			result.add(appRecordVO);

			if (type  != null && type ==3){
				minTime = minTime.plusMonths(1);
			}else {
				minTime = minTime.plusDays(1);
			}
		}while (minTime.isBefore(maxTime));

//		if (CollectionUtils.isNotEmpty(list)){
//
//
//			//以时间进行分组
//			Map<String, List<AppUseRecordVO>> collect = new HashMap<>();
//			if (type != null && type ==3){
//				collect =list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy/MM"))));
//			}else {
//				collect =list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("MM/dd"))));
//			}
//			if (CollectionUtils.isNotEmpty(appList)){
//
//
//				for (Map.Entry<String, List<AppUseRecordVO>> entry : collect.entrySet()) {
//					AppRecordVO appRecordVO = new AppRecordVO();
//					appRecordVO.setTimes(entry.getKey());
//					appRecordVO.setRecordSum(entry.getValue().size());
//					List<AppMapVO> app = new ArrayList<>();
//					appRecordVO.setAppInfo(app);
//					//以应用进行分组
//					Map<Long, List<AppUseRecordVO>> appCollect = entry.getValue().stream().collect(Collectors.groupingBy(AppUseRecordVO::getAppId));
//					for (HeadAppInfoVO headAppInfoVO : appList) {
//						AppMapVO appMapVO = new AppMapVO();
//						List<AppUseRecordVO> appUseRecordVOS = appCollect.get(Long.valueOf(headAppInfoVO.getAppId()));
//						appMapVO.setAppId(headAppInfoVO.getAppId());
//						appMapVO.setAppName(headAppInfoVO.getAppName());
//						appMapVO.setAppRecordSum(0);
//						if (CollectionUtils.isNotEmpty(appUseRecordVOS)){
//							appMapVO.setAppRecordSum(appUseRecordVOS.size());
//						}
//						app.add(appMapVO);
//					}
//
//					result.add(appRecordVO);
//				}
//			}
//		}
//		if (CollectionUtils.isNotEmpty(result)){
//			result= result.stream().sorted(Comparator.comparing(i -> i.getTimes())).collect(Collectors.toList());
//		}
		return result;
	}


	public List<BhDept> queryAllDeptId(List<Long> ids){
		LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).in(BhDept::getParentId, ids);
		//根据父ID查询部门
		List<BhDept> departments = bhDeptService.list(query);
		if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(departments)) {
			//拿到当前所有部门ID
			List<Long> parentIds = departments.stream().map(item -> item.getDeptId()).collect(Collectors.toList());
			//拼接子部门查询结果
			departments.addAll(queryAllDeptId(parentIds));
			return departments;
		} else {
			//如果没有下级部门那么我们就返回空集合，结束递归。
			return Lists.newArrayList();
		}
	}


	@Override
	public List<AppUseRecordVO> getAppRecord(LocalDateTime start, LocalDateTime end) {
		List<AppUseRecordVO> list = baseMapper.getAppRecord(start, end);
		return list;
	}


	@Override
	public List<NVMapVO> getUserRecord(Integer type) {
		List<NVMapVO> result = new ArrayList<>();
		LocalDateTime start = null;
		LocalDateTime end = null;
		if (type == SwapConstant.ReportType.month.getValue()){
			end = LocalDateTime.now();
			start = end.plusMonths(-1L);
		}else {
			end = LocalDateTime.now();
			start = end.plusDays(-7L);
		}
		List<AppUseRecordVO> list = baseMapper.getUserAppRecord(AuthUtil.getUserAccount(),start,end);
		if (CollectionUtils.isNotEmpty(list)){
			Map<Long, List<AppUseRecordVO>> collect = list.stream().collect(Collectors.groupingBy(AppUseRecordVO::getAppId));
			for (Map.Entry<Long, List<AppUseRecordVO>> entry : collect.entrySet()) {
				NVMapVO nvMapVO = new NVMapVO();
				nvMapVO.setName(entry.getValue().get(0).getAppName());
				nvMapVO.setValue(entry.getValue().size());
				result.add(nvMapVO);
			}
		}
		return result;
	}

	@Override
	public List<HeadPageVO> getYOYRecord() {
		List<HeadPageVO> result = new ArrayList<>();
		LocalDateTime start = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
		LocalDateTime end = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
//		应用接入总数
		List<BhApplicationManagementVO> list  = bhApplicationManagementService.getAppSumData(start,end);
		//本月接入数量
		List<BhApplicationManagementVO> nowMonth  = bhApplicationManagementService.getAppSumData(start,end);
		//上月接入数量
		List<BhApplicationManagementVO> lastMonth  = bhApplicationManagementService.getAppSumData(start.plusMonths(-1L),end.plusMonths(-1L));
		//计算接入应用同比 （本-上）/上
		String percent = "0.00";
		if (CollectionUtils.isNotEmpty(nowMonth)){
			if (CollectionUtils.isNotEmpty(lastMonth)){
				percent = new BigDecimal(nowMonth.size()-lastMonth.size()).divide(new BigDecimal(lastMonth.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))+"";
			}else {
				percent= "100.00";
			}
		}
		result.add(new HeadPageVO("应用接入数量",list.size(),percent));

		//应用访问次数
		List<AppUseRecordVO> useRecordVOAll = baseMapper.getAppUseRecord(start,end);
		//本月访问数量
		List<AppUseRecordVO> nowMonthUse  = baseMapper.getAppUseRecord(start,end);
		//上月访问数量
		List<AppUseRecordVO> lastMonthUse  =  baseMapper.getAppUseRecord(start.plusMonths(-1L),end.plusMonths(-1L));
		//计算接入应用同比 （本-上）/上
		String percent2 = "0.00";
		if (CollectionUtils.isNotEmpty(nowMonthUse)){
			if (CollectionUtils.isNotEmpty(lastMonthUse)){
				percent2 = new BigDecimal(nowMonthUse.size()-lastMonthUse.size()).divide(new BigDecimal(lastMonthUse.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))+"";
			}else {
				percent2= "100.00";
			}
		}
		result.add(new HeadPageVO("应用访问次数",useRecordVOAll.size(),percent2));
		return result;
	}


	@Override
	public List<AppClassificationStatisticsVO> getAppClassificationStatistics() {
		List<AppClassificationStatisticsVO> result = new ArrayList<>();
		LocalDateTime start = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
		LocalDateTime end = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
		List<AppUseRecordVO> list = baseMapper.getAppUseStatistics(start,end);
		if (CollectionUtils.isNotEmpty(list)){
			Map<String, List<AppUseRecordVO>> collect = list.stream().collect(Collectors.groupingBy(AppUseRecordVO::getCategoryName));
			for (Map.Entry<String, List<AppUseRecordVO>> entry : collect.entrySet()) {
				AppClassificationStatisticsVO appClassificationStatisticsVO = new AppClassificationStatisticsVO();
				appClassificationStatisticsVO.setName(entry.getKey());
				appClassificationStatisticsVO.setAccessNumber(entry.getValue().size());
				appClassificationStatisticsVO.setAppNumber(entry.getValue().stream().map(AppUseRecordVO::getAppId).distinct().collect(Collectors.toList()).size());
			 	result.add(appClassificationStatisticsVO);
			}
		}
		return result;
	}

	@Override
	public List<HeadPageVO> getApplicationAccessRanking() {
		List<HeadPageVO> result = new ArrayList<>();
		LocalDateTime start = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
		LocalDateTime end = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);

		List<AppUseRecordVO> list = this.getAppRecord(start,end);
		if (CollectionUtils.isNotEmpty(list)){
			Map<Long, List<AppUseRecordVO>> collect = list.stream().collect(Collectors.groupingBy(AppUseRecordVO::getAppId));
			for (Map.Entry<Long, List<AppUseRecordVO>> entry : collect.entrySet()) {
				HeadPageVO headPageVO = new HeadPageVO();
				headPageVO.setNumber(entry.getValue().size());
				headPageVO.setName(entry.getValue().get(0).getAppName());
//				headPageVO.setPercent(new BigDecimal(entry.getValue().size()).divide(new BigDecimal(list.size()),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"");

				result.add(headPageVO);
			}
		}
		if (CollectionUtils.isNotEmpty(result)){
			result = result.stream().sorted(Comparator.comparing(HeadPageVO::getNumber).reversed()).collect(Collectors.toList());
		}

		return result;
	}


	@Override
	public List<AccessStatisticsDSQVO> getAccessStatistics() {

		List<AccessStatisticsDSQVO> result = new ArrayList<>();
		List<AppUseRecordVO> list = baseMapper.getAccessStatistics(null,null);

		if (CollectionUtils.isNotEmpty(list)){
			Map<String,List<AppUseRecordVO>> map = list.stream().collect(Collectors.groupingBy(AppUseRecordVO::getClientId));
			for (Map.Entry<String, List<AppUseRecordVO>> entry : map.entrySet()) {
				AccessStatisticsDSQVO accessStatisticsDSQVO = new AccessStatisticsDSQVO();
				AppUseRecordVO appUseRecordVO = entry.getValue().get(0);
				accessStatisticsDSQVO.setAppId(appUseRecordVO.getClientId()+"");
				accessStatisticsDSQVO.setApplicationName(appUseRecordVO.getAppName());
				accessStatisticsDSQVO.setId(appUseRecordVO.getAppId());
				accessStatisticsDSQVO.setViewNum(entry.getValue().size()+"");
				accessStatisticsDSQVO.setAppSecret(appUseRecordVO.getAppSecret());
				result.add(accessStatisticsDSQVO);

			}
		}

		return result;
	}


	@Override
	public EchartsDSQVO getAccessEcharts() {
		EchartsDSQVO result = new EchartsDSQVO();
		List<String> xList = new ArrayList<>();
		List<String> yList = new ArrayList<>();
//		LocalDateTime start = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()),LocalTime.MIN);
//		LocalDateTime end = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()),LocalTime.MAX);
		LocalDateTime end = LocalDateTime.now();
		LocalDateTime start = end.plusDays(-29L);
		List<AppUseRecordVO> list = baseMapper.getAccessStatistics(start,end);
		Map<String,List<AppUseRecordVO>> map = new HashMap<>();

		if (CollectionUtils.isNotEmpty(list)){
			map = list.stream().filter(k->k.getCreateTime() != null).collect(Collectors.groupingBy(f->f.getCreateTime().getDayOfMonth()+""));
		}
		for (LocalDateTime i = start; i.isBefore(end);) {

			xList.add(i.getDayOfMonth()+"");
			List<AppUseRecordVO> dayList = map.get(i.getDayOfMonth() + "");
			yList.add(CollectionUtils.isNotEmpty(dayList) ? dayList.size()+"" : "0");
			i = i.plusDays(1L);
		}
		result.setX(xList);
		result.setY(yList);
		return result;
	}
}
