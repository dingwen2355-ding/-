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

import cn.hutool.http.HttpUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.common.utils.CommonUtil;
import com.jslc.common.utils.HsmUtil;
import com.jslc.modules.job.entiry.ScheduleJobEntity;
import com.jslc.modules.job.service.ScheduleJobService;
import com.jslc.modules.job.utils.ScheduleJob;
import com.jslc.modules.resource.builder.sms.SmsBuilder;
import com.jslc.modules.resource.endpoint.SmsEndpoint;
import com.jslc.modules.resource.utils.SmsUtil;
import com.jslc.modules.system.entity.User;
import com.jslc.modules.szbh.entity.*;
import com.jslc.modules.szbh.service.IBhApplicationManagementService;
import com.jslc.modules.szbh.service.IBhSmsSendService;
import com.jslc.modules.szbh.service.IBhUserService;
import com.jslc.modules.szbh.vo.*;
import com.jslc.modules.szbh.mapper.SmsSendSettingMapper;
import com.jslc.modules.szbh.service.ISmsSendSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.sms.model.SmsResponse;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateTimeUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 短信发送配置 服务实现类
 *
 * @author BladeX
 * @since 2023-06-07
 */
@Service
public class SmsSendSettingServiceImpl extends ServiceImpl<SmsSendSettingMapper, SmsSendSetting> implements ISmsSendSettingService {

	//用于打印日志
	private static Logger logger = LoggerFactory.getLogger(SmsSendSettingServiceImpl.class);
	@Value(value = "${sms.localUrl}")
	private String localUrl;


	@Autowired
	private IBhSmsSendService bhSmsSendService;

	@Autowired
	private IBhUserService bhUserService;
	@Autowired
	private IBhApplicationManagementService bhApplicationManagementService;
	@Autowired
	private ScheduleJobService scheduleJobService;

	@Resource
	private HsmUtil hsmUtil;


	@Override
	public IPage<SmsSendSettingVO> selectSmsSendSettingPage(IPage<SmsSendSettingVO> page, String title, String start , String end, Integer isRegularTime, Integer sendStatus) {
		List<SmsSendSettingVO> list = getSmsSendSettingList(page, title, start , end, isRegularTime, sendStatus);
		return page.setRecords(list);
	}

	private List<SmsSendSettingVO> getSmsSendSettingList(IPage<SmsSendSettingVO> page, String title, String start, String end, Integer isRegularTime, Integer sendStatus) {

		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		if (Func.isNotEmpty(start)){
			startTime = LocalDateTime.of(LocalDate.parse(start, DateTimeUtil.DATE_FORMAT), LocalTime.MIN);

		}

		if (Func.isNotEmpty(end)){
			endTime = LocalDateTime.of(LocalDate.parse(end, DateTimeUtil.DATE_FORMAT), LocalTime.MAX);
		}


		List<SmsSendSettingVO> list = baseMapper.getSmsSendSettingList( page, title, startTime, endTime, isRegularTime, sendStatus);
		if (CollectionUtils.isNotEmpty(list)){
			List<Long> smsIds = list.stream().map(SmsSendSettingVO::getId).collect(Collectors.toList());
			LambdaQueryWrapper<BhSmsSend> query = Wrappers.lambdaQuery(BhSmsSend.class).in(BhSmsSend::getSettingId, smsIds).eq(BhSmsSend::getResult,1);
			List<BhSmsSend> list1 = bhSmsSendService.list(query);
			//根据短信设置id进行分组
			Map<Long,List<BhSmsSend>> map = new HashMap<>();
			if (CollectionUtils.isNotEmpty(list1)){
				map = list1.stream().collect(Collectors.groupingBy(BhSmsSend::getSettingId));
			}
			for (SmsSendSettingVO smsSendSettingVO : list) {
				smsSendSettingVO.setMobileSum(0);
				smsSendSettingVO.setSucceedMobileSum(0);

				if (smsSendSettingVO.getSendStatus() != null){

				}
				smsSendSettingVO.setSendStatusName(smsSendSettingVO.getSendStatus() != null ? (smsSendSettingVO.getSendStatus() == 1 ? "已发送":"未发送"): null);
				smsSendSettingVO.setIsRegularTimeName(smsSendSettingVO.getIsRegularTime() != null ? (smsSendSettingVO.getIsRegularTime() == 1 ? "定时":"即时"): null);
//				(CASE WHEN bsss.send_status = 1 THEN '已发送' ELSE '未发送' END) as  sendStatusName,
//				(CASE WHEN bsss.is_regular_time = 1 THEN '定时' ELSE '即时' END) as  isRegularTimeName

				if (Func.isNotEmpty(smsSendSettingVO.getMobile())){
					smsSendSettingVO.setMobileSum(Func.toStrList(smsSendSettingVO.getMobile()).size());
//					LambdaQueryWrapper<BhSmsSend> query = Wrappers.lambdaQuery(BhSmsSend.class).eq(BhSmsSend::getSettingId, smsSendSettingVO.getId()).eq(BhSmsSend::getResult,1);
//					List<BhSmsSend> list1 = bhSmsSendService.list(query);
					if (CollectionUtils.isNotEmpty(map.get(smsSendSettingVO.getId()))){
						smsSendSettingVO.setSucceedMobileSum(map.get(smsSendSettingVO.getId()).size());
					}
				}
			}
		}
		return list;
	}


