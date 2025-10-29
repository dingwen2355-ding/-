package com.znv.manage.service.impl.resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.Protection;
import com.znv.manage.common.bean.resource.Risk;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.resource.RiskDao;
import com.znv.manage.service.resource.RiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class RiskServiceImpl implements RiskService {
    @Autowired
    private RiskDao riskDao;

    @Override
    public PageInfo<Risk> queryRiskList(String industry, String enterpriseName, String rectificationProgress, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        if (!ObjectUtils.isEmpty(pageNum) && !ObjectUtils.isEmpty(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<Risk> list = riskDao.queryRiskList(industry, enterpriseName, rectificationProgress, startTime, endTime);
        return new PageInfo<Risk>(list);
    }

    @Override
    public Result deleteByPrimaryKey(String ids) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(ids)) {
                result.setCode(ResultCodeEnum.INVALIDREQUEST.getCode());
                result.setMessage(ResultCodeEnum.INVALIDREQUEST.getName());
                return result;
            }
            riskDao.deleteByPrimaryKey(ids);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result insertSelective(Risk risk) {
        Result result = new Result();
        try {
            riskDao.insertSelective(risk);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }


    @Override
    public Result updateByPrimaryKeySelective(Risk risk) {
        Result result = new Result();
        try {
            riskDao.updateByPrimaryKeySelective(risk);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result insertSelectiveList(List<Risk> riskList) {
        Result result = new Result();
        try {
            riskDao.insertSelectiveList(riskList);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }
}
