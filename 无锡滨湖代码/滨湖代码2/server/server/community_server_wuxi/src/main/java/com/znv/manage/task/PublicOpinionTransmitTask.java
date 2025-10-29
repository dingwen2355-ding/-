package com.znv.manage.task;

import com.znv.manage.service.impl.PublicOpinionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PublicOpinionTransmitTask {

    @Autowired
    PublicOpinionServiceImpl publicOpinionService;

//    @Scheduled(initialDelay = 10000,fixedRate = 8*60*1000)
//    @Async
//    public void monitDevice(){
//        log.info("定时任务-同步舆情");
//        publicOpinionService.saveOpinion();
//    }


//    @Scheduled(initialDelay = 10000,fixedRate = 60*1000)
//    @Async
//    public void updateEventOpinionLevel(){
//        publicOpinionService.updateEventOpinionLevel();
//    }


//    @Scheduled(initialDelay = 5000,fixedRate = 8*60*1000)
//    @Async
//    public void updateOpinionReportName(){
//        log.info("定时任务-更新舆情报道名字");
//        publicOpinionService.updateOpinionReportName();
//    }
//
//    @Scheduled(initialDelay = 5, fixedRate = 30 * 1000)
//    @Async
//    public void UpdatePublicOpinionTask(){
//
//        try {
//            /**
//             * 舆情关联事件
//             */
//            publicOpinionService.savePublicOpinion();
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//    }

}
