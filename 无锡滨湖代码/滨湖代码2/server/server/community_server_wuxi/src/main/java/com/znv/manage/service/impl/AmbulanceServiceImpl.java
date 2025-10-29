package com.znv.manage.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.znv.manage.service.AmbulanceService;
import com.znv.manage.wjw.dao.AmbulanceDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AmbulanceServiceImpl implements AmbulanceService {

    @Autowired
    private AmbulanceDao ambulanceDao;


    @Override
    public List<Map> queryAmbulanceGpsList(String deviceNo) {
        List<Map> result = new ArrayList<>();
        try {
            List<Map> list = ambulanceDao.queryAmbulanceGpsList(deviceNo);
            if(!CollectionUtils.isEmpty(list)){
                for(Map map:list){
                    String jsonPoint = String.valueOf(map.get("jsonPoint"));
                    if(!StringUtils.isEmpty(jsonPoint)){
                        JSONObject tempJson = JSONObject.parseObject(jsonPoint);
                        map.put("gpsx",tempJson.get("x"));
                        map.put("gpsy",tempJson.get("y"));
                        result.add(map);
                    }
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Map> queryAmbulanceGpsListByDeviceNo(String deviceNo) {
        List<Map> result = new ArrayList<>();
        try {
            List<Map> list = ambulanceDao.queryAmbulanceGpsListByDeviceNo(deviceNo);
            if(!CollectionUtils.isEmpty(list)){
                for(Map map:list){
                    String jsonPoint = String.valueOf(map.get("jsonPoint"));
                    if(!StringUtils.isEmpty(jsonPoint)){
                        JSONObject tempJson = JSONObject.parseObject(jsonPoint);
                        map.put("gpsx",tempJson.get("x"));
                        map.put("gpsy",tempJson.get("y"));
                        result.add(map);
                    }
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }
}
