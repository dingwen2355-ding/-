package com.znv.manage.task;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.service.impl.SyncEventServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 对接事件
 */
@Slf4j
@Component
public class EventTask {

    @Autowired
    SyncEventServiceImpl syncEventService;

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(EventTask.class);

    /**
     * 获取事件定时任务
     */
    @Scheduled(initialDelay = 5000, fixedRateString = "${sync.rate:30000}")
    @Async
    public void syncEvent() {
        logger.info("start event..Task...");

        JSONObject dateRange = new JSONObject();
        dateRange.put("creatDate", DateUtil.dateToStrLong(new Date(DateUtil.getPreTime2(-10))) + "," + DateUtil.getStringDate());

        JSONObject dataFilter = new JSONObject();
        int saveCount = 0;

        //1.12345
        dataFilter.put("flowSource", "");
        saveCount += syncEventService.syncEvent(dataFilter, dateRange,"MAIN_12345");

        //2.其他事件
        dataFilter.put("flowSource", "");
        saveCount += syncEventService.syncEvent(dataFilter, dateRange,"NORMAL_MAIN");

        logger.info("事件数据同步：{} 条数据,end event..Task...", saveCount);
    }
}
