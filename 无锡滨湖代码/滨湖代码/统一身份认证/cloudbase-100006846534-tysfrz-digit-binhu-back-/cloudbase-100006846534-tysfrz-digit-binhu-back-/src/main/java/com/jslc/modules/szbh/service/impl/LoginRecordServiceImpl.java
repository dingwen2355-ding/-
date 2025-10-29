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
import com.jslc.common.utils.CommonUtil;
import com.jslc.common.utils.DateCommonUtil;
import com.jslc.common.utils.DateUtil;
import com.jslc.modules.szbh.entity.BhDept;
import com.jslc.modules.szbh.entity.BhUser;
import com.jslc.modules.szbh.entity.LoginRecord;
import com.jslc.modules.szbh.service.IBhDeptService;
import com.jslc.modules.szbh.service.IBhUserService;
import com.jslc.modules.szbh.vo.*;
import com.jslc.modules.szbh.mapper.LoginRecordMapper;
import com.jslc.modules.szbh.service.ILoginRecordService;
import io.swagger.annotations.ApiModelProperty;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.tool.utils.BeanUtil;
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
 * 客户端登录记录 服务实现类
 *
 * @author BladeX
 * @since 2023-06-02
 */
@Service
public class LoginRecordServiceImpl extends ServiceImpl<LoginRecordMapper, LoginRecord> implements ILoginRecordService {
	@Autowired
	private IBhDeptService bhDeptService;

	@Autowired
	private IBhUserService bhUserService;

	@Override
	public IPage<UserOnlineVO> selectLoginRecordPage(IPage<UserOnlineVO> page,Long deptId, String userAccount ,String userName) {
		List<UserOnlineVO> list = getOnlineRecord(deptId, userAccount , userName);
		IPage iPage = CommonUtil.manualPage(page, list);
		return iPage;
	}

