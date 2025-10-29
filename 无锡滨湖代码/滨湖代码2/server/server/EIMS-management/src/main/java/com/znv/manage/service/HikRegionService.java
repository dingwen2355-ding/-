package com.znv.manage.service;


import com.znv.manage.common.bean.HikCameraInfo;
import com.znv.manage.common.bean.Result;

public interface HikRegionService {

    Result queryRegionList(String regionIds);

    Result queryRegionListByUpId(String UpRegionId);

    Result queryStaticRegionListByUpId(String UpRegionId);

    Result queryRegionAndDeviceByUpId(String UpRegionId);

    Result queryStaticRegionAndDeviceByUpId(String UpRegionId);

    Result editCameraDevices(HikCameraInfo hikCameraInfo);

    Result queryRegionDownDevices(String regionId, String deviceName, String precinctId, String onofflineState, String gpsState, String regionName, Integer pageNum, Integer pageSize);

    Result queryStaticRegionDownDevices(String regionId, String deviceName, String precinctId, String onofflineState, String gpsState, String regionName, Integer pageNum, Integer pageSize);
}
