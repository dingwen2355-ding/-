package com.znv.manage.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author znv
 * @ClassName: ManageService
 * @Description:
 * @date 2018/5/18 14:56
 */
public interface SyncEventService {

    int syncEvent(JSONObject dataFilter, JSONObject dateRange,String modelKey);

    int syncEventContentOne();

    int syncEventContentTwo();

    String handleToken();

    Object queryTaskByParam(String flowNo);

    List<String> queryTaskUserIdsByFlowNo(String flowNo);
}
