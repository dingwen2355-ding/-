package com.znv.manage.service.impl;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.FireAwarenessDao;
import com.znv.manage.service.FireAwarenessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:
 * @Date:
 * @Description:
 */

@Service
@Slf4j
public class FireAwarenessServiceImpl implements FireAwarenessService {

    @Autowired
    private FireAwarenessDao fireAwarenessDao;

    private static Integer STATUS_ONLINE = 1;
    private static Integer STATUS_NULL = -1;

    @Override
    public Result getRealTimePerception(Date beginTime, Date endTime,String precinctId,String precinctName, Integer limit) {
        Result result = new Result();
        try {
            //获取实时报警数据
            List<Map<String, Object>> list = fireAwarenessDao.getRealTimePerception(beginTime, endTime,precinctId,precinctName, limit);
            //获取实时报警数量
            Integer count = fireAwarenessDao.getRealTimePerception(beginTime, endTime,precinctId,precinctName, null).size();
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
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

    @Override
    public Result sensorOnlineRate(String precinctId,String precinctName) {
        Result result = new Result();
        try {
            //获取所有设备数量
            Integer countTotal = fireAwarenessDao.getSensorCount(STATUS_NULL,precinctId,precinctName);
            //获取在线设备数量
            Integer countOnline = fireAwarenessDao.getSensorCount(STATUS_ONLINE,precinctId,precinctName);
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
            Map<String, Object> map = new HashMap<>();
            if(countTotal !=0  && countOnline != 0){
                map.put("data", numberFormat.format((float) countOnline / (float) countTotal * 100));
            }else{
                map.put("data",0);
            }
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
