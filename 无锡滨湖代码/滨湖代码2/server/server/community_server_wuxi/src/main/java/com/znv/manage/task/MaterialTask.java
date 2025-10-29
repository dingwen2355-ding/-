//package com.znv.manage.task;
//
//
//import com.znv.manage.service.impl.MaterialServiceImpl;
//import com.znv.manage.service.impl.UserGpsServiceImpl;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MaterialTask {
//
//    /**
//     * 日志
//     */
//    private static Logger logger = LoggerFactory.getLogger(MaterialTask.class);
//
//    @Autowired
//    private MaterialServiceImpl materialService;
//
//
//    /**
//     * 医疗物资数据同步
//     */
//    @Scheduled(initialDelay = 10000, fixedRate = 15 * 60 * 1000)
//    @Async
//    public void syncMaterial() {
//        logger.info("start syncMaterial..Task...");
//        try {
//            materialService.syncMaterial();
//        }catch (Exception e){
//            logger.error(e.getMessage());
//        }
//        logger.info("end syncUserGps..Task...");
//    }
//}
