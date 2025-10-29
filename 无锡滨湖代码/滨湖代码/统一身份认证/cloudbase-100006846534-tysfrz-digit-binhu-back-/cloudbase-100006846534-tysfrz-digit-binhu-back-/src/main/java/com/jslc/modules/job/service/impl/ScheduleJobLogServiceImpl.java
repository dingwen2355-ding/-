/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.jslc.modules.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jslc.modules.job.entiry.ScheduleJobEntity;
import com.jslc.modules.job.entiry.ScheduleJobLogEntity;
import com.jslc.modules.job.mapper.ScheduleJobLogMapper;
import com.jslc.modules.job.service.ScheduleJobLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLogEntity> implements ScheduleJobLogService {

    @Override
    public IPage queryPage(Map<String, Object> params, IPage page) {
        String jobId = (String) params.get("jobId");
        IPage<ScheduleJobLogEntity> resultPage = this.page(page, Wrappers.lambdaQuery(ScheduleJobLogEntity.class)
                .like(StringUtils.isNotBlank(jobId), ScheduleJobLogEntity::getJobId, jobId)
                .orderByDesc(ScheduleJobLogEntity::getCreateTime)
        );
        return resultPage;
    }

}
