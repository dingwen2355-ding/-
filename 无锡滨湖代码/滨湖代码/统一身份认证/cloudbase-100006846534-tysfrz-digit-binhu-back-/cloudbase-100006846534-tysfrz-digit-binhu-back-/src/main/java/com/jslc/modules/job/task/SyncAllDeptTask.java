package com.jslc.modules.job.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jslc.modules.szbh.entity.SysCronConfig;
import com.jslc.modules.szbh.service.IBhDeptService;
import com.jslc.modules.szbh.service.IBhDeptSyncService;
import com.jslc.modules.szbh.service.ISysCronConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springblade.core.tool.utils.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhengyongbiao
 * @title: Tesk
 * @projectName digit-binhu-back
 * @description:
 * @date 2023/9/13 17:12
 */
@Slf4j
@Component("syncAllDeptTask")
public class SyncAllDeptTask extends AbstractTask {

    @Autowired
    private IBhDeptSyncService iBhDeptSyncService;


    @Override
    public void handler(String params) {
        log.info("-----------------------------------定时同步全部部门开始：" + LocalDateTime.now());

        iBhDeptSyncService.syncDept();

        log.info("-----------------------------------定时同步全部部门结束：" + LocalDateTime.now());
    }
}
