package com.znv.manage.service;


import com.znv.manage.common.bean.Result;

public interface DaHuaVideoService
{
    Result getVideoUrl(String videoType,String deviceId);

    Result getDeviceList(String name);

    Result getCityEyesVideoUrl(String deviceId,String protocol);

    Result closeCityEyesVideoUrl(String tgtSessionId);

    int SyncCityEyesDevice();

    Result getDeviceListNew(String name);

}
