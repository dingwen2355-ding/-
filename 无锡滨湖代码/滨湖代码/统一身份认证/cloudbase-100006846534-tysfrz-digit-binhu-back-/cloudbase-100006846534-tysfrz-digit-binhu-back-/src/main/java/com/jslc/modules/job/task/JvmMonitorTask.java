package com.jslc.modules.job.task;

import com.jslc.modules.monitor.service.IMonitorJvmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lc
 * @title: JvmMonitorTask
 * @projectName digit-binhu-back
 * @description: TODO
 * @date 2023/10/8 13:54
 */
@Slf4j
@Component("jvmMonitorTask")
public class JvmMonitorTask implements ITask{
    @Autowired
    IMonitorJvmService monitorJvmService;

    @Override
    public void run(String params, Long jobId) {
        this.handler(params);
    }

    @Override
    public void handler(String params) {
        monitorJvmService.collectJvmInfo();
    }
}
