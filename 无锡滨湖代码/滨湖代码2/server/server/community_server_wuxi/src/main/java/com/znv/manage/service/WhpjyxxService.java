package com.znv.manage.service;


import com.znv.manage.common.bean.Result;

public interface WhpjyxxService
{
    int getData();

    Result getWhpjyxx(String beginTime, String endTime,Integer limit);
}
