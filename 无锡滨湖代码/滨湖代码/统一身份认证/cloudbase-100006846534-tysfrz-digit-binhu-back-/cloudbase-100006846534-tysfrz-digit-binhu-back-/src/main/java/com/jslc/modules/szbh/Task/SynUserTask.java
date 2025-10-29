//package com.jslc.modules.szbh.Task;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.jslc.modules.szbh.entity.BhDeptSync;
//import com.jslc.modules.szbh.entity.BhUserSync;
//import com.jslc.modules.szbh.entity.SysCronConfig;
//import com.jslc.modules.szbh.service.IBhUserService;
//import com.jslc.modules.szbh.service.IBhUserSyncService;
//import com.jslc.modules.szbh.service.ISysCronConfigService;
//import com.jslc.modules.szbh.vo.SysCronConfigVO;
//import lombok.extern.slf4j.Slf4j;
//import org.checkerframework.checker.units.qual.A;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springblade.core.tool.utils.DateTimeUtil;
//import org.springblade.core.tool.utils.Func;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
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
//public class SynUserTask implements SchedulingConfigurer {
//
////    private String cron = "0 55 23 ? * *";
//    @Autowired
//    private ISysCronConfigService iSysCronConfigService;
//    @Autowired
//    private IBhUserService iBhUserService;
//    @Autowired
//    private IBhUserSyncService iBhUserSyncService;
//
//
//    private static Logger logger = LoggerFactory.getLogger(SynUserTask.class);
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        LambdaQueryWrapper<SysCronConfig> query = Wrappers.lambdaQuery(SysCronConfig.class).eq(SysCronConfig::getType, 1).eq(SysCronConfig::getIsEnable, 1);
//        SysCronConfig sysCronConfig = iSysCronConfigService.getOne(query);
//        if (Func.isNotEmpty(sysCronConfig) && sysCronConfig.getIsEnable() == 1 && Func.isNotEmpty(sysCronConfig.getSynIds())){
//            taskRegistrar.addTriggerTask(() -> {
//                logger.info("开始触发："+ sysCronConfig.getCron());
//
//                //获取同步表中的数据
//
//                List<String> synUserIds = Func.toStrList(sysCronConfig.getSynIds());
//                LambdaQueryWrapper<BhUserSync> queryUser = Wrappers.lambdaQuery(BhUserSync.class).in(BhUserSync::getUserid, synUserIds);
//
//
//                List<BhUserSync> list = iBhUserSyncService.list(queryUser);
//                if(CollectionUtils.isNotEmpty(list)){
//                    String collect = list.stream().map(BhUserSync::getUserid).collect(Collectors.joining(","));
//                    iBhUserService.syn(collect);
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
