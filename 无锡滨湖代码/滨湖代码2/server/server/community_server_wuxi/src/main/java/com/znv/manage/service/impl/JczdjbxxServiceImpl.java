package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.Jczdjbxx;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.GeometryUtil;
import com.znv.manage.dao.JczdjbxxDao;
import com.znv.manage.service.JczdjbxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
public class JczdjbxxServiceImpl implements JczdjbxxService {

    @Autowired
    JczdjbxxDao jczdjbxxDao;


    @Value("${url}")
    private String IP;
    private static String url = "/service/api/swj1/st_stbprp_b";

    @Override
    public int getData() {
        Map<String, String> map = new HashMap<>();
        int i = 0;
        try {
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info("getData object : " + object);
            String returnJson = String.valueOf(object);
            List<Jczdjbxx> list = JSON.parseArray(returnJson, Jczdjbxx.class);
            for (Jczdjbxx j : list) {
                Map<String, String> precinctIdByGps = GeometryUtil.getPrecinctIdByGps(j.getLgtd().toString(), j.getLttd().toString());
                j.setStreet(precinctIdByGps.get("precinctId"));
            }
            i += insertJczdjbxxList(list);
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }


    public int insertJczdjbxxList(List<Jczdjbxx> list) {
        int i = 0;
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertJczdjbxxList list.size为0");
            } else {
                i += jczdjbxxDao.insertJczdjbxxList(list);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    @Override
    public List<Map<String, Object>> selectWater(String precinctId, String precinctName) {
        return jczdjbxxDao.selectWater(precinctId,precinctName);
    }

    @Override
    public List<Map<String, Object>> selectRainFall(String precinctId, String precinctName) {
        return jczdjbxxDao.selectRainFall(precinctId,precinctName);
    }


}
