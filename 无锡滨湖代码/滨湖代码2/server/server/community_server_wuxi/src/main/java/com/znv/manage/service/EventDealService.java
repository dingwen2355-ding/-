package com.znv.manage.service;

import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.HistoryDealContent;
import com.znv.manage.common.bean.Result;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EventDealService {
    Result getEventDealFlowInfo(String eventId, String status);

    Map<String, Object> getEventNewDealFlowInfo(String eventId);

    Result getEventInfo(String eventId);

    Result insertEventDealFlowInfo(HistoryDealContent historyDealContent);

    Result updateEventDealFlowInfo(HistoryDealContent historyDealContent);

    Result deleteEventDealFlowInfo(String ids);

    Result batchUpdateDeafFlow(String eventId, JSONArray list);

    Result getVideoListByEvent(String gpsX,String gpsY);

    Set<Map<String,Object>> queryOperators(String eventId);
}
