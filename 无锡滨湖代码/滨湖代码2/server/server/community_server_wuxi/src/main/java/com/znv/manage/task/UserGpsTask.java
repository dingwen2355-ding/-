// package com.znv.manage.task;


// import com.znv.manage.service.impl.UserGpsServiceImpl;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;
// import org.springframework.web.client.RestTemplate;

//@Component
// public class UserGpsTask {

    // /**
     // * 日志
     // */
    // private static Logger logger = LoggerFactory.getLogger(UserGpsTask.class);

    // @Autowired
    // private UserGpsServiceImpl userGpsService;


    // /**
    /* 获取勤务人员坐标
    //*/
    // @Scheduled(initialDelay = 10000, fixedRate = 1 * 60 * 1000)
    // @Async
    // public void syncUserGps() {
        // logger.info("start syncUserGps..Task...");
        // try {
            // userGpsService.syncUserGps();
        // }catch (Exception e){
            // logger.error(e.getMessage());
        // }
        // logger.info("end syncUserGps..Task...");
    // }
// }
