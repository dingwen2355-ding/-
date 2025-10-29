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
import com.jslc.common.constant.CommonConstant;
import com.jslc.common.utils.CommonUtil;
import com.jslc.common.utils.HsmUtil;
import com.jslc.common.utils.ValidatorUtil;
import com.jslc.modules.system.entity.User;
import com.jslc.modules.system.service.IUserService;
import com.jslc.modules.szbh.dto.DeptPositionDto;
import com.jslc.modules.szbh.entity.*;
import com.jslc.modules.szbh.listener.ImportBhUserExcelListener;
import com.jslc.modules.szbh.service.*;
import com.jslc.modules.szbh.vo.*;
import com.jslc.modules.szbh.mapper.BhUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 用户管理 服务实现类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Slf4j
@Service
public class BhUserServiceImpl extends ServiceImpl<BhUserMapper, BhUser> implements IBhUserService {

	private static final int OPENID_PHONE_NUM = 800; //经实践，800一批插入相对较快

	@Lazy
	@Autowired
	private ISyncRecordService iSyncRecordService;


	@Autowired
	private IBhDeptService iBhDeptService;


	@Autowired
	private IBhUserSyncService iBhUserSyncService;

	@Lazy
	@Autowired
	private IUserService userService;

	@Autowired
	private IBhApplicationManagementService bhApplicationManagementService;

	@Lazy
	@Autowired
	private ISecuritySettingService iSecuritySettingService;


	@Autowired
	private IApplicationAuthorityService applicationAuthorityService;

	@Autowired
	private IBhEncryptDataService bhEncryptDataService;


	@Autowired
	private IDownstreamSyncService downstreamSyncService;

	@Resource
	private HsmUtil hsmUtil;

	private Map<Long,BhDept> deptMap = new HashMap<>();
	private Map<Long,List<BhDept>> deptParentMap = new HashMap<>();



	@Override
	public BhUserVO getAccountDetail(String account) {
		BhUserVO bhUserVO = new BhUserVO();
		LambdaQueryWrapper<BhUser> userQurery = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).eq(BhUser::getAccount, account);

		BhUser one = this.getOne(userQurery);
		if (Func.isNotEmpty(one)) {
			BeanUtil.copy(one, bhUserVO);
			LambdaQueryWrapper<BhDept> queryOne = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, one.getMajorDept());
			BhDept Bhdept = iBhDeptService.getOne(queryOne);
			bhUserVO.setMajorBhDeptName(Bhdept.getName());
			List<DeptPositionDto> deptPositionList = new ArrayList<>();

