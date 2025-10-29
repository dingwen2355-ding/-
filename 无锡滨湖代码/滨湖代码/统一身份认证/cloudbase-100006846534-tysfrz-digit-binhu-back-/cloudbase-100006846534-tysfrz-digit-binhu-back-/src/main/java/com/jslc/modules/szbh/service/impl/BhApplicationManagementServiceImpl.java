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

import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jslc.common.utils.AppUtils;
import com.jslc.common.utils.CommonUtil;
import com.jslc.common.vo.AppIdAndSecretVO;
import com.jslc.modules.szbh.entity.*;
import com.jslc.modules.szbh.service.*;
import com.jslc.modules.szbh.vo.BhApplicationManagementVO;
import com.jslc.modules.szbh.mapper.BhApplicationManagementMapper;
import com.jslc.modules.szbh.vo.BhUserApplicationGroupRestVO;
import com.jslc.modules.szbh.vo.BhUserApplicationManagementRestVO;
import com.jslc.modules.szbh.vo.CommonMapVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.oss.model.BladeFile;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 应用管理 服务实现类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Service
public class BhApplicationManagementServiceImpl extends ServiceImpl<BhApplicationManagementMapper, BhApplicationManagement> implements IBhApplicationManagementService {

	@Lazy
	@Autowired
	private IBhApplicationCategoryService iBhApplicationCategoryService;

	@Autowired
	private IBhSystemParamService iBhSystemParamService;
	@Autowired
	private IOauthClientDetailsService iOauthClientDetailsService;
	@Autowired
	private IFileService fileService;

	@Autowired
	private IBhUserApplicationManagementService bhUserApplicationManagementService;
	@Autowired
	private IAuthorityApplyForService authorityApplyForService;




	@Override
	public BhApplicationManagementVO getDetail(Long id) {
		BhApplicationManagementVO detail = baseMapper.getDetail(id);
		//系统截图
		if (Func.isNotEmpty(detail.getLogoId())){
			List<BhFile> filesByFileIds = fileService.listByIds(Func.toLongList(detail.getLogoId()));
			detail.setFiles(filesByFileIds);
		}

		//系统图标
		if (Func.isNotEmpty(detail.getIconId())){
			List<BhFile> iconFiles = fileService.listByIds(Func.toLongList(detail.getIconId()));
			detail.setIconFiles(iconFiles);
		}

		BhApplicationCategory category = iBhApplicationCategoryService.getById(detail.getCategoryId());
		if (Func.isNotEmpty(category)){
			detail.setType(category.getType());
		}
		return detail;
	}

	@Override
	public IPage<BhApplicationManagementVO> selectBhApplicationManagementPage(IPage<BhApplicationManagementVO> page, String name,String appCategory,Integer isGround, Integer isRecommend ) {
		List<Long> appCategoryIds = new ArrayList<>();

		if (Func.isNotEmpty(appCategory)){
			//使用递归 去重获取其下 和本身的数据
			List<Long> ids = Func.toLongList(appCategory);
			List<BhApplicationCategory> bhApplicationCategories = queryAllDepartmentId(ids);
			if (CollectionUtils.isNotEmpty(bhApplicationCategories)){
				appCategoryIds =bhApplicationCategories.stream().map(BhApplicationCategory::getId).distinct().collect(Collectors.toList());

			}
			appCategoryIds.addAll(ids);
		}
		if (CollectionUtils.isEmpty(appCategoryIds)){
			appCategoryIds=null;
		}
		List<BhApplicationManagementVO> list = baseMapper.getPageList(null, name, appCategoryIds, isGround,  isRecommend);
		if (CollectionUtils.isNotEmpty(list) && CollectionUtils.isNotEmpty(appCategoryIds)){
			List<Long> ids = appCategoryIds;
			list = list.stream().filter(d->!d.getAppId().equals("c3piaGdldHRva2Vu")).filter(f->f.getCategoryId() != null).filter(k-> ids.contains(k.getCategoryId())).collect(Collectors.toList());
		}
		IPage iPage = CommonUtil.manualPage(page, list);
		return iPage;
	}


