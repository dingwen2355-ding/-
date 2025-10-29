package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.znv.manage.common.bean.Dqhjxssjb;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.AirQualityDao;
import com.znv.manage.service.AirQualityService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: AirQualityServiceImpl
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/7/8 11:06
 **/
@Service
@Slf4j
public class AirQualityServiceImpl implements AirQualityService {

    private static Logger logger = LoggerFactory.getLogger(AirQualityServiceImpl.class);

    @Autowired
    private AirQualityDao airQualityDao;

    @Override
    public int getData() {
            //设置请求参数
            int pageNumber = 0;
            int pageSize = 900;
            int i = 0;
            boolean flag = true;
            try {
                do {
                    pageNumber += 1;
                    HashMap<String, String> params = new HashMap<>();
                    params.put("startdate", DateUtil.getStringDateShort());
//                    params.put("startdate", " ");
                    params.put("pageSize",Integer.toString(pageSize));
                    params.put("pageNumber",Integer.toString(pageNumber));
                    String url = "http://10.214.111.238:9090/service/api/sthjj/source_air_data_hour";
                    Object result = AccessUtil.get(url, params);
//                  Object result ="";
//                  log.info("大气环境小时数据接口返回："+result);
                    //返回结果为unicode编码，需要特殊处理
                    List<Dqhjxssjb> list = JSON.parseArray(String.valueOf(result), Dqhjxssjb.class);
                    //向数据库插入数据
                    if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                        i += insertDqhjxssjbList(list);
                    }
                    if (list.size()<pageSize){
                        flag = false;
                    }
                }while (flag);
            } catch (Exception e) {
                log.error("getData result error ： " + e.getMessage());
            }
            return i;
        }

    public int insertDqhjxssjbList(List<Dqhjxssjb> insertDqhjxssjbList){
        int i = 0;
        try {
            if (CollectionUtils.isEmpty(insertDqhjxssjbList) || (insertDqhjxssjbList.size() == 0)){
                log.info("insertDqhjxssjbList list.size为0");
            }
            else {
//                //获取数据库中已有数据的Id
//                List<HashMap<String, String>> hashMaps = airQualityDao.queryDqhjxssjbAllId();
//                //根据id去重
//                for (HashMap map:hashMaps) {
//                    String id = (String)map.get("id");
//                    for (int j = 0; j < insertDqhjxssjbList.size(); j++) {
//                        if (id.equals(insertDqhjxssjbList.get(j).getId())){
//                            insertDqhjxssjbList.remove(j);
//                        }
//                    }
//                }
                if (insertDqhjxssjbList.size() != 0) {
                    i += airQualityDao.insertDqhjxssjbList(insertDqhjxssjbList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    @Override
    public List<Map<String, Object>> selectAirReal(String stationname) {
        return airQualityDao.selectAirReal(stationname);
    }

    @Override
    public List<Map<String, Object>> selectAirHistory(String stationname, Date beginTime, Date endTime, Integer limit) {
        return airQualityDao.selectAirHistory(stationname,beginTime,endTime,limit);
    }
}
