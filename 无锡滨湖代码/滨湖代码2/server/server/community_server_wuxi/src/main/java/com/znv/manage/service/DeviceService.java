package com.znv.manage.service;

import com.znv.manage.common.bean.DeviceInfo;
import com.znv.manage.common.bean.VideoDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DeviceService {

    List<VideoDevice> getDeviceFixedVideo(String deviceTypeName,String precinctName);

    List<Map<String,String>> getDeviceFixedVideoType(String precinctName);

    void saveHikDevice();

    void syncDbEquip();

    List<Map<String,String>> getDeviceRotationMapping(String rotationType,String precinctName);

    List<Map<String,String>> getDeviceRotationType(String precinctName);

    int getDeviceGpsIsNotNull();

    List<Map<String,Object>> queryIndividualList(String precinctId,String deviceId);

    List<Map<String,Object>> queryIndividualList2(String precinctId);

    List<Map<String,Object>> query32DeviceList();

    List<Map<String,Object>> query34DeviceList();


    List<DeviceInfo> query33DeviceList(String precinctId);

    List<DeviceInfo> query35DeviceList(String precinctId,String deviceKind);

    void updateDanBinGps();

    void updateWenJiangDanBin();

    void updateDongbuDanBin();

    void updateXinjinDanBin();

    void updateXinduDanBin();

    void saveChongzhouDanbin();

    void saveGaoxinDanbin();

    void saveShuangliuDanbin();

    void saveWEXDanbin();

    List<Map<String,Object>> queryCityEyeDeviceList(@Param("precinctId")String precinctId, @Param("deviceName")String deviceName);

    void saveXFC();

    void saveWRJ();
}
