package com.znv.manage.dao;

import com.znv.manage.common.bean.Dqhjxssjb;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface XFCDao {
    List<Map<String,String>> queryXFCList(@Param("id") String id);
}
