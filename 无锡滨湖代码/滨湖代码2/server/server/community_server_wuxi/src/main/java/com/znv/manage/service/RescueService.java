package com.znv.manage.service;

import com.alibaba.fastjson.JSONArray;

import java.util.List;
import java.util.Map;

public interface RescueService {

    List<Map<String, String>> getRescueTeam();

    List<Map<String, String>> getRescueDevice();

}