			if (bhUserVO.getOtherDept() != null) {
				LambdaQueryWrapper<BhDept> queryList = Wrappers.lambdaQuery(BhDept.class).in(BhDept::getDeptId, bhUserVO.getOtherDept()).eq(BhDept::getIsDeleted,0);
				List<BhDept> list = iBhDeptService.list(queryList);
				if (CollectionUtils.isNotEmpty(list)){
					bhUserVO.setOtherBhDeptName(list.stream().map(BhDept::getName).collect(Collectors.joining(",")));
				}

				List<Long> deptIds = Func.toLongList(bhUserVO.getOtherDept());
				List<String> positions = Func.toStrList(bhUserVO.getOtherPosition());
				for (int i = 0; i < deptIds.size(); i++) {
					DeptPositionDto deptPositionDto = new DeptPositionDto();
					Long deptId = deptIds.get(i);
					deptPositionDto.setDeptId(deptId);
					deptPositionDto.setPosition(positions.size() > i ?positions.get(i) : null);
					LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, deptId);
					BhDept getOne = iBhDeptService.getOne(query);
					deptPositionDto.setDeptName(Func.isNotEmpty(getOne) ? getOne.getName() : null);
					deptPositionList.add(deptPositionDto);
				}
			}
			bhUserVO.setDeptPositions(deptPositionList);

			bhUserVO.setMajorMobile(hsmUtil.decryptDeprecatedSM4(bhUserVO.getMajorMobile()));
			bhUserVO.setPassword(hsmUtil.decryptDeprecatedSM4(bhUserVO.getPassword()));
			bhUserVO.setTelephone(hsmUtil.decryptDeprecatedSM4(bhUserVO.getTelephone()));
			bhUserVO.setEmail(hsmUtil.decryptDeprecatedSM4(bhUserVO.getEmail()));


		}else {
			//获取blade_user中的用户信息
			LambdaQueryWrapper<User> bladeUserQuery = Wrappers.lambdaQuery(User.class).eq(User::getIsDeleted, 0).eq(User::getAccount, account);
			User bladeUser = userService.getOne(bladeUserQuery);
			if (Func.isNotEmpty(bladeUser)){
				bhUserVO.setAccount(bladeUser.getAccount());
				bhUserVO.setName(bladeUser.getName());
//				bhUserVO.setTelephone(bladeUser.getPhone());
				bhUserVO.setMajorMobile(hsmUtil.decryptDeprecatedSM4(bladeUser.getPhone()));
				bhUserVO.setAvatar(bladeUser.getAvatar());
				bhUserVO.setId(bladeUser.getId());
			}
		}
		return bhUserVO;
	}

	@Override
	public BhUserVO getDetail(Long id) {
		BhUserVO bhUserVO = new BhUserVO();
		BhUser one = this.getById(id);
		BeanUtil.copy(one,bhUserVO);
		bhUserVO.setPassword(bhUserVO.getPassword() != null ? hsmUtil.decryptDeprecatedSM4(bhUserVO.getPassword()) : bhUserVO.getPassword());
		bhUserVO.setEmail(bhUserVO.getEmail() != null ? hsmUtil.decryptDeprecatedSM4(bhUserVO.getEmail()) : bhUserVO.getEmail());
		bhUserVO.setTelephone(bhUserVO.getTelephone() != null ? hsmUtil.decryptDeprecatedSM4(bhUserVO.getTelephone()) : bhUserVO.getTelephone());
		bhUserVO.setMajorMobile(bhUserVO.getMajorMobile() != null ? hsmUtil.decryptDeprecatedSM4(bhUserVO.getMajorMobile()) : bhUserVO.getMajorMobile());

		List<DeptPositionDto> deptPositionList = new ArrayList<>();

		if (bhUserVO.getOtherDept() != null){

			List<Long> deptIds = Func.toLongList(bhUserVO.getOtherDept());
			List<String> positions = Func.toStrList(bhUserVO.getOtherPosition());
			for (int i = 0; i < deptIds.size(); i++) {
				DeptPositionDto deptPositionDto = new DeptPositionDto();
				Long deptId = deptIds.get(i);
				deptPositionDto.setDeptId(deptId);
				deptPositionDto.setPosition(positions.size() >i ? positions.get(i): null);
				LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, deptId);
				BhDept getOne = iBhDeptService.getOne(query);
				deptPositionDto.setDeptName(Func.isNotEmpty(getOne) ? getOne.getName() : null);
				deptPositionList.add(deptPositionDto);
			}
		}
		bhUserVO.setDeptPositions(deptPositionList);
		return bhUserVO;
	}

	@Override
	public IPage<BhUserVO> selectBhUserPage(IPage<BhUserVO> page, Long bhDeptId,String  code ,Integer isEnable,Integer isOnline,Integer isAuthorized ,String start,String end) {
		List<BhUserVO> list = getBhUserList(null,   bhDeptId,  code , isEnable, isOnline, isAuthorized , start, end,null);
//		if (CollectionUtils.isNotEmpty(list)) {
//			list.stream().forEach(f -> {
//				f.setPassword(f.getPassword() != null ? hsmUtil.decryptDeprecatedSM4(f.getPassword()) : f.getPassword());
//				f.setEmail(f.getEmail() != null ? hsmUtil.decryptDeprecatedSM4(f.getEmail()) : f.getEmail());
//				f.setTelephone(f.getTelephone() != null ? hsmUtil.decryptDeprecatedSM4(f.getTelephone()) : f.getTelephone());
//				f.setMajorMobile(f.getMajorMobile() != null ? hsmUtil.decryptDeprecatedSM4(f.getMajorMobile()) : f.getMajorMobile());
//
//			});
//		}
		IPage iPage = CommonUtil.manualPage(page, list);
		List<BhUserVO> records = iPage.getRecords();
		for (BhUserVO record : records) {
		    String majorMobile = record.getMajorMobile();
            if (StringUtil.isNotBlank(majorMobile) && !(ValidatorUtil.isValidMobileNum(majorMobile)||ValidatorUtil.isValidTelephone(majorMobile))) {
                record.setMajorMobile("手机号码篡改");
            }
		}
		return iPage;
	}

	public List<BhUserVO> getBhUserList(IPage<BhUserVO> page,Long bhDeptId,String  code ,Integer isEnable,Integer isOnline,Integer isAuthorized ,String start,String end,List<Long> ids){
		//为递归提供数据
		LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getIsDeleted, 0);
		List<BhDept> deptLists = iBhDeptService.list(query);
		if (CollectionUtils.isNotEmpty(deptLists)){
			deptMap=deptLists.stream().collect(Collectors.toMap(BhDept::getDeptId, a -> a,(k1, k2)->k1));
			deptParentMap=deptLists.stream().collect(Collectors.groupingBy(BhDept::getParentId));
		}
		List<Long> id = null;
		if (CollectionUtils.isNotEmpty(ids)){
			id = ids;
		}
		List<Long> bhDeptIds = null;
		//通过递归获取所有部门下的id
		if (Func.isNotEmpty(bhDeptId)){
//			List<SystemTreeNode> bhDeptTree = iBhDeptService.getBhDeptTree(bhDeptId);
			bhDeptIds = new ArrayList<>();
			bhDeptIds.add(bhDeptId);
			//递归
			List<BhDept> bhDeptList = queryBhDepts(bhDeptIds);
			if (CollectionUtils.isNotEmpty(bhDeptList)){
				bhDeptIds.addAll(bhDeptList.stream().map(BhDept::getDeptId).distinct().collect(Collectors.toList()));
			}
		}
		LocalDateTime startTime = null;
		LocalDateTime endTime = null;
		if (Func.isNotEmpty(start)){
			startTime = LocalDateTime.of(DateTimeUtil.parseDate(start,DateTimeUtil.DATE_FORMAT), LocalTime.MIN);

		}
		if (Func.isNotEmpty(end)){
			endTime = LocalDateTime.of(DateTimeUtil.parseDate(end,DateTimeUtil.DATE_FORMAT), LocalTime.MAX);
		}
		List<BhUserVO> result = new ArrayList<>();
		List<BhUserVO> list = baseMapper.getBhUserList(page, bhDeptIds,  code , isEnable, isOnline, isAuthorized,startTime,endTime, id);

		//将获取的数据 进行以部门排序
		if(CollectionUtils.isNotEmpty(list) && CollectionUtils.isNotEmpty(bhDeptIds)){
			for (BhUserVO f : list) {
				f.setOrder(0L);
				Boolean flag = true;
				if (bhDeptIds.indexOf(f.getMajorDept()) != -1 && bhDeptIds.get(bhDeptIds.indexOf(f.getMajorDept())).longValue() ==  f.getMajorDept().longValue()){
					f.setOrder(f.getSort() != null && f.getSort().length() > 0 ? Func.toLongList(f.getSort()).get(0) : 0L);
					flag = false;
					result.add(f);
				}
				if (flag && Func.isNotEmpty(f.getOtherDept())){
					List<Long> sorts = Func.toLongList(f.getOtherDept());
//					CollectionUtils.intersection(sorts,bhDeptIds);
					List<Long> collect = bhDeptIds.stream().filter(item -> sorts.contains(item)).collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(collect)){
						f.setOrder(0L);
						result.add(f);
					}
				}
			}

			result = result.stream().sorted(Comparator.comparing(BhUserVO::getOrder).reversed()).collect(Collectors.toList());
		}else if (Func.isEmpty(bhDeptId)){
			result = list;
		}
		return result;
	}

	public List<BhDept> queryBhDepts(List<Long> ids){
//		LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getIsDeleted, 0).in(BhDept::getParentId, ids);
		//根据父ID查询部门
		List<BhDept> departments = new ArrayList<>();
		for (Long id : ids) {
			if (CollectionUtils.isNotEmpty(deptParentMap.get(id))) {
				departments.addAll(deptParentMap.get(id));
			}
		}
		if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(departments)) {
			//拿到当前所有部门ID
			List<Long> parentIds = departments.stream().map(item -> item.getDeptId()).collect(Collectors.toList());
			//拼接子部门查询结果
			departments.addAll(queryBhDepts(parentIds));
			return departments;
		} else {
			//如果没有下级部门那么我们就返回空集合，结束递归。
			return Lists.newArrayList();
		}
	}

	@Override
	public Boolean del(List<Long> ids) {
		LambdaUpdateWrapper<BhUser> set = Wrappers.lambdaUpdate(BhUser.class).in(BhUser::getId, ids)
				.set(BhUser::getIsDeleted, 1).set(BhUser::getIsUpdated, 1)
				.set(BhUser::getUpdateTime,LocalDateTime.now())
				.set(BhUser::getUpdateUser,AuthUtil.getUserId());
		boolean result = this.update(set);
		if (result){
			//删除blade_user
			LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).in(BhUser::getId, ids);
			List<BhUser> list = this.list(query);
			if (CollectionUtils.isNotEmpty(list)){
				List<String> acounts = list.stream().map(BhUser::getAccount).distinct().collect(Collectors.toList());
				LambdaUpdateWrapper<User> userUpdate = Wrappers.lambdaUpdate(User.class).in(User::getAccount, acounts)
						.set(User::getIsDeleted, 1)
						.set(User::getUpdateTime,LocalDateTime.now())
						.set(User::getUpdateUser,AuthUtil.getUserId());
				userService.update(userUpdate);
			}
		}
		return result;
	}

	@Override
	public Boolean changeEnable(Long id) {
		Integer state = 0;
		BhUser one = this.getById(id);
//		if (Func.isNotEmpty(one)) {
//			if (Func.isEmpty(one.getIsEnable()) || one.getIsEnable() == 0) {
//				state = 1;
//			}
//		}
		LambdaUpdateWrapper<BhUser> set = Wrappers.lambdaUpdate(BhUser.class).eq(BhUser::getId, id).set(BhUser::getIsEnable, state);
		Boolean flag = this.update(set);

		if (flag){
			//更改blade_user这个表的状态
			User user = userService.getOne(Wrappers.lambdaQuery(User.class).eq(User::getAccount, one.getAccount()));
			if (Func.isNotEmpty(user)){
//				LambdaUpdateWrapper<User> updateUser = Wrappers.lambdaUpdate(User.class).eq(BhUser::getId, id).set(BhUser::getIsEnable, state);
				user.setStatus(state);
				userService.updateById(user);
			}
		}
		return flag;
	}

	@Override
	public Boolean changeOnline(String ids) {
		List<Long> longs = Func.toLongList(ids);
		LambdaUpdateWrapper<BhUser> set = Wrappers.lambdaUpdate(BhUser.class).in(BhUser::getId, longs).set(BhUser::getIsOnline, 0);
		return  this.update(set);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean submitList(List<BhUser> bhUsers) {
		LambdaQueryWrapper<User> queryUser = Wrappers.lambdaQuery(User.class).eq(User::getAccount, "ptyh");
		User one = userService.getOne(queryUser);
		List<User> users = new ArrayList<>();
		for (BhUser bhUser : bhUsers) {

			String password = "Szbh@2024";
			bhUser.setPassword(hsmUtil.encryptDeprecatedSM4(DigestUtil.encrypt(password)));
			bhUser.setCreateUser(AuthUtil.getUserId());
			bhUser.setIsDeleted(0);
			bhUser.setCreateTime(LocalDateTime.now());
			bhUser.setDataSource(2);
			bhUser.setIsOnline(0);

			bhUser.setEmail(bhUser.getEmail());
			bhUser.setMajorMobile(bhUser.getMajorMobile());
			bhUser.setTelephone(bhUser.getTelephone());

			User user2 = new User();
			LambdaQueryWrapper<User> queryAccount = Wrappers.lambdaQuery(User.class).eq(User::getAccount, bhUser.getAccount());
			User user1 = userService.getOne(queryAccount);
			if (Func.isNotEmpty(user1)){
				//修改
				user2.setId(user1.getId());
				user2.setUpdateTime(new Date());
				user2.setPassword(user1.getPassword());
				user2.setDeptId(user1.getDeptId());
			}else {
				//新增
				user2.setCreateTime(new Date());
				user2.setPassword(bhUser.getPassword());
				user2.setDeptId(one.getDeptId());
			}
			user2.setTenantId(one.getTenantId());
			user2.setCode(one.getCode());
			user2.setUserType(one.getUserType());
			user2.setAccount(bhUser.getAccount());
//			user2.setPassword(bhUser.getPassword());
			user2.setName(bhUser.getName());
			user2.setRealName(bhUser.getName());
			user2.setAvatar(bhUser.getAvatar());
			user2.setEmail(hsmUtil.encryptDeprecatedSM4(bhUser.getEmail()));
			user2.setPhone(hsmUtil.encryptDeprecatedSM4(bhUser.getMajorMobile()));
			user2.setSex(Integer.valueOf(bhUser.getGender()));
			user2.setRoleId(one.getRoleId());

			user2.setCreateUser(one.getCreateUser());
			user2.setStatus(1);
			user2.setIsDeleted(0);
//			userService.saveOrUpdate(user2);
			users.add(user2);

		}
		boolean result = this.saveBatch(bhUsers);
		if (result){
			userService.saveBatch(users);

		}
		if (result){
			for (User user : users) {
				bhEncryptDataService.encryptData(user.getId(),user.getRoleId(),CommonConstant.EncryptDataType.ROLE);
			}
		}
		return result;

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean submit(BhUser bhUser) {
		Long oldDept = null;
		Boolean addFlag = false;
		Boolean updateFlag = false;
		LambdaQueryWrapper<User> queryUser = Wrappers.lambdaQuery(User.class).eq(User::getAccount, "ptyh");
		User one = userService.getOne(queryUser);
		Boolean result = false;
		//获取部门的最大人员数
//		BhDept hbDept = iBhDeptService.getById(bhUser.getMajorDept());
		//查询当前 企业下已经有多少人了
		LambdaUpdateWrapper<BhUser> userEq = Wrappers.lambdaUpdate(BhUser.class).eq(BhUser::getIsDeleted, 0);
		List<BhUser> list = this.list(userEq);
//		if (CollectionUtils.isNotEmpty(list) && list.size() >= hbDept.getPartyUserLimit()){
//			throw new ServiceException("当前租户已到最大账号额度!");
//		}
		//判断账户是否重复
		LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getAccount, bhUser.getAccount()).eq(BhUser::getIsDeleted,0);
		BhUser user = this.getOne(query);

		//判断主部门电话唯一性
		LambdaQueryWrapper<BhUser> queryPhone = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getMajorMobile, hsmUtil.encryptDeprecatedSM4(bhUser.getMajorMobile())).eq(BhUser::getIsDeleted,0);
		List<BhUser> userPhone = this.list(queryPhone);
		if (CollectionUtils.isNotEmpty(userPhone)){
			if (Func.isNotEmpty(bhUser.getId())){//修改
				//若是本身不报错
				List<BhUser> samePhone = userPhone.stream().filter(f -> f.getId().longValue() != bhUser.getId().longValue()).collect(Collectors.toList());
				if (CollectionUtils.isNotEmpty(samePhone)){
					throw new ServiceException("当前修改的用户手机号已存在");
				}
			}else {
				//新增 当有已存在的手机号提示报错
				throw new ServiceException("当前新增的用户手机号已存在");
			}
		}


		Long sort = 10000000L;
		String sorts = null;
		//新增 获取最大的sort 要大2位以上
		if (CollectionUtils.isNotEmpty(list)) {
//			String collect = list.stream().filter(f -> f.getSort() != null).map(BhUser::getSort).collect(Collectors.joining(","));
			Boolean flag = true;
			//自建数据
			List<BhUser> collect2 = list.stream().filter(f -> f.getDataSource() == 2).filter(f -> f.getSort() != null).collect(Collectors.toList());
			//同步数据
			List<BhUser> collect1 = list.stream().filter(f -> f.getDataSource() == 1).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(collect2)) {
				String str = collect2.stream().filter(f -> f.getSort() != null).map(BhUser::getSort).collect(Collectors.joining(","));
				sort = Func.toLongList(str).stream().filter(f -> f != null).max(Long::compareTo).get() + 1;
				flag = false;
			}
			if (flag && CollectionUtils.isNotEmpty(collect1)) {
				String str = collect1.stream().filter(f -> f.getSort() != null).map(BhUser::getSort).collect(Collectors.joining(","));
				sort = Func.toLongList(str).stream().filter(f -> f != null).max(Long::compareTo).get() * 100;

			}
		}
		sorts = sort.toString();
		if (bhUser.getOtherDept() != null){
			List<Long> longs = Func.toLongList(bhUser.getOtherDept());
			for (int i = 0; i < longs.size(); i++) {
				sort =sort+1+i;
				sorts = sorts +","+sort;
			}

		}
		if (Func.isEmpty(bhUser.getId())){ // 新增
			bhUser.setCreateUser(AuthUtil.getUserId());
			bhUser.setIsDeleted(0);
			bhUser.setCreateTime(LocalDateTime.now());
			bhUser.setDataSource(2);
			bhUser.setIsOnline(0);
			bhUser.setSort(sorts);
			String password = "Szbh@2024";
			bhUser.setPassword(hsmUtil.encryptDeprecatedSM4(DigestUtil.encrypt(password)));
			//新增账户唯一性判断
			if (Func.isNotEmpty(user) &&  user.getAccount().equals(bhUser.getAccount())) {
				throw new ServiceException(StringUtil.format("当前用户 [{}] 已存在!", user.getAccount()));
			}
			bhUser.setTelephone(bhUser.getTelephone() != null ? hsmUtil.encryptDeprecatedSM4(bhUser.getTelephone()) : bhUser.getTelephone());
			bhUser.setMajorMobile(bhUser.getMajorMobile() != null ? hsmUtil.encryptDeprecatedSM4(bhUser.getMajorMobile()):bhUser.getMajorMobile());
			bhUser.setEmail(bhUser.getEmail() != null ? hsmUtil.encryptDeprecatedSM4(bhUser.getEmail()):bhUser.getEmail());

			addFlag = true;
			//
		}else {//修改
			bhUser.setUpdateUser(AuthUtil.getUserId());
			bhUser.setUpdateTime(LocalDateTime.now());
			bhUser.setIsUpdated(1);
			if (Func.isNotEmpty(user) &&  user.getAccount().equals(bhUser.getAccount()) && user.getId().longValue() != bhUser.getId().longValue()) {
				throw new ServiceException(StringUtil.format("当前修改的账户 [{}] 已存在!", user.getAccount()));
			}
			//将bh_user_application_management这表对应的数据删除
			if (!user.getMajorDept().equals(bhUser.getMajorDept())){

				updateFlag = true;
				oldDept=user.getMajorDept();
			}
			bhUser.setTelephone(bhUser.getTelephone() != null ? hsmUtil.encryptDeprecatedSM4(bhUser.getTelephone()) : bhUser.getTelephone());
			bhUser.setMajorMobile(bhUser.getMajorMobile() != null ? hsmUtil.encryptDeprecatedSM4(bhUser.getMajorMobile()):bhUser.getMajorMobile());
			bhUser.setEmail(bhUser.getEmail() != null ? hsmUtil.encryptDeprecatedSM4(bhUser.getEmail()):bhUser.getEmail());

		}
		result = this.saveOrUpdate(bhUser);

		if (result){
			//同步人员的时候 同步到 blade_user 表
			User user2 = new User();
			LambdaQueryWrapper<User> queryAccount = Wrappers.lambdaQuery(User.class).eq(User::getAccount, bhUser.getAccount());
			User user1 = userService.getOne(queryAccount);
			if (Func.isNotEmpty(user1)){
				user2.setId(user1.getId());
				user2.setUpdateTime(new Date());
				user2.setPassword(bhUser.getPassword());
			}else {
				user2.setCreateTime(new Date());
				user2.setPassword(bhUser.getPassword());
			}
			user2.setTenantId(one.getTenantId());
			user2.setCode(one.getCode());
			user2.setUserType(one.getUserType());
			user2.setAccount(bhUser.getAccount());
//			user2.setPassword(bhUser.getPassword());
			user2.setName(bhUser.getName());
			user2.setRealName(bhUser.getName());
			user2.setAvatar(bhUser.getAvatar());
			user2.setEmail(bhUser.getEmail());
			user2.setPhone(bhUser.getMajorMobile());
			user2.setSex(Integer.valueOf(bhUser.getGender()));
			user2.setRoleId(one.getRoleId());
			user2.setDeptId(bhUser.getMajorDept()+"");
			user2.setCreateUser(one.getCreateUser());
			user2.setStatus(1);
			user2.setIsDeleted(0);
			boolean userFlag = userService.saveOrUpdate(user2);
			if(userFlag){
				//权限加密
				bhEncryptDataService.encryptData(user2.getId(), user2.getRoleId(), CommonConstant.EncryptDataType.ROLE);
			}
		}

		Integer type = 1; //组织类型
		if (result && updateFlag){

			//修改了主岗 修改了对应主岗的权限
			List<ApplicationAuthority> update = new ArrayList<>();
			//刷新老主岗的权限
			List<ApplicationAuthority> old =  applicationAuthorityService.getAuthorityByType(user.getMajorDept(),type);
			if (CollectionUtils.isNotEmpty(old)){
				update.addAll(old);
			}
			List<ApplicationAuthority> now =  applicationAuthorityService.getAuthorityByType(bhUser.getMajorDept(),type);
			if (CollectionUtils.isNotEmpty(now)){
				update.addAll(now);
			}
			applicationAuthorityService.submits(update);

			//downstreamSyncService 下游
			downstreamSyncService.updateDown(bhUser.getMajorDept());
			if (oldDept != null) {
				downstreamSyncService.updateDown(oldDept);
			}

		}
		//上游
		if (result && addFlag){
//			Integer type = 1; //组织类型
			List<ApplicationAuthority> applicat =  applicationAuthorityService.getAuthorityByType(bhUser.getMajorDept(),type);
			applicationAuthorityService.submits(applicat);

			//下游
			downstreamSyncService.updateDown(bhUser.getMajorDept());
		}

		return result;
	}

	@Override
	public Boolean updatePhone(BhUser bhUser) {

		BhUser one = this.getById(bhUser.getId());
		if (one == null){
			User user = userService.getById(bhUser.getId());
			boolean bladeUserUpdate = userService.update(Wrappers.<User>update().lambda()
					.set(User::getPhone, bhUser.getMajorMobile() != null ? hsmUtil.encryptDeprecatedSM4(bhUser.getMajorMobile()):bhUser.getMajorMobile())
					.set(User::getAvatar, bhUser.getAvatar())
					.eq(User::getAccount, user.getAccount()));
			return bladeUserUpdate;
		} else {
			//手机号唯一性判断
			LambdaQueryWrapper<BhUser> queryPhone = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getMajorMobile, bhUser.getMajorMobile() != null ? hsmUtil.encryptDeprecatedSM4(bhUser.getMajorMobile()):bhUser.getMajorMobile()).eq(BhUser::getIsDeleted,0);
			List<BhUser> userPhone = this.list(queryPhone);
			if (CollectionUtils.isNotEmpty(userPhone)){
				if (Func.isNotEmpty(bhUser.getId())){//修改
					//若是本身不报错
					List<BhUser> samePhone = userPhone.stream().filter(f -> f.getId().longValue() != bhUser.getId().longValue()).collect(Collectors.toList());
					if (CollectionUtils.isNotEmpty(samePhone)){
						throw new ServiceException("当前修改的用户手机号已存在");
					}
				}else {
					//新增 当有已存在的手机号提示报错
					throw new ServiceException("当前新增的用户手机号已存在");
				}
			}



			boolean update = this.update(Wrappers.<BhUser>update().lambda()
					.set(BhUser::getTelephone, bhUser.getTelephone() != null ? hsmUtil.encryptDeprecatedSM4(bhUser.getTelephone()):bhUser.getTelephone())
					.set(BhUser::getMajorMobile, bhUser.getMajorMobile() != null ? hsmUtil.encryptDeprecatedSM4(bhUser.getMajorMobile()):bhUser.getMajorMobile())
					.set(BhUser::getAvatar, bhUser.getAvatar())
					.set(BhUser::getIsUpdated, 1)
					.eq(BhUser::getAccount, one.getAccount()));
//			BhUser bhUser1 = new BhUser();
//			bhUser1.setTelephone(bhUser.getTelephone());
//			bhUser1.setIsUpdated(1);
//			bhUser1.setIsUpdated(1);
//			bhUser1.setIsUpdated(1);

			boolean bladeUserUpdate = userService.update(Wrappers.<User>update().lambda()
					.set(User::getPhone, bhUser.getMajorMobile() != null ? hsmUtil.encryptDeprecatedSM4(bhUser.getMajorMobile()):bhUser.getMajorMobile())
					.set(User::getAvatar, bhUser.getAvatar())
					.eq(User::getAccount, one.getAccount()));

			if (update && bladeUserUpdate) {
				return true;
			} else {
				return false;
			}
		}
	}

