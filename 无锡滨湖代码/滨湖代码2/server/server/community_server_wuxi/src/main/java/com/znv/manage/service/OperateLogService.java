package com.znv.manage.service;

import com.znv.manage.common.bean.OperateLog;
import com.znv.manage.common.bean.Result;

import java.util.Date;
import java.util.List;

public interface OperateLogService {

    Result saveLog(OperateLog operateLog);


    Result queryOperatelogList(String operatorName,String precinctId, Date beginTime, Date endTime, Integer pageStart, Integer pageLen);
}
