package com.znv.manage.dao;


import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface XyxcUserDao {

//    List<Map<String,Object>> queryUserInfo(@Param("userId") String userId);

    int saveUserGps(@Param("list")JSONArray jsonArray);

    List<Map<String,Object>> queryUserGpsList(@Param("userId") String userId,@Param("names")String names,@Param("dept")String dept);

    List<Map<String,Object>> queryUserGpsListByUserId(@Param("userId") String userId);

    List<Map<String,Object>> queryUserInfo2(@Param("userId") String userId,@Param("names")String names,
                                            @Param("userIds")String userIds,@Param("dept")String dept);

    String queryUserDept(@Param("userId")String userId);
}
