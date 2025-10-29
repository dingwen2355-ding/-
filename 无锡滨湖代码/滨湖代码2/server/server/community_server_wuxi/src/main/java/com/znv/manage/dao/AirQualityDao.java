package com.znv.manage.dao;

import com.znv.manage.common.bean.Dqhjxssjb;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Title: AirQualityDao
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/7/8 11:25
 **/
@Repository
public interface AirQualityDao {

    int insertDqhjxssjbList(@Param("list") List<Dqhjxssjb> resultList);

    List<Map<String,String>> queryDqhjxssjbAllId();

    List<Map<String, Object>> selectAirReal(@Param("stationname") String stationname);

    List<Map<String, Object>> selectAirHistory(@Param("stationname") String stationname, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("limit") Integer limit);
}
