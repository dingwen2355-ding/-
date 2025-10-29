package com.znv.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Dict;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.DictDao;
import com.znv.manage.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * TCfgDict表服务实现类
 *
 * @author 0049003788
 * @since 2022-11-11 13:41:48
 */
@Service
@Slf4j
public class DictServiceImpl implements DictService {
    @Resource
    private DictDao dictDao;

    @Override
    public Result insert(Dict dict) {
        Result result = new Result();
        try {
            dictDao.insert(dict);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result update(Dict dict) {
        Result result = new Result();
        try {
            dictDao.update(dict);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteById(Integer id) {
        Result result = new Result();
        try {
            result.setData(dictDao.deleteById(id) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteBatch(String ids) {
        Result result = new Result();
        try {
            result.setData(dictDao.deleteBatch(ids) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryByCondition(Integer id, String dictName, Integer dictOrder, String dictType, Integer parentId, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(dictDao.queryByCondition(id, dictName, dictOrder, dictType, parentId,null)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public List<Dict> dictTree(Integer parentId,Integer typeId) {
        return dictDao.queryByCondition(null,null,null,null,parentId,typeId);
    }

}
