package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.MzJzGh;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.Sw;
import com.znv.manage.common.bean.Ywgd;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.MzJzGhDao;
import com.znv.manage.dao.SwDao;
import com.znv.manage.service.MzJzGhService;
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
public class MzJzGhServiceImpl implements MzJzGhService {

    @Autowired
    MzJzGhDao mzJzGhDao;


    @Value("${url}")
    private String IP;
    private static String url = "/service/api/wjw/registerrecord";

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
                List<MzJzGh> list = JSON.parseArray(returnJson, MzJzGh.class);
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    if (pageNumber == 1) {
                        mzJzGhDao.delAll();
                    }
                    i += insertMzJzGhList(list);
                } else {
                    log.info("insertMzJzGhList list.size为0");
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


    public int insertMzJzGhList(List<MzJzGh> list) {
        int i = 0;
        try {

            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertMzJzGhList list.size为0");
            } else {
                i += mzJzGhDao.insertMzJzGhList(list);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    @Override
    public Result getOutpatientEmergencyCount(Date beginTime, Date endTime,String precinctId, String precinctName) {

        Result result = new Result();
        try {
            Integer count = mzJzGhDao.getOutpatientEmergencyCount(beginTime, endTime,precinctId,precinctName);
            Map<String, Object> map = new HashMap<>();
            map.put("count", count);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

}
