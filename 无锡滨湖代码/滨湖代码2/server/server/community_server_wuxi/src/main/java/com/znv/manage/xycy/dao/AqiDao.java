package com.znv.manage.xycy.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AqiDao {

    List<Map> queryAqiInfo();
}
