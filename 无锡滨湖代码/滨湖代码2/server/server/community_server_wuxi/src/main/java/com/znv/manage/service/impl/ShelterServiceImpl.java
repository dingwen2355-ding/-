package com.znv.manage.service.impl;

import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.ShelterDao;
import com.znv.manage.service.ShelterService;
import com.znv.manage.common.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: ShelterServiceImpl
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/9/6 15:47
 **/
@Service
@Slf4j
public class ShelterServiceImpl implements ShelterService {

    private static Logger logger = LoggerFactory.getLogger(ShelterService.class);

    @Autowired
    private ShelterDao shelterDao;


    @Override
    public Result shelterCoordinatesList() {
        Result result = new Result();
        try {
            List<Map<String, Object>> data = shelterDao.shelterCoordinatesList();
            int size = shelterDao.shelterCoordinatesList().size();
            Map<String, Object> map = new HashMap<>();
            map.put("data", data);
            map.put("count",size);
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
            List<Map<String, Object>> data = shelterDao.queryCoordinatesDetails(id);
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
