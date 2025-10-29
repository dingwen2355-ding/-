package com.znv.manage.service;


import com.znv.manage.common.bean.Result;

import java.util.Date;

public interface WorkOrderService
{
    int getData();

    Result getWorkOrder(String beginTime, String endTime,Integer limit,String precinctName);

    int syncWorkOrder(String syncDate);
}
