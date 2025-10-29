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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.modules.szbh.entity.BhDeptSync;
import com.jslc.modules.szbh.entity.SyncRecord;
import com.jslc.modules.szbh.mapper.BhDeptSyncMapper;
import com.jslc.modules.szbh.service.IBhDeptSyncService;
import com.jslc.modules.szbh.service.ISyncRecordService;
import com.jslc.modules.szbh.vo.SystemTreeNode;
import org.apache.commons.lang3.StringUtils;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.node.ForestNodeMerger;
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
 * 部门管理同步原始表 服务实现类
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Service
public class BhDeptSyncServiceImpl extends ServiceImpl<BhDeptSyncMapper, BhDeptSync> implements IBhDeptSyncService {


    @Value(value = "${zwwx.tokenUrl}")
    private String TOKEN_URL;
    @Value(value = "${zwwx.corpId}")
    private String CORPID;
    @Value(value = "${zwwx.corpSecret}")
    private String CORPSECRET;
    @Value(value = "${zwwx.deptUrl}")
    private String DEPT_URL;
    @Value(value = "${zwwx.deptId}")
    private String DEPT_ID;

    @Autowired
    private ISyncRecordService iSyncRecordService;

    public String getAccessToken() {
        String token = "";
        //请求地址
        String url = TOKEN_URL + "?corpid=" + CORPID + "&corpsecret=" + CORPSECRET;

        //发送请求
        String backResult = HttpUtil.get(url);

        //判断提取返回结果
        if (StringUtils.isNotBlank(backResult)) {
            JSONObject json = JSONObject.parseObject(backResult);
            if (json.getInteger("errcode") == 0) {
                token = json.getString("access_token");
                Integer expiresIn = json.getInteger("expires_in");
                long expiresTime = System.currentTimeMillis() + expiresIn * 1000;
                if (baseMapper.updateAccessToken("access_token", token, expiresTime) > 0) {

                } else {
                    throw new RuntimeException("更新token失败");

                }
            } else {
                throw new RuntimeException(json.get("errmsg").toString());
            }
        } else {
            throw new RuntimeException("请求失败");
        }

        return token;
    }

    @Override
//    @Scheduled(cron = "0 30 0 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public boolean syncDept() {
        Boolean result = false;

        Map<String, Object> accessTokenMap = baseMapper.getAccessToken("access_token");
        String value = accessTokenMap.get("value") != null ? (String) accessTokenMap.get("value") : "";
        Long time = accessTokenMap.get("time") != null ? (Long) accessTokenMap.get("time") : null;

        String token = "";
        if (Func.isNotEmpty(value) && time > System.currentTimeMillis()) {
            token = value;
        } else {
            token = getAccessToken();
        }

        //请求地址
        String url = DEPT_URL + "?access_token=" + token + "&id=" + DEPT_ID + "&no_fetch_child=0";

        //发送请求
        String backResult = HttpUtil.get(url);

        //缺失的主岗部门同步
        List<Long> missDeptIds = baseMapper.getMissDeptIds();
        JSONArray missDeptList = new JSONArray();
        if (Func.isNotEmpty(missDeptIds)) {
            for (Long s : missDeptIds) {
                url = DEPT_URL + "?access_token=" + token + "&id=" + s + "&no_fetch_child=1";
                String backResult1 = HttpUtil.get(url);
                if (StringUtils.isNotBlank(backResult1)) {
                    JSONObject json = JSONObject.parseObject(backResult1);
                    if (json.getInteger("errcode") == 0) {
                        JSONArray tempList = json.getJSONArray("department");
                        for (int i = 0; i < tempList.size(); i++) {
                            JSONObject o = tempList.getJSONObject(i);
                            if(o.getLong("id").equals(s)){
                                missDeptList.add(o);
                                continue;
                            }
                        }
                    }
                }
            }
        }

