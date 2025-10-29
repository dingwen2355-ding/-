package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.Jdjl;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.JdjlGhDao;
import com.znv.manage.service.JdjlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
public class JdjlServiceImpl implements JdjlService {

    @Autowired
    JdjlGhDao jdjlGhDao;


    @Value("${url}")
    private String IP;
    private static String url = "/service/api/xfj/t_reception_list";

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
                List<Jdjl> list = JSON.parseArray(returnJson, Jdjl.class);
                i += insertJdjlList(list);
                if (list.size() < pageSize) {
                    flag = false;
                }
            } while (flag);
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }


    public int insertJdjlList(List<Jdjl> list) {
        int i = 0;
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertJdjlList list.size为0");
            } else {
                i += jdjlGhDao.insertJdjlList(list);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    @Override
    public int getLettersAndVisitsCount(Date beginTime, Date endTime) {
        return jdjlGhDao.getLettersAndVisitsCount(beginTime,endTime);
    }
}
