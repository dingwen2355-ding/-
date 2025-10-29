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
package com.jslc.modules.szbh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.szbh.dto.ApplicationActivationDTO;
import com.jslc.modules.szbh.entity.BhApplicationManagement;
import com.jslc.modules.szbh.entity.BhDept;
import com.jslc.modules.szbh.vo.BhApplicationManagementVO;
import com.jslc.modules.szbh.vo.CommonMapVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 应用管理 Mapper 接口
 *
 * @author BladeX
 * @since 2023-05-09
 */
public interface BhApplicationManagementMapper extends BaseMapper<BhApplicationManagement> {

    /**
     * 自定义分页
     *
     * @param page
     * @param bhApplicationManagement
     * @return
     */
    List<BhApplicationManagementVO> getPageList(@Param("page") IPage<BhApplicationManagementVO> page,
                                                @Param("name") String name,
                                                @Param("appCategoryIds") List<Long> appCategoryIds,
                                                @Param("isGround") Integer isGround,
                                                @Param("isRecommend") Integer isRecommend);

    BhApplicationManagementVO getDetail(@Param("id") Long id);

    /**
     * 根据用户账号获取应用信息
     */
    List<BhApplicationManagementVO> getUserApplicationManagementByAccount(String account, String keyword);

    /**
     * 根据用户获取appSecret
     */
    String getAppSecretByAppId(String appId);

    List<BhApplicationManagement> getList(@Param("key") String key);

    List<BhDept> applicationDept();

    List<BhApplicationManagementVO> getApplicationByClassificationAndDept(Long applicationClassificationId, Long deptId, String keywords, IPage page);

    List<CommonMapVO> getAppUserRecord(String account);

    List<ApplicationActivationDTO> getApplicationActivation(LocalDateTime startTime, LocalDateTime endTime);

    Long getApplicationActivationCount(LocalDateTime startTime, LocalDateTime endTime);

    List<CommonMapVO> getApplicationCategoryParent();

    Integer getApplicationManagementCount(String id);

    List<BhApplicationManagementVO> getUserAppById(@Param("userId") Long userId);


    List<BhApplicationManagementVO> getAppSumData(@Param("start") LocalDateTime start,
                                                  @Param("end") LocalDateTime end);

    /**
     * 根据用户账号获取应用信息
     */
    List<BhApplicationManagementVO> getUserAppByAccount(String account, String keyword);
}
