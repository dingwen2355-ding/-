package com.znv.manage.dao.event;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * TCfgResourceList表数据库访问层
 *
 * @author 0049003788
 * @since 2022-11-10 15:30:13
 */
public interface EventSituationDao {

    List<Map<String, Object>> quartAnalysis();

    List<Map<String, Object>> eventLevelCount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime);

    List<Map<String, Object>> eventStreetCount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime);

    List<Map<String, Object>> burstEventTotalCount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime);

    List<Map<String, Object>> burstEventTypeCount(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("type") String type);

    List<Map<String, Object>> getTypeList();

    Long burstEventByType(
            @Param("beginTime") String beginTime,
            @Param("endTime") String endTime,
            @Param("typeId") String typeId);

}

