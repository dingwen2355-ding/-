/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.jslc.modules.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.common.utils.HsmUtil;
import com.jslc.modules.system.entity.LcSysLogEntity;

import com.jslc.modules.system.mapper.LcSysLogMapper;
import com.jslc.modules.system.service.LcSysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LcSysLogServiceImpl extends ServiceImpl<LcSysLogMapper, LcSysLogEntity> implements LcSysLogService {
    @Autowired
    private HsmUtil hsmUtil;

    @Override
    public String encryptOper(LcSysLogEntity log) {
        String plaintext = log.getUsername() + log.getOperation() + log.getParams() + log.getIp();
        String digest = hsmUtil.encryptSM3(plaintext);
        return digest;
    }
}
