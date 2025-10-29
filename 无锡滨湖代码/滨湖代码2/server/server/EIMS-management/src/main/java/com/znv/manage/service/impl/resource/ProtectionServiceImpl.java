package com.znv.manage.service.impl.resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.Law;
import com.znv.manage.common.bean.resource.Protection;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.resource.ProtectionDao;
import com.znv.manage.service.resource.ProtectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class ProtectionServiceImpl implements ProtectionService {
    @Autowired
    private ProtectionDao protectionDao;

    @Override
    public PageInfo<Protection> queryProtectionList(String protectionName, String protectionType, Integer pageNum, Integer pageSize) {
        if (!ObjectUtils.isEmpty(pageNum) && !ObjectUtils.isEmpty(pageSize)) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<Protection> list = protectionDao.queryProtectionList(protectionName, protectionType);
        return new PageInfo<Protection>(list);
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
            protectionDao.deleteByPrimaryKey(ids);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result insertSelective(Protection protection) {
        Result result = new Result();
        try {
            protectionDao.insertSelective(protection);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result updateByPrimaryKeySelective(Protection protection) {
        Result result = new Result();
        try {
            protectionDao.updateByPrimaryKeySelective(protection);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }

    @Override
    public Result insertSelectiveList(List<Protection> protectionList) {
        Result result = new Result();
        try {
            System.out.println("protectionList = " + protectionList);
            protectionDao.insertSelectiveList(protectionList);
        } catch (Exception e) {
            log.error(e.toString(), e);
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }



}