//	public List<String> queryDeptParentId(Long id){
//		List<String> result = new ArrayList<>();
//		LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, id);
////		List<Long> result = new ArrayList<>();
//		//根据父ID查询部门
//		BhDept one = iBhDeptService.getOne(query);
////		result.add();
//		if (Func.isNotEmpty(one)) {
////			拿到当前所有部门ID
////			List<Long> parentIds = departments.stream().map(item -> item.getParentId()).distinct().collect(Collectors.toList());
//			//拼接子部门查询结果
//			result.add(one.getName());
//
//			return result;
//		} else {
//			//如果没有下级部门那么我们就返回空集合，结束递归。
//			return Lists.newArrayList();
//		}
//	}

//	public String queryDeptParentName(Long id){
//		String deptName = null;
//		LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, id);
//		List<String> result = new ArrayList<>();
//		//根据父ID查询部门
//		BhDept departments = iBhDeptService.getOne(query);
//		do {
//			if (Func.isNotEmpty(departments)){
//				result.add(departments.getName());
//				LambdaQueryWrapper<BhDept> in = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, departments.getParentId());
//				departments = iBhDeptService.getOne(in);
//			}
//		}while (Func.isNotEmpty(departments));
//		if (CollectionUtils.isNotEmpty(result)){
//			Collections.reverse(result);
//			deptName = Func.join(result, "/");
//		}
//		return deptName;
//	}

	private Map<Long, BhDept> getDeptMap() {
		LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getIsDeleted, 0);
		List<BhDept> allList = iBhDeptService.list(query);
		Map<Long, BhDept> rootMap = new HashMap<>();