	@Override
	public Boolean submit(SmsSendSetting smsSendSetting) {


		Boolean flag = false;
		if (Func.isEmpty(smsSendSetting.getId())){ // 新增
			if (smsSendSetting.getIsRegularTime() == 0){

				smsSendSetting.setRegularTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
			}
			smsSendSetting.setCreateUser(AuthUtil.getUserId());
			smsSendSetting.setIsDeleted(0);
			smsSendSetting.setCreateTime(LocalDateTime.now());
			smsSendSetting.setUpdateUser(AuthUtil.getUserId());
			smsSendSetting.setUpdateTime(LocalDateTime.now());
			if (smsSendSetting.getIsRegularTime() == 0){
				smsSendSetting.setSendStatus(1);
			}else {
				smsSendSetting.setSendStatus(0);
			}


			flag = true;
		}else {//修改
			smsSendSetting.setUpdateUser(AuthUtil.getUserId());
			smsSendSetting.setUpdateTime(LocalDateTime.now());
		}
		Boolean result = this.saveOrUpdate(smsSendSetting);
		if (result  && smsSendSetting.getIsRegularTime() == 0 && flag){
			sendMsg(null, smsSendSetting.getMobile(),smsSendSetting.getContent(),smsSendSetting.getId());
		}

		//定时任务调用定时接口
		if(result && smsSendSetting.getIsRegularTime() == 1){

			Long id = smsSendSetting.getId();

			String cronExpression = CommonUtil.getCronExpression(smsSendSetting.getRegularTime());
			JSONObject json = new JSONObject();
			json.put("mobile",smsSendSetting.getMobile());
			json.put("content",smsSendSetting.getContent());
			json.put("bizId",id);

			ScheduleJobEntity scheduleJob = new ScheduleJobEntity();
			scheduleJob.setBeanName("sendMsmTask");
			scheduleJob.setParams(json.toJSONString());
			scheduleJob.setCronExpression(cronExpression);
			scheduleJob.setBizId(id);
			scheduleJob.setStatus(0);

			ScheduleJobEntity scheduleJobEntity = scheduleJobService.getOne(Wrappers.<ScheduleJobEntity>query().lambda().eq(ScheduleJobEntity::getBizId, id));
			if(scheduleJobEntity!=null && scheduleJobEntity.getJobId()!=null){
				scheduleJob.setJobId(scheduleJobEntity.getJobId());
				scheduleJob.setCreateTime(scheduleJobEntity.getCreateTime());
				scheduleJobService.update(scheduleJob);
			}else{
				scheduleJobService.saveJob(scheduleJob);
			}
		}


		return result;
	}

	private void updateSmsSetting(Long id) {
		SmsSendSetting one = this.getById(id);
		one.setSendStatus(1);
		this.saveOrUpdate(one);

	}
	public void updateSmsSend(List<Long> ids) {
		List<BhSmsSend> list = bhSmsSendService.listByIds(ids);
		if (Func.isNotEmpty(list)) {
			list.forEach(o -> {
				o.setResult(1);
			});
			bhSmsSendService.saveOrUpdateBatch(list);
		}
	}


