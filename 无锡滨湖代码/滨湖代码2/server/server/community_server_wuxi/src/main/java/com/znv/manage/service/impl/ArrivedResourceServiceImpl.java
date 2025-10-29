package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.ArrivedResource;
import com.znv.manage.common.bean.Result;
import com.znv.manage.dao.ArrivedResourceMapper;
import com.znv.manage.service.ArrivedResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
@Slf4j
public class ArrivedResourceServiceImpl implements ArrivedResourceService {

    @Autowired
    private ArrivedResourceMapper arrivedResourceMapper;

    @Override
    public List<Map<String, Object>> queryArrivedResourceListByEventId(String eventId) {
        return arrivedResourceMapper.queryArrivedResourceListByEventId(eventId);
    }

    @Override
    public List<Map<String, Object>> queryUnitList(String type,String precinctId) {
        return arrivedResourceMapper.queryUnitList(type,precinctId);
    }

    @Override
    public List<Map<String, Object>> queryResourceTypeList() {
        return arrivedResourceMapper.queryResourceTypeList();
    }

    @Override
    public List<Map<String,Object>> queryArrivedResourceList(String eventId,String precinctId) {
        return arrivedResourceMapper.queryArrivedResourceList(null,eventId,null,null,precinctId);
    }

    @Override
    public Result insertArrivedResource(List<ArrivedResource> arrivedResources) {
        Result result = new Result();
        try {
        for(ArrivedResource arrivedResource:arrivedResources){
            if(!StringUtils.isEmpty(arrivedResource.checkInfo())){
                result.setCode(500);
                result.setMessage(arrivedResource.checkInfo());
                return result;
            }
            //判断资源力量类型是不是自定义，resourceId为空是自定义
            if(!StringUtils.isEmpty(arrivedResource.getResourceId())){
                List<Map<String,Object>> list = arrivedResourceMapper.queryArrivedResourceList(null,arrivedResource.getEventId(),arrivedResource.getUnitId(),arrivedResource.getResourceId(),null);
                if(!CollectionUtils.isEmpty(list)){
                    result.setCode(500);
                    result.setMessage("添加的到场资源力量已经存在");
                    return result;
                }
            }else {
                //resourceId为空是自定义,添加新的资源力量类型
                int count = arrivedResourceMapper.addResourceType(arrivedResource.getResourceName(),arrivedResource.getType());
                if(count > 0){
                    List<String> ids = arrivedResourceMapper.queryResourceId(arrivedResource.getResourceName(),arrivedResource.getType());
                    String recourceId = ids.get(0);
                    arrivedResource.setResourceId(recourceId);
                }
            }

        }
        int count = arrivedResourceMapper.insertArrivedResource(arrivedResources);
        result.setMessage("save success count:"+count);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Result updateArrivedResource(List<ArrivedResource> arrivedResources) {
        Result result = new Result();
        try {
        for(ArrivedResource arrivedResource:arrivedResources){
            if(StringUtils.isEmpty(arrivedResource.getId())){
                result.setCode(500);
                result.setMessage("存在 id 为空资源力量，找不到更新条目");
                return result;
            }
            if(!StringUtils.isEmpty(arrivedResource.checkInfo())){
                result.setCode(500);
                result.setMessage("存在"+arrivedResource.checkInfo());
                return result;
            }
            List<Map<String,Object>> list = arrivedResourceMapper.queryArrivedResourceList(arrivedResource.getId(),arrivedResource.getEventId(),arrivedResource.getUnitId(),arrivedResource.getResourceId(),null);
            if(!CollectionUtils.isEmpty(list)){
                result.setCode(500);
                result.setMessage("添加的到场资源力量id"+arrivedResource.getResourceId()+"已经存在");
                return result;
            }
            arrivedResourceMapper.updateArrivedResource(arrivedResource);

        }}catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
            return result;
        }
        result.setMessage("update success");
        return result;
    }

    @Override
    public int deleteArrivedResourceById(String id) {
        return arrivedResourceMapper.deleteArrivedResourceById(id);
    }

    @Override
    public Result saveArrivedResource(JSONObject data) {
        Result result = new Result();
        try {
            //插入数据
            JSONArray lists = data.getJSONArray("lists");
            List<Map> maps = lists.toJavaList(Map.class);
            //判重
            if(!StringUtils.isEmpty(checkList(maps))){
                result.setCode(500);
                result.setMessage(checkList(maps));
                return result;
            }
            
            //删资源力量
            String eventId = data.getString("eventId");
            arrivedResourceMapper.deleteArrivedResourceByEventId(eventId);
            for(Map map:maps){
                if(!map.containsKey("unitName")){
                    map.put("unitName","");
                }
                //判断资源力量类型是不是自定义，resourceId为空是自定义
                if(StringUtils.isEmpty(map.get("resourceId"))){
                    //resourceId为空是自定义,添加新的资源力量类型
                    int count = arrivedResourceMapper.addResourceType(String.valueOf(map.get("resourceName")),String.valueOf(map.get("type")));
                    if(count > 0){
                        List<String> ids = arrivedResourceMapper.queryResourceId(String.valueOf(map.get("resourceName")),String.valueOf(map.get("type")));
                        String recourceId = ids.get(0);
                        map.put("resourceId",recourceId);
                    }
                }
            }

            //插入数据
            if(!CollectionUtils.isEmpty(maps)){
                arrivedResourceMapper.saveArrivedResource(maps);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            result.setMessage(e.getMessage());
            result.setCode(1);
        }
        return result;
    }

    private String checkList(List<Map> maps) {
        Set<ArrivedResource> set = new HashSet<>();
        for(Map map : maps)
        {
            ArrivedResource arrivedResource = new ArrivedResource();
            arrivedResource.setEventId(String.valueOf(map.get("eventId")));
            arrivedResource.setUnitId(String.valueOf(map.get("unitId")));
            arrivedResource.setResourceId(String.valueOf(map.get("resourceId")));
            arrivedResource.setResourceName(String.valueOf(map.get("resourceName")));
            set.add(arrivedResource);
        }
        if(set.size() != maps.size())
        {
            return "存在重复资源力量";//有重复
        }else
        {
            return "";//不重复
        }
    }
}
