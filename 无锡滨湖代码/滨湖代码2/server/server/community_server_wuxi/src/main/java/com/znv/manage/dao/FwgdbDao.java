package com.znv.manage.dao;

import com.znv.manage.common.bean.Fwgdb;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface FwgdbDao {

    int insertFwgdbList(@Param("list") List<Fwgdb> resultList);

    List<Map<String,String>> queryFwgdbAllId();

    int countFwgdbToday(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
