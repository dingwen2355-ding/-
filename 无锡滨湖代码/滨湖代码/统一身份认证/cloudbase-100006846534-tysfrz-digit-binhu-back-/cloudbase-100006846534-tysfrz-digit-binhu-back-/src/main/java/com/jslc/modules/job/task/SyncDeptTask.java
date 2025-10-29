package com.jslc.modules.job.task;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jslc.modules.szbh.entity.BhDeptSync;
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
@Component("syncDeptTask")
public class SyncDeptTask extends AbstractTask {

    @Autowired
    private ISysCronConfigService iSysCronConfigService;
    @Autowired
    private IBhDeptService bhDeptService;
    @Autowired
    private IBhDeptSyncService iBhDeptSyncService;


    @Override
    public void handler(String params) {
        log.info("-----------------------------------定时同步部门开始：" + LocalDateTime.now());


        if(Func.isNotEmpty(params)) {
            JSONObject jsonObject = JSONObject.parseObject(params);
            String bizId = jsonObject.getString("bizId");
            SysCronConfig one = iSysCronConfigService.getById(bizId);

            //获取同步表中的数据
            List<String> synDeptIds = Func.toStrList(one.getSynIds());
//            LambdaQueryWrapper<BhDeptSync> queryDept = Wrappers.lambdaQuery(BhDeptSync.class).in(BhDeptSync::getId, synDeptIds);
//            List<BhDeptSync> list = iBhDeptSyncService.list(queryDept);

            if (CollectionUtils.isNotEmpty(synDeptIds)) {
                String ids = synDeptIds.stream().distinct().collect(Collectors.joining(","));
//                bhDeptService.syn(ids);

                //同步全部部门
                bhDeptService.syn();

            }

        }else{
            log.info("-----------------------------------定时同步部门失败："+ LocalDateTime.now() + "，params:" + params);
        }

        log.info("-----------------------------------定时同步部门结束：" + LocalDateTime.now());
    }
}
