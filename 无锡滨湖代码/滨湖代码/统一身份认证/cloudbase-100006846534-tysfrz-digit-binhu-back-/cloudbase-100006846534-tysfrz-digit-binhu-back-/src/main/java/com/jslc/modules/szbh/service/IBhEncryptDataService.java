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
package com.jslc.modules.szbh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jslc.common.constant.CommonConstant;
import com.jslc.modules.szbh.entity.BhEncryptData;
import com.jslc.modules.szbh.entity.BhEnt;
import com.jslc.modules.szbh.vo.BhEntVO;

import java.util.List;
import java.util.Map;

/**
 * 企业表 服务类
 *
 * @author BladeX
 * @since 2023-06-06
 */
public interface IBhEncryptDataService extends IService<BhEncryptData> {
    void encryptData(Long bizId, String sourceData, CommonConstant.EncryptDataType type);

    /**
     *@创建人 zhengyongbiao
     *@描述 获取加密信息
     *@创建时间 2024/5/29 14:46
     */
    Map<Long, String> getEncryptData(List<Long> bizIds, CommonConstant.EncryptDataType type);
}
