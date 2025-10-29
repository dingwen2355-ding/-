package com.znv.manage.dao;

import com.znv.manage.common.bean.Znsbgjsj;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface ZnsbgjsjDao {

    int insertZnsbgjsjList(@Param("list") List<Znsbgjsj> resultList);

    List<HashMap<String,Object>> queryAlarmNo();

    int countZnsbgjsjToday(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    List<Map<String, String>> ZnsbgjsjTodayList(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
