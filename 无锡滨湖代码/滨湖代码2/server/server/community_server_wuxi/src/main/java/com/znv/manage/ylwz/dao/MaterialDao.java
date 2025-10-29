package com.znv.manage.ylwz.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MaterialDao {

    List<Map<String,Object>> queryMaterialList();

    List<Map<String,Object>> queryAllMaterialList();


}
