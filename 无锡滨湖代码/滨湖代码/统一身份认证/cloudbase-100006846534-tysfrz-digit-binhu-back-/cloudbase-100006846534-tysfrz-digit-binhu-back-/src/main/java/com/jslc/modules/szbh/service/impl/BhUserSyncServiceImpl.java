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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jslc.modules.szbh.entity.BhApplicationCategory;
import com.jslc.modules.szbh.entity.BhDeptSync;
import com.jslc.modules.szbh.entity.BhUserSync;
import com.jslc.modules.szbh.entity.SyncRecord;
import com.jslc.modules.szbh.mapper.BhDeptSyncMapper;
import com.jslc.modules.szbh.mapper.BhUserSyncMapper;
import com.jslc.modules.szbh.service.IBhDeptSyncService;
import com.jslc.modules.szbh.service.IBhUserSyncService;
import com.jslc.modules.szbh.service.ISyncRecordService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户管理 服务实现类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Service
public class BhUserSyncServiceImpl extends ServiceImpl<BhUserSyncMapper, BhUserSync> implements IBhUserSyncService {

    @Autowired
    private ISyncRecordService iSyncRecordService;


    @Autowired
    private IBhDeptSyncService bhDeptSyncService;
    @Autowired
    private BhDeptSyncMapper bhDeptSyncMapper;

    @Value(value = "${zwwx.userUrl}")
    private String USER_URL;
    @Value(value = "${zwwx.deptId}")
    private String DEPT_ID;

    @Override
//    @Scheduled(cron = "0 30 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public boolean syncUser() {
        Boolean result = false;

        Map<String, Object> accessTokenMap = bhDeptSyncMapper.getAccessToken("access_token");
        String value = accessTokenMap.get("value") != null ? (String) accessTokenMap.get("value") : "";
        Long time = accessTokenMap.get("time") != null ? (Long) accessTokenMap.get("time") : null;

        String token = "";
        if (Func.isNotEmpty(value) && time > System.currentTimeMillis()) {
            token = value;
        } else {
            token = bhDeptSyncService.getAccessToken();
        }

        //请求地址
        String url = USER_URL + "?access_token=" + token + "&department_id=" + DEPT_ID + "&fetch_child=1";
        //发送请求
        String backResult = HttpUtil.get(url);

