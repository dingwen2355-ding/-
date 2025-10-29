package com.znv.manage.dao.event;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventSyncDao {
    void insertOrUpdate(@Param("list") JSONArray jsonArray,
                        @Param("source") String source);
}
