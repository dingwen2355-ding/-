package com.znv.manage.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface VehicleService {

    List<Map> queryVehicleGpsList(String vehicleNo,String dateStr);

    List<Map> queryVehicleGpsListByVehicleNo(String vehicleNo,String dateStr);

    List<Map<String,Object>> queryBusList(String number,String hours);

    List<Map<String,Object>> queryTaxiList(String number,String hours);

    List<Map<String,Object>> queryTruckList(String number,String hours);

    List<Map<String,String>> queryXFCList(String id);
}
