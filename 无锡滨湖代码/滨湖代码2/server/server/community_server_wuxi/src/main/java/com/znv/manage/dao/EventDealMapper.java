package com.znv.manage.dao;

import com.znv.manage.common.bean.DeviceInfo;
import com.znv.manage.common.bean.HistoryDealContent;
import com.znv.manage.common.bean.SyncDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EventDealMapper {
    List<Map<String, Object>> getEventDealFlowInfo(@Param("eventId") String eventId,
                                                   @Param("status") String status);

    Map<String, Object> getEventNewDealFlowInfo(@Param("eventId") String eventId);


    List<Map<String, Object>> getEventInfo(@Param("eventId") String eventId);

    int insertEventDealInfo(HistoryDealContent historyDealContent);

    int updateEventDealFLowInfo(HistoryDealContent historyDealContent);

    int deleteFlowByEventId(@Param("eventId") String eventId);

    void batchUpdateDealFlow(@Param("lists") List<HistoryDealContent> lists);

    List<Map<String, Object>> getEventEmergencyInfo(@Param("eventId") String eventId);

    List<SyncDevice> getDeviceList();
}
