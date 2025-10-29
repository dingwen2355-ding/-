package com.znv.manage.service;


import com.znv.manage.common.bean.Result;

public interface WaterPointsService
{
    Result getWaterPoints(String beginTime, String endTime,String precinctId,String precinctName);

    Result updateWaterPointsStreet();
}
