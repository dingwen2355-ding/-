package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.ZdwxyBean;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.ZdwxyDao;
import com.znv.manage.service.ZdwxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ZdwxyServiceImpl implements ZdwxyService {

    @Autowired
    ZdwxyDao zdwxyDao;

    @Value("${url}")
    private String IP;
    private static String url = "/service/api/yjglj/xls_zdwxy";

    @Override
    public int getData() {
        Map<String, String> map = new HashMap<>();
        int i = 0;
        try {
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info("getData /service/api/yjglj/xls_zdwxy object : " + object);
            String returnJson = String.valueOf(object);
            JSONArray jsonArray = JSONArray.parseArray(returnJson);
            List<JSONObject> dataArr = jsonArray.toJavaList(JSONObject.class);
            i += insertZdwxyList(dataArr);
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }


    public int insertZdwxyList(List<JSONObject> list) {
        int i = 0;
        List<ZdwxyBean> insertZdwxy = new ArrayList();
        List<ZdwxyBean> updateZdwxy = new ArrayList();
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertZdwxyList list.size为0");
            } else {
                zdwxyDao.deleteZdwxyList();
                for (JSONObject jsonObject : list) {
                    ZdwxyBean zdwxyBean = new ZdwxyBean();
                    zdwxyBean.setZDWXYDJSL(jsonObject.getString("ZDWXYDJSL"));
                    zdwxyBean.setSJWXHXPMC(jsonObject.getString("SJWXHXPMC"));
                    zdwxyBean.setGXRQ(jsonObject.getString("GXRQ"));
                    zdwxyBean.setAQSCJDZRBM(jsonObject.getString("AQSCJDZRBM"));
                    zdwxyBean.setRKSJ(jsonObject.getString("RKSJ"));
                    zdwxyBean.setQYMC(jsonObject.getString("QYMC"));
                    zdwxyBean.setFXDJ(jsonObject.getString("FXDJ"));
                    insertZdwxy.add(zdwxyBean);

//                    if (updateFlag(zdwxyBean.getSJWXHXPMC(), zdwxyBean.getRKSJ())) {
//                        log.info("insertZdwxyList SJWXHXPMC 已存在:" + jsonObject.toJSONString());
//                        updateZdwxy.add(zdwxyBean);
//                    } else {
//                        log.info("insertZdwxyList SJWXHXPMC 不存在:" + jsonObject.toJSONString());
//                        insertZdwxy.add(zdwxyBean);
//                    }
                }
            }
            if (insertZdwxy.size() != 0) {
                i += zdwxyDao.insertZdwxyList(insertZdwxy);
            }
//            if (updateZdwxy.size() != 0) {
//                try {
//                    i += zdwxyDao.updateZdwxyList(updateZdwxy);
//                } catch (Exception e) {
//                    log.error("updateZdwxyList error " + updateZdwxy.toString() + " exception : " + e.getMessage());
//                }
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

//    public boolean updateFlag(String SJWXHXPMC, String RKSJ) {
//        boolean flag = false;
//        int count = zdwxyDao.queryZdwxyList(SJWXHXPMC, RKSJ);
//        if (count > 0) {
//            flag = true;
//        }
//        return flag;
//    }

}
