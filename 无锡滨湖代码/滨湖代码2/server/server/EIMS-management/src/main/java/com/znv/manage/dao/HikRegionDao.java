package com.znv.manage.dao;

import com.znv.manage.common.bean.HikCameraInfo;
import com.znv.manage.common.bean.Region;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HikRegionDao {

    List<Region> queryRegionList(@Param("regionIds") String regionIds);

    List<Region> queryRegionListByUpId(@Param("UpRegionId") String UpRegionId);

    List<Region> queryStaticRegionListByUpId(@Param("UpRegionId") String UpRegionId);

    List<Map<String, Object>> queryRegionListByUpIdNew(@Param("UpRegionId") String UpRegionId);

    List<Map<String, Object>> queryStaticRegionListByUpIdNew(@Param("UpRegionId") String UpRegionId);

    List<Map<String, Object>> queryDeviceByRegionId(@Param("regionId") String regionId);

    List<Map<String, Object>> queryStaticDeviceByRegionId(@Param("regionId") String regionId);

    List<Map<String, String>> queryRegionDownDevices(@Param("regionId") String regionId,
                                                     @Param("pageStart") Integer pageStart,
                                                     @Param("pageLen") Integer pageLen);

    List<Map<String, String>> queryRegionDownDevices2(@Param("list") List<String> regionIds,
                                                      @Param("deviceName") String deviceName,
                                                      @Param("precinctId") String precinctId,
                                                      @Param("onofflineState") String onofflineState,
                                                      @Param("gpsState") String gpsState,
                                                      @Param("regionName") String regionName,
                                                      @Param("pageStart") Integer pageStart,
                                                      @Param("pageLen") Integer pageLen);

    List<Map<String, String>> queryStaticRegionDownDevices2(@Param("list") List<String> regionIds,
                                                            @Param("deviceName") String deviceName,
                                                            @Param("precinctId") String precinctId,
                                                            @Param("onofflineState") String onofflineState,
                                                            @Param("gpsState") String gpsState,
                                                            @Param("regionName") String regionName,
                                                            @Param("pageStart") Integer pageStart,
                                                            @Param("pageLen") Integer pageLen);

    String queryRegionDownDevicesCount(@Param("list") List<String> regionIds,
                                       @Param("deviceName") String deviceName,
                                       @Param("precinctId") String precinctId,
                                       @Param("onofflineState") String onofflineState,
                                       @Param("gpsState") String gpsState,
                                       @Param("regionName") String regionName);

    String queryStaticRegionDownDevicesCount(@Param("list") List<String> regionIds,
                                             @Param("deviceName") String deviceName,
                                             @Param("precinctId") String precinctId,
                                             @Param("onofflineState") String onofflineState,
                                             @Param("gpsState") String gpsState,
                                             @Param("regionName") String regionName);

    String queryRegionDown(@Param("regionId") String regionId);

    String queryStaticRegionDown(@Param("regionId") String regionId);

    void editCameraDevices(HikCameraInfo hikCameraInfo);

}
