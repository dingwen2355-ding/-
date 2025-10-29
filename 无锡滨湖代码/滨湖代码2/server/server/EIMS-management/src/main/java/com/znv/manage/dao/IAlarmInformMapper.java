package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yangbo
 * @email
 * @date 2022/8/11
 */
@Repository
public interface IAlarmInformMapper {
    List<Map<String, Object>> selAlarmInform(@Param("alarmId") String alarmId,
                                             @Param("content") String content,
                                             @Param("isAccept") String isAccept,
                                             @Param("eventId") String eventId,
                                             @Param("startTime") String startTime,
                                             @Param("endTime") String endTime,
                                             @Param("userId") String userId,
                                             @Param("departmentId") String departmentId,
                                             @Param("announcementType") String announcementType,
                                             @Param("pageStart") Integer pageStart,
                                             @Param("pageLen") Integer pageLen,
                                             @Param("queryUserIdIsEmpty") Integer queryUserIdIsEmpty,
                                             @Param("reportSource") String reportSource);

    /**
     * 获取告警数据总数
     *
     * @param alarmId
     * @param content
     * @param isAccept
     * @param eventId
     * @param startTime
     * @param endTime
     * @param userId
     * @param departmentId
     * @param announcementType
     * @param pageStart
     * @param pageLen
     * @param queryUserIdIsEmpty 查询用户ID为空的，0 否 1 是
     * @param reportSource       事件上报来源, 多个用英文逗号分隔
     * @return
     */
    Long selAlarmInformCount(@Param("alarmId") String alarmId,
                             @Param("content") String content,
                             @Param("isAccept") String isAccept,
                             @Param("eventId") String eventId,
                             @Param("startTime") String startTime,
                             @Param("endTime") String endTime,
                             @Param("userId") String userId,
                             @Param("departmentId") String departmentId,
                             @Param("announcementType") String announcementType,
                             @Param("pageStart") Integer pageStart,
                             @Param("pageLen") Integer pageLen,
                             @Param("queryUserIdIsEmpty") Integer queryUserIdIsEmpty,
                             @Param("reportSource") String reportSource);

    int update(@Param("alarmIds") String[] alarmIds,
               @Param("isAccept") String isAccept,
               @Param("remark") String remark);

    List<Map<String, Object>> selOverTimeAlarm(@Param("time") String time);

    /**
     * 流式查询超期/即将超期事件
     *
     * @param announcementType 告警类型
     * @param reportSource     上报来源
     * @param dateTime         日期
     * @return
     */
    Cursor<Map<String, Object>> selOverTimeAlarmByCursor(@Param("announcementType") String announcementType,
                                                         @Param("reportSource") String reportSource,
                                                         @Param("dateTime") String dateTime);
}
