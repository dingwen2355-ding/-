package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

import java.util.List;
import java.util.Map;

public interface CenterService {
    List<Map<String,Object>> queryCoalMine();
    List<Map<String,Object>> queryEnterprise();
    List<Map<String,Object>> queryObservationStation();
    List<Map<String,Object>> queryProneDisaster();
    List<Map<String,Object>> queryWaterlogging();
    List<Map<String,Object>> queryReservoir();
    List<Map<String,Object>> queryTransport();
    List<Map<String,Object>> queryWeatherStation();
    List<Map<String,Object>> queryRescue();
    List<Map<String,Object>> queryHazard();
    List<Map<String,Object>> queryMedicalInstitution();
    Long queryCount();
    Result queryMonthCount();
    Result queryWeibo();
    Result queryOthers();
    Result query48Hours();

    Result queryWeather();
}
