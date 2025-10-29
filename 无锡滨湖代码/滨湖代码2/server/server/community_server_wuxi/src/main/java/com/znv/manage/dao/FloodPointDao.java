package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 0049003788
 */
@Repository
public interface FloodPointDao {
    /**
     * 点位数据查询撒点
     *
     * @param floodType 类型
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> queryPoint(@Param("floodType") String floodType);

    /**
     * 点位附近摄像头查询
     *
     * @param lon      经度
     * @param lat      纬度
     * @param distance 距离(km)
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    List<Map<String, Object>> queryDeviceByGpsAndDistance(@Param("lon") double lon,
                                                          @Param("lat") double lat,
                                                          @Param("distance") float distance);
}