        //判断提取返回结果
        if (StringUtils.isNotBlank(backResult)) {
            JSONObject json = JSONObject.parseObject(backResult);
            if (json.getInteger("errcode") == 0) {
                JSONArray userlist = json.getJSONArray("userlist");

                List<BhUserSync> list = new ArrayList<>();
                for (int i = 0; i < userlist.size(); i++) {
                    JSONObject o = userlist.getJSONObject(i);

                    BhUserSync bhUserSync = new BhUserSync();
                    bhUserSync.setName(o.getString("name"));
                    bhUserSync.setUserid(o.getString("userid"));
                    JSONArray departments = o.getJSONArray("department");
                    bhUserSync.setMajorDepartment(departments.getLong(0));
                    bhUserSync.setDepartment(Func.join(departments));
                    bhUserSync.setPosition(o.getString("position"));
                    bhUserSync.setMobile(o.getString("mobile"));
                    bhUserSync.setGender(o.getString("gender"));
                    bhUserSync.setEmail(o.getString("email"));
                    bhUserSync.setAvatar(o.getString("avatar"));
                    bhUserSync.setStatus(o.getInteger("status"));
                    bhUserSync.setEnable(o.getInteger("enable"));
                    JSONObject extattr = o.getJSONObject("extattr");
                    bhUserSync.setExtattr(extattr.toJSONString());
                    bhUserSync.setHideMobile(o.getInteger("hide_mobile"));
                    bhUserSync.setEnglishName(o.getString("english_name"));
                    bhUserSync.setTelephone(o.getString("telephone"));
                    String orderList = Func.join(o.getJSONArray("order"));
                    bhUserSync.setSort(orderList);
                    bhUserSync.setQrCode(o.getString("qr_code"));
                    String isLeaderInDepts = Func.join(o.getJSONArray("is_leader_in_dept"));
                    bhUserSync.setIsLeaderInDept(isLeaderInDepts);
                    String positions = Func.join(o.getJSONArray("positions"));
                    bhUserSync.setPositions(positions);
                    bhUserSync.setCountryCode(o.getString("country_code"));
                    JSONObject externalProfile = o.getJSONObject("external_profile");
                    String externalCorpName = Func.isNotEmpty(externalProfile) ? externalProfile.getString("external_corp_name") : null;
                    bhUserSync.setExternalCorpName(externalCorpName);
                    bhUserSync.setCreateUser(AuthUtil.getUserId());
                    bhUserSync.setCreateTime(LocalDateTime.now());
                    list.add(bhUserSync);
                }

                //先删除已有的
                baseMapper.removeAllUserSync();
                result = this.saveBatch(list);
            } else {
                SyncRecord syncRecord = new SyncRecord();
                syncRecord.setUpOrDown("上游同步");
                syncRecord.setAppName("政务微信");
                syncRecord.setDataType("人员信息");
                syncRecord.setResult("同步用户至原始记录表");
                syncRecord.setCause("异常");
                Long userId = AuthUtil.getUserId();
                syncRecord.setIsAutomatic(Func.isNotEmpty(userId) ? 0:1);
                syncRecord.setCreateTime(LocalDateTime.now());
                syncRecord.setCreateUser(userId);
                iSyncRecordService.save(syncRecord);
                throw new RuntimeException("同步用户至原始记录表失败: 信息"+ json.get("errmsg").toString());
            }
        } else {
            SyncRecord syncRecord = new SyncRecord();
            syncRecord.setUpOrDown("上游同步");
            syncRecord.setAppName("政务微信");
            syncRecord.setDataType("人员信息");
            syncRecord.setResult("同步用户至原始记录表");
            syncRecord.setCause("异常");
            Long userId = AuthUtil.getUserId();
            syncRecord.setIsAutomatic(Func.isNotEmpty(userId) ? 0:1);
            syncRecord.setCreateTime(LocalDateTime.now());
            syncRecord.setCreateUser(userId);
            iSyncRecordService.save(syncRecord);
            throw new RuntimeException("请求失败");
        }

        return result;
    }

    @Override
    public List<BhUserSync> getUserSyn(String ids) {
        if (Func.isEmpty(ids)){
            return null;
        }
        List<Long> longs = Func.toLongList(ids);
        List<Long> deptSynIds = new ArrayList<>();

        deptSynIds.addAll(longs);
//        LambdaQueryWrapper<BhUserSync> query = Wrappers.lambdaQuery(BhUserSync.class).in(BhUserSync::getMajorDepartment, deptSynIds);
        List<BhUserSync> list = baseMapper.getUserSync(deptSynIds);
        return list;
    }

    /**
     * 递归查询
     * @param ids
     * @return
     */
    public List<BhDeptSync> queryAllDeptSynId(List<Long> ids){
        LambdaQueryWrapper<BhDeptSync> query = Wrappers.lambdaQuery(BhDeptSync.class).in(BhDeptSync::getParentId, ids);
        //根据父ID查询部门
        List<BhDeptSync> departments = bhDeptSyncService.list(query);
        if (CollectionUtils.isNotEmpty(departments)) {
            //拿到当前所有部门ID
            List<Long> parentIds = departments.stream().map(item -> item.getId()).collect(Collectors.toList());
            //拼接子部门查询结果
            departments.addAll(queryAllDeptSynId(parentIds));
            return departments;
        } else {
            //如果没有下级部门那么我们就返回空集合，结束递归。
            return Lists.newArrayList();
        }
    }
}
