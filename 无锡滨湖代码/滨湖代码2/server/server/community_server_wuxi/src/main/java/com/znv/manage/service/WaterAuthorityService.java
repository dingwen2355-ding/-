package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WaterAuthorityService {
     int getYjzbData();

     int getYjfkbData();

     int getSzjcsjData();

     Result getYjzbStatistics(Date beginTime, Date endTime);

     List<Map<String, Object>> selectWaterQualityReal(String stationname);

     List<Map<String, Object>> selectWaterQualityHistory(String stationname, Date beginTime, Date endTime, Integer limit);
}
