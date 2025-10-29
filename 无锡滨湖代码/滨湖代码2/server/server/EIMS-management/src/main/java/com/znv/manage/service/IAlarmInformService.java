package com.znv.manage.service;

import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author yangbo
 * @email
 * @date 2022/8/11
 */
public interface IAlarmInformService {

    Result selAlarmInform(String alarmId, String announcementType, String content, String isAccept, String eventId, String startTime, String endTime,
                          String userId, String departmentId, Integer pageStart, Integer pageLen, String isAll, HttpServletRequest request, String reportSource);

    Result update(String alarmIds, String isAccept, String remark);

    Result installAlarmInform(JSONArray jsonArray);

    /**
     * 生成”即将超期“或“超期告警”数据
     *
     * @param announcementType 告警类型
     * @param reportSource     事件上报来源
     * @param dateTime         日期
     * @param remindTemplate   告警提示信息模板
     * @param precinctMap      区域Map
     */
    void generateAlarmData(String announcementType, String reportSource, String dateTime, String remindTemplate, Map<String, String> precinctMap);

}
