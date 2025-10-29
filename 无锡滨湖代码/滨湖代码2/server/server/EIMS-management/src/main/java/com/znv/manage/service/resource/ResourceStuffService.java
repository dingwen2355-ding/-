package com.znv.manage.service.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.ResourceOperation;
import com.znv.manage.common.bean.resource.ResourceStuff;

/**
 * TCfgResourceStuff表服务接口
 *
 * @author 0049003788
 * @since 2023-05-19 15:13:42
 */
public interface ResourceStuffService {

    Result insert(ResourceStuff resourceStuff);

    Result update(ResourceStuff resourceStuff);

    Result deleteById(Integer id);

    Result deleteBatch(String ids);

    Result queryByCondition(Integer id, String stuffName, String stuffType, String helpType, String helpTypeName, String dutyPerson, String dutyPhone, String region, String planId, String eventId, String isAll, Integer page, Integer pageSize);

    Result operate(ResourceOperation resourceOperation);

    Result queryStock(String stockName, String eventId, String planId,String isAll, Integer page, Integer pageSize);
}