	@Override
	public void resultNotification(String majorMobile, String userName, String message, String appId, String appName) {
		ResultVO sendFlag = new ResultVO();
		//权限审核通知
		List<Long> recordIds = resultRecord(majorMobile,userName, message, appId, appName);
//		String phones = users.stream().map(User::getPhone).collect(Collectors.joining(","));
		if (Func.isEmpty(majorMobile)){
			throw new ServiceException("通知人员手机号为null");
		}
		try {
			String url = localUrl + "?mobile=" + majorMobile + "&msg=" + message;
			String backResult = HttpUtil.get(url);
			if (Func.isNotEmpty(backResult)) {
				sendFlag = JSONObject.parseObject(backResult, ResultVO.class);
			}
			//发送成功回写结果
			if (sendFlag.getCode().equals("200")) {
				updateSmsSend(recordIds);
//				BhSmsSend one = bhSmsSendService.getById(recordId);
//				one.setResult(1);
//				bhSmsSendService.saveOrUpdate(one);
//				updateSmsSetting(id);
			}
		} catch (Exception e) {
			logger.error("初始用户提示短信发送失败：{}，发送内容：{}", sendFlag.getMessage(), "mobile:" + majorMobile + ",msg:" + message);
		}
	}

	private List<Long> resultRecord(String majorMobile, String userName, String message, String appId, String appName) {

		List<Long> result = null;
//		users = users.stream().filter(f->f.getPhone() != null).collect(Collectors.toList());
		if (Func.isEmpty(majorMobile)){
			return null;
		}
//		String phones = users.stream().map(User::getPhone).collect(Collectors.joining(","));
		List<BhSmsSend> list = new ArrayList<>();

		BhSmsSend bhSmsSend = new BhSmsSend();
		bhSmsSend.setNoticePerson(userName);
		bhSmsSend.setNoticeTime(LocalDateTime.now());
		bhSmsSend.setMobile(majorMobile);
		bhSmsSend.setEventType("权限审核通知");
		bhSmsSend.setApplication(appName);
		bhSmsSend.setAppId(appId);
		bhSmsSend.setContent(message);
		bhSmsSend.setResult(0);
		bhSmsSend.setCreateTime(LocalDateTime.now());
//			bhSmsSend.setSettingId(id);
		list.add(bhSmsSend);


		boolean saveResult = bhSmsSendService.saveBatch(list);
		if (saveResult) {
			result = list.stream().map(BhSmsSend::getId).collect(Collectors.toList());
		}
		return result;

	}


	@Override
	public void applictionNotification(List<User> users, String message , String appId, String appName) {
		ResultVO sendFlag = new ResultVO();
		if (CollectionUtils.isNotEmpty(users)){
			users = users.stream().filter(f->f.getPhone() != null && Pattern.matches("^1[3-9]\\d{9}$", f.getPhone())).collect(Collectors.toList());
		}


		if (CollectionUtils.isNotEmpty(users)){
//			return null;

			//即时 保存时发送短信
			List<Long> recordIds = applicationRecord(users, message, appId, appName);
			String phones = users.stream().filter(f->f.getPhone() != null).map(f->f.getPhone()).collect(Collectors.joining(","));

			if (Func.isEmpty(phones)){
				throw new ServiceException("请检查用户手机号");
			}
			try {
				String url = localUrl + "?mobile=" + phones + "&msg=" + message;
				logger.info("发送的地址信息："+url);
				String backResult = HttpUtil.get(url);
				logger.info("短信发送返回结果："+url);
				if (Func.isNotEmpty(backResult)) {
					sendFlag = JSONObject.parseObject(backResult, ResultVO.class);
					logger.info("短信发送返回结果json："+sendFlag);
				}
				//发送成功回写结果
				if (sendFlag.getCode().equals("200")) {
					updateSmsSend(recordIds);
	//				BhSmsSend one = bhSmsSendService.getById(recordId);
	//				one.setResult(1);
	//				bhSmsSendService.saveOrUpdate(one);
	//				updateSmsSetting(id);
				}
			} catch (Exception e) {
				logger.error("初始用户提示短信发送失败：{}，发送内容：{}", sendFlag.getMessage(), "mobile:" + phones + ",msg:" + message);
			}
		}
	}

	private List<Long> applicationRecord(List<User> users, String message , String appId, String appName) {
		List<Long> result = null;
		users = users.stream().filter(f->f.getPhone() != null).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(users)){
			return null;
		}
//		String phones = users.stream().map(User::getPhone).collect(Collectors.joining(","));
		List<BhSmsSend> list = new ArrayList<>();
//		List<String> mobiles = Func.split(phones, ',');

//		List<BhUser> userList = bhUserService.list(Wrappers.<BhUser>query().lambda().in(BhUser::getMajorMobile, mobiles).eq(BhUser::getIsDeleted, 0));
//		Map<String, String> userMap = null;
//		if (Func.isNotEmpty(userList)) {
//			userMap = userList.stream().collect(Collectors.toMap(BhUser::getMajorMobile, BhUser::getName, (k1, k2) -> k1));
//		}

//		BhApplicationManagement bhApplicationManagement = null;
//		if (Func.isNotEmpty(appId)) {
//			bhApplicationManagement = bhApplicationManagementService.getOne(Wrappers.<BhApplicationManagement>query().lambda()
//					.eq(BhApplicationManagement::getAppId, appId).eq(BhApplicationManagement::getIsDeleted, 0));
//		}

