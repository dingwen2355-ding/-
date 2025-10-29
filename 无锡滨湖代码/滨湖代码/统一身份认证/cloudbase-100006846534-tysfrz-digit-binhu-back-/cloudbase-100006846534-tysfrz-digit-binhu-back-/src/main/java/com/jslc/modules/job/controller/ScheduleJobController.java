/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.jslc.modules.job.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jslc.modules.job.entiry.ScheduleJobEntity;
import com.jslc.modules.job.service.ScheduleJobService;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.api.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/blade-system/job/schedule")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 定时任务列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, Query query) {
        IPage page = scheduleJobService.queryPage(params, Condition.getPage(query));
        return R.data(page);
    }

    /**
     * 定时任务信息
     */
    @RequestMapping("/info/{jobId}")
    public R info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity schedule = scheduleJobService.getById(jobId);
        return R.data(schedule);
    }

    /**
     * 新增保存定时任务
     */
    @RequestMapping("/save")
    public R save(@RequestBody ScheduleJobEntity scheduleJob) {
        if (scheduleJob.getJobId() == null) {
            scheduleJobService.saveJob(scheduleJob);
        } else {
            scheduleJobService.update(scheduleJob);
        }
        return R.success(ResultCode.SUCCESS);
    }

    /**
     * 修改定时任务
     */
    @RequestMapping("/update")
    public R update(@RequestBody ScheduleJobEntity scheduleJob) {
        scheduleJobService.update(scheduleJob);
        return R.success(ResultCode.SUCCESS);
    }

    /**
     * 删除定时任务
     */
    @RequestMapping("/delete")
    public R delete(Long ids) {
        scheduleJobService.deleteBatch(Arrays.asList(ids).stream().mapToLong(Long::longValue).toArray());
        return R.success(ResultCode.SUCCESS);
    }

    /**
     * 立即执行任务
     */
    @RequestMapping("/run")
    public R run(@RequestBody Long[] jobIds) {
        scheduleJobService.run(jobIds);
        return R.success(ResultCode.SUCCESS);
    }

    /**
     * 暂停定时任务
     */
    @RequestMapping("/pause")
    public R pause(@RequestBody Long[] jobIds) {
        scheduleJobService.pause(jobIds);
        return R.success(ResultCode.SUCCESS);
    }

    /**
     * 恢复定时任务
     */
    @RequestMapping("/resume")
    public R resume(@RequestBody Long[] jobIds) {
        scheduleJobService.resume(jobIds);
        return R.success(ResultCode.SUCCESS);
    }

}
