package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.WhpjyxxBean;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.dao.WhpjyxxDao;
import com.znv.manage.service.WhpjyxxService;
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
public class WhpjyxxServiceImpl implements WhpjyxxService {

    @Autowired
    WhpjyxxDao whpjyxxDao;

    @Value("${url}")
    private String IP;
    private static String url = "/service/api/yjglj/xls_whpjyxx";

    @Override
    public int getData() {
        Map<String, String> map = new HashMap<>();
        int i = 0;
        try {
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info("getData /service/api/yjglj/xls_whpjyxx object : " + object);
            String returnJson = String.valueOf(object);
            JSONArray jsonArray = JSONArray.parseArray(returnJson);
            List<JSONObject> dataArr = jsonArray.toJavaList(JSONObject.class);
            i += insertWhpjyxxList(dataArr);
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }

    @Override
    public Result getWhpjyxx(String beginTime, String endTime,Integer limit) {
        Result result = new Result();
        List<Map<String, Object>> list = whpjyxxDao.queryWhpjyxxList(beginTime, endTime,limit);
        Integer count = whpjyxxDao.queryWhpjyxxCount(beginTime, endTime,limit);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        result.setData(map);
        return result;
    }


    public int insertWhpjyxxList(List<JSONObject> list) {
        int i = 0;
        List<WhpjyxxBean> insertWhpjyxx = new ArrayList();
        List<WhpjyxxBean> updateWhpjyxx = new ArrayList();
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertWhpjyxxList list.size为0");
            } else {
                whpjyxxDao.deleteWhpjyxxList();
                for (JSONObject jsonObject : list) {
                    WhpjyxxBean whpjyxxBean = new WhpjyxxBean();
                    whpjyxxBean.setZYFZR(jsonObject.getString("ZYFZR"));
                    whpjyxxBean.setCCCSDZ(jsonObject.getString("CCCSDZ"));
                    whpjyxxBean.setYXQQSR(jsonObject.getString("YXQQSR"));
                    whpjyxxBean.setYZBM(jsonObject.getString("YZBM"));
                    whpjyxxBean.setDWMC(jsonObject.getString("DWMC"));
                    whpjyxxBean.setCCCSSZJD(jsonObject.getString("CCCSSZJD"));
                    whpjyxxBean.setJYCSSZJD(jsonObject.getString("JYCSSZJD"));
                    whpjyxxBean.setGXRQ(jsonObject.getString("GXRQ"));
                    whpjyxxBean.setQYLX(jsonObject.getString("QYLX"));
                    whpjyxxBean.setZSBH(jsonObject.getString("ZSBH"));
                    whpjyxxBean.setZCDZ(jsonObject.getString("ZCDZ"));
                    whpjyxxBean.setCCCSZDMJ(jsonObject.getString("CCCSZDMJ"));
                    whpjyxxBean.setJYCSXCSFYWXHXPCC(jsonObject.getString("JYCSXCSFYWXHXPCC"));
                    whpjyxxBean.setJYCSDZ(jsonObject.getString("JYCSDZ"));
                    whpjyxxBean.setZSZT(jsonObject.getString("ZSZT"));
                    whpjyxxBean.setCCCSCQ(jsonObject.getString("CCCSCQ"));
                    whpjyxxBean.setSFGCZDWXY(jsonObject.getString("SFGCZDWXY"));
                    whpjyxxBean.setJYCSSZDS(jsonObject.getString("JYCSSZDS"));
                    whpjyxxBean.setZCDZSZQX(jsonObject.getString("ZCDZSZQX"));
                    whpjyxxBean.setYXQZZR(jsonObject.getString("YXQZZR"));
                    whpjyxxBean.setJYCSCQ(jsonObject.getString("JYCSCQ"));
                    whpjyxxBean.setJYCSSZQX(jsonObject.getString("JYCSSZQX"));
                    whpjyxxBean.setCCCSSZQX(jsonObject.getString("CCCSSZQX"));
                    whpjyxxBean.setZCDZSZDS(jsonObject.getString("ZCDZSZDS"));
                    whpjyxxBean.setYCCJYLX(jsonObject.getString("YCCJYLX"));
                    whpjyxxBean.setYYZZH(jsonObject.getString("YYZZH"));
                    whpjyxxBean.setCPYJYLX(jsonObject.getString("CPYJYLX"));
                    whpjyxxBean.setCYRYS(jsonObject.getString("CYRYS"));
                    whpjyxxBean.setZCDZSZJD(jsonObject.getString("ZCDZSZJD"));
                    whpjyxxBean.setFZJG(jsonObject.getString("FZJG"));
                    whpjyxxBean.setFZRQ(jsonObject.getString("FZRQ"));
                    whpjyxxBean.setLXDH(jsonObject.getString("LXDH"));
                    whpjyxxBean.setCCCSSZQY(jsonObject.getString("CCCSSZQY"));
                    whpjyxxBean.setRKSJ(jsonObject.getString("RKSJ"));
                    whpjyxxBean.setAQGLRYSL(jsonObject.getString("AQGLRYSL"));
                    whpjyxxBean.setWCCJYLX(jsonObject.getString("WCCJYLX"));
                    whpjyxxBean.setJYLX(jsonObject.getString("JYLX"));
                    whpjyxxBean.setCCCSSZDS(jsonObject.getString("CCCSSZDS"));
                    whpjyxxBean.setDWDM(jsonObject.getString("DWDM"));
                    whpjyxxBean.setXKJYFW(jsonObject.getString("XKJYFW"));
                    insertWhpjyxx.add(whpjyxxBean);
//                    if (updateFlag(WhpjyxxBean.getSJWXHXPMC(), WhpjyxxBean.getRKSJ())) {
//                        log.info("insertWhpjyxxList SJWXHXPMC 已存在:" + jsonObject.toJSONString());
//                        updateWhpjyxx.add(whpjyxxBean);
//                    } else {
//                        log.info("insertWhpjyxxList SJWXHXPMC 不存在:" + jsonObject.toJSONString());
//                        insertWhpjyxx.add(whpjyxxBean);
//                    }
                }
            }
            if (insertWhpjyxx.size() != 0) {
                i += whpjyxxDao.insertWhpjyxxList(insertWhpjyxx);
            }
//            if (updateWhpjyxx.size() != 0) {
//                try {
//                    i += whpjyxxDao.updateWhpjyxxList(updateWhpjyxx);
//                } catch (Exception e) {
//                    log.error("updateWhpjyxxList error " + updateWhpjyxx.toString() + " exception : " + e.getMessage());
//                }
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }
//
//    public boolean updateFlag(String SJWXHXPMC, String RKSJ) {
//        boolean flag = false;
//        int count = whpjyxxDao.queryWhpjyxxList(SJWXHXPMC, RKSJ);
//        if (count > 0) {
//            flag = true;
//        }
//        return flag;
//    }

}
