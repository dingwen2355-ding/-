package com.znv.manage.task;

import com.znv.manage.common.utils.LoginErrorTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author znv
 * @ClassName:
 * @Description: 设备监控任务
 * @date 2018/5/30 16:29
 */
@Component
public class MonitTask {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(MonitTask.class);

    /**
     * 安全策略：登录接口错误次数
     */
//    @Scheduled(initialDelay = 1000,fixedRate = 1000 * 60 * 5)
//    @Async
    public void clear(){
        logger.info("定时任务，判断是否需要清理登录错误次数....");
        LoginErrorTimes.clearTimesByTime(10);
    }


}
