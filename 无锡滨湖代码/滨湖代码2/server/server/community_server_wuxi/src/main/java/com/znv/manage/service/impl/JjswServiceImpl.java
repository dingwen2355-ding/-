package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Jczdjbxx;
import com.znv.manage.common.bean.Jjsw;
import com.znv.manage.common.bean.Ywgd;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.dao.JczdjbxxDao;
import com.znv.manage.dao.JjswDao;
import com.znv.manage.service.JczdjbxxService;
import com.znv.manage.service.JjswService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class JjswServiceImpl implements JjswService {

    @Autowired
    JjswDao jjswDao;


    @Value("${url}")
    private String IP;
    private static String url = "/service/api/swj/st_rvfcch_b";

    @Override
    public int getData() {
        int pageNumber = 0;
        int pageSize = 100;
        int i = 0;
        boolean flag = true;
        try {
            do {
                Map<String, String> map = new HashMap<>();
                pageNumber += 1;
                map.put("pageSize", Integer.toString(pageSize));
                map.put("pageNumber", Integer.toString(pageNumber));
                String newUrl = IP + url;
                Object object = AccessUtil.get(newUrl, map);
                log.info("getData object : " + object);
                String returnJson = String.valueOf(object);
                List<Jjsw> list = JSON.parseArray(returnJson, Jjsw.class);
                i += insertJjswList(list);
                if (list.size() < pageSize) {
                    flag = false;
                }
            } while (flag);
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }


    public int insertJjswList(List<Jjsw> list) {
        int i = 0;
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertJjswList list.size为0");
            } else {
                i += jjswDao.insertJjswList(list);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

}
