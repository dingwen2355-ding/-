package com.znv.manage.dao;

import com.znv.manage.common.bean.Szjcsj;
import com.znv.manage.common.bean.Yjfkb;
import com.znv.manage.common.bean.Yjzb;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: waterAuthorityDao
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/6/30 9:19
 **/
@Repository
public interface WaterAuthorityDao {

    int insertYjzbList(@Param("list") List<Yjzb> resultList);

    int insertYjfkbList(@Param("list") List<Yjfkb> resultList);

    int insertSzjcsjList(@Param("list") List<Szjcsj> resultList);

    int querySzjcsjListByCollectTime(@Param("collectTime") Date collectTime);

    List<HashMap<String,Object>> queryYjzbAllId();

    List<HashMap<String,Date>> getSzjcsjAllCollectTime();

    int countYjzbToday(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    List<Map<String, String>> YjzbTodayList(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    List<Map<String, Object>> selectWaterQualityReal(@Param("stationname") String stationname);

    List<Map<String, Object>> selectWaterQualityHistory(@Param("stationname") String stationname, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("limit") Integer limit);
}
