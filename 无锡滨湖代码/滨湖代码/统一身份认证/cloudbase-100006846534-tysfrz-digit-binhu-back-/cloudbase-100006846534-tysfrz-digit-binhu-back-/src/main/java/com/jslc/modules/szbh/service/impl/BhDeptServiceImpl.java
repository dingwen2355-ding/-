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

import cn.hutool.core.convert.Convert;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jslc.modules.system.entity.Dept;
import com.jslc.modules.system.entity.User;
import com.jslc.modules.system.service.IDeptService;
import com.jslc.modules.system.wrapper.DeptWrapper;
import com.jslc.modules.szbh.entity.*;
import com.jslc.modules.szbh.service.*;
import com.jslc.modules.szbh.vo.ResultVO;
import com.jslc.modules.szbh.vo.SystemTreeNode;
import com.jslc.modules.szbh.vo.BhDeptVO;
import com.jslc.modules.szbh.mapper.BhDeptMapper;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.DateTimeUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 部门管理 服务实现类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Service
public class BhDeptServiceImpl extends ServiceImpl<BhDeptMapper, BhDept> implements IBhDeptService {

	@Lazy
	@Autowired
	private IBhDeptSyncService iBhDeptSyncService;

	@Lazy
	@Autowired
	private ISyncRecordService iSyncRecordService;

	@Lazy
	@Autowired
	private IBHSmsService ibhSmsService;

	@Lazy
	@Autowired
	private IDeptService deptService;

	@Lazy
	@Autowired
	private IBhUserApplicationManagementService bhUserApplicationManagementService;

	@Lazy
	@Autowired
	private IBhUserService bhUserService;

	private static final int OPENID_PHONE_NUM = 800; //经实践，800一批插入相对较快

	@Override
	public BhDeptVO getDetail(Long id) {
		BhDeptVO deptVO = baseMapper.getDetail(id);
		return deptVO;
	}

	@Override
	public IPage<BhDeptVO> selectBhDeptPage(IPage<BhDeptVO> page, BhDeptVO bhDept) {
		return page.setRecords(baseMapper.selectBhDeptPage(page, bhDept));
	}

	@Override
	public List<BhDeptVO> getList(String name, Integer type, Long parentId,Integer isEnable) {
		List<BhDeptVO> result = getDeptList(name,type,parentId,isEnable);
		return result;
	}

	@Override
	public List<BhDeptVO> getRootDepts() {
		List<BhDeptVO> result = getRootList();
		return result;
	}

	private List<BhDeptVO> getRootList() {

		List<BhDeptVO> list = baseMapper.getRootList();
		List<BhDeptVO> result  = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(list)){
			result = DeptWrapper.build().listNodeLazyVO2(list);
		}

