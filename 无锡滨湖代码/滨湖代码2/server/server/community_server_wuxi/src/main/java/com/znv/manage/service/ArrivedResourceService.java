package com.znv.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.ArrivedResource;
import com.znv.manage.common.bean.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ArrivedResourceService {

    List<Map<String,Object>> queryArrivedResourceListByEventId(@Param("eventId") String eventId);

    List<Map<String,Object>> queryUnitList(String type,String precinctId);

    List<Map<String,Object>> queryResourceTypeList();

    List<Map<String,Object>> queryArrivedResourceList(String eventId,String precinctId);

    Result insertArrivedResource(List<ArrivedResource> arrivedResource);

    Result updateArrivedResource(List<ArrivedResource> arrivedResources);

    int deleteArrivedResourceById(@Param("id") String id);

    Result saveArrivedResource(JSONObject data);
}
