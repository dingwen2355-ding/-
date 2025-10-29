package com.znv.manage.task;

import com.znv.manage.service.UserDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 从统一认证平台获取用户存到我们表(2h1次)
 *
 * @author ：0049003788
 * @date ：2023/7/20 9:36
 */
@Component
@Slf4j
public class UserSyncTask {
    @Autowired
    UserDataService userDataService;

    @Scheduled(initialDelay = 1000, fixedRate = 1000 * 60 * 60 * 2)
    @Async
    public void userSync() {
        log.info("start sync user");
        userDataService.getUserList();
        log.info("end sync user");
    }
}
