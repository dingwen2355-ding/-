package com.znv.manage.dao;


import com.znv.manage.common.bean.ShzldldgdBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author: yzx
 * @Date: 2021/06/30
 * @Description: 注：
 */

@Repository
public interface ShzldldgdDao {

    int insertShzldldgdList(@Param("list") List<ShzldldgdBean> resultList);

    int updateShzldldgdList(@Param("list") List<ShzldldgdBean> resultList);

    int queryShzldldgdListByEventId(@Param("EVENTID") String EVENTID);

    List<Map<String, Object>> queryShzldldgdList(@Param("beginTime") String beginTime,
                                                 @Param("endTime") String endTime,
                                                 @Param("limit") Integer limit,
                                                 @Param("EVENTSUBJECTNAME") String EVENTSUBJECTNAME,
                                                 @Param("precinctName") String precinctName);

    int queryShzldldgdCount(@Param("beginTime") String beginTime,
                            @Param("endTime") String endTime,
                            @Param("limit") Integer limit,
                            @Param("EVENTSUBJECTNAME") String EVENTSUBJECTNAME,
                            @Param("precinctName") String precinctName);

    List<Map<String, Object>> queryShzldldgdType(@Param("precinctName") String precinctName);

    int queryShzldldgdStatusAll(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("precinctName") String precinctName);

    int queryShzldldgdStatusUnDeal(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("precinctName") String precinctName);

    int queryShzldldgdStatusDeal(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("precinctName") String precinctName);
}