        //判断提取返回结果
        if (StringUtils.isNotBlank(backResult)) {
            JSONObject json = JSONObject.parseObject(backResult);
            if (json.getInteger("errcode") == 0) {
                JSONArray departmentList = json.getJSONArray("department");

                if (missDeptList != null) {
                    departmentList.addAll(missDeptList);
                }

                List<BhDeptSync> list = new ArrayList<>();
                for (int i = 0; i < departmentList.size(); i++) {
                    JSONObject o = departmentList.getJSONObject(i);

                    BhDeptSync bhDeptSync = new BhDeptSync();
                    bhDeptSync.setId(o.getLong("id"));
                    bhDeptSync.setName(o.getString("name"));
                    bhDeptSync.setType(o.getInteger("type"));
                    bhDeptSync.setSort(o.getLong("order"));
                    bhDeptSync.setParentId(o.getLong("parentid"));
                    bhDeptSync.setPartyUserLimit(o.getInteger("party_user_limit"));

                    JSONObject partyDetail = o.getJSONObject("party_detail");
                    if (partyDetail != null) {
                        bhDeptSync.setFullName(partyDetail.getString("full_name"));
                        bhDeptSync.setShortName(partyDetail.getString("short_name"));
                        bhDeptSync.setBriefIntroduction(partyDetail.getString("brief_introduction"));
                        bhDeptSync.setDomainName(partyDetail.getString("domain_name"));
                        bhDeptSync.setAddress(partyDetail.getString("address"));
                        bhDeptSync.setTelephone(partyDetail.getString("telephone"));
                        bhDeptSync.setOrganizationCode(partyDetail.getString("organization_code"));
                    }
                    bhDeptSync.setCreateUser(AuthUtil.getUserId());
                    bhDeptSync.setCreateTime(LocalDateTime.now());
                    list.add(bhDeptSync);
                }

                //去重
                list = list.stream().distinct().collect(Collectors.toList());
                //先删除已有的
                baseMapper.removeAllDeptSync();
                result = this.saveBatch(list);
                if (result) {
                    SyncRecord syncRecord = new SyncRecord();
                    syncRecord.setUpOrDown("上游同步");
                    syncRecord.setAppName("政务微信");
                    syncRecord.setDataType("部门信息");
                    syncRecord.setResult("同步部门至原始记录表");
                    syncRecord.setCause("无异常");
                    Long userId = AuthUtil.getUserId();
                    syncRecord.setIsAutomatic(Func.isNotEmpty(userId) ? 0 : 1);
                    syncRecord.setCreateTime(LocalDateTime.now());
                    syncRecord.setCreateUser(userId);
                    iSyncRecordService.save(syncRecord);
                }
            } else {
                SyncRecord syncRecord = new SyncRecord();
                syncRecord.setUpOrDown("上游同步");
                syncRecord.setAppName("政务微信");
                syncRecord.setDataType("部门信息");
                syncRecord.setResult("同步部门至原始记录表");
                syncRecord.setCause("异常");
                Long userId = AuthUtil.getUserId();
                syncRecord.setIsAutomatic(Func.isNotEmpty(userId) ? 0 : 1);
                syncRecord.setCreateTime(LocalDateTime.now());
                syncRecord.setCreateUser(userId);
                iSyncRecordService.save(syncRecord);
                throw new RuntimeException("同步部门至原始记录表失败: 信息" + json.get("errmsg").toString());
            }
        } else {
            SyncRecord syncRecord = new SyncRecord();
            syncRecord.setUpOrDown("上游同步");
            syncRecord.setAppName("政务微信");
            syncRecord.setDataType("部门信息");
            syncRecord.setResult("同步部门至原始记录表");
            syncRecord.setCause("异常");
            Long userId = AuthUtil.getUserId();
            syncRecord.setIsAutomatic(Func.isNotEmpty(userId) ? 0 : 1);
            syncRecord.setCreateTime(LocalDateTime.now());
            syncRecord.setCreateUser(userId);
            iSyncRecordService.save(syncRecord);
            throw new RuntimeException("请求失败");
        }

        return result;
    }


    @Override
    public List<SystemTreeNode> getBhDeptSynTree() {
        List<SystemTreeNode> result = new ArrayList<>();
        List<SystemTreeNode> tree = baseMapper.getBhDeptSynTree();
        result = ForestNodeMerger.merge(tree);
        return result;
    }
}
