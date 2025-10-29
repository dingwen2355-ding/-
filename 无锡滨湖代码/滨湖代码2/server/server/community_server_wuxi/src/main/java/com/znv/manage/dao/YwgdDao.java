package com.znv.manage.dao;

import com.znv.manage.common.bean.Ywgd;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author: yzx
 * @Date: 2021/07/06
 * @Description: 注：
 */

@Repository
public interface YwgdDao {

    int insertYwgdList(@Param("list") List<Ywgd> resultList);

    List<Map<String, Object>> selectConformingBuildingAlarm(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("precinctId") String precinctId, @Param("precinctName") String precinctName, @Param("limit") Integer limit);

    int getConformingBuildingAlarmCount(@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);
}
