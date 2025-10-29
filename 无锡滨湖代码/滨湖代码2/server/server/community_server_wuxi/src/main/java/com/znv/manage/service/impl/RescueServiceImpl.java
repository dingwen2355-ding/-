package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.dao.RescueMapper;
import com.znv.manage.service.RescueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RescueServiceImpl implements RescueService {

    @Autowired
    RescueMapper rescueMapper;


    @Override
    public List<Map<String, String>> getRescueTeam() {
        List<Map<String, String>> list = rescueMapper.getRescueTeam();
        return list;
    }

    @Override
    public List<Map<String, String>> getRescueDevice() {
        List<Map<String, String>> list = rescueMapper.getRescueDevice();
        return list;
    }
}
