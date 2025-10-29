package com.znv.manage.dao;

import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.VideoDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DeviceMapper {

    List<VideoDevice> getDeviceFixedVideo(@Param("deviceTypeName") String deviceTypeName,
                                          @Param("precinctName") String precinctName);

    List<Map<String, String>> getDeviceFixedVideoType(@Param("precinctName") String precinctName);

    int saveHikDevice(@Param("list") JSONArray jsonArray);

    List<Map<String, String>> getDeviceRotationMapping(@Param("rotationType") String rotationType, @Param("precinctName") String precinctName);

    List<Map<String, String>> getDeviceRotationType(@Param("precinctName") String precinctName);

    List<Map<String, Object>> getDeviceGpsIsNotNull();

    int updateDeviceStreet(@Param("street") String street,
                           @Param("deviceId") String deviceId);

    List<Map<String, Object>> queryIndividualList();

    List<Map<String, Object>> queryIndividualListByPrecinctId(@Param("precinctId") String precinctId,
                                                              @Param("deviceId") String deviceId,
                                                              @Param("noPrecinctIds") String noPrecinctIds,
                                                              @Param("devtype") String devtype,
                                                              @Param("noDevtype") String noDevtype);

    List<Map<String, Object>> queryIndividualListByPrecinctId2(@Param("precinctId") String precinctId);

    List<Map<String, Object>> query32DeviceList();

    List<Map<String, Object>> query34DeviceList();

    int updateQbjDanbinGps(@Param("list") JSONArray jsonArray);

    int insertDb(@Param("list") JSONArray jsonArray);

    int updateLqyDanbinGps(@Param("list") JSONArray jsonArray);

    int insertLqyDanbinGps(@Param("list") JSONArray jsonArray);

    String queryLongQuanDanBinIds();

    List<Map<String, Object>> queryXujianDanbin(@Param("precinctId") String precinctId);

    int updateXujianIndividualGps(@Param("list") List<Map<String, Object>> list, @Param("precinctId") String precinctId);

    int updateChongZhouDanbinGps(@Param("list") JSONArray jsonArray);

    int updateGaoxinDanbinGps(@Param("list") JSONArray jsonArray);

    int updateWEXDanbinGps(@Param("list") JSONArray jsonArray, @Param("precinctId") String precinctId);

    int updateWEXWurenjiGps(@Param("list") JSONArray jsonArray, @Param("precinctId") String precinctId);

    List<Map<String, Object>> queryCityEyeDeviceList(@Param("precinctId") String precinctId, @Param("deviceName") String deviceName);

    int saveXFC(@Param("list") JSONArray jsonArray);

    int updateShuangliuDanbinGps(@Param("list") List<Map<String, String>> list);

    int replaceDeviceBySync(@Param("list") JSONArray jsonArray);

    int replaceOrgBySync(@Param("list") JSONArray jsonArray);


}
