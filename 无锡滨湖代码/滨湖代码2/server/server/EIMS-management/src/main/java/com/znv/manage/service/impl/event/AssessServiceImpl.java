package com.znv.manage.service.impl.event;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.event.Assess;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.event.AssessDao;
import com.znv.manage.service.event.AssessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TCfgAssess表服务实现类
 *
 * @author 0049003788
 * @since 2022-12-12 10:29:12
 */
@Service
@Slf4j
public class AssessServiceImpl implements AssessService {
    @Resource
    private AssessDao assessDao;

    @Override
    public Result insert(Assess assess) {
        Result result = new Result();
        try {
            assessDao.insert(assess);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result update(Assess assess) {
        Result result = new Result();
        try {
            assessDao.update(assess);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteById(Integer id) {
        Result result = new Result();
        try {
            result.setData(assessDao.deleteById(id) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteBatch(String ids) {
        Result result = new Result();
        try {
            result.setData(assessDao.deleteBatch(ids) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryByCondition(Integer id, String name, Integer eventId, String eventTitle, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(assessDao.queryByCondition(id, name, eventId, eventTitle)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }
}
