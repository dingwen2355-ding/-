package com.znv.manage.xycy.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {

    List<Map<String,Object>> queryUserGpsList(@Param("userId") String userId,@Param("names")String names);

    List<Map<String,Object>> queryUserGpsListByUserId(@Param("userId") String userId);

    List<Map<String,Object>> queryUserInfo(@Param("userId") String userId);
}
