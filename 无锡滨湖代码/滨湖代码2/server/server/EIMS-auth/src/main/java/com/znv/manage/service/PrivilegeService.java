package com.znv.manage.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PrivilegeService {
    JSONArray getCommitteeVilageTree(String userId);
    List<Map<String, Object>> getUserPricinctTree(String userId);
    List<Map<String, Object>> getPrecinctInfo(String precinctId);
    void updateUserPricinctTree(String userId,String precinctTreeId);

    void updatePersonPrecinctFromEP();

    String getUserStreetId(@Param("userId")String userId);
}
