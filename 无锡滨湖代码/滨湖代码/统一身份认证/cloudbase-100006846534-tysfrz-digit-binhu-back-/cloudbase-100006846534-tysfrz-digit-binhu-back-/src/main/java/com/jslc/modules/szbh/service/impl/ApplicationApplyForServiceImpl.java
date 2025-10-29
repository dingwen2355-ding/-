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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.common.constant.SwapConstant;
import com.jslc.modules.szbh.entity.ApplicationApplyFor;
import com.jslc.modules.szbh.mapper.ApplicationApplyForMapper;
import com.jslc.modules.szbh.service.IApplicationApplyForService;
import com.jslc.modules.szbh.service.IBhApplicationManagementService;
import com.jslc.modules.szbh.vo.ApplicationApplyForRestVO;
import com.jslc.modules.szbh.vo.ApplicationApplyForVO;
import com.jslc.modules.szbh.vo.BhApplicationManagementVO;
import com.jslc.modules.szbh.vo.HeadPageDSQVO;
import org.springblade.core.secure.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 应用申请表 服务实现类
 *
 * @author BladeX
 * @since 2023-07-25
 */
@Service
public class ApplicationApplyForServiceImpl extends ServiceImpl<ApplicationApplyForMapper, ApplicationApplyFor> implements IApplicationApplyForService {
    @Autowired
    private IBhApplicationManagementService bhApplicationManagementService;


    @Override
    public ApplicationApplyForVO detail(Long id) {
        ApplicationApplyForVO detail = baseMapper.getDetail(id);

        return detail;
    }

    @Override
    public IPage<ApplicationApplyForVO> selectApplicationApplyForPage(IPage<ApplicationApplyForVO> page) {
        List<ApplicationApplyForVO> list = baseMapper.selectApplicationApplyForPage(page);
        if (CollectionUtils.isNotEmpty(list)) {
            for (ApplicationApplyForVO authorityApplyForVO : list) {
                authorityApplyForVO.setReviewStatusName(SwapConstant.ReviewStatus.getDescByValue(authorityApplyForVO.getReviewStatus()));
            }
        }
        return page.setRecords(list);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean submit(ApplicationApplyFor applicationApplyFor) {
        Boolean flag = false;
        if (applicationApplyFor.getId() != null) {//修改
            applicationApplyFor.setUpdateTime(LocalDateTime.now());
            applicationApplyFor.setUpdateUser(AuthUtil.getUserId());
            flag = true;
        } else {
            applicationApplyFor.setCreateTime(LocalDateTime.now());
            applicationApplyFor.setCreateUser(AuthUtil.getUserId());
//			applicationApplyFor.setApplicant();
        }
        boolean result = this.saveOrUpdate(applicationApplyFor);

        if (flag && applicationApplyFor.getReviewStatus() != null && applicationApplyFor.getReviewStatus() == 1) {
            ApplicationApplyFor one = getById(applicationApplyFor.getId());
            //审核通过 新增应用
            BhApplicationManagementVO bhApplicationManagement = new BhApplicationManagementVO();
            bhApplicationManagement.setName(one.getName());
            bhApplicationManagement.setCategoryId(one.getCategoryId());
//			bhApplicationManagement.setAppId(applicationApplyFor.get());
//			bhApplicationManagement.setAppSecret();
            bhApplicationManagement.setLogoId(one.getLogoId());
            bhApplicationManagement.setLogo(one.getLogo());
            bhApplicationManagement.setDescription(one.getDescription());
            bhApplicationManagement.setDeptId(one.getDeptId());
            bhApplicationManagement.setContacts(one.getContacts());
            bhApplicationManagement.setTelephone(one.getTelephone());
            bhApplicationManagement.setUrl(one.getUrl());
            bhApplicationManagement.setIsGround(one.getIsGround());
            bhApplicationManagement.setIsRecommend(one.getIsRecommend());
            bhApplicationManagement.setIsSendSms(one.getIsSendSms());
            bhApplicationManagement.setProtocol(one.getProtocol());
//			bhApplicationManagement.setUserColumn(applicationApplyFor.get());
//			bhApplicationManagement.setSort(applicationApplyFor.getS());
            bhApplicationManagement.setDataSource(one.getDataSource());
            bhApplicationManagement.setIntro(one.getIntro());
//			bhApplicationManagement.setEntId(applicationApplyFor.get());
            bhApplicationManagement.setIcon(one.getIcon());
            bhApplicationManagement.setIconId(one.getIconId());
            //默认已建应用
            bhApplicationManagement.setType(1);

            bhApplicationManagementService.submit(bhApplicationManagement);

        }
        return result;
    }


    @Override
    public IPage<ApplicationApplyForRestVO> applicationApplyForList(IPage<ApplicationApplyForRestVO> page, String applicant) {
        List<ApplicationApplyForRestVO> list = baseMapper.applicationApplyForList(page, applicant);
        return page.setRecords(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addAuthApp(ApplicationApplyFor applicationApplyFor) {
        Long resultId = null;

        if (applicationApplyFor.getReviewStatus() != null && applicationApplyFor.getReviewStatus() == 1) {
            ApplicationApplyFor one = getById(applicationApplyFor.getId());
            //审核通过 新增应用
            BhApplicationManagementVO bhApplicationManagement = new BhApplicationManagementVO();
            bhApplicationManagement.setName(one.getName());
            bhApplicationManagement.setCategoryId(one.getCategoryId());
            bhApplicationManagement.setLogoId(one.getLogoId());
            bhApplicationManagement.setLogo(one.getLogo());
            bhApplicationManagement.setDescription(one.getDescription());
            bhApplicationManagement.setDeptId(one.getDeptId());
            bhApplicationManagement.setContacts(one.getContacts());
            bhApplicationManagement.setTelephone(one.getTelephone());
            bhApplicationManagement.setUrl(one.getUrl());
            bhApplicationManagement.setIsGround(one.getIsGround());
            bhApplicationManagement.setIsRecommend(one.getIsRecommend());
            bhApplicationManagement.setIsSendSms(one.getIsSendSms());
            bhApplicationManagement.setProtocol(one.getProtocol());
            bhApplicationManagement.setDataSource(one.getDataSource());
            bhApplicationManagement.setIntro(one.getIntro());
            bhApplicationManagement.setIcon(one.getIcon());
            bhApplicationManagement.setIconId(one.getIconId());
            //默认已建应用
            bhApplicationManagement.setType(1);

            resultId = bhApplicationManagementService.submitV1(bhApplicationManagement);
        }
        return resultId;
    }


}
