package com.znv.manage.service.impl.resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.Law;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.resource.LawDao;
import com.znv.manage.service.resource.LawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class LawServiceImpl implements LawService {
    @Autowired
    private LawDao lawDao;

    @Override
    public PageInfo<Law> queryLawList(String lawName, String accidentType, Integer pageNum, Integer pageSize) {
        if (!ObjectUtils.isEmpty(pageNum) && !ObjectUtils.isEmpty(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<Law> list = lawDao.queryLawList(lawName,accidentType);
        return new PageInfo<Law>(list);
    }

    @Override
    public Result deleteLaw(String ids) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(ids)) {
                result.setCode(ResultCodeEnum.INVALIDREQUEST.getCode());
                result.setMessage(ResultCodeEnum.INVALIDREQUEST.getName());
                return result;
            }
            lawDao.deleteLaw(ids);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result insertSelective(Law law) {
        Result result = new Result();
        try {
            lawDao.insertSelective(law);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result updateByPrimaryKeySelective(Law law) {
        Result result = new Result();
        try {
            lawDao.updateByPrimaryKeySelective(law);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }
}
