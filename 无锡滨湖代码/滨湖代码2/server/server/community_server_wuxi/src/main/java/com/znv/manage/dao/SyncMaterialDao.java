package com.znv.manage.dao;


import com.znv.manage.common.bean.Jjsw;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: yzx
 * @Date: 2021/07/01
 * @Description: 注：
 */

@Repository
public interface SyncMaterialDao {

    int insertMaterialData(@Param("list") List<Map<String,Object>> resultList);

    int getDataCount();

    List<Map<String, Object>> queryMaterialUnitList();

    List<Map<String, Object>> queryMaterialList(@Param("unitId") String unitId);

    List<Map<String, Object>> queryMaterialUnitGroup();

    int insertMaterialUnitGroup(@Param("list") List<Map<String,Object>> resultList);
}
