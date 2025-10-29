package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Jczdjbxx;
import com.znv.manage.common.bean.Jjsw;
import com.znv.manage.common.bean.Wgyxx;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.dao.JczdjbxxDao;
import com.znv.manage.dao.WgyxxDao;
import com.znv.manage.service.JczdjbxxService;
import com.znv.manage.service.WgyxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class WgyxxServiceImpl implements WgyxxService {

    @Autowired
    WgyxxDao wgyxxDao;


    @Value("${url}")
    private String IP;
    private static String url = "/service/api/zzdld/wgyxx";

    @Override
    public int getData() {
        Map<String, String> map = new HashMap<>();
        int i = 0;
        try {
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info("getData object : " + object);
            String returnJson = String.valueOf(object);
            List<Wgyxx> list = JSON.parseArray(returnJson, Wgyxx.class);
            i += insertWgyxxList(list);
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }


    public int insertWgyxxList(List<Wgyxx> list) {
        int i = 0;
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertWgyxxList list.size为0");
            } else {
                i += wgyxxDao.insertWgyxxList(list);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

}
