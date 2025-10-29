package com.znv.manage.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AirQualityService {
    int getData();

    List<Map<String, Object>> selectAirReal(String stationname);

    List<Map<String, Object>> selectAirHistory(String stationname, Date beginTime, Date endTime, Integer limit);
}
