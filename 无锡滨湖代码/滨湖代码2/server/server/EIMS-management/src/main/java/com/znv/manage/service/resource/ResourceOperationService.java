package com.znv.manage.service.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.ResourceOperation;

/**
 * TCfgResourceOperation表服务接口
 *
 * @author 0049003788
 * @since 2022-11-10 15:30:27
 */
public interface ResourceOperationService {

    Result insert(ResourceOperation resourceOperation);

    Result update(ResourceOperation resourceOperation);

    Result query(Integer id, String stuffName, String type, String info, Integer page, Integer pageSize);

    Result deleteBatch(String ids);

    Result export(String ids);
}
