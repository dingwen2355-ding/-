package com.znv.manage.dao;


import com.znv.manage.common.bean.SyncDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @Author: zl
 * @Date: 2021/06/30
 * @Description: 注：
 */

@Repository
public interface DaHuaVideoDao {

    List<Map<String, Object>> getDeviceList(@Param("name") String name);

    List<Map<String, Object>> getDeviceListNew(@Param("name") String name);

    int insertSyncDevice(@Param("list") List<SyncDevice> resultList);
    int insertTempDevice(@Param("list") List<SyncDevice> resultList);
    int deleteUselessDevice();
    int deleteTempDevice();
    int insertAddDevice();

    int updatePrecinctId1();
    int updatePrecinctId2();
    int updatePrecinctId3();

}
