package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.QxyjxxBean;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.common.utils.GeometryUtil;
import com.znv.manage.dao.QxyjxxDao;
import com.znv.manage.dao.WaterPointsDao;
import com.znv.manage.service.QxyjxxService;
import com.znv.manage.service.WaterPointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WaterPointsServiceImpl implements WaterPointsService {

    @Autowired
    WaterPointsDao waterPointsDao;

    @Override
    public Result getWaterPoints(String beginTime, String endTime,String precinctId,String precinctName) {
        Result result = new Result();
        List<Map<String, Object>> list = waterPointsDao.getWaterPoints(beginTime, endTime,precinctId,precinctName);
        result.setData(list);
        return result;
    }

    @Override
    public Result updateWaterPointsStreet() {
        Result result = new Result();
        List<Map<String, Object>> list = waterPointsDao.getWaterPoints(null, null,null,null);
        for(Map map : list){
           String id = map.get("id").toString();
           String lon = map.get("lon").toString();
           String lat = map.get("lat").toString();
           Map<String,String> data = GeometryUtil.getPrecinctIdByGps(lon,lat);
           String street = data.get("precinctId");
           if(!StringUtils.isEmpty("street")){
               waterPointsDao.updateWaterPointsStreet(id,street);
           }
        }
        return result;
    }

}
