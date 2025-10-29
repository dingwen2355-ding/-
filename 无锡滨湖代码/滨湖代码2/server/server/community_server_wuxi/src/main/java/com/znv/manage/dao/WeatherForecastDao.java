package com.znv.manage.dao;


import com.znv.manage.common.bean.WeatherForecastBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: yzx
 * @Date: 2021/06/30
 * @Description: 注：
 */

@Repository
public interface WeatherForecastDao {

    int insertWeatherForecastList(@Param("list") List<WeatherForecastBean> resultList);

    int deleteWeatherForecastList();

    int queryWeatherForecastList(@Param("SJWXHXPMC") String SJWXHXPMC, @Param("RKSJ") String RKSJ);
}
