package com.znv.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface EventJointService {
    Result getDepartmentAll();

    Result drawBill(JSONObject param);

    Result queryTitle(String eventId,String startDate,String endDate,Integer page, Integer pageSize);

    Result dealMessage(JSONObject param);

    Result eventList(JSONObject param);

    Result queryEventList(String beginTime, String endTime, Integer pageNum, Integer pageSize);

    Result queryEventStatisticsMap(@Param("beginTime")String beginTime,
                                               @Param("endTime")String endTime);
}
