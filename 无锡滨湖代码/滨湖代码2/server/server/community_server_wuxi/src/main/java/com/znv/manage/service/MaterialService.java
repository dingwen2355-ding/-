package com.znv.manage.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MaterialService {

    void syncMaterial();

    List<Map<String, Object>> queryMaterialUnitList();

    List<Map<String, Object>> queryMaterialList(String unitId);

    void syncMaterialUnit();
}
