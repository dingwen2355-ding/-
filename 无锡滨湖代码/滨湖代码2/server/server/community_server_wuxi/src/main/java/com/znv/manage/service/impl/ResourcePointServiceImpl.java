package com.znv.manage.service.impl;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.ResourcePointDao;
import com.znv.manage.service.ResourcePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 地图撒点
 *
 * @author ：0049003788
 * @date ：2023/1/12 14:05
 */
@Service
@Slf4j
public class ResourcePointServiceImpl implements ResourcePointService {
    @Resource
    ResourcePointDao resourcePointDao;


    @Override
    public Result queryPoint(String type) {
        Result result = new Result();
        try {
            switch (type) {
                case "避难场所":
                    result.setData(resourcePointDao.selectShelter());
                    break;
                case "应急专家":
                    result.setData(resourcePointDao.selectExpert());
                    break;
                case "学校":
                    result.setData(resourcePointDao.selectSchool());
                    break;
                case "应急物资仓库":
                    result.setData(resourcePointDao.selectStock());
                    break;
                default:
                    throw new BusinessException("类型错误");
            }
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }
}
