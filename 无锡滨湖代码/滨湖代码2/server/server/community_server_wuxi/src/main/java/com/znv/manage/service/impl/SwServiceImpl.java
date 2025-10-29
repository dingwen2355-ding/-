package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Jjsw;
import com.znv.manage.common.bean.Sw;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.SwDao;
import com.znv.manage.service.SwService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class SwServiceImpl implements SwService {

    @Autowired
    SwDao swDao;


    @Value("${url}")
    private String IP;
    private static String url = "/service/api/swj/st_river_r_hi";

    @Override
    public int getData() {
        int pageNumber = 0;
        int pageSize = 100;
        int i = 0;
        boolean flag = true;
        try {
            do {
                pageNumber += 1;
                Map<String, String> map = new HashMap<>();
                map.put("pageSize", Integer.toString(pageSize));
                map.put("pageNumber", Integer.toString(pageNumber));
                map.put("startdate", DateUtil.getStringDateShort());
                String newUrl = IP + url;
                Object object = AccessUtil.get(newUrl, map);
                log.info("getData object : " + object);
                String returnJson = String.valueOf(object);
                List<Sw> list = JSON.parseArray(returnJson, Sw.class);
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    if (pageNumber == 1) {
                        swDao.delAll();
                    }
                    i += insertSwList(list);
                } else {
                    log.info("insertSwList list.size为0");
                }
                if (list.size() < pageSize) {
                    flag = false;
                }
            } while (flag);
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }


    public int insertSwList(List<Sw> list) {
        int i = 0;
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertSwList list.size为0");
            } else {
                i += swDao.insertSwList(list);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    @Override
    public List<Map<String, Object>> selectHistoryWaterAndRainList(String stcd, Date beginTime, Date endTime, Integer limit) {
        return swDao.selectHistoryWaterAndRainList(stcd, beginTime, endTime, limit);
    }

    @Override
    public List<Map<String, Object>> selectRealWaterAndRainList(String stcd) {
        return swDao.selectRealWaterAndRainList(stcd);
    }
}
