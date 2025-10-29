package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.ZdzskjcsjBean;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.dao.ZdzskjcsjDao;
import com.znv.manage.service.ZdzskjcsjService;
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
public class ZdzskjcsjServiceImpl implements ZdzskjcsjService {

    @Autowired
    ZdzskjcsjDao zdzskjcsjDao;

    @Value("${url}")
    private String IP;
    private static String url = "/service/api/lishui-app/rest/v1/index/getIndexLiveByLonLat";

    @Override
    public int getData() {
        Map<String, String> map = new HashMap<>();
        int i = 0;
        try {
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info("getData /service/api/lishui-app/rest/v1/index/getIndexLiveByLonLat object : " + object);
            String returnJson = String.valueOf(object);
            JSONArray jsonArray = JSONArray.parseArray(returnJson);
            List<JSONObject> dataArr = jsonArray.toJavaList(JSONObject.class);
            i += insertZdzskjcsjList(dataArr);
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }


    public int insertZdzskjcsjList(List<JSONObject> list) {
        int i = 0;
        List<ZdzskjcsjBean> insertZdzskjcsj = new ArrayList();
        List<ZdzskjcsjBean> updateZdzskjcsj = new ArrayList();
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertZdzskjcsjList list.size为0");
            } else {
                zdzskjcsjDao.deleteZdzskjcsjList();
                for (JSONObject jsonObject : list) {
                    ZdzskjcsjBean zdzskjcsjBean = new ZdzskjcsjBean();
                    zdzskjcsjBean.setVoice(jsonObject.getString("voice"));
                    zdzskjcsjBean.setVis(jsonObject.getString("vis"));
                    zdzskjcsjBean.setHourr(jsonObject.getString("hourr"));
                    zdzskjcsjBean.setBg(jsonObject.getString("bg"));
                    zdzskjcsjBean.setWarmList(jsonObject.getString("warmList"));
                    zdzskjcsjBean.setUpdateTime(jsonObject.getString("updateTime"));
                    zdzskjcsjBean.setNext3day(jsonObject.getString("next3day"));
                    zdzskjcsjBean.setWd(jsonObject.getString("wd"));
                    zdzskjcsjBean.setFireLlevel(jsonObject.getString("fireLlevel"));
                    zdzskjcsjBean.setT(jsonObject.getString("t"));
                    zdzskjcsjBean.setObtid(jsonObject.getString("obtid"));
                    zdzskjcsjBean.setHumidity(jsonObject.getString("humidity"));
                    zdzskjcsjBean.setStationName(jsonObject.getString("stationName"));
                    zdzskjcsjBean.setHour24r(jsonObject.getString("hour24r"));
                    zdzskjcsjBean.setCountryName(jsonObject.getString("countryName"));
                    zdzskjcsjBean.setWs(jsonObject.getString("ws"));
                    insertZdzskjcsj.add(zdzskjcsjBean);

//                    if (updateFlag(ZdzskjcsjBean.getSJWXHXPMC(), ZdzskjcsjBean.getRKSJ())) {
//                        log.info("insertZdzskjcsjList SJWXHXPMC 已存在:" + jsonObject.toJSONString());
//                        updateZdzskjcsj.add(ZdzskjcsjBean);
//                    } else {
//                        log.info("insertZdzskjcsjList SJWXHXPMC 不存在:" + jsonObject.toJSONString());
//                        insertZdzskjcsj.add(ZdzskjcsjBean);
//                    }
                }
            }
            if (insertZdzskjcsj.size() != 0) {
                i += zdzskjcsjDao.insertZdzskjcsjList(insertZdzskjcsj);
            }
//            if (updateZdzskjcsj.size() != 0) {
//                try {
//                    i += ZdzskjcsjDao.updateZdzskjcsjList(updateZdzskjcsj);
//                } catch (Exception e) {
//                    log.error("updateZdzskjcsjList error " + updateZdzskjcsj.toString() + " exception : " + e.getMessage());
//                }
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    public boolean updateFlag(String SJWXHXPMC, String RKSJ) {
        boolean flag = false;
        int count = zdzskjcsjDao.queryZdzskjcsjList(SJWXHXPMC, RKSJ);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

}
