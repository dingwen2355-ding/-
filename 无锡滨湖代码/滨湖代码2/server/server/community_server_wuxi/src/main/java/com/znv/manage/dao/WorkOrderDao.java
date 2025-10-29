package com.znv.manage.dao;


import com.znv.manage.common.bean.WorkOrderBean;
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
public interface WorkOrderDao {

    int insertWorkOrderList(@Param("list") List<WorkOrderBean> resultList);

    int updateWorkOrderList(@Param("list") List<WorkOrderBean> resultList);

    int deleteWorkOrderList();

    List<Map<String, Object>> queryWorkOrderList(@Param("beginTime") String beginTime,
                                                 @Param("endTime") String endTime,
                                                 @Param("serialnum") String serialnum,
                                                 @Param("limit") Integer limit,
                                                 @Param("precinctName")String precinctName);

    int queryWorkOrderCount(@Param("beginTime") String beginTime,
                            @Param("endTime") String endTime,
                            @Param("serialnum") String serialnum,
                            @Param("limit") Integer limit,
                            @Param("precinctName")String precinctName);
}
