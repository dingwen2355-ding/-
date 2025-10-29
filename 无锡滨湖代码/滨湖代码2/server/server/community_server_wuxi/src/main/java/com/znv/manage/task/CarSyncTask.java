package com.znv.manage.task;

import com.znv.manage.service.SyncCarService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 车辆信息同步定时任务
 *
 * @author ：0049003788
 * @date ：2023/1/10 16:00
 */
@Component
public class CarSyncTask {
    @Resource
    SyncCarService syncCarService;

    /**
     * 普通车同步
     *
     * @return void
     */
    @Scheduled(initialDelay = 5000, fixedRate = 60 * 1000)
    @Async
    public void sync() {
        //syncCarService.sync();
    }

    /**
     * 消防车同步
     *
     * @return void
     */
    @Scheduled(initialDelay = 5000, fixedRate = 2 * 60 * 1000)
    @Async
    public void syncFireEngine() {
        //syncCarService.syncFireEngine();
    }
}
