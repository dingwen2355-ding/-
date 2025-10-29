package com.znv.manage.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: zl
 * @Date: 2021/06/30
 * @Description: 注：
 */

@Repository
public interface WaterPointsDao {

    List<Map<String, Object>> getWaterPoints(@Param("beginTime") String beginTime, @Param("endTime") String endTime,
                                             @Param("precinctId") String precinctId,@Param("precinctName")String precinctName);

    int updateWaterPointsStreet(@Param("id") String id,@Param("street") String street);

}
