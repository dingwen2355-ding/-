package com.znv.manage.service;


import java.util.List;
import java.util.Map;

public interface JczdjbxxService
{
    int getData();

    List<Map<String, Object>> selectWater(String precinctId, String precinctName);

    List<Map<String, Object>> selectRainFall(String precinctId, String precinctName);
}
