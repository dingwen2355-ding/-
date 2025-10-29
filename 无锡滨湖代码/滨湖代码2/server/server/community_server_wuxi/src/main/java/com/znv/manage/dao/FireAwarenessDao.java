package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author znv
 * @ClassName:
 * @Description: xx描述xx
 * @date
 */
@Repository
public interface FireAwarenessDao {

    List<Map<String, Object>> getRealTimePerception(@Param("beginTime") Date beginTime,
                                                    @Param("endTime") Date endTime,
                                                    @Param("precinctId")String precinctId,
                                                    @Param("precinctName")String precinctName,
                                                    @Param("limit") Integer limit);
    //int getRealTimePerceptionCount(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    int getSensorCount(@Param("status") Integer status,@Param("precinctId")String precinctId,
                       @Param("precinctName")String precinctName);

}
