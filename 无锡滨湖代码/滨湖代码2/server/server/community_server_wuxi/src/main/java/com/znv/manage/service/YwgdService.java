package com.znv.manage.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

public interface YwgdService {
    int getData();

    List<Map<String, Object>> selectConformingBuildingAlarm(Date beginTime, Date endTime, String precinctId,String precinctName, Integer limit);

    int getConformingBuildingAlarmCount(Date beginTime, Date endTime);
}
