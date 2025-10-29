package com.znv.manage.dao;


import com.znv.manage.common.bean.Sw;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author: yzx
 * @Date: 2021/07/01
 * @Description: 注：
 */

@Repository
public interface SwDao {

    int insertSwList(@Param("list") List<Sw> resultList);

    int delAll();

    List<Map<String, Object>> selectHistoryWaterAndRainList(@Param("stcd") String stcd, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("limit") Integer limit);

    List<Map<String, Object>> selectRealWaterAndRainList(@Param("stcd") String stcd);
}
