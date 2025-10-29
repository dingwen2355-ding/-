package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Jqsskll;
import com.znv.manage.common.bean.Ywgd;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.JqsskllDao;
import com.znv.manage.dao.YwgdDao;
import com.znv.manage.service.JqsskllService;
import com.znv.manage.service.YwgdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class JqsskllServiceImpl implements JqsskllService {

    @Autowired
    JqsskllDao jqsskllDao;


    @Value("${url}")
    private String IP;
    private static String url = "/service/api/wlj/t_scenic_now_passenger";

    @Override
    public int getData() {
        int i = 0;
        try {
            Map<String, String> map = new HashMap<>();
            map.put("startdate", DateUtil.getStringDateShort());
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info("getData object : " + object);
            String returnJson = String.valueOf(object);
            List<Jqsskll> list = JSON.parseArray(returnJson, Jqsskll.class);
            i += insertJqsskllList(list);
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }


    public int insertJqsskllList(List<Jqsskll> list) {
        int i = 0;
        try {
            jqsskllDao.delAll();
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertJqsskllList list.size为0");
            } else {
                i += jqsskllDao.insertJqsskllList(list);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

}