	@Override
	public IPage<UserOnlineVO> getDatAPage(IPage<UserOnlineVO> page, String userAccount) {
//		List<UserOnlineVO> result = new ArrayList<>();
		LocalDateTime starTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		List<UserOnlineVO> list = baseMapper.getOnlineRecord(null, null , null,starTime,endTime);
		List<UserOnlineVO> result = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list) && Func.isNotEmpty(userAccount) && userAccount.length() >0){
			list =list.stream().filter(f->f.getUserAccount() != null).filter(k->k.getUserAccount().contains(userAccount)).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(list)){
				Map<String, List<UserOnlineVO>> collect = list.stream().collect(Collectors.groupingBy(UserOnlineVO::getIp));
				for (Map.Entry<String, List<UserOnlineVO>> entry : collect.entrySet()) {
					UserOnlineVO userOnlineVO = entry.getValue().stream().filter(f -> f.getCreateTime() != null).max(Comparator.comparing(UserOnlineVO::getCreateTime)).orElse(null);
					if (Func.isNotEmpty(userOnlineVO)){
						result.add(userOnlineVO);
					}
				}
			}

		}
		if (CollectionUtils.isNotEmpty(result)){
			result = result.stream().sorted(Comparator.comparing(UserOnlineVO::getCreateTime).reversed()).collect(Collectors.toList());
		}
		IPage iPage = CommonUtil.manualPage(page, result);
		return iPage;
	}

	private List<UserOnlineVO> getOnlineRecord(Long deptId, String userAccount , String userName) {
		List<UserOnlineVO> result = new ArrayList<>();
		LocalDateTime starTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		List<UserOnlineVO> list = baseMapper.getOnlineRecord(null, userAccount , userName,starTime,endTime);
		if (CollectionUtils.isNotEmpty(list) && Func.isNotEmpty(deptId)){
			List<Long> dept = new ArrayList<>();
			dept.add(deptId);
			List<BhDept> bhDepts = queryAllDeptId(dept);
			if (CollectionUtils.isNotEmpty(bhDepts)){
				dept.addAll(bhDepts.stream().map(BhDept::getDeptId).collect(Collectors.toList()));
			}
			list = list.stream().filter(f->f.getDeptId() != null).filter(f->dept.contains(f.getDeptId())).collect(Collectors.toList());
		}
		if (CollectionUtils.isNotEmpty(list) && Func.isNotEmpty(userAccount) && userAccount.length() >0){
			list =list.stream().filter(f->f.getUserAccount() != null).filter(k->k.getUserAccount().contains(userAccount)).collect(Collectors.toList());
		}
		if (CollectionUtils.isNotEmpty(list) && Func.isNotEmpty(userName) && userName.length() >0){
			list =list.stream().filter(f->f.getUserName() != null).filter(k->k.getUserName().contains(userName)).collect(Collectors.toList());
		}

//		if (CollectionUtils.isNotEmpty(list) && Func.isNotEmpty(deptId)){
//
//			list =list.stream().filter(f->f.getDeptId() != null).filter(k->k.getDeptId().longValue() == deptId.longValue()).collect(Collectors.toList());
//		}


		//根据userId进行分组
		if (CollectionUtils.isNotEmpty(list)){
			Map<Long, List<UserOnlineVO>> collect = list.stream().filter(f -> f.getUserId() != null).collect(Collectors.groupingBy(UserOnlineVO::getUserId));
			for (Map.Entry<Long, List<UserOnlineVO>> entry : collect.entrySet()) {
				UserOnlineVO userOnlineVO = new UserOnlineVO();
				BeanUtil.copy(entry.getValue().get(0),userOnlineVO);
				userOnlineVO.setOnLineSum(entry.getValue().stream().map(UserOnlineVO::getIp).distinct().collect(Collectors.toList()).size());
//				userOnlineVO.setData(entry.getValue());
				result.add(userOnlineVO);

			}
		}
		return result;
	}

	@Override
	public IPage<ActiveUserVO> getActivePage(IPage<ActiveUserVO> page, Integer type, String start, String end, String deptIds) {
		IPage iPage = CommonUtil.manualPage(page, getActive(type, start, end, deptIds));
		return iPage;

	}

	@Override
	public void downLoad(HttpServletResponse response, Integer type, String start, String end, String deptIds) {
		OutputStream out = null;
		List<ActiveUserVO> list = getActive(type, start, end, deptIds);
		try {
			String fileName = "用户活跃度.xlsx";

			out = response.getOutputStream();
			//设置返回头
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");


			//动态添加 表头 headList --> 所有表头行集合
			List<List<String>> headList = new ArrayList<List<String>>();
			List<String> headTitle = new ArrayList<String>();
			headTitle.add("日期");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("用户总数");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("新增用户");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("活跃用户");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("活跃度");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("登录人次");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("人均登录（活跃）");
			headList.add(headTitle);

			// 所有行的集合
			List<List<Object>> resultList = new ArrayList<List<Object>>();

			for (ActiveUserVO activeUserVO : list) {
				List<Object> row = new ArrayList<Object>();
				row.add(activeUserVO.getTime());
				row.add(activeUserVO.getUserSum());
				row.add(activeUserVO.getCreateUserSum());
				row.add(activeUserVO.getActiveUserSum());
				row.add(activeUserVO.getActivePercent());
				row.add(activeUserVO.getOnLineSum());
				row.add(activeUserVO.getAvgOnLineSum());
				resultList.add(row);

			}

			EasyExcel.write(response.getOutputStream())
					.head(headList)
					.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
					.sheet("用户活跃度")
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
	public LoginUserEcharsVO getEcharsList(Integer type, String start, String end, String deptIds) {
		LoginUserEcharsVO result = new LoginUserEcharsVO();
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		List<Long> deptId = null;
		List<String> times = new ArrayList<>();
//		List<String> userSum = new ArrayList<>();
		List<String> createUserSum = new ArrayList<>();
		List<String> activeUserSum = new ArrayList<>();
		List<String> onLineSum = new ArrayList<>();

		if (Func.isNotEmpty(deptIds)){
			deptId = Func.toLongList(deptIds);
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

		List<LoginRecordVO> list = baseMapper.getUserRecord(startTime,endTime,null);
		List<BhUser> userList = bhUserService.list(Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).between(BhUser::getCreateTime, startTime, endTime));

//		if (CollectionUtils.isNotEmpty(list) && CollectionUtils.isNotEmpty(deptId)){
//			List<Long> dept = new ArrayList<>();
//			dept.addAll(deptId);
//			List<BhDept> bhDepts = queryAllDeptId(deptId);
//			if (CollectionUtils.isNotEmpty(bhDepts)){
//				dept.addAll(bhDepts.stream().map(BhDept::getDeptId).collect(Collectors.toList()));
//			}
//			list = list.stream().filter(f->f.getDeptId() != null).filter(f->dept.contains(f.getDeptId())).collect(Collectors.toList());
//		}

		Map<String, List<LoginRecordVO>> collect = new HashMap<>();
		if (CollectionUtils.isNotEmpty(list)) {//获取list中的最大时间 和 最小时间
			//最大时间
//			LocalDateTime maxTime = endTime;
//			list.stream().map(LoginRecordVO::getCreateTime).max(LocalDateTime::compareTo).get();
			//最小时间
//			LocalDateTime minTime = startTime;
//					list.stream().map(LoginRecordVO::getCreateTime).min(LocalDateTime::compareTo).get().withHour(0).withMinute(0).withSecond(0);
			if (type != null && type == 3) {
				collect = list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy/MM"))));
			} else {
				collect = list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("MM/dd"))));
			}
		}
		Map<String, List<BhUser>> userMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(userList)) {//获取list中的最大时间 和 最小时间

			if (type != null && type == 3) {
				userMap = userList.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy/MM"))));
			} else {
				userMap = userList.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("MM/dd"))));
			}
		}
		//最大时间
		LocalDateTime maxTime = endTime;
