package com.znv.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.ThirdPlatformEntity;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.ThirdPlatformDao;
import com.znv.manage.service.ThirdPlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author SunJiaHao
 * @date 2022-08-16
 * @Description 第三方系统平台业务
 */
@Slf4j
@Service
public class ThirdPlatformServiceImpl implements ThirdPlatformService {

    @Autowired
    private ThirdPlatformDao thirdPlatformDao;

    @Override
    public Result addThirdPlatform(ThirdPlatformEntity entity) {
        Result result = new Result();
        try {
            if (ObjectUtils.isEmpty(entity)) {
                result.setResutCode(ResultCodeEnum.INVALIDREQUEST.getCode(), ResultCodeEnum.INVALIDREQUEST.getName());
                return result;
            }
            thirdPlatformDao.add(entity);
        } catch (Exception e) {
            log.error("Add third platform occur an exception!", e);
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result deleteThirdPlatform(String ids) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(ids)) {
                result.setResutCode(ResultCodeEnum.INVALIDREQUEST.getCode(), ResultCodeEnum.INVALIDREQUEST.getName());
                return result;
            }
            String[] idList = ids.split(",");
            for (String id : idList) {
                thirdPlatformDao.deleteById(Long.parseLong(id));
            }
            log.info("The third platforms (ids={}) is deleted!", ids);
        } catch (Exception e) {
            log.error("Delete third platform occur an exception!", e);
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result updateThirdPlatformById(ThirdPlatformEntity entity) {
        Result result = new Result();
        try {
            if (ObjectUtils.isEmpty(entity) || ObjectUtils.isEmpty(entity.getId())) {
                result.setResutCode(ResultCodeEnum.INVALIDREQUEST.getCode(), ResultCodeEnum.INVALIDREQUEST.getName());
                return result;
            }
            thirdPlatformDao.updateById(entity);
        } catch (Exception e) {
            log.error("Update third platform occur an exception!", e);
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result queryThirdPlatformList(Map<String, Object> paramMap, Integer pageNum, Integer pageSize) {
        Result result = new Result();
        try {
            if (!ObjectUtils.isEmpty(pageNum) && !ObjectUtils.isEmpty(pageSize)) {
                PageHelper.startPage(pageNum, pageSize);
            }
            List<ThirdPlatformEntity> dataList = thirdPlatformDao.queryList(paramMap);
            PageInfo<ThirdPlatformEntity> pageInfo = new PageInfo<>(dataList);
            result.setData(pageInfo);
        } catch (Exception e) {
            log.error("Query third platform list occur an exception!", e);
            result.setResutCode(ResultCodeEnum.SYSTEMERROR.getCode(), ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }
}
