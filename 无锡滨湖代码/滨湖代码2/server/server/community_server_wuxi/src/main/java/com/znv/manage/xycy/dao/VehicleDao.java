package com.znv.manage.xycy.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VehicleDao {

    List<Map> queryVehicleGpsList(@Param("vehicleNo") String vehicleNo,
                                  @Param("dateStr") String dateStr);

    List<Map> queryVehicleGpsListByVehicleNo(@Param("vehicleNo") String vehicleNo,
                                             @Param("dateStr") String dateStr);

    List<Map<String,Object>> queryBusList(@Param("number")String number,@Param("timeStamp")String timeStamp);

    List<Map<String,Object>> queryTaxiList(@Param("number")String number,@Param("timeStamp")String timeStamp);

    List<Map<String,Object>> queryTruckList(@Param("number")String number,@Param("timeStamp")String timeStamp);

}
