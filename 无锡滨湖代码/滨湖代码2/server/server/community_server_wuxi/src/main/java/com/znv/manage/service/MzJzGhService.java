package com.znv.manage.service;


import com.znv.manage.common.bean.Result;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MzJzGhService
{
    int getData();

    Result getOutpatientEmergencyCount(Date beginTime, Date endTime,String precinctId, String precinctName);
}
