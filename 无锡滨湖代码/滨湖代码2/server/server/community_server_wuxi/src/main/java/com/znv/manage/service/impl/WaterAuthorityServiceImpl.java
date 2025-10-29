package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.Szjcsj;
import com.znv.manage.common.bean.Yjfkb;
import com.znv.manage.common.bean.Yjzb;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.AccessUtil;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.dao.WaterAuthorityDao;
import com.znv.manage.service.WaterAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Title: waterAuthorityServiceImpl
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/6/30 9:21
 **/
@Service
@Slf4j
public class WaterAuthorityServiceImpl implements WaterAuthorityService {

    private static Logger logger = LoggerFactory.getLogger(WaterAuthorityServiceImpl.class);

    @Autowired
    private WaterAuthorityDao waterAuthorityDao;


    @Override
    public int getYjzbData() {
        //设置请求参数
        int pageNumber = 0;
        int pageSize = 100;
        int i = 0;
        boolean flag = true;
        try {
            do {
                pageNumber += 1;
                HashMap<String, String> params = new HashMap<>();
                params.put("startdate", DateUtil.getStringDateShort());
//                params.put("startdate", " ");
                params.put("pageSize",Integer.toString(pageSize));
                params.put("pageNumber",Integer.toString(pageNumber));
                String url = "http://10.214.111.238:9090/service/api/swj1/t_fm_report";
                Object result = AccessUtil.get(url, params);
//                log.info("溧水水位水量总预警接口返回："+result.toString());
                //返回结果为unicode编码，需要特殊处理
                List<Yjzb> list = JSON.parseArray(String.valueOf(result), Yjzb.class);
                //向数据库插入数据
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    i += insertYjzbList(list);
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

    @Override
    public int getYjfkbData() {
         //设置请求参数
        int pageNumber = 0;
        int pageSize = 100;
        int i = 0;
        boolean flag = true;
        try {
            do {
                pageNumber += 1;
                HashMap<String, String> params = new HashMap<>();
                params.put("startdate", DateUtil.getStringDateShort());
//                params.put("startdate", " ");
                params.put("pageSize",Integer.toString(pageSize));
                params.put("pageNumber",Integer.toString(pageNumber));
                String url = "http://10.214.111.238:9090/service/api/swj1/t_fm_feedback";
                Object result = AccessUtil.get(url, params);
//                log.info("溧水水位水量预警反馈接口返回："+result);
                //返回结果为unicode编码，需要特殊处理
                List<Yjfkb> list = JSON.parseArray(String.valueOf(result), Yjfkb.class);
                //向数据库插入数据
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    i += insertYjfkbList(list);
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

    @Override
    public int getSzjcsjData() {
        //设置请求参数
        int pageNumber = 0;
        int pageSize = 100;
        int i = 0;
        boolean flag = true;
        try {
            do {
                pageNumber += 1;
                HashMap<String, String> params = new HashMap<>();
                params.put("startdate", DateUtil.getStringDateShort());
//                params.put("startdate", " ");
                params.put("pageSize",Integer.toString(pageSize));
                params.put("pageNumber",Integer.toString(pageNumber));
                String url = "http://10.214.111.238:9090/service/api/sthjj/t_monitor_water_quality";
                Object result = AccessUtil.get(url, params);
//              log.info("水质监测数据接口返回："+result);
                //返回结果为unicode编码，需要特殊处理
                List<Szjcsj> list = JSON.parseArray(String.valueOf(result), Szjcsj.class);
                //向数据库插入数据
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    i += insertSzjcsjList(list);
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

    @Override
    public Result getYjzbStatistics(Date beginTime, Date endTime) {

        Result result = new Result();

        try {
            List list = waterAuthorityDao.YjzbTodayList(beginTime,endTime);
            Integer count = waterAuthorityDao.countYjzbToday(beginTime,endTime);
            Map<String,Object> map = new HashMap<>();
            map.put("list",list);
            map.put("count",count);
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

    public int insertYjzbList(List<Yjzb> insertYjzbList){
        int i = 0;
        try {
            if (CollectionUtils.isEmpty(insertYjzbList) || (insertYjzbList.size() == 0)){
                log.info("insertYjzbList list.size为0");
            }
            else {
//                //获取数据库中已有数据的Id
//                List<HashMap<String, Object>> hashMaps = waterAuthorityDao.queryYjzbAllId();
//                //根据id去重
//                for (HashMap map:hashMaps) {
//                    String id = (String) map.get("id");
//                    for (int j = 0; j < insertYjzbList.size(); j++) {
//                        if (id.equals(insertYjzbList.get(j).getId())){
//                            insertYjzbList.remove(j);
//                        }
//                    }
//
//                }
                if (insertYjzbList.size() != 0) {
                    i += waterAuthorityDao.insertYjzbList(insertYjzbList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    public int insertYjfkbList(List<Yjfkb> insertYjfkbList){
        int i = 0;
        try {
            if (CollectionUtils.isEmpty(insertYjfkbList) || (insertYjfkbList.size() == 0)){
                log.info("insertYjfkbList list.size为0");
            }
            else {
//                //获取数据库中已有数据的Id
//                List<HashMap<String, Object>> hashMaps = waterAuthorityDao.queryYjzbAllId();
//                //根据id去重
//                for (HashMap map:hashMaps) {
//                    String id = (String) map.get("id");
//                    for (int j = 0; j < insertYjfkbList.size(); j++) {
//                        if (id.equals(insertYjfkbList.get(j).getId())){
//                            insertYjfkbList.remove(j);
//                        }
//                    }
//                }
                if (insertYjfkbList.size() != 0) {
                    i += waterAuthorityDao.insertYjfkbList(insertYjfkbList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    public int insertSzjcsjList(List<Szjcsj> insertSzjcsjList){
        int i = 0;
        try {
            if (CollectionUtils.isEmpty(insertSzjcsjList) || (insertSzjcsjList.size() == 0)){
                log.info("insertSzjcsjList list.size为0");
            }
            else {
                //获取数据库中已有数据的collectTime
                List<HashMap<String, Date>> hashMaps = waterAuthorityDao.getSzjcsjAllCollectTime();
                //根据collectTime去重
                for (HashMap map:hashMaps) {
                    Date collectTime = (Date) map.get("collect_time");
                    String siteName = (String)map.get("site_name");
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = sdf.format(collectTime);
                    for (int j = 0; j < insertSzjcsjList.size(); j++) {
                        Date collectTime1 = insertSzjcsjList.get(j).getCollectTime();
                        String dateString1 = sdf.format(collectTime1);
                        String siteName1 = insertSzjcsjList.get(j).getSiteName();
                        if (dateString.equals(dateString1) && siteName1.equals(siteName1)){
                            insertSzjcsjList.remove(j);
                        }
                    }
                }
                if (insertSzjcsjList.size() != 0) {
                    i += waterAuthorityDao.insertSzjcsjList(insertSzjcsjList);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return i;
    }

    @Override
    public List<Map<String, Object>> selectWaterQualityReal(String stationname) {
        return waterAuthorityDao.selectWaterQualityReal(stationname);
    }

    @Override
    public List<Map<String, Object>> selectWaterQualityHistory(String stationname, Date beginTime, Date endTime, Integer limit) {
        return waterAuthorityDao.selectWaterQualityHistory(stationname,beginTime,endTime,limit);
    }
}
