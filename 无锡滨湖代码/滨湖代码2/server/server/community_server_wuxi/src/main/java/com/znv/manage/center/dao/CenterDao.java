package com.znv.manage.center.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author ：0049003788
 * @date ：2023/1/17 10:58
 */
@Repository
public interface CenterDao {
    List<Map<String, Object>> select();
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
    Long queryCount(@Param("list") List<String> list);
    String queryMonthCount(@Param("yearMonth") String yearMonth);
    String queryDayCount(@Param("yearMonth") String yearMonth);
    List<Map<String,Object>> queryWeibo(@Param("nowMonth") String NowMonth ,@Param("lastMonth") String LastMonth);
    List<Map<String,Object>> queryOthers(@Param("nowMonth") String NowMonth ,@Param("lastMonth") String LastMonth);
    List<Map<String,Object>> query48Hours(@Param("nowMonth") String NowMonth ,@Param("lastMonth") String LastMonth);
    String queryTableName();
    List<String> queryTableNameList(@Param("nowTime") Timestamp nowTime);


    Map<String,Object> queryLastWeather();

}
