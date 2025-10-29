package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.WeatherForecastBean;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.WeatherForecastDao;
import com.znv.manage.service.WeatherForecastService;
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
public class WeatherForecastServiceImpl implements WeatherForecastService {

    @Autowired
    WeatherForecastDao weatherForecastDao;

    @Value("${url}")
    private String IP;
    private static String url = "/service/api/lishui-app/rest/v1/index/forecast24Hour";

    @Override
    public int getData() {
        Map<String, String> map = new HashMap<>();
        int i = 0;
        try {
            String newUrl = IP + url;
            Object object = AccessUtil.get(newUrl, map);
            log.info("getData /service/api/lishui-app/rest/v1/index/forecast24Hour object : " + object);
            String returnJson = String.valueOf(object);
            JSONArray jsonArray = JSONArray.parseArray(returnJson);
            List<JSONObject> dataArr = jsonArray.toJavaList(JSONObject.class);
            i += insertWeatherForecastList(dataArr);
        } catch (Exception e) {
            log.error("getData object error ： " + e.getMessage());
        }
        return i;
    }


    public int insertWeatherForecastList(List<JSONObject> list) {
        int i = 0;
        List<WeatherForecastBean> insertWeatherForecast = new ArrayList();
        try {
            if ((CollectionUtils.isEmpty(list)) || (list.size() == 0)) {
                log.info("insertWeatherForecastList list.size为0");
            } else {
                weatherForecastDao.deleteWeatherForecastList();
                for (JSONObject jsonObject : list) {
                    WeatherForecastBean weatherForecastBean = new WeatherForecastBean();
                    weatherForecastBean.setT(jsonObject.getString("t"));
                    weatherForecastBean.setUpdateTime(jsonObject.getString("updateTime"));
                    weatherForecastBean.setWd(jsonObject.getString("wd"));
                    weatherForecastBean.setWeatherCode(jsonObject.getString("weatherCode"));
                    weatherForecastBean.setWeatherName(jsonObject.getString("weatherName"));
                    weatherForecastBean.setWs(jsonObject.getString("ws"));
                    insertWeatherForecast.add(weatherForecastBean);

//                    if (updateFlag(WeatherForecastBean.getSJWXHXPMC(), WeatherForecastBean.getRKSJ())) {
//                        log.info("insertWeatherForecastList SJWXHXPMC 已存在:" + jsonObject.toJSONString());
//                        updateWeatherForecast.add(WeatherForecastBean);
//                    } else {
//                        log.info("insertWeatherForecastList SJWXHXPMC 不存在:" + jsonObject.toJSONString());
//                        insertWeatherForecast.add(WeatherForecastBean);
//                    }
                }
            }
            if (insertWeatherForecast.size() != 0) {
                i += weatherForecastDao.insertWeatherForecastList(insertWeatherForecast);
            }
//            if (updateWeatherForecast.size() != 0) {
//                try {
//                    i += WeatherForecastDao.updateWeatherForecastList(updateWeatherForecast);
//                } catch (Exception e) {
//                    log.error("updateWeatherForecastList error " + updateWeatherForecast.toString() + " exception : " + e.getMessage());
//                }
//            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    public boolean updateFlag(String SJWXHXPMC, String RKSJ) {
        boolean flag = false;
        int count = weatherForecastDao.queryWeatherForecastList(SJWXHXPMC, RKSJ);
        if (count > 0) {
            flag = true;
        }
        return flag;
    }

}
