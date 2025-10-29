package com.znv.manage.service.impl;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.EmergencySuppliesDao;
import com.znv.manage.service.EmergencySuppliesService;
import com.znv.manage.service.ShelterService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: EmergencySuppliesServiceImpl
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/9/6 16:29
 **/
@Service
@Slf4j
public class EmergencySuppliesServiceImpl implements EmergencySuppliesService {

    private static Logger logger = LoggerFactory.getLogger(EmergencySuppliesService.class);

    @Autowired
    private EmergencySuppliesDao emergencySuppliesDao;

    @Override
    public Result emergencySuppliesCoordinatesList() {
        Result result = new Result();
        try {
            List<Map<String, Object>> data = emergencySuppliesDao.emergencySuppliesCoordinatesList();
            int size = emergencySuppliesDao.emergencySuppliesCoordinatesList().size();
            Map<String, Object> map = new HashMap<>();
            map.put("count",size);
            map.put("data", data);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            logger.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }

        return result;
    }

    @Override
    public Result queryCoordinatesDetails(Integer id) {
        Result result = new Result();
        try {
            List<Map<String, Object>> data = emergencySuppliesDao.queryCoordinatesDetails(id);
            Map<String, Object> map = new HashMap<>();
            map.put("data", data);
            result.setData(map);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            logger.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }
}
