//package com.jslc.modules.szbh.Task;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.jslc.common.utils.DateCommonUtil;
//import com.jslc.modules.szbh.entity.BhUserSync;
//import com.jslc.modules.szbh.entity.SmsSendSetting;
//import com.jslc.modules.szbh.entity.SysCronConfig;
//import com.jslc.modules.szbh.service.IBhUserService;
//import com.jslc.modules.szbh.service.IBhUserSyncService;
//import com.jslc.modules.szbh.service.ISmsSendSettingService;
//import com.jslc.modules.szbh.service.ISysCronConfigService;
//import lombok.extern.slf4j.Slf4j;
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
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
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
//public class SmsSendTask implements SchedulingConfigurer {
//
//    private static Logger logger = LoggerFactory.getLogger(SmsSendTask.class);
//
//    // 默认的cron表达式
//    private String cron = "0 55 23 ? * *";
//    @Autowired
//    private ISmsSendSettingService smsSendSettingService;
////    @Autowired
////    private IBhUserService iBhUserService;
////    @Autowired
////    private IBhUserSyncService iBhUserSyncService;
////    @Autowired
////    private SquirrelRedis squirrelRedis;
//
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//
//        //lockReportTaskKey 任务锁的key，repostTaskStr 缓存中的任务内容 ，lockVal任务锁的值用于标识由谁加锁，解锁也只能由加锁的线程解锁
//        String lockVal = UUID.randomUUID().toString();
//
////
//        LambdaQueryWrapper<SmsSendSetting> query = Wrappers.lambdaQuery(SmsSendSetting.class).eq(SmsSendSetting::getIsRegularTime, 1).eq(SmsSendSetting::getSendStatus, 0).eq(SmsSendSetting::getIsDeleted, 0);
//        List<SmsSendSetting> list = smsSendSettingService.list(query);
//        logger.info("定时短信的数据：" + list);
//
//        if (CollectionUtils.isNotEmpty(list)) {
//
//            list = list.stream().filter(f->f.getRegularTime() != null && f.getRegularTime().length() > 0).collect(Collectors.toList());
//            for (SmsSendSetting f : list) {
//                taskRegistrar.addTriggerTask(() -> {
//                    logger.info("开始触发：" + f);
//
//                    if (f.getMobile() != null) {
//                        smsSendSettingService.sendMsg(null, f.getMobile(), f.getContent(), f.getId());
//                    }
//                }, triggerContext -> {
//                    // 此代码块用于动态拿到cron表达式并设置定时任务，当定时任务时间到了，就会重新获取cron表达式，重新设置定时任务
//                    cron = this.getCronString(f);
//                    logger.info("定时时间：" + cron);
//                    CronTrigger trigger;
//                    try {
//                        // 一旦设置，立即生效
//                        trigger = new CronTrigger(cron);
//                        return trigger.nextExecutionTime(triggerContext);
//                    } catch (Exception e) {
//                        // 如果格式有问题就按默认时间（时间为每天23点55分）
//                        trigger = new CronTrigger("0 55 23 ? * *");
//                        return trigger.nextExecutionTime(triggerContext);
//                    }
//                });
//            }
//        }
////
//    }
////
//    /**
//     * 拿到cron表达式
//     */
//    private String getCronString(SmsSendSetting smsSendSetting) {
//
//        // 截取分钟和小时
//        String hour = smsSendSetting.getRegularTime().substring(0, 2);
//        String min = smsSendSetting.getRegularTime().substring(3, 5);
////        // 替换min,hour
////        String cron = "0 0/min * * * ?";
////        return cron.replaceAll("min", min).replaceAll("hour", hour);
//        // 替换min,hour
//        String cron = "0 min hour ? * *";
//        return cron.replaceAll("min", min).replaceAll("hour", hour);
//    }
//}
