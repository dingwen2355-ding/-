package com.znv.manage.service.impl.resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.ResourceOperation;
import com.znv.manage.common.bean.resource.ResourceStuff;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.CommonUtils;
import com.znv.manage.dao.event.EventDao;
import com.znv.manage.dao.resource.ResourceOperationDao;
import com.znv.manage.dao.resource.ResourceStuffDao;
import com.znv.manage.service.resource.ResourceStuffService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * TCfgResourceStuff表服务实现类
 *
 * @author 0049003788
 * @since 2023-05-19 15:13:42
 */
@Service
@Slf4j
public class ResourceStuffServiceImpl implements ResourceStuffService {
    @Resource
    private ResourceStuffDao resourceStuffDao;

    @Resource
    private ResourceOperationDao resourceOperationDao;

    @Resource
    private EventDao eventDao;

    @Override
    public Result insert(ResourceStuff resourceStuff) {
        Result result = new Result();
        try {
            if (StringUtils.isBlank(resourceStuff.getStuffName())) {
                throw new BusinessException("物资名称不能为空");
            }
            resourceStuffDao.insert(resourceStuff);
            //保存物资操作记录
            ResourceOperation resourceOperation = new ResourceOperation();
            resourceOperation.setRsId(resourceStuff.getId().toString());
            resourceOperation.setType("1");
            resourceOperation.setNum(resourceStuff.getStuffNum());
            resourceOperation.setInfo("管理员入库");
            resourceOperationDao.insert(resourceOperation);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result update(ResourceStuff resourceStuff) {
        Result result = new Result();
        try {
            resourceStuffDao.update(resourceStuff);
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
            result.setData(resourceStuffDao.deleteById(id) > 0);
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
            result.setData(resourceStuffDao.deleteBatch(ids) > 0);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryByCondition(Integer id, String stuffName, String stuffType, String helpType, String helpTypeName, String dutyPerson, String dutyPhone, String region, String planId, String eventId, String isAll, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            String tempEventId = eventId;
            if ("1".equals(isAll)) {
                tempEventId = null;
            }
            List<ResourceStuff> list = resourceStuffDao.queryByCondition(id, stuffName, stuffType, helpType, helpTypeName, dutyPerson, dutyPhone, region, planId, tempEventId);
            //计算距离
            if (!org.springframework.util.StringUtils.isEmpty(eventId) && !CollectionUtils.isEmpty(list)) {
                Map<String, String> map = eventDao.queryGps(eventId);
                if (map != null) {
                    list.forEach(temp -> temp.setDistance(CommonUtils.calculateDistance(map.get("lat"), map.get("lon"), temp.getGpsy(), temp.getGpsx())));
                } else {
                    list.forEach(temp -> temp.setDistance(null));
                }
            }
            result.setData(new PageInfo<>(list));
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    @Override
    public Result queryStock(String stockName, String eventId, String planId, String isAll, Integer page, Integer pageSize) {
        Result result = new Result();
        try {
            if (page != null && pageSize != null) {
                PageHelper.startPage(page, pageSize);
            }
            String tempEventId = eventId;
            if ("1".equals(isAll)) {
                tempEventId = null;
            }
            List<ResourceStuff> list = resourceStuffDao.queryGroupByAddress(stockName, tempEventId, planId);
            //计算距离
            if (!org.springframework.util.StringUtils.isEmpty(eventId) && !CollectionUtils.isEmpty(list)) {
                Map<String, String> map = eventDao.queryGps(eventId);
                if (map != null) {
                    list.forEach(temp -> temp.setDistance(CommonUtils.calculateDistance(map.get("lat"), map.get("lon"), temp.getGpsy(), temp.getGpsx())));
                } else {
                    list.forEach(temp -> temp.setDistance(null));
                }
            }
            result.setData(new PageInfo<>(list));
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
    public Result operate(ResourceOperation resourceOperation) {
        Result result = new Result();
        try {
            ResourceStuff resourceStuff = resourceStuffDao.queryById(
                    Integer.parseInt(StringUtils.isBlank(resourceOperation.getRsId()) ? "-1" : resourceOperation.getRsId()));
            if (resourceStuff != null) {
                if ("1".equals(resourceOperation.getType())) {
                    resourceStuff.setStuffNum(resourceStuff.getStuffNum() + resourceOperation.getNum());
                } else {
                    if ("2".equals(resourceOperation.getType())) {
                        resourceStuff.setStuffNum(resourceStuff.getStuffNum() - resourceOperation.getNum());
                    } else {
                        throw new BusinessException("type类型不合法");
                    }
                }
                if (resourceStuff.getStuffNum() >= 0) {
                    resourceStuffDao.update(resourceStuff);
                    resourceOperationDao.insert(resourceOperation);
                } else {
                    throw new BusinessException("库存不足");
                }
            } else {
                throw new BusinessException("无对应库存数据");
            }
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }
}
