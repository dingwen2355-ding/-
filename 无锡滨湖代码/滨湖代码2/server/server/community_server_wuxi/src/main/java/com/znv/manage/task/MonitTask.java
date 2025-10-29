package com.znv.manage.task;

import com.znv.manage.service.impl.DahuaServiceImpl;
import com.znv.manage.service.impl.IndividualServiceImpl;
import com.znv.manage.service.impl.PhoneServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IndividualServiceImpl individualService;

    @Autowired
    private DahuaServiceImpl dahuaService;

    @Autowired
    private PhoneServiceImpl phoneService;


////    @Scheduled(cron = "0 * * * * ?")
//@Scheduled(initialDelay = 5000, fixedRate = 30 * 60 * 1000)
//    @Async
//    public void individualGps(){
//        logger.info("定时任务,对接单兵gps信息");
////        individualService.gps();
////        individualService.gpsZL();
//        individualService.updateGps();
//    }

    /**
     * 大华token
     */
    // @Scheduled(initialDelay = 5000, fixedRate = 10 * 1000)
    // @Async
    // public void keepAlive(){
        // logger.info("dahua keepAlive...");
        // dahuaService.keepAlive();
    // }


   /**
    * 电话保活
    */
   @Scheduled(initialDelay = 5000, fixedRate = 10 * 1000)
   @Async
   public void PhoneKeepAlive(){
       logger.info("phone keepAlive...");
       phoneService.keepAlive();
   }


}
