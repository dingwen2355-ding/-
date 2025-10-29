package com.znv.manage.task;

import com.znv.manage.dao.IAlarmInstallMapper;
import com.znv.manage.service.IAlarmInformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author yangbo
 * @email
 * @date 2022/8/12
 */
@Slf4j
@Component
public class AlarmTask {

    @Autowired
    private IAlarmInstallMapper alarmInstallMapper;
    @Autowired
    private IAlarmInformService alarmInformService;

    /**
     * 定时任务检测生成”即将超期“告警
     */
    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void generateAlarmData() {
        generateAlarm(false);
    }

    /**
     * 定时任务检测生成”超期告警“告警
     */
    @Async
    @Scheduled(cron = "0 0 3 * * ?")
    public void generateOverTimeAlarmData() {
        generateAlarm(true);
    }


    private void generateAlarm(Boolean isOverTimeAlarmProcess) {
        /* 1. 查询所有预警规则 */
        List<Map<String, Object>> alarmRuleList = alarmInstallMapper.selAlarmInstall(null, null);
        if (CollectionUtils.isEmpty(alarmRuleList)) {
            log.warn("Do not found any alarm rules!");
            return;
        }

        /* 2. 获取所有区域信息（用于生成告警时的区域名称补齐） */
        List<Map<String, Object>> precinctList = alarmInstallMapper.queryPrecinctList(Collections.emptyMap());
        Map<String, String> precinctMap = precinctList.stream().filter(o -> !ObjectUtils.isEmpty(o.get("precinctId")))
                .collect(Collectors.toMap(k -> String.valueOf(k.get("precinctId")), v -> String.valueOf(v.get("precinctName"))));

        /* 3. 遍历预警规则, 生成每种规则对应的告警数据 */
        for (Map<String, Object> alarmRule : alarmRuleList) {
            // 预警名称
            String name = String.valueOf(alarmRule.get("name"));
            // 预警规则状态
            String rulesState = String.valueOf(alarmRule.get("rulesState"));
            if (!"1".equals(rulesState)) {
                log.info("The alarm rule [{}] state is [{}]! skip it ! ", name, rulesState);
                continue;
            }

            // 预警规则ID（同时也是事件上报来源）
            String id = String.valueOf(alarmRule.get("id"));
            // 预警提醒模板
            String remindTemplate = String.valueOf(alarmRule.get("remindTemplate"));
            // 触发阈值（提前几天提醒）
            int threshold = Integer.parseInt(String.valueOf(alarmRule.get("threshold")));

            // 非空校验
            if (StringUtils.isEmpty(id) || "null".equals(id) || StringUtils.isEmpty(remindTemplate) || "null".equals(remindTemplate)) {
                continue;
            }

            if (Boolean.TRUE.equals(isOverTimeAlarmProcess)) {
                /* 超期告警产生流程： 事件处置截至时间超过今天的生成“超期告警” */
                alarmInformService.generateAlarmData("1", id, null, remindTemplate, precinctMap);
            } else {
                /* 即将超期告警产生流程：查询当天之后某一天为处置截至日的事件，生成“即将超期”告警 */
                if (ObjectUtils.isEmpty(threshold)) {
                    log.warn("The alarm rule [{}] threshold is empty! skip it ! ", name);
                    continue;
                }
                // 查询 (当前日期 + threshold天) 的日期，作为时间检索条件
                String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now().plusDays(threshold));
                alarmInformService.generateAlarmData("3", id, dateTime, remindTemplate, precinctMap);
            }
        }
    }

}