		for (User user : users) {
			BhSmsSend bhSmsSend = new BhSmsSend();
			bhSmsSend.setNoticePerson(user.getName());
			bhSmsSend.setNoticeTime(LocalDateTime.now());
			bhSmsSend.setMobile(user.getPhone());
			bhSmsSend.setEventType("权限申请短信通知");
			bhSmsSend.setApplication(appName);
			bhSmsSend.setAppId(appId);
			bhSmsSend.setContent(message);
			bhSmsSend.setResult(0);
			bhSmsSend.setCreateTime(LocalDateTime.now());
//			bhSmsSend.setSettingId(id);
			list.add(bhSmsSend);
		}

		boolean saveResult = bhSmsSendService.saveBatch(list);
		if (saveResult) {
			result = list.stream().map(BhSmsSend::getId).collect(Collectors.toList());
		}
		return result;


	}

	@Override
	public void sendMsg(String appId, String mobile, String msg,Long id){
		ResultVO sendFlag = new ResultVO();
		//即时 保存时发送短信
		List<Long> longs = addSmsSend(appId, mobile, msg, id);
		try {
			String url = localUrl + "?mobile=" + mobile + "&msg=" + msg;
			String backResult = HttpUtil.get(url);
			if (Func.isNotEmpty(backResult)) {
				sendFlag = JSONObject.parseObject(backResult, ResultVO.class);
			}
			//发送成功回写结果
			if (sendFlag.getCode().equals("200")) {
				updateSmsSend(longs);
				updateSmsSetting(id);
			}
		} catch (Exception e) {
			logger.error("初始用户提示短信发送失败：{}，发送内容：{}", sendFlag.getMessage(), "mobile:" + mobile + ",msg:" + msg);
		}

	}

	@Override
	public IPage<BhSmsSend> getDetail(Long id, IPage<BhSmsSend> page) {
		LambdaQueryWrapper<BhSmsSend> query = Wrappers.lambdaQuery(BhSmsSend.class).eq(BhSmsSend::getSettingId, id);
		List<BhSmsSend> list = bhSmsSendService.list(query);
		IPage iPage = CommonUtil.manualPage(page, list);
		return iPage;
	}

	@Override
	public void downLoad(HttpServletResponse response, Long id) {
		OutputStream out = null;
		LambdaQueryWrapper<BhSmsSend> query = Wrappers.lambdaQuery(BhSmsSend.class).eq(BhSmsSend::getSettingId, id);
		List<BhSmsSend> list = bhSmsSendService.list(query);
		try {
			String fileName = "短信发送明细.xlsx";

			out = response.getOutputStream();
			//设置返回头
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");


			List<List<String>> headList = new ArrayList<List<String>>();
			List<String> headTitle = new ArrayList<String>();
			headTitle.add("手机号");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("用户名称");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("发送状态");
			headList.add(headTitle);

			// 所有行的集合
			List<List<Object>> resultList = new ArrayList<List<Object>>();

			for (BhSmsSend bhSmsSend : list) {
				List<Object> row = new ArrayList<Object>();
				row.add(bhSmsSend.getMobile());
				row.add(bhSmsSend.getNoticePerson());
				row.add(bhSmsSend.getResult() == 1 ? "成功":"失败");

				resultList.add(row);

			}

			EasyExcel.write(response.getOutputStream())
					.head(headList)
					.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
					.sheet("短信发送明细")
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
	public String getLoginUserPhone() {

		String phones = bhUserService.getLoginPhones();
		return phones;
	}

	@Override
	public Boolean sendSmsNotice(String msg) {
		ResultVO sendFlag = new ResultVO();
		Boolean flag = false;
		List<String> phones = baseMapper.getUserPhones();
		if (CollectionUtils.isNotEmpty(phones)){
			phones = phones.stream().map(f->hsmUtil.decryptSM4(f)).collect(Collectors.toList());
		}
		if (CollectionUtils.isEmpty(phones)){
			throw new ServiceException("请检查用户手机号");
		}
		try {
			String url = localUrl + "?mobile=" + phones + "&msg=" + msg;
			String backResult = HttpUtil.get(url);
			if (Func.isNotEmpty(backResult)) {
				sendFlag = JSONObject.parseObject(backResult, ResultVO.class);
			}
			//发送成功回写结果
			if (sendFlag.getCode().equals("200")) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("初始用户提示短信发送失败：{}，发送内容：{}", sendFlag.getMessage(), "mobile:" + phones + ",msg:" + msg);
		}
		return flag;
	}

	@Override
	public Boolean testSend(String phone, String msg) {
		ResultVO sendFlag = new ResultVO();
		Boolean flag = false;
		//即时 保存时发送短信
//		List<Long> longs = addSmsSend(appId, mobile, msg, id);
		try {
			String url = localUrl + "?mobile=" + phone + "&msg=" + msg;
			String backResult = HttpUtil.get(url);
			if (Func.isNotEmpty(backResult)) {
				sendFlag = JSONObject.parseObject(backResult, ResultVO.class);
			}
			//发送成功回写结果
			if (sendFlag.getCode().equals("200")) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("初始用户提示短信发送失败：{}，发送内容：{}", sendFlag.getMessage(), "mobile:" + phone + ",msg:" + msg);
		}
		return flag;
	}

	@Override
	@Transactional
	public Boolean dels(List<Long> ids) {
		boolean result = false;
		List<SmsSendSetting> smsSendSettings = this.list(Wrappers.<SmsSendSetting>query().lambda().in(SmsSendSetting::getId,ids).eq(SmsSendSetting::getIsRegularTime,1));

		LambdaUpdateWrapper<SmsSendSetting> set = Wrappers.lambdaUpdate(SmsSendSetting.class).in(SmsSendSetting::getId, ids).set(SmsSendSetting::getIsDeleted, 1);
		result = this.update(set);

		//同步删除对应定时任务
		if(Func.isNotEmpty(smsSendSettings) && result){
			List<Long> bizIds = smsSendSettings.stream().map(SmsSendSetting::getId).collect(Collectors.toList());
			List<ScheduleJobEntity> list = scheduleJobService.list(Wrappers.<ScheduleJobEntity>query().lambda().in(ScheduleJobEntity::getBizId, bizIds));
			if(Func.isNotEmpty(list)){
				List<Long> jobIds = list.stream().map(ScheduleJobEntity::getJobId).collect(Collectors.toList());
				long[] array = new long[jobIds.size()];
				for (int i = 0; i < list.size(); i++) {
					array[i] = jobIds.get(i);
				}
				scheduleJobService.deleteBatch(array);
			}
		}

		return result;
	}

	public List<Long> addSmsSend(String appId, String mobile, String msg,Long id) {

		List<Long> result = new ArrayList<>();

		List<BhSmsSend> list = new ArrayList<>();
		List<String> mobiles = Func.split(mobile, ',');

		List<BhUser> userList = bhUserService.list(Wrappers.<BhUser>query().lambda().in(BhUser::getMajorMobile, mobiles).eq(BhUser::getIsDeleted, 0));
		Map<String, String> userMap = null;
		if (Func.isNotEmpty(userList)) {
			userMap = userList.stream().collect(Collectors.toMap(BhUser::getMajorMobile, BhUser::getName, (k1, k2) -> k1));
		}

		BhApplicationManagement bhApplicationManagement = null;
		if (Func.isNotEmpty(appId)) {
			bhApplicationManagement = bhApplicationManagementService.getOne(Wrappers.<BhApplicationManagement>query().lambda()
					.eq(BhApplicationManagement::getAppId, appId).eq(BhApplicationManagement::getIsDeleted, 0));
		}

		for (String mb : mobiles) {
			BhSmsSend bhSmsSend = new BhSmsSend();
			bhSmsSend.setNoticePerson(userMap != null ? userMap.get(mb) : null);
			bhSmsSend.setNoticeTime(LocalDateTime.now());
			bhSmsSend.setMobile(mb);
			bhSmsSend.setEventType(Func.isEmpty(appId) ? "短信发送配置" : null);
			bhSmsSend.setApplication(bhApplicationManagement != null ? bhApplicationManagement.getName() : null);
			bhSmsSend.setAppId(appId);
			bhSmsSend.setContent(msg);
			bhSmsSend.setResult(0);
			bhSmsSend.setCreateTime(LocalDateTime.now());
			bhSmsSend.setSettingId(id);
			list.add(bhSmsSend);
		}

		boolean saveResult = bhSmsSendService.saveBatch(list);
		if (saveResult) {
			result = list.stream().map(BhSmsSend::getId).collect(Collectors.toList());
		}
		return result;
	}
}
