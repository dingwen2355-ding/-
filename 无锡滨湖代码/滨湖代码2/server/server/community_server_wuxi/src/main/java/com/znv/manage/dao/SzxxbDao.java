package com.znv.manage.dao;

import com.znv.manage.common.bean.Szxxb;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public interface SzxxbDao {
    int insertSzxxbList(@Param("list") List<Szxxb> resultList);

    int countDeathNumToday(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    List<HashMap<String,String>> querySzxxbAllIdCard();
}
