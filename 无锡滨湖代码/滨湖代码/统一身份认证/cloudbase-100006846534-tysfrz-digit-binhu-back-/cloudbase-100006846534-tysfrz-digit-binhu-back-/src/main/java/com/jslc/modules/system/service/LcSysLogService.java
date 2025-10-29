/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.jslc.modules.system.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jslc.modules.system.entity.LcSysLogEntity;


/**
 *@描述 用户操作日志
 *@创建人 zhengyongbiao
 *@创建时间 2023/2/14 9:10
 */
public interface LcSysLogService extends IService<LcSysLogEntity> {

    String  encryptOper(LcSysLogEntity log);
}
