package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public interface EventCommandDao {
    List<Map<String, String>> communication(@Param("eventId") String eventId);

    List<Map<String, String>> communicationNew(@Param("eventId") String eventId, @Param("isMax") String isMax);

    List<Map<String, String>> communicationNewByPrecinctId(@Param("precinctId") String precinctId, @Param("isMax") String isMax, @Param("planId") String planId, @Param("linkType") String linkType);

    List<Map<String, String>> communicationNewByEventType(@Param("eventType") String eventType, @Param("isMax") String isMax);

    String queryEventType(@Param("eventId") String eventId);

    String queryEventPlanId(@Param("eventId") String eventId);

    List<Map<String, String>> unitMember(@Param("unitId") String unitId);

    List<Map<String, String>> areaMember(@Param("areaCode") String areaCode);

    void eventLeader(@Param("maps") List<Map<String, String>> maps);

    List<Map<String, String>> queryEventCommunicationConfig(@Param("eventId") String eventId);

    void deleteEventCommunication(@Param("eventId") String eventId);

    void deleteEventCommunicationByPrecinctId(@Param("precinctId") String precinctId, @Param("planId") String planId);

    void deleteEventCommunicationByEventType(@Param("eventType") String eventType);

    void saveEventCommunication(@Param("maps") List<Map<String, String>> maps);

    void saveEventCommunicationByPrecinctId(@Param("maps") List<Map<String, String>> maps);

    void saveEventCommunicationByEventType(@Param("maps") List<Map<String, String>> maps);

    List<Map<String, Object>> queryUnitSystemByEventId(@Param("eventId") String eventId, @Param("systemType") String systemType);

//    void defaultLeader(@Param("unitId") String unitId, @Param("personId") String personId);

    Map<String, Object> queryEventCommunicationOn(@Param("eventId") String eventId);

    List<Map<String, String>> queryUnitSystem(@Param("systemType") String systemType, @Param("unit") String unit, @Param("systemName") String systemName);

    void updateLeader(@Param("eventId") String eventId, @Param("type") String type, @Param("leader") String leader);

    List<Map<String, Object>> queryUnitSystemList();

    List<Map<String, Object>> queryOperatorDeviceList();

    List<Map<String, Object>> queryTopicInfoList(@Param("topicId") String topicId, @Param("topicName") String topicName);

    List<Map<String, Object>> getEventData(@Param("beginTime") String beginTime,
                                           @Param("endTime") String endTime,
                                           @Param("precinctId") String precinctId,
                                           @Param("title") String title,
                                           @Param("eventType") String eventType,
                                           @Param("pageStart") Integer pageStart,
                                           @Param("pageLen") Integer pageLen);

    List<Map<String, Object>> getEventDataByType(
            @Param("title") String title,
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("precinctId") String precinctId,
            @Param("eventType") String eventType,
            @Param("gpsx") String gpsx,
            @Param("gpsy") String gpsy,
            @Param("eventId") String eventId,
            @Param("pageStart") Integer pageStart,
            @Param("pageLen") Integer pageLen);

    int addVisitLog();

    String queryVistitCount(@Param("beginTime") Date beginTime,
                            @Param("endTime") Date endTime);

    Map<String, String> queryGpsByEventId(@Param("eventId") String eventId);

    List<Map<String, Object>> queryNearbyEvent(@Param("gpsx") String gpsx, @Param("gpsy") String gpsy);

    List<Map<String, Object>> queryStuffs(@Param("planId") String planId);

    List<Map<String, Object>> queryExperts(@Param("planId") String planId);

    List<Map<String, Object>> queryShelters(@Param("planId") String planId);

    List<Map<String, Object>> queryTeams(@Param("planId") String planId);
}
