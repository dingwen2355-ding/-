package com.ruoyi.system.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

public interface IInsightApiService {

    JSONArray getOnePersonMultiplyComplaints(JSONObject jsonObject);

    JSONArray getFlowsClassified();

    JSONArray getOneDepartmentMultiplyComplaints();

    JSONArray getComplaintsByHours(int max,int min);

    JSONArray getComplaintsCountInWeek();

    JSONObject getTotalFlow();

    JSONObject getWeather();

    JSONArray getAIInfos();

    void countAi();

    JSONArray get12345Infos();

    JSONArray getBasicLevelInfos();

    JSONArray getFlowSourceClassified();
}
