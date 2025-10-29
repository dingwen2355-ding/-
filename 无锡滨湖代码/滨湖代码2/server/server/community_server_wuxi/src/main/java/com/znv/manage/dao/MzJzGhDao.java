package com.znv.manage.dao;


import com.znv.manage.common.bean.MzJzGh;
import com.znv.manage.common.bean.Sw;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author: yzx
 * @Date: 2021/07/08
 * @Description: 注：
 */

@Repository
public interface MzJzGhDao {

    int insertMzJzGhList(@Param("list") List<MzJzGh> resultList);

    int getOutpatientEmergencyCount(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("precinctId") String precinctId,@Param("precinctName") String precinctName);

    int delAll();
}
