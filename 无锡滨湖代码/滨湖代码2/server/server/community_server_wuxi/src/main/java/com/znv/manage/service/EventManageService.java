package com.znv.manage.service;

import com.znv.manage.common.bean.EventInfo;
import com.znv.manage.common.bean.Result;

import java.util.List;
import java.util.Map;

public interface EventManageService {
    Result addEvent(EventInfo eventInfo);

    List<Map<String, Object>> queryEventInfo(String eventId, String type, String title, String address,
                                             String happenTime, String endTime, String level, String area,
                                             String isFalsity,String precinctId);

    List<Map<String,Object>> queryEventTypeList();

    Result updateEventInfoByKey(String eventId, String emergencyPlanIds);
}
