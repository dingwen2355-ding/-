package com.znv.manage.service;


import com.znv.manage.common.bean.Result;

import java.util.Date;

public interface QxyjxxService
{
    int getData();

    Result getQxyjxx(String beginTime, String endTime);
}
