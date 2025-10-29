package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

import java.util.Date;
public interface SpAIgjxxService {
    int getSpAIgjxxData();

    int getAIsxtData();

    Result getSpAIgjxxStatistics(Date beginTime,Date endTime);

    Result getSpAIyjStatistics(Date beginTime,Date endTime);

    Result SpAIgjxxTop5Today(Date beginTime,Date endTime);

}
