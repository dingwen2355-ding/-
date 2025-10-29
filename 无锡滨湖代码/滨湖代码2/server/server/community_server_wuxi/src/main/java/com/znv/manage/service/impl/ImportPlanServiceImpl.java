package com.znv.manage.service.impl;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.ImportPlanDao;
import com.znv.manage.service.ImportPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 导入的新预案
 *
 * @author ：0049003788
 * @date ：2022/10/26 9:40
 */
@Service
@Slf4j
public class ImportPlanServiceImpl implements ImportPlanService {
    @Autowired
    ImportPlanDao importPlanDao;

    @Override
    public Result queryPlan(Integer planId) {
        Result result = new Result();
        try {
            List<Map<String, Object>> plans = importPlanDao.queryPlan(planId);
            plans.forEach(map -> map.put("requirements", importPlanDao.queryRequirement(planId)));
            result.setData(plans);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }
}
