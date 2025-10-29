package com.znv.manage.dao;

import com.znv.manage.bean.user.PrecinctBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PrivilegeDao {
    List<Map<String, Object>> queryPrecinctListByKind(Map<String, Object> map);
    List<Map<String, Object>> getUserPricinctTree(@Param("userId") String userId);
    List<Map<String, Object>> getPrecinctInfo(@Param("precinctId") String precinctId);
    void updateUserPricinctTree(@Param("userId") String userId,
                                @Param("precinctTreeId") String precinctTreeId);

    List<Map<String, Object>> queryPrecinctExist(@Param("precinctId") String precinctId);
    void addPrecinctData(PrecinctBean obj);
    String getPrecinctIds(@Param("precinctId") String precinctId) ;
    String getUserStreetId(@Param("userId")String userId);
    String queryPrecinctUp(@Param("precinctId") String precinctId);
}