		if (CollectionUtils.isNotEmpty(result)){
			for (BhDeptVO f : result) {
				if (CollectionUtils.isNotEmpty(f.getChildren())){
					f.setHasChildren(true);
				}
				f.setChildren(null);
			}
		}
		return result;
	}

	@Override
	public List<BhDeptVO> getList2(String name, Integer type, Long parentId, Integer isEnable) {

		List<BhDeptVO> result = getDeptList2(name,type,parentId,isEnable);
		return result;
	}

	private List<BhDeptVO> getDeptList(String name, Integer type, Long parentId, Integer isEnable) {
//		if (Func.isNotEmpty(name)  || Func.isNotEmpty(type)){
//			parentId = null;
//		}
		Boolean flag = false;
		if (parentId != null && parentId.longValue() == 1){
			parentId = null;
			flag = true;
		}
		List<BhDeptVO> list = baseMapper.getDeptList(name,type,parentId,null,isEnable);
		List<BhDeptVO> result  = new ArrayList<>();

		if (CollectionUtils.isNotEmpty(list)){
			result = DeptWrapper.build().listNodeLazyVO2(list);
		}

		if (CollectionUtils.isNotEmpty(result)){
			for (BhDeptVO f : result) {
				if (CollectionUtils.isNotEmpty(f.getChildren())){
					f.setHasChildren(true);
				}
				f.setChildren(null);
			}
		}
		if (flag){
			result = result.stream().filter(f->f.getName().equals("滨湖区") || f.getName().equals("无锡市诚运中心") || f.getId().toString().length() >17 ).collect(Collectors.toList());
		}
		return result;
	}

	private List<BhDeptVO> getDeptList2(String name, Integer type, Long parentId, Integer isEnable) {
//		if (Func.isNotEmpty(name)  || Func.isNotEmpty(type)){
//			parentId = null;
//		}

		List<BhDeptVO> result = new ArrayList<>();

		List<BhDeptVO> listAll = new ArrayList<>();
		List<BhDeptVO> list = baseMapper.getDeptList2(name,type,parentId,null,isEnable);
		listAll.addAll(list);
		if (CollectionUtils.isNotEmpty(list)){
			for (BhDeptVO bhDeptVO : list) {
				listAll.addAll(baseMapper.getDeptList(name,type,bhDeptVO.getId(),null,isEnable));
			}

		}
		if (CollectionUtils.isNotEmpty(listAll)){
			result = DeptWrapper.build().listNodeLazyVO2(listAll);
		}
		if (CollectionUtils.isNotEmpty(result)){
			for (BhDeptVO f : result) {
				if (CollectionUtils.isNotEmpty(f.getChildren())){
					f.setHasChildren(true);
				}
				f.setChildren(null);
			}
		}
		return result;
	}


	@Override
	public Boolean del(List<Long> ids) {
		//删除的时候把父节点下的子节点也删除
		List<Long> hdDeptIds  = new ArrayList<>();
		for (Long id : ids) {
			LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getIsDeleted, 0).eq(BhDept::getParentId, id);
			List<BhDept> list = this.list(query);
			if (CollectionUtils.isNotEmpty(list)){
				hdDeptIds.addAll(list.stream().map(BhDept::getDeptId).collect(Collectors.toList()));
			}
			hdDeptIds.add(id);
		}
		//部门下有人员不让删除
		if (CollectionUtils.isNotEmpty(hdDeptIds)){
			LambdaQueryWrapper<BhUser> query = Wrappers.lambdaQuery(BhUser.class).eq(BhUser::getIsDeleted, 0).in(BhUser::getMajorDept, hdDeptIds);
			List<BhUser> userList = bhUserService.list(query);
			if (CollectionUtils.isNotEmpty(userList)){
				throw new ServiceException("该部门及其子部门下有人员，请先删除部门下的人员信息");
			}
		}

		LambdaUpdateWrapper<BhDept> set = Wrappers.lambdaUpdate(BhDept.class).in(BhDept::getDeptId, hdDeptIds).set(BhDept::getIsDeleted, 1).set(BhDept::getDataSource,1);
		Boolean flag = this.update(set);
		if(flag){
			//将blade_dept中删除
			LambdaUpdateWrapper<Dept> delDept = Wrappers.lambdaUpdate(Dept.class).in(Dept::getId, hdDeptIds).set(Dept::getIsDeleted, 1);
			deptService.update(delDept);
		}
		return flag;
	}

	@Override
	public Boolean changeEnable(Long id) {
		Integer state = 0;
		LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getDeptId, id);
		BhDept one = this.getOne(query);
		if (Func.isEmpty(one.getIsEnable()) || one.getIsEnable() == 0){
			state = 1;
		}
		LambdaUpdateWrapper<BhDept> set = Wrappers.lambdaUpdate(BhDept.class).eq(BhDept::getDeptId, id).set(BhDept::getIsEnable, state);
		return  this.update(set);
	}

	@Override
	public void downLoad(HttpServletResponse response, String name, Integer type,List<Long> ids,Integer isEnable) {
		OutputStream out = null;
		List<BhDeptVO> deptList = baseMapper.getDeptList(name,type,null,ids, isEnable);
//		List<String> factorHead = factorCodeService.getFactorHead(treeType, factorCodes, INDUSTRIAL);
		try {
			String fileName = "部门列表.xlsx";
			out = response.getOutputStream();
			//设置返回头
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");


//			// 表头
			List<List<String>> headList = new ArrayList<List<String>>();

			List<String> headTitle = new ArrayList<String>();
			headTitle.add("部门名称");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("状态");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("类型");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("部门描述");
			headList.add(headTitle);

			headTitle = new ArrayList<String>();
			headTitle.add("更新时间");
			headList.add(headTitle);



			// 所有行的集合
			List<List<Object>> resultList = new ArrayList<List<Object>>();

			for (BhDeptVO bhDeptVO : deptList) {
				List<Object> row = new ArrayList<Object>();
				row.add(bhDeptVO.getName());
				row.add(bhDeptVO.getIsEnableName());
				row.add(bhDeptVO.getTypeName());
				row.add(bhDeptVO.getDescription());
				row.add(Func.isNotEmpty(bhDeptVO.getCreateTime()) ? DateTimeUtil.format(bhDeptVO.getCreateTime(),"yyyy-MM-dd HH:mm:ss") : null);
				resultList.add(row);

			}

			EasyExcel.write(response.getOutputStream())
					.head(headList)
					.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
					.sheet("部门列表")
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
	public Boolean submit(BhDept bhDept) {
		LambdaQueryWrapper<BhDept> queryBhdept = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getIsDeleted, 0);
		List<BhDept> list1 = this.list(queryBhdept);
		String upName = null;
		//获取所有部门 获取最大的一个排序
		Long sort = 10000000L;
		List<BhDept> list = this.list();
		if (CollectionUtils.isNotEmpty(list)){
			Boolean flag = true;
			List<BhDept> collect = list.stream().filter(f -> f.getDataSource() == 2).collect(Collectors.toList());
			List<BhDept> collect2 = list.stream().filter(f -> f.getDataSource() == 1).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(collect)){
				sort = collect.stream().map(BhDept::getSort).filter(f->f != null).max(Long::compareTo).get()+1;
				flag =false;
			}
			if (flag && CollectionUtils.isNotEmpty(collect2)){
				sort = collect2.stream().map(BhDept::getSort).filter(f->f != null).max(Long::compareTo).get()*100;
			}
		}
		Boolean flag = false;
		if (Func.isNotEmpty(bhDept.getParentId()) && bhDept.getParentId() != 1L){
			LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getIsDeleted, 0).eq(BhDept::getDeptId, bhDept.getParentId());
			BhDept byId = this.getOne(query);
			if (Func.isNotEmpty(byId)){
				bhDept.setAncestors(byId.getParentId()+","+bhDept.getParentId());
				upName = byId.getName();
			}
		}else {

			bhDept.setParentId(1L);
			bhDept.setAncestors("0");

		}
		List<BhDept> collect = list1.stream().filter(f -> f.getName().equals(bhDept.getName())).collect(Collectors.toList());
		if (Func.isEmpty(bhDept.getId())){ // 新增
			if (CollectionUtils.isNotEmpty(collect)){
				throw new ServiceException("新增部门名已存在");
			}
			bhDept.setCreateUser(AuthUtil.getUserId());
			bhDept.setIsDeleted(0);
			bhDept.setCreateTime(LocalDateTime.now());
			bhDept.setDataSource(2);
			bhDept.setIsUpdated(0);
			bhDept.setSort(sort);
			bhDept.setUpdateUser(AuthUtil.getUserId());
			bhDept.setUpdateTime(LocalDateTime.now());
			this.save(bhDept);
			bhDept.setDeptId(bhDept.getId());
			flag = this.saveOrUpdate(bhDept);
			//新增部门的时候 需要新增到blade_dept表
			Dept dept = new Dept();
			dept.setTenantId(AuthUtil.getTenantId());
			//用来获取 父级id
			if (upName != null){
				LambdaQueryWrapper<Dept> query = Wrappers.lambdaQuery(Dept.class).eq(Dept::getDeptName, upName).eq(Dept::getIsDeleted, 0);
				Dept one = deptService.getOne(query);
				if (Func.isNotEmpty(one)){
					dept.setParentId(one.getId());
				}else {
					dept.setParentId(0L);
				}
			}else {
				dept.setParentId(0L);
			}
			//用来区分是新增还是修改
			if (bhDept.getName() != null){
				LambdaQueryWrapper<Dept> query = Wrappers.lambdaQuery(Dept.class).eq(Dept::getDeptName, bhDept.getName()).eq(Dept::getIsDeleted, 0);
				Dept one = deptService.getOne(query);
				if (Func.isNotEmpty(one)){
					if (one.getId().longValue() != bhDept.getId().longValue()){
						deptService.removeById(one.getId());
						dept.setIsDeleted(0);
						dept.setSort(0);
						dept.setDeptCategory(1);
						dept.setId(bhDept.getId());
					}else {
						dept.setId(one.getId());
					}
				}else {//新增
					dept.setIsDeleted(0);
					dept.setSort(0);
					dept.setDeptCategory(1);
					dept.setId(bhDept.getId());
				}
			}

			dept.setDeptName(bhDept.getName());
			dept.setFullName(bhDept.getFullName());


			dept.setRemark(bhDept.getBriefIntroduction());
			deptService.saveOrUpdate(dept);


		}else {//修改
			//判断修改名称唯一性
			if (CollectionUtils.isNotEmpty(collect) && collect.get(0).getId().longValue() != bhDept.getId().longValue()){
				throw new ServiceException("修改的部门名称已存在");
			}
			//用来记录修改之前的数据
			BhDept bhdept1 = this.getById(bhDept.getId());


			bhDept.setUpdateUser(AuthUtil.getUserId());
			bhDept.setUpdateTime(LocalDateTime.now());
			bhDept.setIsUpdated(1);
			flag = this.saveOrUpdate(bhDept);
			//新增部门的时候 需要新增到blade_dept表
			Dept dept = new Dept();
			dept.setTenantId(AuthUtil.getTenantId());
			//用来获取 父级id
			if (upName != null){
				LambdaQueryWrapper<Dept> query = Wrappers.lambdaQuery(Dept.class).eq(Dept::getDeptName, upName).eq(Dept::getIsDeleted, 0);
				Dept one = deptService.getOne(query);
				if (Func.isNotEmpty(one)){
					dept.setParentId(one.getId());
				}else {
					dept.setParentId(0L);
				}
			}else {
				dept.setParentId(0L);
			}
			//用来区分是新增还是修改
			if (bhdept1.getName() != null){
				LambdaQueryWrapper<Dept> query = Wrappers.lambdaQuery(Dept.class).eq(Dept::getDeptName, bhdept1.getName()).eq(Dept::getIsDeleted, 0);
				Dept one = deptService.getOne(query);
				if (Func.isNotEmpty(one)){
					//id不一样是因为之前添加数据问题
					if (one.getId().longValue() != bhDept.getId().longValue()){
						deptService.removeById(one.getId());
						dept.setIsDeleted(0);
						dept.setSort(0);
						dept.setDeptCategory(1);
						dept.setId(bhDept.getId());
					}else {
						dept.setId(one.getId());
					}
				}else {//新增
					dept.setIsDeleted(0);
					dept.setSort(0);
					dept.setDeptCategory(1);
					dept.setId(bhDept.getId());
				}
			}
//			dept.setDeptCategory(1);
			dept.setDeptName(bhDept.getName());
			dept.setFullName(bhDept.getFullName());
//			dept.setIsDeleted(0);
//			dept.setSort(0);
			dept.setRemark(bhDept.getBriefIntroduction());
			deptService.saveOrUpdate(dept);
		}
		return flag;
	}

	@Override
	public List<SystemTreeNode> getBhDeptTree(Long id) {
		List<SystemTreeNode> result = new ArrayList<>();
		List<SystemTreeNode> list = baseMapper.getOrgTree2(id);
		LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getIsDeleted, 0);
		List<BhDept> deptList = this.list(query);
		if (CollectionUtils.isNotEmpty(list) && CollectionUtils.isNotEmpty(deptList)){
			Map<Long, List<BhDept>> map = deptList.stream().collect(Collectors.groupingBy(BhDept::getParentId));

			List<Long> ids = new ArrayList<>();
			Long deptId = 373L;
			ids.add(deptId);
			queryParentId(deptId,map,ids);
//			List<Long> deptIds = deptList.stream().filter(f -> f.getDataSource() == 2).map(BhDept::getDeptId).distinct().collect(Collectors.toList());
//			ids.addAll(deptIds);
			List<Long> collect = deptList.stream().filter(f -> f.getDeptId().toString().length() > 18).map(BhDept::getDeptId).distinct().collect(Collectors.toList());
			ids.addAll(collect);
			list= list.stream().filter(f->ids.contains(f.getId())).collect(Collectors.toList());
		}
		result = ForestNodeMerger.merge(list);

		return result;
	}

	/**
	 * 递归查询
	 * @param ids
	 * @return
	 */
	public void queryParentId(Long id,Map<Long, List<BhDept>> map,List<Long> ids){

		//根据父ID查询部门
		List<BhDept> depts = map.get(id);
		if (CollectionUtils.isNotEmpty(depts)){
			//拿到当前所有部门ID
			for (BhDept dept : depts) {
				ids.add(dept.getDeptId());
				queryParentId(dept.getDeptId(),map,ids);
			}
		}else {
			//如果没有下级部门那么我们就返回空集合，结束递归。
			ids.add(id);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean syn() {
		//所有勾选的要同步的id
		List<BhDeptSync> bhDeptSyncList = new ArrayList<>();

		//所有勾选的要同步的id
		List<BhDept> data = new ArrayList<>();

		//所有勾选的需要新增的bh_dept
		List<BhDept> createData = new ArrayList<>();
		//所有勾选的需要修改的bh_dept
		List<BhDept> updateData = new ArrayList<>();

		//需要新增到blade_dept表中的数据
		List<Dept> createDepts = new ArrayList<>();
		//需要修改到blade_dept表中的数据
		List<Dept> updateDepts = new ArrayList<>();


		//获取所有部门
		LambdaQueryWrapper<Dept> queryAccount = Wrappers.lambdaQuery(Dept.class).eq(Dept::getIsDeleted, 0);
		List<Dept> listDept = deptService.list(queryAccount);
		Map<Long,Dept> mapDept = new HashMap<>();
		if (CollectionUtils.isNotEmpty(listDept)){
			mapDept = listDept.stream().collect(Collectors.toMap(Dept::getId,o->o));
		}

//		if (Func.isNotEmpty(ids)){
//			List<Long> resultIds = new ArrayList<>();
//			resultIds.addAll(Func.toLongList(ids));

			//判断id下是否有子节点数据
//			iBhDeptSyncService
//			for (Long id : resultIds) {
//				LambdaQueryWrapper<BhDeptSync> query = Wrappers.lambdaQuery(BhDeptSync.class).eq(BhDeptSync::getId,id);
				//所有的
//				List<BhDeptSync> list = iBhDeptSyncService.list();
//				bhDeptSyncList.addAll(list);
//			}
//		}
		//获取所有同步部门数据
		List<BhDeptSync> list = iBhDeptSyncService.list();
		bhDeptSyncList.addAll(list);

		if (CollectionUtils.isNotEmpty(bhDeptSyncList)) {
			bhDeptSyncList = bhDeptSyncList.stream().distinct().collect(Collectors.toList());
		}
//		获取 要同步的表的 需要同步的dept_id数据
		LambdaQueryWrapper<BhDept> query = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getIsUpdated, 0);
		List<BhDept> bhDeptList = this.list(query);
		//不需要同步的数据
		LambdaQueryWrapper<BhDept> query1 = Wrappers.lambdaQuery(BhDept.class).eq(BhDept::getIsUpdated, 1);
		List<BhDept> bhDeptList2 = this.list(query1);

		//将不需要同步的数据过滤
		if (CollectionUtils.isNotEmpty(bhDeptSyncList) && CollectionUtils.isNotEmpty(bhDeptList2)){
			List<Long> collect = bhDeptList2.stream().map(BhDept::getDeptId).collect(Collectors.toList());
			bhDeptSyncList = bhDeptSyncList.stream().filter(f->!collect.contains(f.getId())).collect(Collectors.toList());
		}

		//开始同步 若是同步表没有则新增 有则覆盖
		if (CollectionUtils.isNotEmpty(bhDeptSyncList)){
			List<Long> bhDeptIds = new ArrayList<>();
			Map<Long, List<BhDept>> collect = new HashMap<>();
			if (CollectionUtils.isNotEmpty(bhDeptList)){
				bhDeptIds = bhDeptList.stream().map(BhDept::getDeptId).collect(Collectors.toList());
				 collect = bhDeptList.stream().collect(Collectors.groupingBy(BhDept::getDeptId));
			}
			for (BhDeptSync f : bhDeptSyncList) {
				BhDept bhDept = new BhDept();
				if (bhDeptIds.contains(f.getId())){//修改
					bhDept.setDeptId(f.getId());
					bhDept.setId(collect.get(f.getId()).get(0).getId());
					bhDept.setName(f.getName());
					bhDept.setType(Func.isNotEmpty(f.getType()) ? f.getType() : 0);
					//部门描述
//					bhDept.setDescription(f.getDescription);
					bhDept.setSort(f.getSort());
//					bhDept.setIsEnable(1);

					bhDept.setPartyUserLimit(f.getPartyUserLimit());
					bhDept.setFullName(f.getFullName());
					bhDept.setShortName(f.getShortName());
					bhDept.setBriefIntroduction(f.getBriefIntroduction());
					bhDept.setDomainName(f.getDomainName());
					bhDept.setAddress(f.getAddress());
					bhDept.setTelephone(f.getTelephone());
					bhDept.setOrganizationCode(f.getOrganizationCode());
					bhDept.setUpdateUser(AuthUtil.getUserId());
					bhDept.setUpdateTime(LocalDateTime.now());

					updateData.add(bhDept);

					//同步人员的时候 同步到 blade_dept 表
					Dept dept = new Dept();
//					LambdaQueryWrapper<Dept> queryAccount = Wrappers.lambdaQuery(Dept.class).eq(Dept::getId, f.getId());
//					Dept dept1 = deptService.getOne(queryAccount);
//					Dept byId = deptService.getById(f.getId());
					Dept byId = mapDept.get(f.getId());

					dept.setParentId(f.getParentId() == 1L?0L:f.getParentId());
					dept.setRemark(f.getBriefIntroduction());
					dept.setDeptName(f.getName());
					dept.setFullName(f.getFullName());
					dept.setId(f.getId());
					if (Func.isNotEmpty(byId)){
						updateDepts.add(dept);
//						deptService.saveOrUpdate(dept);
					}else {
						dept.setTenantId("000000");
						dept.setDeptCategory(1);
						dept.setIsDeleted(0);
//						deptService.save(dept);
						createDepts.add(dept);
					}


				}else {//新增
					bhDept.setDeptId(f.getId());
					bhDept.setName(f.getName());
					bhDept.setType(Func.isNotEmpty(f.getType()) ? f.getType() : 0);
					//部门描述
//					bhDept.setDescription(f.getDescription);
					bhDept.setSort(f.getSort());
					bhDept.setIsEnable(1);
					bhDept.setParentId(f.getParentId());
					//没有祖节点
//					bhDept.setAncestors(f.get());

					bhDept.setPartyUserLimit(f.getPartyUserLimit());
					bhDept.setFullName(f.getFullName());
					bhDept.setShortName(f.getShortName());
					bhDept.setBriefIntroduction(f.getBriefIntroduction());
					bhDept.setDomainName(f.getDomainName());
					bhDept.setAddress(f.getAddress());
					bhDept.setTelephone(f.getTelephone());

					bhDept.setOrganizationCode(f.getOrganizationCode());
					bhDept.setCreateUser(AuthUtil.getUserId());
					bhDept.setCreateTime(LocalDateTime.now());
					bhDept.setUpdateUser(AuthUtil.getUserId());
					bhDept.setUpdateTime(LocalDateTime.now());
					bhDept.setIsDeleted(0);
					bhDept.setIsUpdated(0);
					bhDept.setDataSource(1);

					createData.add(bhDept);

//					//同步人员的时候 同步到 blade_user 表
//					Dept dept = new Dept();
//					dept.setId(f.getId());
//					dept.setTenantId("000000");
//					dept.setParentId(f.getParentId() == 1L?0L:f.getParentId());
//					dept.setRemark(f.getBriefIntroduction());
//					dept.setDeptName(f.getName());
//					dept.setFullName(f.getFullName());
//					dept.setDeptCategory(1);
//					dept.setIsDeleted(0);
//
//					deptService.save(dept);

					//同步人员的时候 同步到 blade_dept 表
					Dept dept = new Dept();
//					LambdaQueryWrapper<Dept> queryAccount = Wrappers.lambdaQuery(Dept.class).eq(Dept::getId, f.getId());
//					Dept dept1 = deptService.getOne(queryAccount);
//					Dept byId = deptService.getById(f.getId());
					Dept byId = mapDept.get(f.getId());

					dept.setParentId(f.getParentId() == 1L?0L:f.getParentId());
					dept.setRemark(f.getBriefIntroduction());
					dept.setDeptName(f.getName());
					dept.setFullName(f.getFullName());
					dept.setId(f.getId());
					if (Func.isNotEmpty(byId)){
						updateDepts.add(dept);
//						deptService.saveOrUpdate(dept);
					}else {
						dept.setTenantId("000000");
						dept.setDeptCategory(1);
						dept.setIsDeleted(0);
//						deptService.save(dept);
						createDepts.add(dept);
					}

				}
//				data.add(bhDept);
			}

			SyncRecord syncRecord = new SyncRecord();
			syncRecord.setUpOrDown("上游同步");
			syncRecord.setAppName("政务微信");
			syncRecord.setDataType("部门信息");
			syncRecord.setResult("同步"+bhDeptSyncList.size()+"条数据");
			syncRecord.setCause("无异常");
			Long userId = AuthUtil.getUserId();
			syncRecord.setIsAutomatic(Func.isNotEmpty(userId) ? 0:1);
			syncRecord.setCreateTime(LocalDateTime.now());
			syncRecord.setCreateUser(userId);
			iSyncRecordService.save(syncRecord);
		}
//		if (CollectionUtils.isEmpty(data)){
//			throw new ServiceException("无部门可同步");
//		}


		if (CollectionUtils.isNotEmpty(createDepts)) {
			//新增blade_dept
			int aa = createDepts.size();
			int ab = OPENID_PHONE_NUM;
			for (int i = 0; i < createDepts.size(); i += OPENID_PHONE_NUM) {
				if (i + OPENID_PHONE_NUM > createDepts.size()) {//作用为toIndex最后没有800条数据则剩余几条newList中就装几条
					ab = aa - i;
				}
				List<Dept> subDepts = createDepts.subList(i, i + ab);
				//批量插入
				deptService.saveBatch(subDepts);
			}
		}

		if (CollectionUtils.isNotEmpty(updateDepts)) {
			//修改新增blade_dept
			int ba = updateDepts.size();
			int bb = OPENID_PHONE_NUM;
			for (int i = 0; i < updateDepts.size(); i += OPENID_PHONE_NUM) {
				if (i + OPENID_PHONE_NUM > updateDepts.size()) {//作用为toIndex最后没有800条数据则剩余几条newList中就装几条
					bb = ba - i;
				}
				List<Dept> subDepts = updateDepts.subList(i, i + bb);
				//批量插入
				deptService.updateBatchById(subDepts);
			}
		}


		if (CollectionUtils.isNotEmpty(createData)) {
			//新增bh_dept
			int ca = createData.size();
			int cb = OPENID_PHONE_NUM;
			for (int i = 0; i < createData.size(); i += OPENID_PHONE_NUM) {
				if (i + OPENID_PHONE_NUM > ca) {//作用为toIndex最后没有800条数据则剩余几条newList中就装几条
					cb = ca - i;
				}
				List<BhDept> subDepts = createData.subList(i, i + cb);
				//批量插入
				this.saveBatch(subDepts);
			}
		}

		if (CollectionUtils.isNotEmpty(updateData)) {
			//修改bh_dept
			int da = updateData.size();
			int db = OPENID_PHONE_NUM;
			for (int i = 0; i < updateData.size(); i += OPENID_PHONE_NUM) {
				if (i + OPENID_PHONE_NUM > da) {//作用为toIndex最后没有800条数据则剩余几条newList中就装几条
					db = da - i;
				}
				List<BhDept> subDepts = updateData.subList(i, i + db);
				//批量插入
				this.updateBatchById(subDepts);
			}
		}
		return true;
	}

	@Override
	public List<BhDept> getDeptListByDeptId(Long deptId, Integer noFetchChild, List<String> syncDepts,List<String> syncFields) {
		List<BhDept> result = new ArrayList<>();

		List<String> columns = new ArrayList<>();
		if(Func.isNotEmpty(syncFields)){
			if(syncFields.contains("部门名称")){
				columns.add("name");
			}
			if(syncFields.contains("部门类型")){
				columns.add("type");
			}
			if(syncFields.contains("成员上限")){
				columns.add("party_user_limit");
			}
			if(syncFields.contains("部门详情")){
				columns.add("description");
			}
			if(syncFields.contains("单位全称")){
				columns.add("full_name");
			}
			if(syncFields.contains("单位简称")){
				columns.add("short_name");
			}
			if(syncFields.contains("单位简介")){
				columns.add("brief_introduction");
			}
			if(syncFields.contains("单位网站")){
				columns.add("domain_name");
			}
			if(syncFields.contains("单位地址")){
				columns.add("address");
			}
			if(syncFields.contains("单位电话")){
				columns.add("telephone");
			}
			if(syncFields.contains("信用代码")){
				columns.add("organization_code");
			}
			columns.add("dept_id");
			columns.add("parent_id");
			columns.add("sort");
		}
		String columnStr = Func.join(columns);
		List<BhDept> list = baseMapper.getDeptListBySyncDeptAndField(syncDepts, columnStr);
		if(Func.isNotEmpty(list)){
			//1不递归，0递归
			if(noFetchChild == null || noFetchChild == 1){
//				result = list.stream().filter(o->o.getDeptId().equals(deptId)||o.getParentId().equals(deptId)).collect(Collectors.toList());
				result = list;
			}else{
				//查询部门
				BhDept bhDept = this.getOne(Wrappers.<BhDept>query().lambda().eq(BhDept::getDeptId,deptId));
				BhDeptVO bhDeptVO = BeanUtil.copyProperties(bhDept,BhDeptVO.class);

				//所有子集合
				List<BhDeptVO> bhDeptVOList = BeanUtil.copyProperties(list,BhDeptVO.class);
				List<BhDeptVO> childrenList = getChildren(bhDeptVO, bhDeptVOList);

				//加入查询部门
				childrenList.add(bhDeptVO);
				result = BeanUtil.copyProperties(childrenList,BhDept.class);
			}

			result = result.stream().distinct().sorted(Comparator.comparing(BhDept::getSort).reversed()).collect(Collectors.toList());
			result.forEach(o->{
				o.setId(o.getDeptId());
			});
		}

		return result;
	}

	/*
	 * @method getChildren
	 * @description 查询所有子节点
	 * @author xxz
	 * @date 2023/5/16
	 * @param father
	 * @param list
	 */
	public List<BhDeptVO> getChildren(BhDeptVO father, List<BhDeptVO> list) {
		List<BhDeptVO> result = new ArrayList<>();
		for(BhDeptVO children:list){
			if(father.getDeptId().equals(children.getParentId())){
				result.addAll(getChildren(children,list));
				result.add(children);
			}
		}

		return result;
	}

	@Override
	public ResultVO initUserSendSms(String phones, String msg) {

		return ibhSmsService.initUserSendSms(phones,msg);
	}
	@Override
	public String getAppIds(Long userId,Long deptId) {
		String appIds = null;
		LambdaQueryWrapper<BhUserApplicationManagement> query = Wrappers.lambdaQuery(BhUserApplicationManagement.class).eq(BhUserApplicationManagement::getUserId, userId).eq(BhUserApplicationManagement::getDeptId, deptId);
		List<BhUserApplicationManagement> list = bhUserApplicationManagementService.list(query);
		if (CollectionUtils.isNotEmpty(list)){
			appIds = list.stream().map(BhUserApplicationManagement::getApplicationManagementId).map(id -> Convert.toStr(id)).collect(Collectors.joining(","));

		}
		return appIds;
	}

	@Override
	public List<BhDept> getDepts(String bizId) {
		List<Long> ids = Func.toLongList(bizId);
		LambdaUpdateWrapper<BhDept> query = Wrappers.lambdaUpdate(BhDept.class).in(BhDept::getDeptId, ids).orderByDesc(BhDept::getSort).orderByAsc(BhDept::getDeptId);
		List<BhDept> list = this.list(query);
		return list;

	}
}
