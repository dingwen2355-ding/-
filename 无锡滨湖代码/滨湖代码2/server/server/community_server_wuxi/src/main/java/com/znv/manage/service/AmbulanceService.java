package com.znv.manage.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AmbulanceService {

    List<Map> queryAmbulanceGpsList(@Param("deviceNo")String deviceNo);

    List<Map> queryAmbulanceGpsListByDeviceNo(@Param("deviceNo")String deviceNo);
}
