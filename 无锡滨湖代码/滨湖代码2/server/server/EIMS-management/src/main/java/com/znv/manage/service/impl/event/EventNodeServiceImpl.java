package com.znv.manage.service.impl.event;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.EventNode;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.event.EventNodeDao;
import com.znv.manage.service.event.EventNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TCfgEventNode表服务实现类
 *
 * @author 0049003788
 * @since 2022-12-09 16:32:20
 */
@Service
@Slf4j
public class EventNodeServiceImpl implements EventNodeService {
    @Resource
    private EventNodeDao eventNodeDao;

    @Override
    public Result insert(EventNode eventNode) {
        Result result = new Result();
        try {
            eventNodeDao.insert(eventNode);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result update(EventNode eventNode) {
        Result result = new Result();
        try {
            eventNodeDao.update(eventNode);
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
            result.setData(eventNodeDao.deleteById(id) > 0);
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
            result.setData(eventNodeDao.deleteBatch(ids) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryByCondition(Integer id, String nodeName, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(eventNodeDao.queryByCondition(id, nodeName)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }
}
