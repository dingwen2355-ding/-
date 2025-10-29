package com.znv.manage.dao;

import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.EventInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EventManageMapper {

    String queryPlanIdsByEventType(@Param("eventType") String eventType);

    List<Map<String, Object>> queryEventInfo(@Param("eventId") String eventId,
                                             @Param("type") String type,
                                             @Param("title") String title,
                                             @Param("address") String address,
                                             @Param("happenTime") String happenTime,
                                             @Param("endTime") String endTime,
                                             @Param("level") String level,
                                             @Param("area") String area,
                                             @Param("isFalsity") String isFalsity,
                                             @Param("precinctId")String precinctId);

    int insertEventInfo(EventInfo eventInfo);

    List<Map<String, Object>> queryEventTypeList();

    int updateEventInfoByKey(@Param("eventId") String eventId,
                             @Param("emIds") String emIds);

    List<Map<String, Object>> getEvent(@Param("eventId") String eventId);

    void updateEventOpinionLevel(List<Map<String, Object>> list);

    int deleteEventPlanCfg(@Param("eventId") String eventId,
                           @Param("planId")String planId);

    int saveEmergencyPlanCfg(@Param("list") List<Map<String, Object>> list);

    List<String> queryEventPlanCfgList(@Param("eventId") String eventId);

    int insertEventSync(@Param("list")JSONArray jsonArray);

    List<Map<String,String>> queryEventInfoList(@Param("beginTime")String beginTime,
                                                @Param("endTime")String endTime);

    List<Map<String,Object>> queryEventStatisticsMap(@Param("beginTime")String beginTime,
                                               @Param("endTime")String endTime);
}
