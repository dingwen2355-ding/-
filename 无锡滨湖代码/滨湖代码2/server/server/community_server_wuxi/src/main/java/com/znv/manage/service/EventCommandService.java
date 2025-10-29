package com.znv.manage.service;


import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EventCommandService {

    Map<String, List<Map<String, String>>> communication(String eventId);

    Result communicationNew(String eventId, String isMax);

    Object communicationOrderByDepartment(String eventId, String isMax, String precinctId);

    Object communicationOrderByDepartmentByPrecinctId(String isMax, String precinctId, String eventType, String linkType);

    Object communicationOrderByDepartmentByEventType(String isMax, String eventType);

    List<Map<String, String>> unitMember(String unitId);

    List<Map<String, String>> areaMember(String areaCode);

    void eventLeader(String eventId, String data);

    Map<String, List<Map<String, String>>> queryEventCommunicationConfig(String eventId);

    void updateEventCommunication(JSONObject json);

    void updateEventCommunicationByPrecinctId(JSONObject json);

    void updateEventCommunicationByEventType(JSONObject json);

    List<Map<String, Object>> queryUnitSystemByEventId(String eventId, String systemType);

//    void defaultLeader(String unitId,String personId);

    Map<String, Object> queryEventCommunicationOn(String eventId);

    Map<String, List<Map<String, String>>> queryUnitSystem(String systemType, String unit, String systemName);

    void updateLeader(String eventId, String type, String leader);

    void updateLeader2(JSONObject json);

    void updateLeaderByEventType(JSONObject json);

    void updateLeaderByPrecinctId(JSONObject json);

    List<Map<String, Object>> queryUnitSystemList();

    Map<String, List<Map<String, Object>>> queryUnitSystem2();

    List<Map<String, Object>> queryTopicInfoList(@Param("topicId") String topicId, @Param("topicName") String topicName);

    Result getEventData(String beginTime, String endTime, String precinctId, String title, String eventType, Integer pageNum, Integer pageSize);

    Result getEventDataByType(String title, String beginTime, String endTime, String precinctId, String type, String eventId, Integer pageNum, Integer pageSize);

    String queryVistitCount(@Param("beginTime") Date beginTime,
                            @Param("endTime") Date endTime);

    Result queryEventResource(String eventId);
}
