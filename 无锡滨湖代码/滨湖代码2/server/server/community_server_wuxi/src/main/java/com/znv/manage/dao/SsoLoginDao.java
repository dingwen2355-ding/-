package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface SsoLoginDao {


    Map<String,String> querySsoUserInfo(@Param("loginId")String loginId);

    Map<String,String> querySsoAuthkey(@Param("precinctId")String precinctId);
}
