package com.znv.manage.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SwService {
    int getData();

    List<Map<String, Object>> selectHistoryWaterAndRainList(String stcd, Date beginTime, Date endTime, Integer limit);

    List<Map<String, Object>> selectRealWaterAndRainList(String stcd);
}
