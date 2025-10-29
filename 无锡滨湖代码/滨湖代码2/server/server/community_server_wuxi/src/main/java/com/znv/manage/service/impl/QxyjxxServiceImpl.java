package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.QxyjxxBean;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.QxyjxxDao;
import com.znv.manage.service.QxyjxxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
public class QxyjxxServiceImpl implements QxyjxxService {

    @Autowired
    QxyjxxDao qxyjxxDao;

    @Value("${url}")
    private String IP;
    private static String url = "/service/api/qxj/qxyjxx";

    @Override
    public int getData() {
        Map<String, String> map = new HashMap<>();
        map.put("start_time", DateUtil.getStringDateShort());
        int i = 0;
        try {
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info("getData /service/api/qxj/qxyjxx object : " + object);
            String returnJson = String.valueOf(object);
            JSONArray jsonArray = JSONArray.parseArray(returnJson);
            List<JSONObject> dataArr = jsonArray.toJavaList(JSONObject.class);
            i += insertQxyjxxList(dataArr);
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }

    @Override
    public Result getQxyjxx(String beginTime, String endTime) {
        Result result = new Result();
        List<Map<String, Object>> list = qxyjxxDao.queryQxyjxxList(beginTime, endTime);
        Integer count = qxyjxxDao.queryQxyjxxCount(beginTime, endTime);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        result.setData(map);
        return result;
    }


    public int insertQxyjxxList(List<JSONObject> list) {
        int i = 0;
        List<QxyjxxBean> insertQxyjxx = new ArrayList();
        List<QxyjxxBean> updateQxyjxx = new ArrayList();
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertQxyjxxList list.size为0");
            } else {
                qxyjxxDao.deleteQxyjxxList();
                for (JSONObject jsonObject : list) {
                    QxyjxxBean qxyjxxBean = new QxyjxxBean();
                    qxyjxxBean.setMODELCONTENT(jsonObject.getString("MODELCONTENT"));
                    qxyjxxBean.setDEFENSE(jsonObject.getString("DEFENSE"));
                    qxyjxxBean.setDISASTERID(jsonObject.getString("DISASTERID"));
                    qxyjxxBean.setIMG(jsonObject.getString("IMG"));
                    qxyjxxBean.setCREATE_TIME(jsonObject.getString("CREATE_TIME"));
                    qxyjxxBean.setALARMCODE(jsonObject.getString("ALARMCODE"));
                    qxyjxxBean.setDISASTERNAME(jsonObject.getString("DISASTERNAME"));
                    qxyjxxBean.setTASKCODE(jsonObject.getString("TASKCODE"));
                    qxyjxxBean.setORGNAME(jsonObject.getString("ORGNAME"));
                    qxyjxxBean.setENTRYTIME(jsonObject.getString("ENTRYTIME"));
                    qxyjxxBean.setISEFFECT(jsonObject.getString("ISEFFECT"));
                    qxyjxxBean.setCOLORLEVELNAME(jsonObject.getString("COLORLEVELNAME"));
                    qxyjxxBean.setISSUE(jsonObject.getString("ISSUE"));
                    qxyjxxBean.setPROCESS(jsonObject.getString("PROCESS"));
                    qxyjxxBean.setAREACODES(jsonObject.getString("AREACODES"));
                    qxyjxxBean.setSENDUESER(jsonObject.getString("SENDUESER"));
                    qxyjxxBean.setAREANAMES(jsonObject.getString("AREANAMES"));
                    insertQxyjxx.add(qxyjxxBean);

//                    if (updateFlag(QxyjxxBean.getSJWXHXPMC(), QxyjxxBean.getRKSJ())) {
//                        log.info("insertQxyjxxList SJWXHXPMC 已存在:" + jsonObject.toJSONString());
//                        updateQxyjxx.add(qxyjxxBean);
//                    } else {
//                        log.info("insertQxyjxxList SJWXHXPMC 不存在:" + jsonObject.toJSONString());
//                        insertQxyjxx.add(QxyjxxBean);
//                    }
                }
            }
            if (insertQxyjxx.size() != 0) {
                i += qxyjxxDao.insertQxyjxxList(insertQxyjxx);
            }
//            if (updateQxyjxx.size() != 0) {
//                try {
//                    i += QxyjxxDao.updateQxyjxxList(updateQxyjxx);
//                } catch (Exception e) {
//                    log.error("updateQxyjxxList error " + updateQxyjxx.toString() + " exception : " + e.getMessage());
//                }
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

//    public boolean updateFlag(String SJWXHXPMC, String RKSJ) {
//        boolean flag = false;
//        int count = QxyjxxDao.queryQxyjxxList(SJWXHXPMC, RKSJ);
//        if (count > 0) {
//            flag = true;
//        }
//        return flag;
//    }

}
