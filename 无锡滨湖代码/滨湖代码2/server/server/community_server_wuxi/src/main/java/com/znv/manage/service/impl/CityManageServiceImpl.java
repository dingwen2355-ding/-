package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Device;
import com.znv.manage.dao.CityManageMapper;
import com.znv.manage.dao.DeviceMapper;
import com.znv.manage.service.CityManageService;
import com.znv.manage.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CityManageServiceImpl implements CityManageService {

    @Autowired
    CityManageMapper cityManageMapper;


    @Override
    public JSONArray getCityManageCategory() {
        JSONArray jsonArray = new JSONArray();
        List<Map<String, String>> largeTypes = cityManageMapper.getCityManageCategory();
        for (int i = 0; i < largeTypes.size(); i++) {
            Map<String, String> largeTypeMap = largeTypes.get(i);
            String largeType = largeTypeMap.get("largeType");
            String largeTypeIcon = largeTypeMap.get("largeTypeIcon");
            List<Map<String, String>> detail = cityManageMapper.getCityManageDetail(largeType);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("largeType", largeType);
            jsonObject.put("largeTypeIcon", largeTypeIcon);
            jsonObject.put("detail", detail);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
