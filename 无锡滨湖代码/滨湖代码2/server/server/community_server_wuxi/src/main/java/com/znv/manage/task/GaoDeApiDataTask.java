package com.znv.manage.task;

import com.znv.manage.service.GaoDeApiDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 调用高德Api的定时任务
 */
@Slf4j
@Component
public class GaoDeApiDataTask {

    @Resource
    private GaoDeApiDataService gaoDeApiDataService;

    /**
     * 天气数据任务
     */
    @Async
    @Scheduled(initialDelay = 1000, fixedRate = 30 * 60 * 1000)
    public void syncWeather() {
        try {
            gaoDeApiDataService.syncWeatherInfo();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 道路数据任务
     */
    @Async
    @Scheduled(cron = "0 0 8,14 * * ?")
    public void syncRoadInfo() {
        try {
            gaoDeApiDataService.syncRoadInfo();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