	/**
	 * 递归查询
	 * @param ids
	 * @return
	 */
	public List<BhApplicationCategory> queryAllDepartmentId(List<Long> ids){
		LambdaQueryWrapper<BhApplicationCategory> query = Wrappers.lambdaQuery(BhApplicationCategory.class).eq(BhApplicationCategory::getIsDeleted, 0).in(BhApplicationCategory::getParentId, ids);
		//根据父ID查询部门
		List<BhApplicationCategory> departments = iBhApplicationCategoryService.list(query);
		if (CollectionUtils.isNotEmpty(departments)) {
			//拿到当前所有部门ID
			List<Long> parentIds = departments.stream().map(item -> item.getId()).collect(Collectors.toList());
			//拼接子部门查询结果
			departments.addAll(queryAllDepartmentId(parentIds));
			return departments;
		} else {
			//如果没有下级部门那么我们就返回空集合，结束递归。
			return Lists.newArrayList();
		}
	}



	@Override
	public Boolean submit(BhApplicationManagementVO bhApplicationManagementVO) {
        BhApplicationManagement bhApplicationManagement = new BhApplicationManagement();
        BeanUtil.copy(bhApplicationManagementVO,bhApplicationManagement);
//		判断唯一性appid
//		LambdaQueryWrapper<BhApplicationManagement> query = Wrappers.lambdaQuery(BhApplicationManagement.class).eq(BhApplicationManagement::getIsDeleted, 0).eq(BhApplicationManagement::getAppId, bhApplicationManagement.getAppId());
//		BhApplicationManagement one = this.getOne(query);
		Integer type = bhApplicationManagementVO.getType();
//		if (bhApplicationManagement.getCategoryId() != null){
//			BhApplicationCategory one = iBhApplicationCategoryService.getById(bhApplicationManagement.getCategoryId());
//			type = one.getType();
//		}
		Boolean flag = false;
		if (Func.isEmpty(bhApplicationManagement.getId())){ // 新增
			bhApplicationManagement.setCreateUser(AuthUtil.getUserId());
			bhApplicationManagement.setIsDeleted(0);
			bhApplicationManagement.setCreateTime(LocalDateTime.now());
			bhApplicationManagement.setDataSource(2);
			bhApplicationManagement.setIsUpdated(0);
			bhApplicationManagement.setIsEnable(1);
			if (type == 1) {
				//获取AppId 和 AppSecret
				AppIdAndSecretVO appIdAndSecret = AppUtils.createAppIdAndSecret();

				bhApplicationManagement.setAppId(appIdAndSecret.getAppId());
				bhApplicationManagement.setAppSecret(appIdAndSecret.getAppSecret());
			}
			bhApplicationManagement.setIsEnable(1);
//			bhApplicationManagement.setAppSecret(getSecret());
//			if (Func.isNotEmpty(one)){
//				throw new ServiceException("当前AppId已存在!");
//			}
		}else {//修改
			bhApplicationManagement.setUpdateUser(AuthUtil.getUserId());
			bhApplicationManagement.setUpdateTime(LocalDateTime.now());
			bhApplicationManagement.setIsUpdated(1);
//			if (Func.isNotEmpty(one) && one.getId().longValue() != bhApplicationManagement.getId().longValue()){
//				throw new ServiceException("当前AppId已存在!");
//			}
		}
		flag = this.saveOrUpdate(bhApplicationManagement);

		if (flag && type == 1){
			OauthClientDetails oauthClientDetails = new OauthClientDetails();
			oauthClientDetails.setClientId(bhApplicationManagement.getAppId() != null ?bhApplicationManagement.getAppId() : null);
			oauthClientDetails.setClientSecret(bhApplicationManagement.getAppSecret() != null ? bhApplicationManagement.getAppSecret(): null);
			oauthClientDetails.setWebServerRedirectUri(bhApplicationManagement.getUrl());
			oauthClientDetails.setScope("all");
			oauthClientDetails.setAuthorizedGrantTypes("authorization_code,refresh_token");
			oauthClientDetails.setAccessTokenValidity(7200);
			oauthClientDetails.setAutoapprove("true");
			iOauthClientDetailsService.submit(oauthClientDetails);
		}
		if (flag && type == 1){
			BhSystemParam bhSystemParam = new BhSystemParam();
			LambdaQueryWrapper<BhSystemParam> query = Wrappers.lambdaQuery(BhSystemParam.class).eq(BhSystemParam::getName, bhApplicationManagement.getAppId());
			BhSystemParam one = iBhSystemParamService.getOne(query);
			if (one != null){
				bhSystemParam.setId(one.getId());
			}
			bhSystemParam.setName(bhApplicationManagement.getAppId());
			iBhSystemParamService.saveOrUpdate(bhSystemParam);
		}
		return flag;
	}

