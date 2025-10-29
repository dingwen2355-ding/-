package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Jjsw;
import com.znv.manage.common.bean.Wgygjxx;
import com.znv.manage.common.bean.Wgyxx;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.JjswDao;
import com.znv.manage.dao.WgygjxxDao;
import com.znv.manage.service.JjswService;
import com.znv.manage.service.WgygjxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class WgygjxxServiceImpl implements WgygjxxService {

    @Autowired
    WgygjxxDao wgygjxxDao;


    @Value("${url}")
    private String IP;
    private static String url = "/service/api/zhb/tb_gps_log";

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
                List<Wgygjxx> list = JSON.parseArray(returnJson, Wgygjxx.class);
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    if (pageNumber == 1) {
                        wgygjxxDao.delAll();
                    }
                    i += insertWgygjxxList(list);
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


    public int insertWgygjxxList(List<Wgygjxx> list) {
        int i = 0;
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertWgygjxxList list.size为0");
            } else {
                i += wgygjxxDao.insertWgygjxxList(list);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }


}
