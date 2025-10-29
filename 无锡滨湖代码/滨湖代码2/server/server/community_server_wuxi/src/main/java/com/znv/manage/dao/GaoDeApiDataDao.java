package com.znv.manage.dao;

import com.znv.manage.common.bean.WeatherBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GaoDeApiDataDao {

	List<Map<String, Object>> queryWeather();

	int insertWeather(WeatherBean weatherBean);

	void insertRoadInfo(Map<String, Object> paramMap);

	Map<String, Object> queryLatestRoadInfo(@Param("roadName") String roadName);

}