	@Override
	public Boolean changeEnable(Long id) {
		Integer state = 0;
		BhApplicationManagement one = this.getById(id);
		if (Func.isEmpty(one.getIsEnable()) || one.getIsEnable() == 0){
			state = 1;
		}
		LambdaUpdateWrapper<BhApplicationManagement> set = Wrappers.lambdaUpdate(BhApplicationManagement.class).eq(BhApplicationManagement::getId, id).set(BhApplicationManagement::getIsEnable, state);
		return  this.update(set);
	}

	@Override
	public Boolean changeGround(Long id) {
		Integer state = 0;
		BhApplicationManagement one = this.getById(id);
		if (Func.isEmpty(one.getIsGround()) || one.getIsGround() == 0){
			state = 1;
		}
		LambdaUpdateWrapper<BhApplicationManagement> set = Wrappers.lambdaUpdate(BhApplicationManagement.class).eq(BhApplicationManagement::getId, id).set(BhApplicationManagement::getIsGround, state);
		return  this.update(set);
	}

	@Override
	public Boolean del(List<Long> ids) {
		Boolean flag = false;
		LambdaUpdateWrapper<BhApplicationManagement> set = Wrappers.lambdaUpdate(BhApplicationManagement.class).in(BhApplicationManagement::getId, ids).set(BhApplicationManagement::getIsDeleted, 1);
		flag= this.update(set);
		if (flag){
			LambdaQueryWrapper<BhApplicationManagement> query = Wrappers.lambdaQuery(BhApplicationManagement.class).in(BhApplicationManagement::getId, ids).eq(BhApplicationManagement::getIsDeleted, 0);
			List<BhApplicationManagement> list = this.list(query);
			if (CollectionUtils.isNotEmpty(list)){
				//将oauth_client_details  和 bh_system_param这个表删除
				List<String> collect = list.stream().map(BhApplicationManagement::getAppId).collect(Collectors.toList());
				if (CollectionUtils.isNotEmpty(collect)) {
					LambdaQueryWrapper<BhSystemParam> query2 = Wrappers.lambdaQuery(BhSystemParam.class).in(BhSystemParam::getName, collect);
					List<BhSystemParam> list1 = iBhSystemParamService.list(query2);
					if (CollectionUtils.isNotEmpty(list1)) {
						List<Long> id = list1.stream().map(BhSystemParam::getId).collect(Collectors.toList());
						iBhSystemParamService.removeBatchByIds(id);
					}
					iOauthClientDetailsService.delAll(collect);
				}

			}
		}
		return flag;
	}

	@Override
	public String getSecret() {
		UUID uuid = UUID.randomUUID();
		String secret = uuid.toString().toLowerCase();
		return secret;
	}

	@Override
	public List<BhApplicationManagement> getApplication(String applicationId) {
		List<Long> ids = Func.toLongList(applicationId);
		LambdaUpdateWrapper<BhApplicationManagement> query = Wrappers.lambdaUpdate(BhApplicationManagement.class).in(BhApplicationManagement::getId, ids).eq(BhApplicationManagement::getIsDeleted,0);
		List<BhApplicationManagement> list = this.list(query);
		return list;

	}

	@Override
	public List<BhApplicationManagement> getList(String key) {

		List<BhApplicationManagement> list = baseMapper.getList(key);
		if (CollectionUtils.isNotEmpty(list)){
			//过滤数字滨湖后台
			list = list.stream().filter(f->!f.getAppId().contains("ZmVLiSl0")).collect(Collectors.toList());
		}
		return list;
	}


	/*********************前台接口**************************/

