package com.znv.manage.service.impl.event;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.EventNodeContact;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.event.EventNodeContactDao;
import com.znv.manage.dao.event.EventTaskDao;
import com.znv.manage.service.event.EventNodeContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * TCfgEventNodeContact表服务实现类
 *
 * @author 0049003788
 * @since 2022-12-09 16:32:32
 */
@Service
@Slf4j
public class EventNodeContactServiceImpl implements EventNodeContactService {
    @Resource
    private EventNodeContactDao eventNodeContactDao;

    @Resource
    private EventTaskDao eventTaskDao;

    @Override
    public Result insert(EventNodeContact eventNodeContact) {
        Result result = new Result();
        try {
            eventNodeContactDao.insert(eventNodeContact);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result update(EventNodeContact eventNodeContact) {
        Result result = new Result();
        try {
            eventNodeContactDao.update(eventNodeContact);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateOrder(List<EventNodeContact> list) {
        Result result = new Result();
        try {
            for (EventNodeContact eventNodeContact : list) {
                eventNodeContactDao.update(eventNodeContact);
            }
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
            if (!CollectionUtils.isEmpty(eventTaskDao.queryByCondition(null, null, null, id, null, null, null, null,
                    null, null))) {
                throw new BusinessException("存在关联的任务,无法删除！");
            }
            result.setData(eventNodeContactDao.deleteById(id) > 0);
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
            result.setData(eventNodeContactDao.deleteBatch(ids) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryByCondition(Integer id, Integer eventId, Integer nodeId, String isHidden, Integer order, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(eventNodeContactDao.queryByCondition(id, eventId, nodeId, isHidden, order)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }
}
