package com.jslc.modules.job.task;

import com.jslc.modules.szbh.service.IBhDeptSyncService;
import com.jslc.modules.szbh.service.IBhUserSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author zhengyongbiao
 * @title: Tesk
 * @projectName digit-binhu-back
 * @description:
 * @date 2023/9/13 17:12
 */
@Slf4j
@Component("syncAllUserTask")
public class SyncAllUserTask extends AbstractTask {

    @Autowired
    private IBhUserSyncService iBhUserSyncService;


    @Override
    public void handler(String params) {
        log.info("-----------------------------------定时同步全部用户开始：" + LocalDateTime.now());

        iBhUserSyncService.syncUser();

        log.info("-----------------------------------定时同步全部用户结束：" + LocalDateTime.now());
    }
}
