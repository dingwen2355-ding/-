package com.znv.manage.service;

import java.util.List;
import java.util.Map;

public interface GaoDeApiDataService {

    void syncWeatherInfo();

    void syncRoadInfo();

    List<Map<String, Object>> queryWeather();

    List<Map<String, Object>> queryRoadInfoList(String roadName);

}