//			list.stream().map(LoginRecordVO::getCreateTime).max(LocalDateTime::compareTo).get();
		//最小时间
		LocalDateTime minTime = startTime;

		do {
				String str = null;
//				String time = null;
				if (type != null && type ==3){
					str =minTime.format(DateTimeFormatter.ofPattern("yyyy/MM"));
				}else {
					str =minTime.format(DateTimeFormatter.ofPattern("MM/dd"));
				}
//				List<LoginRecordVO> loginRecordVOS = collect.get(str);

				if (type != null && type ==3){
					times.add(minTime.format(DateTimeFormatter.ofPattern("yyyy/MM")));
				}else {
					times.add(minTime.format(DateTimeFormatter.ofPattern("MM/dd")));
				}

			activeUserSum.add("0");
			onLineSum.add("0");
			createUserSum.add("0");


			if (CollectionUtils.isNotEmpty(userMap.get(str))){
					createUserSum.set(times.indexOf(str),userMap.get(str).size()+"");
			}

			//活跃用户
			List<LoginRecordVO> loginRecordVOS1 = collect.get(str);
			if(Func.isNotEmpty(loginRecordVOS1)){
				long activeUser = loginRecordVOS1.stream().map(LoginRecordVO::getUsername).distinct().count();
				activeUserSum.set(times.indexOf(str),activeUser+"");
//				add(activeUser+"");
				//登录数量
				onLineSum.set(times.indexOf(str),loginRecordVOS1.size()+"");
//			add(loginRecordVOS1.size()+"");
			}
			if (type  != null && type ==3){
				minTime = minTime.plusMonths(1);
			}else {
				minTime = minTime.plusDays(1);
			}
		}while (minTime.isBefore(maxTime));
			result.setTimes(times);
			result.setActiveUserSum(activeUserSum);
			result.setCreateUserSum(createUserSum);
			result.setOnLineSum(onLineSum);

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


	private List<ActiveUserVO> getActive(Integer type, String start, String end, String deptIds) {
		List<ActiveUserVO> result = new ArrayList<>();
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		List<Long> deptId = null;
		if (Func.isNotEmpty(deptIds)){
			deptId = Func.toLongList(deptIds);
		}
		if (type != null && type ==1 && Func.isNotEmpty(start)){//yyyy-MM-dd
			startTime = LocalDateTime.of(LocalDate.parse(start,DateTimeUtil.DATE_FORMAT), LocalTime.MIN);
			endTime = LocalDateTime.of(LocalDate.parse(end,DateTimeUtil.DATE_FORMAT), LocalTime.MAX);
		}else if (type != null && type == 2 && Func.isNotEmpty(start)){//yyyy-MM
			startTime = LocalDateTime.of(DateTimeUtil.parseDate(start+"-01").with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
			endTime = LocalDateTime.of(DateTimeUtil.parseDate(end+"-01").with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);

		}

		List<LoginRecordVO> list = baseMapper.getUserRecord(startTime,endTime,deptId);
		if (CollectionUtils.isNotEmpty(list) && CollectionUtils.isNotEmpty(deptId)){
			List<Long> dept = new ArrayList<>();
			dept.addAll(deptId);
			List<BhDept> bhDepts = queryAllDeptId(deptId);
			if (CollectionUtils.isNotEmpty(bhDepts)){
				dept.addAll(bhDepts.stream().map(BhDept::getDeptId).collect(Collectors.toList()));
			}
			list = list.stream().filter(f->f.getDeptId() != null).filter(f->dept.contains(f.getDeptId())).collect(Collectors.toList());
		}
		if (CollectionUtils.isNotEmpty(list)){
			Map<String, List<LoginRecordVO>> collect = new HashMap<>();
			if (type != null && type ==2){
				collect =list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM"))));
			}else {
				collect =list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
			}
			for (Map.Entry<String, List<LoginRecordVO>> entry : collect.entrySet()) {
				ActiveUserVO activeUserVO = new ActiveUserVO();
				activeUserVO.setTime(entry.getKey());
				Integer sum = baseMapper.getUserSum(entry.getKey()+" 23:59:59");
				activeUserVO.setUserSum(sum);
				Integer createSum = 0;
				activeUserVO.setCreateUserSum(createSum);
				long activeUserSum = entry.getValue().stream().map(LoginRecordVO::getUsername).distinct().count();
				activeUserVO.setActiveUserSum(activeUserSum+"");
				activeUserVO.setActivePercent("0%");

				if (activeUserSum != 0){
					if (sum != null && sum !=0){

						activeUserVO.setActivePercent(new BigDecimal(activeUserSum).divide(new BigDecimal(sum),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"))+"%");
					}else {
						activeUserVO.setActivePercent("100%");
					}
				}


				activeUserVO.setOnLineSum(entry.getValue().size());
				activeUserVO.setAvgOnLineSum(new BigDecimal(entry.getValue().size()).divide(new BigDecimal(activeUserSum),BigDecimal.ROUND_UP));
				result.add(activeUserVO);
			}
			result= result.stream().sorted(Comparator.comparing(i -> i.getTime())).collect(Collectors.toList());
		}
		return result;
	}


	@Override
	public List<SmsNumberVO> getAppUse() {
		List<SmsNumberVO> result = new ArrayList<>();
		LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);
		LocalDateTime start = LocalDateTime.now().with(LocalTime.MIN);
		//用户总计bh_login_record
		List<BhUser> listAll = bhUserService.list(Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted,0));

		Integer userAllNum = 0;
		if (CollectionUtils.isNotEmpty(listAll)){
			userAllNum = listAll.size();
		}
		result.add(new SmsNumberVO("总计用户",userAllNum));
		//今日登录
//		LambdaUpdateWrapper<LoginRecord> queryLogin = Wrappers.lambdaUpdate(LoginRecord.class).between(LoginRecord::getCreateTime, start, end);
		List<LoginRecord> list = this.getBhUserRecord(start, end);
		Integer userLogin = 0;
		if (CollectionUtils.isNotEmpty(list)){
			userLogin = list.stream().map(LoginRecord::getUsername).distinct().collect(Collectors.toList()).size();
		}
		result.add(new SmsNumberVO("活跃用户",userLogin));
		//今日新增
		LambdaQueryWrapper<BhUser> queryUser = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted,0).between(BhUser::getCreateTime, start, end);
		List<BhUser> createUser = bhUserService.list(queryUser);
		Integer createUserNum = 0;
		if (CollectionUtils.isNotEmpty(createUser)){
			createUserNum = createUser.size();
		}
		result.add(new SmsNumberVO("今日新增用户",createUserNum));

		return result;
	}

	@Override
	public List<HeadPageVO> getUserData() {
		List<HeadPageVO> result = new ArrayList<>();
		LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);
		LocalDateTime start = LocalDateTime.now().with(LocalTime.MIN);
		//用户总计bh_login_record
		List<BhUser> listAll = bhUserService.list(Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted,0));

		Integer userAllNum = 0;
		if (CollectionUtils.isNotEmpty(listAll)){
			userAllNum = listAll.size();
		}
		result.add(new HeadPageVO("总计用户",userAllNum,null));
		//今日登录
