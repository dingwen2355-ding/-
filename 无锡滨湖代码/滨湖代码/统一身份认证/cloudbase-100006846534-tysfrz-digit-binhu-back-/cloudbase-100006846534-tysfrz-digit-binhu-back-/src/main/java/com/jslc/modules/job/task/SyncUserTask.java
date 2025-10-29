package com.jslc.modules.job.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jslc.modules.szbh.entity.BhUserSync;
import com.jslc.modules.szbh.entity.SysCronConfig;
import com.jslc.modules.szbh.service.IBhUserService;
import com.jslc.modules.szbh.service.IBhUserSyncService;
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
@Component("syncUserTask")
public class SyncUserTask extends AbstractTask {

    @Autowired
    private ISysCronConfigService iSysCronConfigService;
    @Autowired
    private IBhUserService iBhUserService;
//    @Autowired
//    private IBhUserSyncService iBhUserSyncService;


    @Override
    public void handler(String params){
        log.info("-----------------------------------定时同步用户开始：" + LocalDateTime.now());

        if(Func.isNotEmpty(params)) {

                //同步全部人员
            try {
                iBhUserService.syn();
            }catch (Exception e) {
                log.info("-----------------------------------执行定时同步用户失败："+ LocalDateTime.now() + "，e:" + e);
            }
        }else{
            log.info("-----------------------------------执行定时同步用户失败："+ LocalDateTime.now() + "，params:" + params);
        }
        log.info("-----------------------------------定时同步用户结束：" + LocalDateTime.now());
    }
}
