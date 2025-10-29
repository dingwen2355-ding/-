package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.KqzlzsBean;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.dao.KqzlzsDao;
import com.znv.manage.service.KqzlzsService;
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
public class KqzlzsServiceImpl implements KqzlzsService {

    @Autowired
    KqzlzsDao kqzlzsDao;

    @Value("${url}")
    private String IP;
    private static String url = "/service/api/lishui-app/rest/v1/index/getIndexAqi";

    @Override
    public int getData() {
        Map<String, String> map = new HashMap<>();
        int i = 0;
        try {
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info("getData /service/api/lishui-app/rest/v1/index/getIndexAqi object : " + object);
            String returnJson = String.valueOf(object);
            JSONArray jsonArray = JSONArray.parseArray(returnJson);
            List<JSONObject> dataArr = jsonArray.toJavaList(JSONObject.class);
            i += insertKqzlzsList(dataArr);
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }


    public int insertKqzlzsList(List<JSONObject> list) {
        int i = 0;
        List<KqzlzsBean> insertKqzlzs = new ArrayList();
        List<KqzlzsBean> updateKqzlzs = new ArrayList();
        try {
            String maxTaskId;
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertKqzlzsList list.size为0");
            } else {
                kqzlzsDao.deleteKqzlzsList();
                for (JSONObject jsonObject : list) {
                    KqzlzsBean kqzlzsBean = new KqzlzsBean();
                    kqzlzsBean.setAqi(jsonObject.getString("aqi"));
                    kqzlzsBean.setAirLevel(jsonObject.getString("airLevel"));
                    kqzlzsBean.setAirKind(jsonObject.getString("airKind"));
                    kqzlzsBean.setStationName(jsonObject.getString("stationName"));
                    insertKqzlzs.add(kqzlzsBean);

//                    if (updateFlag(KqzlzsBean.getSJWXHXPMC(), KqzlzsBean.getRKSJ())) {
//                        log.info("insertKqzlzsList SJWXHXPMC 已存在:" + jsonObject.toJSONString());
//                        updateKqzlzs.add(KqzlzsBean);
//                    } else {
//                        log.info("insertKqzlzsList SJWXHXPMC 不存在:" + jsonObject.toJSONString());
//                        insertKqzlzs.add(KqzlzsBean);
//                    }
                }
            }
            if (insertKqzlzs.size() != 0) {
                i += kqzlzsDao.insertKqzlzsList(insertKqzlzs);
            }
//            if (updateKqzlzs.size() != 0) {
//                try {
//                    i += KqzlzsDao.updateKqzlzsList(updateKqzlzs);
//                } catch (Exception e) {
//                    log.error("updateKqzlzsList error " + updateKqzlzs.toString() + " exception : " + e.getMessage());
//                }
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    public boolean updateFlag(String SJWXHXPMC, String RKSJ) {
        boolean flag = false;
        int count = kqzlzsDao.queryKqzlzsList();
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

}
