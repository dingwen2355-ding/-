package com.znv.manage.service.impl.event;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.Event;
import com.znv.manage.common.bean.event.EventCheck;
import com.znv.manage.common.bean.event.EventCheckFeedback;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.event.EventCheckDao;
import com.znv.manage.dao.event.EventCheckFeedbackDao;
import com.znv.manage.dao.event.EventDao;
import com.znv.manage.service.event.EventCheckService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;

/**
 * TCfgEventCheck表服务实现类
 *
 * @author 0049003788
 * @since 2023-05-18 15:14:37
 */
@Service
@Slf4j
public class EventCheckServiceImpl implements EventCheckService {
    @Resource
    private EventCheckDao eventCheckDao;

    @Resource
    private EventCheckFeedbackDao eventCheckFeedbackDao;

    @Resource
    private EventDao eventDao;

    @Override
    public Result insertCheck(EventCheck eventCheck) {
        Result result = new Result();
        try {
            if (eventCheck.getEventId() == null) {
                throw new BusinessException("事件id不允许为空");
            }
            //将对应事件修改为核查中状态
            Event event = new Event();
            event.setId(eventCheck.getEventId());
            event.setStatus(135);
            eventDao.update(event);
            eventCheckDao.insert(eventCheck);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateCheck(EventCheck eventCheck) {
        Result result = new Result();
        try {
            eventCheckDao.update(eventCheck);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteCheckById(Integer id) {
        Result result = new Result();
        try {
            result.setData(eventCheckDao.deleteById(id) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryCheckByCondition(Integer id, Integer eventId, String checkPersonName, Integer checkUserId, String requirement, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(eventCheckDao.queryByCondition(id, eventId, checkPersonName, checkUserId, requirement)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    @Transactional(value = "baseTransactionManager", rollbackFor = Exception.class)
    public Result insertFeedback(EventCheckFeedback feedback) {
        Result result = new Result();
        try {
            if (feedback.getCheckId() == null) {
                throw new BusinessException("核查id不允许为空");
            }
            EventCheck check = eventCheckDao.queryById(feedback.getCheckId());
            if (check == null || check.getEventId() == null) {
                throw new BusinessException("未核查任务或对应核查任务的事件为空");
            }
            //将对应事件修改为待启动状态
            Event event = new Event();
            event.setId(check.getEventId());
            if("是".equals(feedback.getIsTrue())){
                event.setStatus(41);
            }else {
                //不属实直接让该事件完结
                if("否".equals(feedback.getIsTrue())){
                    event.setStatus(43);
                }else {
                    throw new BusinessException("是否属实请传入“是”或者“否”");
                }
            }
            eventDao.update(event);
            eventCheckFeedbackDao.insert(feedback);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result updateFeedback(EventCheckFeedback feedback) {
        Result result = new Result();
        try {
            eventCheckFeedbackDao.update(feedback);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result deleteFeedbackById(Integer id) {
        Result result = new Result();
        try {
            result.setData(eventCheckFeedbackDao.deleteById(id) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryFeedbackByCondition(Integer id, Integer checkId, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            result.setData(new PageInfo<>(eventCheckFeedbackDao.queryByCondition(id, checkId)));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }
}
