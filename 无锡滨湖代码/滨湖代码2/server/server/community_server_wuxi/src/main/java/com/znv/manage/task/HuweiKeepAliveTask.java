// package com.znv.manage.task;

// import com.znv.manage.controller.HuaweiController;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;


// @Component
// public class HuweiKeepAliveTask {
    // /**
     // * 日志
     // */
    // private static Logger logger = LoggerFactory.getLogger(HuweiKeepAliveTask.class);

    // @Autowired
    // private HuaweiController huaweiController;

    // /**
     // * 设备服务器监控
     // */
    // @Scheduled(initialDelay = 30 * 1000,fixedRate = 30*1000)
    // @Async
    // public void huaweiKeepAlive(){
        // huaweiController.keepAlive();
    // }


// }