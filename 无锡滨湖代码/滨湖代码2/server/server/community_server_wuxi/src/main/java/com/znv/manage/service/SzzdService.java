package com.znv.manage.service;


import java.util.List;
import java.util.Map;

public interface SzzdService
{

    List<Map<String, Object>> selectSiteList(String precinctId, String precinctName);
}
