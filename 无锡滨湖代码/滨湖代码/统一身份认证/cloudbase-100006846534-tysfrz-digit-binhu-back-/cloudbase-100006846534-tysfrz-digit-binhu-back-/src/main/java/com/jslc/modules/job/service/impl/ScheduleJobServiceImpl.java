/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.jslc.modules.job.service.impl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jslc.common.constant.CommonConstant;
import com.jslc.modules.job.constant.Constant;
import com.jslc.modules.job.entiry.ScheduleJobEntity;
import com.jslc.modules.job.mapper.ScheduleJobMapper;
import com.jslc.modules.job.service.ScheduleJobService;
import com.jslc.modules.job.utils.ScheduleUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJobEntity> implements ScheduleJobService {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<ScheduleJobEntity> scheduleJobList = this.list();
        for (ScheduleJobEntity scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public void deleteJob(String message) {
        Long id = Long.parseLong(message.substring(3, 22));
        ScheduleUtils.deleteScheduleJob(scheduler, id);
    }

    @Override
    public void updateJob(String message) {
        this.init();
    }

    @Override
    public IPage queryPage(Map<String, Object> params, IPage page) {
        String beanName = (String) params.get("beanName");
        IPage<ScheduleJobEntity> resultPage = this.page(page, new QueryWrapper<ScheduleJobEntity>()
                .like(StringUtils.isNotBlank(beanName), "bean_name", beanName));
        return resultPage;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJob(ScheduleJobEntity scheduleJob) {
        scheduleJob.setCreateTime(new Date());
        scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        this.save(scheduleJob);
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        redisTemplate.convertAndSend("updateJob", "1");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScheduleJobEntity scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        this.updateById(scheduleJob);
        redisTemplate.convertAndSend("updateJob", "1");

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
            this.removeById(jobId);
        }
        redisTemplate.convertAndSend("deleteJob", jobIds + "");
    }

    @Override
    public int updateBatch(Long[] jobIds, int status) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("list", Arrays.asList(jobIds));
        map.put("status", status);
        return baseMapper.updateBatch(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.run(scheduler, this.getById(jobId));
        }
        redisTemplate.convertAndSend("updateJob", "1");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }
        updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }
        updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
        redisTemplate.convertAndSend("updateJob", "1");
    }

    @Override
    public ScheduleJobEntity getJobByBizId(Long bizId) {
        ScheduleJobEntity job = this.getOne(Wrappers.lambdaQuery(ScheduleJobEntity.class).eq(ScheduleJobEntity::getBizId,bizId));
        return job;
    }
}
