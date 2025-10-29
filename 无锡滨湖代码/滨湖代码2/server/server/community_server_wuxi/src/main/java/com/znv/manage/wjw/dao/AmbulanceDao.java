package com.znv.manage.wjw.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AmbulanceDao {

    List<Map> queryAmbulanceGpsList(@Param("deviceNo")String deviceNo);

    List<Map> queryAmbulanceGpsListByDeviceNo(@Param("deviceNo")String deviceNo);

}