	@Override
	public List<BhApplicationManagementVO> getUserApp(String name) {
		List<BhApplicationManagementVO> appList = baseMapper.getUserApplicationManagementByAccount(AuthUtil.getUserAccount(), name);
		List<BhApplicationManagementVO> result = new ArrayList<>();
		LocalDateTime dateTime = LocalDateTime.now();
		if (CollectionUtils.isNotEmpty(appList)) {

			Map<Long, List<BhApplicationManagementVO>> collect = appList.stream().collect(Collectors.groupingBy(BhApplicationManagementVO::getId));
			for (Map.Entry<Long, List<BhApplicationManagementVO>> entry : collect.entrySet()) {
				BhApplicationManagementVO bhApplicationManagementVO = entry.getValue().stream().filter(f -> f.getUserTime() != null).max(Comparator.comparing(BhApplicationManagementVO::getUserTime)).orElse(null);
				if (bhApplicationManagementVO != null) {
					result.add(bhApplicationManagementVO);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(result)){


			//统一身份认证管理系统ZmVLiSl0、里约网关系统j3n7f2IW
			result= result.stream().filter(f->f.getAppId() != null && !f.getAppId().equals("ZmVLiSl0") && !f.getAppId().equals("j3n7f2IW")).sorted(Comparator.comparing(BhApplicationManagementVO::getAppOrder,Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
			for (BhApplicationManagementVO f : result) {
				//系统截图
				if (Func.isNotEmpty(f.getLogoId())){
					List<BhFile> filesByFileIds = fileService.listByIds(Func.toLongList(f.getLogoId()));
					f.setFiles(filesByFileIds);
				}

				//系统图标
				if (Func.isNotEmpty(f.getIconId())){
					List<BhFile> iconFiles = fileService.listByIds(Func.toLongList(f.getIconId()));
					f.setIconFiles(iconFiles);
				}

				if (ChronoUnit.DAYS.between(f.getUserTime(), dateTime) <= 7){//新的
					f.setIsNew(1);
				}else {
					f.setIsNew(0);
				}

			}
		}
		return result;
	}


	@Override
	public List<BhDept> getUserDept() {
		List<BhDept> appList = baseMapper.applicationDept();

		return appList;
	}

	@Override
	public List<BhApplicationManagementVO> getUserAppById(Long userId) {
		List<BhApplicationManagementVO> list = baseMapper.getUserAppById(userId);
		//根据应用id去重
		if (CollectionUtils.isNotEmpty(list)){
			list =list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
					() -> new TreeSet<>(Comparator.comparing(BhApplicationManagementVO::getId)))
					, ArrayList::new));
		}
		return list;
	}

	@Override
	public IPage<BhApplicationManagementVO> applicationByClassificationAndDept(IPage<BhApplicationManagementVO> page,Long applicationClassificationId, Long deptId, Integer isAuthorization,String appName) {
		List<BhApplicationManagementVO> appList = baseMapper.getApplicationByClassificationAndDept(applicationClassificationId, deptId, appName, null);
		if (CollectionUtils.isNotEmpty(appList)) {
			//过滤是否授权 根据当前账户和应用
			for (BhApplicationManagementVO bhApplicationManagementVO : appList) {
				Boolean flag = true;

				//已授权
				List<BhUserApplicationManagement> list = bhUserApplicationManagementService.getUserApp(AuthUtil.getUserAccount() ,bhApplicationManagementVO.getId());
				if (CollectionUtils.isNotEmpty(list)) {
					bhApplicationManagementVO.setIsAuthorization(2);
					flag = false;
				}
				//待审核
				if (flag) {
					List<AuthorityApplyFor> applyList = authorityApplyForService.list(Wrappers.lambdaQuery(AuthorityApplyFor.class).eq(AuthorityApplyFor::getAccount, AuthUtil.getUserAccount())
							.eq(AuthorityApplyFor::getApplicationManagementId, bhApplicationManagementVO.getId())
							.eq(AuthorityApplyFor::getReviewStatus, 0));

					if (CollectionUtils.isNotEmpty(applyList)) {
						bhApplicationManagementVO.setIsAuthorization(1);
					}

				}

				//系统截图
				if (Func.isNotEmpty(bhApplicationManagementVO.getLogoId())){
					List<BhFile> filesByFileIds = fileService.listByIds(Func.toLongList(bhApplicationManagementVO.getLogoId()));
					bhApplicationManagementVO.setFiles(filesByFileIds);
				}

				//系统图标
				if (Func.isNotEmpty(bhApplicationManagementVO.getIconId())){
					List<BhFile> iconFiles = fileService.listByIds(Func.toLongList(bhApplicationManagementVO.getIconId()));
					bhApplicationManagementVO.setIconFiles(iconFiles);
				}



			}
			if (isAuthorization != null){
				appList = appList.stream().filter(f->f.getIsAuthorization() == isAuthorization).collect(Collectors.toList());
			}
			if (CollectionUtils.isNotEmpty(appList)){
				//统一身份认证管理系统 ZmVLiSl0、里约网关系统 j3n7f2IW
				appList = appList.stream().filter(f->!f.getAppId().equals("ZmVLiSl0") && !f.getAppId().equals("j3n7f2IW")).collect(Collectors.toList());
			}
		}
		IPage iPage = CommonUtil.manualPage(page, appList);
		return iPage;


	}

	@Override
	public List<String> getUserAppId(String account) {
		List<String> result = new ArrayList<>();
		List<BhApplicationManagementVO> appList = baseMapper.getUserApplicationManagementByAccount(account,null);
		if(Func.isNotEmpty(appList)){
			result = appList.stream().filter(o->Func.isNotEmpty(o.getAppId())).map(BhApplicationManagementVO::getAppId).collect(Collectors.toList());
			result = result.stream().distinct().collect(Collectors.toList());
		}

		return result;
	}


	@Override
	public List<BhApplicationManagementVO> getAppSumData(LocalDateTime start, LocalDateTime end) {
		List<BhApplicationManagementVO> list = baseMapper.getAppSumData(start,end);
		return list;
	}


	@Override
	public Long submitV1(BhApplicationManagementVO bhApplicationManagementVO) {
		Long resultId = null;
		BhApplicationManagement bhApplicationManagement = new BhApplicationManagement();
		BeanUtil.copy(bhApplicationManagementVO,bhApplicationManagement);
		Integer type = bhApplicationManagementVO.getType();
		Boolean flag = false;
		if (Func.isEmpty(bhApplicationManagement.getId())){ // 新增
			bhApplicationManagement.setCreateUser(AuthUtil.getUserId());
			bhApplicationManagement.setIsDeleted(0);
			bhApplicationManagement.setCreateTime(LocalDateTime.now());
			bhApplicationManagement.setDataSource(2);
			bhApplicationManagement.setIsUpdated(0);
			if (type == 1) {
				//获取AppId 和 AppSecret
				AppIdAndSecretVO appIdAndSecret = AppUtils.createAppIdAndSecret();

				bhApplicationManagement.setAppId(appIdAndSecret.getAppId());
				bhApplicationManagement.setAppSecret(appIdAndSecret.getAppSecret());
			}
			bhApplicationManagement.setIsEnable(1);
		}else {//修改
			bhApplicationManagement.setUpdateUser(AuthUtil.getUserId());
			bhApplicationManagement.setUpdateTime(LocalDateTime.now());
			bhApplicationManagement.setIsUpdated(1);
		}
		flag = this.saveOrUpdate(bhApplicationManagement);
		resultId = bhApplicationManagement.getId();

		if (flag && type == 1){
			OauthClientDetails oauthClientDetails = new OauthClientDetails();
			oauthClientDetails.setClientId(bhApplicationManagement.getAppId() != null ?bhApplicationManagement.getAppId() : null);
			oauthClientDetails.setClientSecret(bhApplicationManagement.getAppSecret() != null ? bhApplicationManagement.getAppSecret(): null);
			oauthClientDetails.setWebServerRedirectUri(bhApplicationManagement.getUrl());
			oauthClientDetails.setScope("all");
			oauthClientDetails.setAuthorizedGrantTypes("authorization_code,refresh_token");
			oauthClientDetails.setAccessTokenValidity(7200);
			oauthClientDetails.setAutoapprove("true");
			iOauthClientDetailsService.submit(oauthClientDetails);
		}
		if (flag && type == 1){
			BhSystemParam bhSystemParam = new BhSystemParam();
			LambdaQueryWrapper<BhSystemParam> query = Wrappers.lambdaQuery(BhSystemParam.class).eq(BhSystemParam::getName, bhApplicationManagement.getAppId());
			BhSystemParam one = iBhSystemParamService.getOne(query);
			if (one != null){
				bhSystemParam.setId(one.getId());
			}
			bhSystemParam.setName(bhApplicationManagement.getAppId());
			iBhSystemParamService.saveOrUpdate(bhSystemParam);
		}
		return resultId;
	}
}