//		LambdaUpdateWrapper<LoginRecord> queryLogin = Wrappers.lambdaUpdate(LoginRecord.class).between(LoginRecord::getCreateTime, start, end);
		List<LoginRecord> list = this.getBhUserRecord(start, end);
		Integer userLogin = 0;
		if (CollectionUtils.isNotEmpty(list)){
			userLogin = list.stream().map(LoginRecord::getUsername).distinct().collect(Collectors.toList()).size();
		}
		result.add(new HeadPageVO("活跃用户",userLogin,null));
		//今日新增
		LambdaQueryWrapper<BhUser> queryUser = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted,0).between(BhUser::getCreateTime, start, end);
		List<BhUser> createUser = bhUserService.list(queryUser);
		Integer createUserNum = 0;
		if (CollectionUtils.isNotEmpty(createUser)){
			createUserNum = createUser.size();
		}
		result.add(new HeadPageVO("今日新增用户",createUserNum,null));

		return result;
	}


	@Override
	public List<HeadPageVO> getDeptRank() {
		List<HeadPageVO> result = new ArrayList<>();
		//获取所有的登录记录
		List<LoginRecordVO> recordList = baseMapper.getList();
		//获取数字滨湖下的第一层级部门
//		LambdaUpdateWrapper<BhDept> query = Wrappers.lambdaUpdate(BhDept.class).eq(BhDept::getIsDeleted, 0).eq(BhDept::getParentId, 373);
//		List<BhDept> deptOneList = bhDeptService.list(query);
		//获取滨湖区下的所有部门信息
//		LambdaUpdateWrapper<BhDept> queryAll = Wrappers.lambdaUpdate(BhDept.class).eq(BhDept::getIsDeleted, 0);
//		List<BhDept> deptAllList = bhDeptService.list(queryAll);

		//分组访问记录 以deptId分组
		Map<Long, List<LoginRecordVO>> recordMap = new HashMap<>();
		List<Long> longs = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(recordList)){
			recordMap = recordList.stream().filter(f -> f.getDeptId() != null).collect(Collectors.groupingBy(LoginRecordVO::getDeptId));
			longs = recordMap.keySet().stream().collect(Collectors.toList());
		}
		Integer count = 0;
		if (CollectionUtils.isNotEmpty(recordList)) {


			Map<Long, BhDept> deptMap = getDeptMap();
			if (CollectionUtils.isNotEmpty(deptMap)) {

				for (Map.Entry<Long, BhDept> entry : deptMap.entrySet()) {
					List<Long> deptIds = Func.toLongList(entry.getValue().getDescription());
					//取出两集合的交集

					List<Long> collect = longs.stream().filter(f -> deptIds.contains(f)).collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(collect)) {
						HeadPageVO headPageVO = new HeadPageVO();
						headPageVO.setName(entry.getValue().getName());
						Integer sum = 0;
						for (Long id : collect) {
							sum += recordMap.get(id).size();
						}
						headPageVO.setNumber(sum);
						result.add(headPageVO);
						count += sum;
					}
				}
			}
		}
		if (CollectionUtils.isNotEmpty(result)){
			for (HeadPageVO headPageVO : result) {
				headPageVO.setPercent(new BigDecimal(headPageVO.getNumber()).divide(new BigDecimal(count),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))+"");
			}
			result =result.stream().sorted(Comparator.comparing(HeadPageVO::getNumber).reversed()).collect(Collectors.toList());
		}
		return result;
	}

	//获取第二级下的所有部门id
	private Map<Long, BhDept> getDeptMap() {
		List<BhDept> allList = bhDeptService.list(Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getIsDeleted,0));
		Map<Long, BhDept> rootMap = allList.stream().filter(o -> o.getParentId() != null && o.getParentId() == 373).collect(Collectors.toMap(BhDept::getDeptId, o -> o));
		Map<Long, List<BhDept>> parentMap = allList.stream().collect(Collectors.groupingBy(BhDept::getParentId));
		for (Map.Entry<Long, BhDept> longBhDeptEntry : rootMap.entrySet()) {
			BhDept pdept = longBhDeptEntry.getValue();
//			pdept.setDescription(pdept.getDeptId()+"");
			pdept.setDescription(children(pdept, parentMap));
		}
