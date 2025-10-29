//package com.jslc.modules.szbh.Task;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.jslc.modules.szbh.entity.BhDeptSync;
//import com.jslc.modules.szbh.entity.BhUserSync;
//import com.jslc.modules.szbh.entity.SysCronConfig;
//import com.jslc.modules.szbh.service.*;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springblade.core.tool.utils.Func;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author BladeX
// * @since
// */
//@Lazy(false)
//@Component
//@EnableScheduling
//@Slf4j
//public class SynDeptTask implements SchedulingConfigurer {
//
////    private String cron = "0 55 23 ? * *";
//    @Autowired
//    private ISysCronConfigService iSysCronConfigService;
//    @Autowired
//    private IBhDeptService bhDeptService;
//    @Autowired
//    private IBhDeptSyncService iBhDeptSyncService;
//
//
//    private static Logger logger = LoggerFactory.getLogger(SynDeptTask.class);
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        LambdaQueryWrapper<SysCronConfig> query = Wrappers.lambdaQuery(SysCronConfig.class).eq(SysCronConfig::getType, 2).eq(SysCronConfig::getIsEnable, 1);
//        SysCronConfig sysCronConfig = iSysCronConfigService.getOne(query);
//        if (Func.isNotEmpty(sysCronConfig) && sysCronConfig.getIsEnable() == 1 && Func.isNotEmpty(sysCronConfig.getSynIds())){
//            taskRegistrar.addTriggerTask(() -> {
//                logger.info("开始触发："+ sysCronConfig.getCron());
//
//                //获取同步表中的数据
//                List<Long> synDeptIds = Func.toLongList(sysCronConfig.getSynIds());
//                LambdaQueryWrapper<BhDeptSync> queryDept = Wrappers.lambdaQuery(BhDeptSync.class).in(BhDeptSync::getId, synDeptIds);
//                List<BhDeptSync> list = iBhDeptSyncService.list(queryDept);
//
//                if(CollectionUtils.isNotEmpty(list)){
//                    String ids = list.stream().map(BhDeptSync::getId).map(Object::toString).collect(Collectors.joining(","));
//                    bhDeptService.syn(ids);
//                }
//
//            }, triggerContext -> {
//                // 此代码块用于动态拿到cron表达式并设置定时任务，当定时任务时间到了，就会重新获取cron表达式，重新设置定时任务
//                String cron = sysCronConfig.getCron();
//                logger.info("定时时间："+ cron);
//                CronTrigger trigger;
//                try {
//                    // 一旦设置，立即生效
//                    trigger = new CronTrigger(cron);
//                    return trigger.nextExecutionTime(triggerContext);
//                } catch (Exception e) {
//                    // 如果格式有问题就按默认时间（时间为每天6点整）
//                    trigger = new CronTrigger("0 0 6 ? * *");
//                    return trigger.nextExecutionTime(triggerContext);
//                }
//            });
//        }
//    }
//}
