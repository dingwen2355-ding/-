package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

import java.util.Date;

public interface FwgdbService {
    int getData();

    Result getFwgdbStatistics(Date beginTime, Date endTime);
}
