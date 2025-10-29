package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

import java.util.Date;
import java.util.List;


/**
 * @Author: ygr
 * @Date: 2019/10/16
 * @Description: 注：
 */

public interface FireAwarenessService {

    Result getRealTimePerception(Date beginTime, Date endTime, String precinctId, String precinctName,Integer limit);

    Result sensorOnlineRate(String precinctId, String precinctName);

}
