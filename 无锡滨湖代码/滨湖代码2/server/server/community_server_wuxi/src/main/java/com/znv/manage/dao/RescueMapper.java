package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RescueMapper {

    List<Map<String, String>> getRescueTeam();

    List<Map<String, String>> getRescueDevice();


}