//		Map<Long, BhDept> rootMap = allList.stream().filter(o -> o.getParentId() == 1).collect(Collectors.toMap(BhDept::getDeptId, o -> o));
		//获取根
		List<BhDeptVO> deptVOS = iBhDeptService.getRootDepts();
		if (CollectionUtils.isNotEmpty(deptVOS)){
			rootMap = BeanUtil.copy(deptVOS,BhDept.class).stream().collect(Collectors.toMap(BhDept::getDeptId, o -> o));
		}

		Map<Long, List<BhDept>> parentMap = allList.stream().collect(Collectors.groupingBy(BhDept::getParentId));
		for (Map.Entry<Long, BhDept> longBhDeptEntry : rootMap.entrySet()) {
			BhDept pdept = longBhDeptEntry.getValue();
			pdept.setDescription(pdept.getName());
			children(pdept, parentMap);
		}
//		allList = allList.stream().filter(f->f.getDeptId().longValue() == 373L ||f.getDeptId().longValue() == 1669595879511539714L || f.getDeptId().longValue() == 1669598446744330241L).collect(Collectors.toList());
		Set<Long> longs = rootMap.keySet();
		allList.stream().filter(f -> longs.contains(f.getDeptId())).forEach(f -> f.setDescription(f.getName()));
		Map<Long, BhDept> allDepMap = allList.stream().collect(Collectors.toMap(BhDept::getDeptId, o -> o));

		return allDepMap;
	}

	void children(BhDept dept, Map<Long, List<BhDept>> parentMap) {
		List<BhDept> children = parentMap.get(dept.getDeptId());
		if (children != null) {
			for (BhDept child : children) {
				child.setDescription(dept.getDescription() + "/" + child.getName());
				this.children(child, parentMap);
			}
		}
	}


	@Override
	public void downLoad(HttpServletResponse response, Long bhDeptId, String code, Integer isEnable, Integer isOnline, Integer isAuthorized, String start, String end,List<Long> ids) {
		List<BhUserVO> bhUserList = getBhUserList(null, bhDeptId, code, isEnable, isOnline, isAuthorized, start, end, ids);
		OutputStream out = null;
		if (CollectionUtils.isNotEmpty(bhUserList)){
			Map<Long, BhDept> deptMap = getDeptMap();
			for (BhUserVO bhUserVO : bhUserList) {
				if (Func.isNotEmpty(bhUserVO.getMajorDept())){
					BhDept bhDept = deptMap.get(bhUserVO.getMajorDept());
					if (bhDept != null){
						bhUserVO.setDepts(bhDept.getDescription());
					}
				}
			}
		}
		try {
			String fileName = "人员列表.xlsx";
			out = response.getOutputStream();
			//设置返回头
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");


//			// 表头
			List<List<String>> headList = new ArrayList<List<String>>();

			List<String> headTitle = new ArrayList<String>();
			headTitle.add("姓名");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("登录账号");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("主岗部门");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("职务");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("账户状态");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("电话");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("在线状态");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("配置状态");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("最后登录");
			headList.add(headTitle);

			// 所有行的集合
			List<List<Object>> resultList = new ArrayList<List<Object>>();

			for (BhUserVO bhUserVO : bhUserList) {
				List<Object> row = new ArrayList<Object>();
				row.add(bhUserVO.getName());
				row.add(bhUserVO.getAccount());
				row.add(bhUserVO.getDepts());
				row.add(bhUserVO.getMajorPosition());
				row.add(bhUserVO.getIsEnableName());
				row.add(bhUserVO.getTelephone() != null ? bhUserVO.getTelephone(): null);
				row.add(bhUserVO.getIsOnlineName());
				row.add(bhUserVO.getIsAuthorizedName());
				row.add(Func.isNotEmpty(bhUserVO.getLastLoginTime()) ? DateTimeUtil.format(bhUserVO.getLastLoginTime(),"yyyy-MM-dd HH:mm:ss") : null);
				resultList.add(row);
			}

			EasyExcel.write(response.getOutputStream())
					.head(headList)
					.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
					.sheet("人员列表")
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
	public PassWordBitVO getPasswordBit() {
		PassWordBitVO passWordBitVO = new PassWordBitVO();
		List<SecuritySetting> list = iSecuritySettingService.list();
		if (CollectionUtils.isNotEmpty(list)){
			passWordBitVO.setMinLength(list.get(0).getSecretMinLength());
			passWordBitVO.setMaxLength(list.get(0).getSecretMaxLength());
		}
		return passWordBitVO;
	}

	@Override
	public void downTemplate(HttpServletResponse response) {
		OutputStream out = null;

		try {
			String fileName = "导入人员模板.xlsx";
			out = response.getOutputStream();
			//设置返回头
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");


//			// 表头
			List<List<String>> headList = new ArrayList<List<String>>();

			List<String> headTitle = new ArrayList<String>();
			headTitle.add("用户名称");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("用户账户");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("主岗部门职位");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("账号状态停用或启用");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("主部门手机");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("是否开启或关闭短信通知");
			headList.add(headTitle);

//			headTitle = new ArrayList<String>();
//			headTitle.add("其他部门职位");
//			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("性别");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("别名");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("邮箱");
			headList.add(headTitle);


			headTitle = new ArrayList<String>();
			headTitle.add("对外简称");
			headList.add(headTitle);

			// 所有行的集合
			List<List<Object>> resultList = new ArrayList<List<Object>>();

//			for (BhUserVO bhUserVO : bhUserList) {
				List<Object> row = new ArrayList<Object>();
				row.add("张三");
				row.add("zhangsan");
				row.add("职位名称");
				row.add("停用/启用");
				row.add("15562348587");
				row.add("开启/关闭");
//				row.add("职位1,职位2");
				row.add("男/女");
				row.add("别名");
				row.add("1155655@163.com");
				row.add("简称");
				resultList.add(row);
//			}

			EasyExcel.write(response.getOutputStream())
					.head(headList)
					.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
					.sheet("导入人员模板")
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
	public Boolean changePassword(String ids) {
		List<Long> longs = Func.toLongList(ids);
		List<BhUser> bhUsers = this.listByIds(longs);
		if (CollectionUtils.isNotEmpty(bhUsers)){
			for (BhUser bhUser : bhUsers) {
				bhUser.setPassword(DigestUtil.encrypt("Szbh@2024"));
				bhUser.setIsUpdated(1);
			}
		}
		Boolean flag= this.saveOrUpdateBatch(bhUsers);
		if (CollectionUtils.isNotEmpty(bhUsers) && flag){
			List<String> account = bhUsers.stream().map(BhUser::getAccount).distinct().collect(Collectors.toList());
			List<User> list = userService.list(Wrappers.lambdaQuery(User.class).in(User::getAccount, account).eq(User::getIsDeleted, 0));
			if (CollectionUtils.isNotEmpty(list)){
				list.stream().forEach(f->{
					f.setPassword(DigestUtil.encrypt("Szbh@2024"));
				});
				userService.updateBatchById(list);
			}
		}
		return flag;
	}


	public void chuliData(List<String> bhDeptIds ,BhUserSync f,Map<String, List<BhUser>> phoneMap ,List<BhUser> updateData,List<BhUser> createData,Map<String, List<BhUser>> collect,List<ApplicationAuthority> authorities,Map<String,List<User>> mapUserName,Map<String,List<User>> mapUser,List<User> updateUsers,List<User> createUsers,User one){

		BhUser bhUser = new BhUser();
		//判断账号是否已经存在 存在则是修改
		if (bhDeptIds.contains(f.getUserid())){//修改

			bhUser.setId(Func.isNotEmpty(phoneMap.get(f.getName()+"-"+f.getMobile())) ? (phoneMap.get(f.getName()+"-"+f.getMobile()).get(0).getId()) : collect.get(f.getUserid()).get(0).getId());
			bhUser.setName(f.getName());
			bhUser.setAccount(f.getUserid());

			//主要部门
			Long major = null;
			String otherMajor = null;
			if (Func.isNotEmpty(f.getDepartment())){
				List<Long> longs = Func.toLongList(f.getDepartment());
				major = longs.get(0);
				if (longs.size() >1){

					String str = longs.toString();
					otherMajor =str.substring(str.indexOf(",")+1,str.lastIndexOf("]")).replace(" ", "");
				}
			}
			bhUser.setMajorDept(major);
			//其他部门
			bhUser.setOtherDept(otherMajor);
			String isLeader = null;
			String otherLead = null;
			if (Func.isNotEmpty(f.getIsLeaderInDept()) && f.getIsLeaderInDept().length() >0){
				isLeader = Func.toStrList(f.getIsLeaderInDept()).get(0);
				if (Func.toStrList(f.getIsLeaderInDept()).size() >1){
					String str = Func.toStrList(f.getIsLeaderInDept()).toString();
					otherLead =str.substring(str.indexOf(",")+1,str.lastIndexOf("]")).replace(" ", "");
				}
			}
			bhUser.setIsLeaderInDept(isLeader != null ? Integer.valueOf(isLeader) : null);
			bhUser.setIsLeaderInOtherDept(otherLead);
			//其他岗位
			String otherPosition = null;
			if (Func.isNotEmpty(f.getPositions())){
				List<String> longs = Func.toStrList(f.getPositions());
				List<String> positions = new ArrayList<>();
				positions.addAll(longs);
				List<String> lon = Func.toStrList(f.getPosition());
				for (int i = 0; i < lon.size(); i++) {
					positions.remove(i);
				}

				if (positions.size() > 0){
					otherPosition = String.join(",",positions);

				}
			}
			//主要岗位
			bhUser.setMajorPosition(f.getPosition());
			//其他岗位
			bhUser.setOtherPosition(otherPosition);
//					bhUser.setMajorMobile(f.getMobile());
			//是否短信 1发 0 不发
//					bhUser.setIsSendSms(1);
			bhUser.setEnable(f.getEnable());
//					bhUser.setIsEnable(1);

			//其他部门内是否为上级 去掉一个
			bhUser.setIsLeaderInOtherDept(otherLead);
			bhUser.setGender(Integer.valueOf(f.getGender()));
			bhUser.setAvatar(f.getAvatar());
			bhUser.setEnglishName(f.getEnglishName());
			bhUser.setEmail(f.getEmail());
			bhUser.setTelephone(f.getTelephone());
			bhUser.setMajorMobile(f.getMobile());
			bhUser.setSort(f.getSort());
			bhUser.setExternalCorpName(f.getExternalCorpName());
			bhUser.setUpdateUser(AuthUtil.getUserId());
			bhUser.setUpdateTime(LocalDateTime.now());

			updateData.add(bhUser);
			if (Func.isNotEmpty(phoneMap.get(f.getName()+"-"+f.getMobile())) && !bhUser.getMajorDept().equals(phoneMap.get(f.getName()+"-"+f.getMobile()).get(0).getMajorDept())){
				Integer type = 1; //组织类型
				List<ApplicationAuthority> applicat =  applicationAuthorityService.getAuthorityByType(bhUser.getMajorDept(),type);
				authorities.addAll(applicat);
			}

			//同步人员的时候 同步到 blade_user 表
			User user = new User();
			List<User> users = mapUserName.get(f.getName() + "-" + f.getMobile());
			List<User> users1 = mapUser.get(f.getUserid());
			if (CollectionUtils.isNotEmpty(users) || CollectionUtils.isNotEmpty(users1)){//修改
				if (CollectionUtils.isNotEmpty(users)){
					user.setId(users.get(0).getId());
				}
				if (CollectionUtils.isNotEmpty(users1)){
					user.setId(users1.get(0).getId());
				}
				user.setUpdateTime(new Date());
//						user.setPassword(bhUser.getAccount());
				updateUsers.add(user);
			}else {//新增
				user.setCreateTime(new Date());
				user.setPassword(DigestUtil.encrypt("Szbh@2024"));
				user.setRoleId(one.getRoleId());
				createUsers.add(user);

			}
			user.setTenantId(one.getTenantId());
			user.setCode(one.getCode());
			user.setUserType(one.getUserType());
			user.setAccount(f.getUserid());
//					user.setPassword(bhUser.getPassword());
			user.setName(f.getName());
			user.setRealName(f.getName());
			user.setAvatar(f.getAvatar());
			user.setEmail(f.getEmail());
			user.setPhone(f.getMobile());
			user.setSex(Integer.valueOf(f.getGender()));

			user.setDeptId(bhUser.getMajorDept()+"");
			user.setCreateUser(one.getCreateUser());
//					user.setStatus(1);
//					user.setIsDeleted(0);
//					userService.saveOrUpdate(user);
//					users.add(user);



		}else {//新增
			bhUser.setName(f.getName());
			bhUser.setAccount(f.getUserid());
			bhUser.setPassword(DigestUtil.encrypt("Szbh@2024"));
			//主要部门
			Long major = null;
			String otherMajor = null;
			if (Func.isNotEmpty(f.getDepartment())){
				List<Long> longs = Func.toLongList(f.getDepartment());
				major = longs.get(0);
				if (longs.size() >1){

					String str = longs.toString();
					otherMajor =str.substring(str.indexOf(",")+1,str.lastIndexOf("]")).replace(" ", "");
				}
			}
			bhUser.setMajorDept(major);
			//其他部门
			bhUser.setOtherDept(otherMajor);

			String isLeader = null;
			String otherLead = null;
			if (Func.isNotEmpty(f.getIsLeaderInDept())){
				isLeader = Func.toStrList(f.getIsLeaderInDept()).get(0);
				if (Func.toStrList(f.getIsLeaderInDept()).size() >1){

					String str = Func.toStrList(f.getIsLeaderInDept()).toString();
					otherLead =str.substring(str.indexOf(",")+1,str.lastIndexOf("]")).replaceAll(" ","");
				}

			}
			bhUser.setIsLeaderInDept(isLeader != null ? Integer.valueOf(isLeader) : null);
			bhUser.setIsLeaderInOtherDept(otherLead);
			//其他岗位
			String otherPosition = null;
			if (Func.isNotEmpty(f.getPositions())){
				List<String> longs = Func.toStrList(f.getPositions());
				List<String> positions = new ArrayList<>();
				positions.addAll(longs);
				List<String> lon = Func.toStrList(f.getPosition());

				for (int i = 0; i < lon.size(); i++) {
					positions.remove(i);
				}

				if (positions.size() > 0){
					otherPosition = String.join(",",positions);
				}
			}
			//主要岗位
			bhUser.setMajorPosition(f.getPosition());
			//其他岗位
			bhUser.setOtherPosition(otherPosition);
//					bhUser.setMajorMobile(f.getMobile());
			//是否短信
			bhUser.setIsSendSms(0);
			bhUser.setEnable(f.getEnable());
//					bhUser.setIsEnable(1);

			//其他部门内是否为上级 去掉一个
			bhUser.setIsLeaderInOtherDept(otherLead);
			bhUser.setGender(Integer.valueOf(f.getGender()));
			bhUser.setAvatar(f.getAvatar());
			bhUser.setEnglishName(f.getEnglishName());
			bhUser.setEmail(f.getEmail());
			bhUser.setTelephone(f.getTelephone());
			bhUser.setMajorMobile(f.getMobile());
			bhUser.setSort(f.getSort());
			bhUser.setExternalCorpName(f.getExternalCorpName());
			bhUser.setCreateTime(LocalDateTime.now());
			bhUser.setCreateUser(AuthUtil.getUserId());
			bhUser.setUpdateUser(AuthUtil.getUserId());
			bhUser.setUpdateTime(LocalDateTime.now());
			bhUser.setIsEnable(1);
			bhUser.setIsDeleted(0);
			bhUser.setIsAuthorized(0);
			bhUser.setIsOnline(1);
			bhUser.setDataSource(1);
			bhUser.setIsSendSmsInitPassword(0);

			createData.add(bhUser);

			//同步人员的时候 同步到 blade_user 表
			User user = new User();
//					LambdaQueryWrapper<User> queryAccount = Wrappers.lambdaQuery(User.class).eq(User::getAccount, f.getUserid());
			List<User> users = mapUserName.get(f.getName() + "-" + f.getMobile());
			List<User> users1 = mapUser.get(f.getUserid());
			if (CollectionUtils.isNotEmpty(users) && CollectionUtils.isNotEmpty(users1)){//修改
				if (CollectionUtils.isNotEmpty(users)){
					user.setId(users.get(0).getId());
				}
				if (CollectionUtils.isNotEmpty(users1)){
					user.setId(users1.get(0).getId());
				}
				user.setUpdateTime(new Date());
//						user.setPassword(bhUser.getAccount());
				updateUsers.add(user);
			}else {//新增
				user.setCreateTime(new Date());
				user.setRoleId(one.getRoleId());
				user.setPassword(DigestUtil.encrypt("Szbh@2024"));
				createUsers.add(user);
			}
			user.setTenantId(one.getTenantId());
			user.setCode(one.getCode());
			user.setUserType(one.getUserType());
			user.setAccount(f.getUserid());
//					user.setPassword(bhUser.getPassword());
			user.setName(f.getName());
			user.setRealName(f.getName());
			user.setAvatar(f.getAvatar());
			user.setEmail(f.getEmail());
			user.setPhone(f.getMobile());
			user.setSex(Integer.valueOf(f.getGender()));
			user.setDeptId(bhUser.getMajorDept()+"");
			user.setCreateUser(one.getCreateUser());
			user.setStatus(1);
			user.setIsDeleted(0);
//					userService.saveOrUpdate(user);
//					users.add(user);


			Integer type = 1; //组织类型
			List<ApplicationAuthority> applicat =  applicationAuthorityService.getAuthorityByType(bhUser.getMajorDept(),type);
			authorities.addAll(applicat);


		}
	}



	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean syn() throws Exception {


		//更新部门人员权限
		List<ApplicationAuthority> authorities = new ArrayList<>();


		LambdaQueryWrapper<User> queryUser = Wrappers.lambdaQuery(User.class).eq(User::getAccount, "ptyh");
		User one = userService.getOne(queryUser);

		//所有勾选的需要新增的bhuser
		List<BhUser> createData = new ArrayList<>();
		//所有勾选的需要修改的bhuser
		List<BhUser> updateData = new ArrayList<>();

		//需要新增到blade_user表中的数据
		List<User> createUsers = new ArrayList<>();
		//需要修改到blade_user表中的数据
		List<User> updateUsers = new ArrayList<>();
		long start = System.currentTimeMillis();
		//同步表中的数据
//		Wrappers.lambdaQuery(BhUserSync.class).in(BhUserSync::getUserid,accounts);
//		.in(BhUserSync::getUserid,account)
		List<BhUserSync> bhUserSyncs = iBhUserSyncService.list(Wrappers.lambdaQuery(BhUserSync.class));

		//获取所有人员
//		.in(User::getAccount,account)
		LambdaQueryWrapper<User> queryAccount = Wrappers.lambdaQuery(User.class).eq(User::getIsDeleted, 0);
		List<User> listUser = userService.list(queryAccount);
		Map<String,List<User>> mapUser = new HashMap<>();
		Map<String,List<User>> mapUserName = new HashMap<>();

		if (CollectionUtils.isNotEmpty(listUser)){

			mapUser = listUser.stream().collect(Collectors.groupingBy(User::getAccount));
			mapUserName = listUser.stream().collect(Collectors.groupingBy(f->f.getName()+"-"+ f.getPhone()));
		}

		//获取bh_user 要同步的表的user数据
		LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class)
				.eq(BhUser::getIsUpdated, 0)
				.eq(BhUser::getIsDeleted,0);
//				.in(BhUser::getAccount,account);
		List<BhUser> bhUserList = this.list(query);
		//不需要同步的user数据
		LambdaQueryWrapper<BhUser> query1 = Wrappers.lambdaQuery(BhUser.class)
				.eq(BhUser::getIsUpdated, 1 )
				.eq(BhUser::getDataSource,1);
//				.in(BhUser::getAccount,account);
		List<BhUser> bhUserList2 = this.list(query1);

		//将不需要同步的数据过滤
		if (CollectionUtils.isNotEmpty(bhUserSyncs) && CollectionUtils.isNotEmpty(bhUserList2)){

			//不需要同步的账号
			List<String> collect = bhUserList2.stream().map(BhUser::getAccount).collect(Collectors.toList());
			//过滤不要同步的账号bh_user_syn
			bhUserSyncs = bhUserSyncs.stream().filter(k->!collect.contains(k.getUserid())).collect(Collectors.toList());
		}



		//开始同步 若是同步表没有则新增 有则覆盖
		if (CollectionUtils.isNotEmpty(bhUserSyncs)){
			// bh_user表中的所有account
			List<String> bhDeptIds = new ArrayList<>();
			//bh_user表中的所有account分组
			Map<String, List<BhUser>> collect = new HashMap<>();
			//bh_user表中的所有name_majorMobile分组
			Map<String, List<BhUser>> phoneMap = new HashMap<>();

			if (CollectionUtils.isNotEmpty(bhUserList)){

				//bh_user表中的所有account
				bhDeptIds = bhUserList.stream().map(BhUser::getAccount).collect(Collectors.toList());
				//bh_user表中的所有account分组
				collect = bhUserList.stream().collect(Collectors.groupingBy(BhUser::getAccount));
				//bh_user表中的所有name_majorMobile分组
				phoneMap = bhUserList.stream().collect(Collectors.groupingBy(f->f.getName()+"-"+ f.getMajorMobile()));
			}
			for (BhUserSync f : bhUserSyncs) {//bhUserSyncs所有需要同步的数据
//
				//每次循环添加一次 name_majorMobile
				if (!bhDeptIds.contains(f.getUserid())) {
					if (CollectionUtils.isNotEmpty(phoneMap.get(f.getName() + "-" + f.getMobile()))) {
						bhDeptIds.add(f.getUserid());
					}
				}

			}

			List<BhUser> finalUpdateData = new ArrayList<>();
			List<BhUser> finalCreateData = new ArrayList<>();

			for (BhUserSync f : bhUserSyncs) {//bhUserSyncs所有需要同步的数据
				chuliData(bhDeptIds, f, phoneMap, updateData, createData, collect, authorities, mapUserName, mapUser, updateUsers, createUsers, one);
			}

			updateData.addAll(finalUpdateData);
			createData.addAll(finalCreateData);

			SyncRecord syncRecord = new SyncRecord();
			syncRecord.setUpOrDown("上游同步");
			syncRecord.setAppName("政务微信");
			syncRecord.setDataType("人员信息");
			syncRecord.setResult("同步"+bhUserSyncs.size()+"条数据");
			syncRecord.setCause("无异常");
			Long userId = AuthUtil.getUserId();
			syncRecord.setIsAutomatic(Func.isNotEmpty(userId) ? 0:1);
			syncRecord.setCreateTime(LocalDateTime.now());
			syncRecord.setCreateUser(userId);
			iSyncRecordService.save(syncRecord);
		}


		if (CollectionUtils.isEmpty(createData) && CollectionUtils.isEmpty(updateData)){
			throw new ServiceException("无人员可同步");
		}

		if (CollectionUtils.isNotEmpty(createUsers)) {
			//新增blade_user
			int aa = createUsers.size();
			int ab = OPENID_PHONE_NUM;
			for (int i = 0; i < createUsers.size(); i += OPENID_PHONE_NUM) {
				if (i + OPENID_PHONE_NUM > createUsers.size()) {//作用为toIndex最后没有800条数据则剩余几条newList中就装几条
					ab = aa - i;
				}
				List<User> subUsers = createUsers.subList(i, i + ab);
				//批量插入
				userService.saveBatch(subUsers);
				for (User subUser : subUsers) {
					bhEncryptDataService.encryptData(subUser.getId(),subUser.getRoleId(),CommonConstant.EncryptDataType.ROLE);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(updateUsers)) {
			//修改blade_user
			int ba = updateUsers.size();
			int bb = OPENID_PHONE_NUM;
			for (int i = 0; i < updateUsers.size(); i += OPENID_PHONE_NUM) {
				if (i + OPENID_PHONE_NUM > updateUsers.size()) {//作用为toIndex最后没有800条数据则剩余几条newList中就装几条
					bb = ba - i;
				}
				List<User> subUsers = updateUsers.subList(i, i + bb);
				//批量插入
				userService.updateBatchById(subUsers);
//				for (User subUser : updateUsers) {
//					bhEncryptDataService.encryptData(subUser.getId(),subUser.getRoleId(),CommonConstant.EncryptDataType.ROLE);
//				}
			}
		}

		if (CollectionUtils.isNotEmpty(createData)) {
			createData = createData.stream()
					.filter(Objects::nonNull)
					.filter(f->f.getName() != null)
					.collect(Collectors.toList());
		}
		if (CollectionUtils.isNotEmpty(createData)) {
			//新增bh_user
			int ca = createData.size();
			int cb = OPENID_PHONE_NUM;
			for (int i = 0; i < createData.size(); i += OPENID_PHONE_NUM) {
				if (i + OPENID_PHONE_NUM > ca) {//作用为toIndex最后没有800条数据则剩余几条newList中就装几条
					cb = ca - i;
				}
				List<BhUser> subUsers = createData.subList(i, i + cb);
				//批量插入
				this.saveBatch(subUsers);
			}
		}

		if (CollectionUtils.isNotEmpty(createData)) {
			updateData = updateData.stream()
					.filter(Objects::nonNull)
					.filter(f->f.getName() != null)
					.collect(Collectors.toList());
		}
		if (CollectionUtils.isNotEmpty(updateData)) {
			//修改bh_user
			int da = updateData.size();
			int db = OPENID_PHONE_NUM;
			for (int i = 0; i < updateData.size(); i += OPENID_PHONE_NUM) {
				if (i + OPENID_PHONE_NUM > da) {//作用为toIndex最后没有800条数据则剩余几条newList中就装几条
					db = da - i;
				}
				List<BhUser> subUsers = updateData.subList(i, i + db);
					//批量插入
					this.updateBatchById(subUsers);


			}
		}
		applicationAuthorityService.submits(authorities);
		return true;

	}


	@Override
	public List<BhApplicationManagementVO> getDeptList(Long id) {

//		List<BhApplicationManagementVO> result = new ArrayList<>();get
		List<BhApplicationManagementVO> result = bhApplicationManagementService.getUserAppById(id);
//		BhUser user = this.getById(id);
//		String ids = null;
//		String positions = null;
//		if (user != null){
//			if (Func.isNotEmpty(user.getMajorDept())){
//				ids = user.getMajorDept().toString();
//			}
//			if (Func.isNotEmpty(user.getOtherDept())){
//				ids = ids+","+user.getOtherDept();
//			}
//			if (Func.isNotEmpty(user.getMajorPosition())){
//				positions = user.getMajorPosition();
//			}
//			if (Func.isNotEmpty(user.getOtherDept())){
//				positions = positions+","+user.getOtherPosition();
//			}
//		}
//		if (ids != null && Func.isNotEmpty(positions)){
//			//user表中的 部门 和职位
//			List<Long> deptIds = Func.toLongList(ids);
//			List<String> positionNames = Func.toStrList(positions);
//			for (int i = 0; i < deptIds.size(); i++) {
//				if (i > positionNames.size()){
//					return result;
//				}
//				LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, deptIds.get(i));
//				BhDept bhDept = iBhDeptService.getOne(query);
//				if (bhDept != null){
//					DeptPositionDto deptPositionDto = new DeptPositionDto();
//					deptPositionDto.setDeptId(bhDept != null ? bhDept.getId() : null);
//					deptPositionDto.setDeptName(bhDept != null ? bhDept.getName() : null);
//					deptPositionDto.setPosition(positionNames != null && positionNames.size() <= i+1 ? positionNames.get(i) : null);
//					//根据userId 和 部门id 授权应用
//					deptPositionDto.setAppIds(bhApplicationManagementService.get(id));
//					deptPositionDto.setUserId(id);
//					result.add(deptPositionDto);
//				}
//
//			}
//		}
		return result;
	}


	@Override
	public List<DeptPositionDto> getMajorDeptList(String ids) {
		List<DeptPositionDto> result = new ArrayList<>();
		List<Long> users = Func.toLongList(ids);
		List<BhUser> bhUsers = this.listByIds(users);
		if (CollectionUtils.isNotEmpty(bhUsers)){
			for (BhUser user : bhUsers) {
				if (user.getMajorDept() != null && Func.isNotEmpty(user.getMajorPosition())){
					LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, user.getMajorDept());
					BhDept bhDept = iBhDeptService.getOne(query);
					DeptPositionDto deptPositionDto = new DeptPositionDto();
					deptPositionDto.setDeptId(bhDept.getId());
					deptPositionDto.setDeptName(bhDept.getName());
					deptPositionDto.setPosition(user.getMajorPosition());
					deptPositionDto.setUserId(user.getId());
					result.add(deptPositionDto);
				}
			}
		}
		return result;
	}

	@Override
	public ResultVO sendMsg(String userIds) {

		ResultVO result = new ResultVO();

		List<Long> sendUsers = null;
		LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).eq(BhUser::getIsSendSms, 1).eq(BhUser::getIsSendSmsInitPassword, 0);

		if (Func.isNotEmpty(userIds)){
			List<Long> ids = Func.toLongList(userIds);
			query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).in(BhUser::getId,ids);
		}
		List<BhUser> list = this.list(query);
		BhUser user = list.get(0);
		if (Func.isNotEmpty(user.getIsSendSms()) && user.getIsSendSms() == 0){
			result.setCode("500");
			result.setMessage("未开启发送短信");

			return result;
		}

		if (Func.isNotEmpty(user.getIsSendSmsInitPassword()) && user.getIsSendSmsInitPassword() == 1){
			result.setCode("500");
			result.setMessage("该用户已发送过");
			return result;
		}
		if (CollectionUtils.isNotEmpty(list)){
			List<BhUser> collect = list.stream().filter(f -> f.getMajorMobile() != null).map(k->{
				k.setAccount(k.getName()+"你好,您的账号为"+k.getAccount()+"，初始密码为Szbh@2024");
				return k;
			}).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(collect)){
				String phones = collect.stream().filter(f->f.getMajorMobile() != null).map(k->hsmUtil.decryptDeprecatedSM4(k.getMajorMobile())).collect(Collectors.joining(","));
				List<String> passwords = collect.stream().map(BhUser::getAccount).collect(Collectors.toList());
				String msg = passwords.stream().collect(Collectors.joining(","));
				sendUsers = collect.stream().map(BhUser::getId).collect(Collectors.toList());
				result  = iBhDeptService.initUserSendSms(phones, msg);
			}
		}
		if (result.getCode().equals("200") && Func.isNotEmpty(sendUsers)){
			result.setMessage("发送成功");
			LambdaUpdateWrapper<BhUser> set = Wrappers.lambdaUpdate(BhUser.class).in(BhUser::getId, sendUsers).set(BhUser::getIsSendSmsInitPassword, 1);
			this.update(set);
		}else {
			result.setMessage("调用失败");
		}
		return result;
	}

	@Override
	public List<BhUser> getUserGroup(String bizId) {
		List<Long> ids = Func.toLongList(bizId);
		LambdaUpdateWrapper<BhUser> query = Wrappers.lambdaUpdate(BhUser.class).in(BhUser::getId, ids);
		List<BhUser> list = this.list(query);
		return list;

	}

	@Override
	public List<BhUser> getList(String key) {
		LambdaUpdateWrapper<BhUser> query = Wrappers.lambdaUpdate(BhUser.class).eq(BhUser::getIsDeleted,0);
		if (Func.isNotEmpty(key)){
			query = Wrappers.lambdaUpdate(BhUser.class).eq(BhUser::getIsDeleted,0).like(BhUser::getName,key);
		}
		List<BhUser> list = this.list(query);
		if (CollectionUtils.isNotEmpty(list)){
			list.stream().forEach(f -> {
				f.setPassword(f.getPassword() != null ? hsmUtil.decryptDeprecatedSM4(f.getPassword()) : f.getPassword());
				f.setEmail(f.getEmail() != null ? hsmUtil.decryptDeprecatedSM4(f.getEmail()) : f.getEmail());
				f.setTelephone(f.getTelephone() != null ? hsmUtil.decryptDeprecatedSM4(f.getTelephone()) : f.getTelephone());
				f.setMajorMobile(f.getMajorMobile() != null ? hsmUtil.decryptDeprecatedSM4(f.getMajorMobile()) : f.getMajorMobile());

			});
		}
		return this.list(query);
	}

	@Override
	public List<BhUser> getUserGroupData(List<Long> ids) {
		List<BhUser> list = baseMapper.getUserGroupData(ids);
		return list;
	}


	@Override
	public List<BhUser> getUserSyn(String ids) {
		if (Func.isEmpty(ids)){
			return null;
		}
		List<Long> longs = Func.toLongList(ids);
		List<Long> deptSynIds = new ArrayList<>();
		//所有子部门
//        List<BhDeptSync> detps = queryAllDeptSynId(longs);
//        if (CollectionUtils.isNotEmpty(detps)){
//            //去重
//            deptSynIds = detps.stream().map(BhDeptSync::getId).distinct().collect(Collectors.toList());
//        }
		deptSynIds.addAll(longs);
//		LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).in(BhUser::getMajorDept, deptSynIds);
//		List<BhUser> list = this.list(query);
		List<BhUser> list = baseMapper.getUserS(deptSynIds);
		return list;
	}


	@Override
	public List<BhUser> getUserListByDeptId(List<String> syncFields, List<String> syncUsers) {
		List<BhUser> result = new ArrayList<>();

		List<String> columns = new ArrayList<>();
		if(Func.isNotEmpty(syncFields)){
			if(syncFields.contains("用户名称")){
				columns.add("name");
			}
			if(syncFields.contains("用户账号")){
				columns.add("account");
			}
			if(syncFields.contains("密码")){
				columns.add("password");
			}
			if(syncFields.contains("所属主部门")){
				columns.add("major_dept");
			}
			if(syncFields.contains("主部门内是否为上级")){
				columns.add("is_leader_in_dept");
			}
			if(syncFields.contains("主部门职位")){
				columns.add("major_position");
			}
			if(syncFields.contains("主部门手机")){
				columns.add("major_mobile");
			}
			if(syncFields.contains("是否短信通知")){
				columns.add("is_send_sms");
			}
			if(syncFields.contains("是否启用")){
				columns.add("is_enable");
			}
			if(syncFields.contains("其他所属部门")){
				columns.add("other_dept");
			}
			if(syncFields.contains("其他部门内是否为上级")){
				columns.add("is_leader_in_other_dept");
			}
			if(syncFields.contains("其他部门职位")){
				columns.add("other_position");
			}
			if(syncFields.contains("性别")){
				columns.add("gender");
			}
			if(syncFields.contains("用户头像")){
				columns.add("avatar");
			}
			if(syncFields.contains("别名")){
				columns.add("english_name");
			}
			if(syncFields.contains("邮箱")){
				columns.add("email");
			}
			if(syncFields.contains("电话")){
				columns.add("telephone");
			}
			if(syncFields.contains("对外简称")){
				columns.add("external_corp_name");
			}
		}
		String columnStr = Func.join(columns);
		result = baseMapper.getUserListBySyncUserAndField(syncUsers, columnStr);
		if (CollectionUtils.isNotEmpty(result)){
			result.stream().forEach(f->{
				f.setPassword(f.getPassword() != null ? hsmUtil.decryptDeprecatedSM4(f.getPassword()):f.getPassword());
				f.setMajorMobile(f.getMajorMobile() != null ? hsmUtil.decryptDeprecatedSM4(f.getMajorMobile()):f.getMajorMobile());
				f.setTelephone(f.getTelephone() != null ? hsmUtil.decryptDeprecatedSM4(f.getTelephone()):f.getTelephone());
				f.setEmail(f.getEmail() != null ? hsmUtil.decryptDeprecatedSM4(f.getEmail()):f.getEmail());
			});
		}
		return result;
	}


	/*********************前台接口**************************/
	@Override
	public Integer getUserRole() {
		Integer flag = 1;
		String roleAlias =  baseMapper.getRole(AuthUtil.getUserId());
		if (roleAlias != null && roleAlias.contains("admin")){
			flag = 0;
		}
		return flag;
	}

	@Override
	public Integer getPasswordType() {
		//默认密码已经修改过
		Integer result = 1;
		BhUser bhUser = this.getOne(Wrappers.<BhUser>query().lambda()
				.eq(BhUser::getAccount, AuthUtil.getUserAccount())
				.eq(BhUser::getIsDeleted, BladeConstant.DB_NOT_DELETED));

		//获取blade_user
//		User bladeUser = userService.getById(AuthUtil.getUserId());
//		String roleId = bladeUser.getRoleId();
//		Integer count = baseMapper.getAdminRole(roleId);
		if ( bhUser != null && (bhUser.getIsPasswordUpdated() == 0 || (bhUser.getUpdatePasswordTime() != null && bhUser.getUpdatePasswordTime().plusMonths(3L).isBefore(LocalDateTime.now())))) {
//			Integer isPasswordUpdated = bhUser.getIsPasswordUpdated();
			result = 0;
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean updatePassword(String account, String oldPassword, String newPassword) {

//		BhUser bhUser = this.getOne(Wrappers.<BhUser>query().lambda()
//				.eq(BhUser::getAccount, account)
//				.eq(BhUser::getPassword, DigestUtil.encrypt(oldPassword))
//				.eq(BhUser::getIsDeleted, BladeConstant.DB_NOT_DELETED));
		String s = DigestUtil.encrypt(oldPassword);
		log.info("old密码："+oldPassword+"：new密码："+newPassword+":账号:"+account);
		log.info("加密后的密码："+s);
		User user = userService.getOne(Wrappers.<User>query().lambda()
				.eq(User::getAccount, account)
				.eq(User::getPassword, DigestUtil.encrypt(oldPassword))
				.eq(User::getIsDeleted, BladeConstant.DB_NOT_DELETED));

		if (user == null) {
			throw new ServiceException("旧密码错误");
		} else {
			boolean update = this.update(Wrappers.<BhUser>update().lambda()
					.set(BhUser::getPassword, DigestUtil.encrypt(newPassword))
					.set(BhUser::getIsPasswordUpdated, 1)
					.set(BhUser::getUpdatePasswordTime, LocalDateTime.now())
					.eq(BhUser::getAccount, account));

			boolean bladeUserUpdate = userService.update(Wrappers.<User>update().lambda()
					.set(User::getPassword, DigestUtil.encrypt(newPassword))
					.eq(User::getAccount, account));

			if (bladeUserUpdate) {
				return true;
			} else {
				return false;
			}
		}
	}


	@Override
	public Boolean importExcel(MultipartFile multipartFile,Long deptId) {
		boolean result = true;
//		iBhDeptService
		ImportBhUserExcelListener bhUserExcelListener = null;
		try {
			bhUserExcelListener = new ImportBhUserExcelListener(this,iBhDeptService,deptId);
			EasyExcel.read(multipartFile.getInputStream(), ImportBhUserInfoVO.class, bhUserExcelListener).sheet().headRowNumber(1).doRead();
		} catch (Exception e) {
			result = false;
			ResultVO resultVO = bhUserExcelListener.getResultVO();
			if ("400".equals(resultVO.getCode())) {
				throw new ServiceException(resultVO.getMessage());
			} else {
				e.printStackTrace();
			}
		}
		return result;
	}

	public BhUserVO getUserDetail(String account){
		BhUserVO detail = baseMapper.getUserDetail(account);
		if (Func.isNotEmpty(detail)){
			detail.setMajorMobile(detail.getMajorMobile() != null ? hsmUtil.decryptDeprecatedSM4(detail.getMajorMobile()):detail.getMajorMobile());
			detail.setTelephone(detail.getTelephone() != null ? hsmUtil.decryptDeprecatedSM4(detail.getTelephone()):detail.getTelephone());
			detail.setPassword(detail.getPassword() != null ? hsmUtil.decryptDeprecatedSM4(detail.getPassword()):detail.getPassword());
			detail.setEmail(detail.getEmail() != null ? hsmUtil.decryptDeprecatedSM4(detail.getEmail()):detail.getEmail());
		}
		return detail;
	}

	@Override
	public List<BhUser> getUserList(List<Long> dept) {
		List<BhUser> list = baseMapper.getUserList(dept);
		return list;
	}


	@Override
	public Boolean changeAllPassword() {
		//bh_user
		LambdaQueryWrapper<BhUser> queryBhUser = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0);
		List<BhUser> bhUsers = this.list(queryBhUser);
		if (CollectionUtils.isNotEmpty(bhUsers)){
			for (BhUser bhUser : bhUsers) {
				bhUser.setPassword(DigestUtil.encrypt("Szbh@2024"));
			}
		}
		//blade_user
		LambdaQueryWrapper<User> queryUser = Wrappers.lambdaQuery(User.class).eq(User::getIsDeleted, 0);
		List<User> users = userService.list(queryUser);
		if (CollectionUtils.isNotEmpty(users)){
			for (User user : users) {
				user.setPassword(DigestUtil.encrypt("Szbh@2024"));
			}
		}
		Boolean result = false;
		Boolean bhUserFlag = true;
		Boolean userFlag = true;
		if (CollectionUtils.isNotEmpty(bhUsers)){
			bhUserFlag = this.updateBatchById(bhUsers);
		}
		if (CollectionUtils.isNotEmpty(users)){
			userFlag = userService.updateBatchById(users);
		}
		if (userFlag && bhUserFlag){
			result = true;
		}
		return result;
	}


	@Override
	public String getLoginPhones() {
		String regex="^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
		List<String> telephones = baseMapper.getLoginUserList();

		String phones = "";
		if (CollectionUtils.isNotEmpty(telephones)){
			telephones = telephones.stream().filter(f-> f.length() > 0 && Pattern.matches(regex,f)).distinct().collect(Collectors.toList());
			phones = Func.join(telephones);
		}
		return phones;
	}
}
