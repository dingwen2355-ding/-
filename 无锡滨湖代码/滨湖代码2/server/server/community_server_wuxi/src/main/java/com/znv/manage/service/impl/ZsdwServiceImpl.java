package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Jdjl;
import com.znv.manage.common.bean.Zsdw;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.common.utils.GeometryUtil;
import com.znv.manage.dao.ZsdwDao;
import com.znv.manage.service.ZsdwService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;


import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class ZsdwServiceImpl implements ZsdwService {

    @Autowired
    ZsdwDao zsdwDao;


    @Value("${url}")
    private String IP;
    private static String url = "/service/api/sthjj/ecology_site_voice";

    @Override
    public int getData() {
        JSONArray array = new JSONArray();
        int i = 0;
        try {
            Map<String, String> map = new HashMap<>();
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info("getData object : " + object);
            String returnJson = String.valueOf(object);
            List<Zsdw> list = JSON.parseArray(returnJson, Zsdw.class);
            for (Zsdw z : list) {
                Map<String, String> precinctIdByGps = GeometryUtil.getPrecinctIdByGps(z.getLng().toString(), z.getLat().toString());
                z.setStreet(precinctIdByGps.get("precinctId"));
            }
            i += insertZsdwList(list);
        } catch (Exception e) {
            log.error("getData result error ： " + e.getMessage());
        }
        return i;
    }


    public int insertZsdwList(List<Zsdw> list) {
        int i = 0;
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertZsdwList list.size为0");
            } else {
                i += zsdwDao.insertZsdwList(list);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

}
