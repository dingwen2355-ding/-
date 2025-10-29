package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

import java.util.Date;

public interface SzxxbService {
    int getData();

    Result getSzxxbStatistics(Date beginTime, Date endTime);
}
