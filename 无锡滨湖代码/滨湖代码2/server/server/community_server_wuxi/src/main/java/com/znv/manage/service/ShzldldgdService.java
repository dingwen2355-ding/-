package com.znv.manage.service;


import com.znv.manage.common.bean.Result;

import java.util.Date;

public interface ShzldldgdService
{
    int getData();

    Result getShzldldgd(String beginTime, String endTime,Integer limit,String EVENTSUBJECTNAME,String precinctName);

    Result getShzldldgdType(String precinctName);

    Result getShzldldgdStatusCount(String beginTime, String endTime,String precinctName);
}