//		Map<Long, BhDept> allDepMap = allList.stream().filter(f->f.getParentId() == 373).collect(Collectors.toMap(BhDept::getDeptId, o -> o));
		return rootMap;
	}

	String children(BhDept dept, Map<Long, List<BhDept>> parentMap) {
		List<BhDept> children = parentMap.get(dept.getDeptId());
		StringBuilder str = new StringBuilder();
		str.append(dept.getDeptId());
		if (children != null) {
			for (BhDept child : children) {
//				child.setDescription(dept.getDescription() + "," + child.getDeptId());
				str.append(",").append(child.getDeptId());
				this.children(child, parentMap);
			}
		}
		return str.toString();
	}


	@Override
	public List<LoginRecord> getBhUserRecord(LocalDateTime startTime, LocalDateTime endTime) {
		List<LoginRecord> list = baseMapper.getBhUserRecord(startTime,endTime);
		return list;
	}

	@Override
	public List<StatisticsMapVO> getAccessStatistics() {
		List<StatisticsMapVO> result = new ArrayList<>();
		LocalDateTime end = LocalDateTime.now();
		LocalDateTime start = end.plusMonths(-2L).with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
		//近三个月访问统计
//		LambdaUpdateWrapper<LoginRecord> queryLogin = Wrappers.lambdaUpdate(LoginRecord.class).between(LoginRecord::getCreateTime, start, end);
		List<LoginRecord> list = this.getBhUserRecord(start, end);
		if (CollectionUtils.isNotEmpty(list)){
			//将list进行以时间进行分组
			Map<Integer, List<LoginRecord>> collect = list.stream().collect(Collectors.groupingBy(f -> f.getCreateTime().getMonthValue()));
			for (LocalDateTime i = start; i.isBefore(end);){

				StatisticsMapVO statisticsMapVO = new StatisticsMapVO();
				statisticsMapVO.setName(i.getMonthValue()+"月");
				statisticsMapVO.setNumber(0);
				if (CollectionUtils.isNotEmpty(collect.get(i.getMonthValue()))){
					statisticsMapVO.setNumber(collect.get(i.getMonthValue()).size());
				}
				result.add(statisticsMapVO);
				i=i.plusMonths(1L);
			}

		}else {
			for (LocalDateTime i = start; i.isBefore(end);){
				StatisticsMapVO statisticsMapVO = new StatisticsMapVO();
				statisticsMapVO.setName(i.getMonthValue()+"月");
				statisticsMapVO.setNumber(0);
				result.add(statisticsMapVO);
				i=i.plusMonths(1L);
			}
		}
		return result;
	}

	@Override
	public LoginRecord getLastLoginRecord(String userAccount) {
		LoginRecord list = baseMapper.getLastLoginRecord(userAccount);
		return list;
	}
}
