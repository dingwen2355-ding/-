package com.znv.manage.dao;


import com.znv.manage.common.bean.Jczdjbxx;
import com.znv.manage.common.bean.Zsdw;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: yzx
 * @Date: 2021/06/30
 * @Description: 注：
 */

@Repository
public interface JczdjbxxDao {

    int insertJczdjbxxList(@Param("list") List<Jczdjbxx> resultList);

    List<Map<String, Object>> selectWater(@Param("precinctId") String precinctId, @Param("precinctName") String precinctName);

    List<Map<String, Object>> selectRainFall(@Param("precinctId") String precinctId, @Param("precinctName") String precinctName);
}
