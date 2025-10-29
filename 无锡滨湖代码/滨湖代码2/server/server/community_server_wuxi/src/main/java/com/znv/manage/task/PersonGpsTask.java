//package com.znv.manage.task;
//
//import com.znv.manage.service.impl.CommunicationServiceImpl;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//
//@Slf4j
//@Component
//public class PersonGpsTask {
//
//    @Resource
//    private CommunicationServiceImpl communicationService;
//
//    @Scheduled(initialDelay = 15*1000, fixedRate = 30 * 60 * 1000)
//    @Async
//    public void Run(){
//        log.info("start PersonGpsTask...");
//
//        communicationService.updatePeopleGps();
//
//        log.info("end PersonGpsTask...");
//
//    }
//}
