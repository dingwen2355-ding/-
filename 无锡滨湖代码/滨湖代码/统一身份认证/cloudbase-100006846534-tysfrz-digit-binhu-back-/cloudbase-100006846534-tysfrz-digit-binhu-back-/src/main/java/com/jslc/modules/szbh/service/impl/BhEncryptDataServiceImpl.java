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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.common.constant.CommonConstant;
import com.jslc.common.utils.HsmUtil;
import com.jslc.modules.szbh.entity.BhEncryptData;
import com.jslc.modules.szbh.mapper.BhEncryptDataMapper;
import com.jslc.modules.szbh.service.IBhEncryptDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据加密
 *
 * @author BladeX
 * @since 2023-05-09
 */
@Service
@Slf4j
public class BhEncryptDataServiceImpl extends ServiceImpl<BhEncryptDataMapper, BhEncryptData> implements IBhEncryptDataService {
    @Autowired
    private HsmUtil hsmUtil;

    @Override
    public void encryptData(Long bizId, String sourceData, CommonConstant.EncryptDataType type) {
        if (StringUtils.isBlank(sourceData)) {
            return;
        }

        String enText = hsmUtil.encryptSM3(bizId + sourceData);
        if (StringUtils.isBlank(enText)) {
            log.error("角色权限加密失败,plattext{}", bizId + sourceData);
            //throw new RuntimeException("角色权限加密失败");
        } else {
            BhEncryptData bhEncryptData = getOne(Wrappers.lambdaQuery(BhEncryptData.class)
                    .eq(BhEncryptData::getBizId, bizId)
                    .eq(BhEncryptData::getType, type.name()));
            if (bhEncryptData == null) {
                bhEncryptData = new BhEncryptData();
                bhEncryptData.setBizId(bizId);
                bhEncryptData.setType(type.name());
            }
            bhEncryptData.setEnData(enText);
            this.saveOrUpdate(bhEncryptData);
        }
    }

    @Override
    public Map<Long, String> getEncryptData(List<Long> bizIds, CommonConstant.EncryptDataType type) {
        List<BhEncryptData> list = this.list(Wrappers.lambdaQuery(BhEncryptData.class).in(BhEncryptData::getBizId, bizIds).eq(BhEncryptData::getType, type.name()));
        Map<Long,String> map = new HashMap<>();
        if(CollectionUtils.isNotEmpty(list)){
            map = list.stream().collect(Collectors.toMap(BhEncryptData::getBizId, BhEncryptData::getEnData));
        }
        return map;
    }
}
