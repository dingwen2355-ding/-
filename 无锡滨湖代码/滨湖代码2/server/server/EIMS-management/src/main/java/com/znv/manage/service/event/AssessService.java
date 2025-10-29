package com.znv.manage.service.event;

import com.znv.manage.common.bean.event.Assess;
import com.znv.manage.common.bean.Result;

/**
 * TCfgAssess表服务接口
 *
 * @author 0049003788
 * @since 2022-12-12 10:29:12
 */
public interface AssessService {

    Result insert(Assess assess);

    Result update(Assess assess);

    Result deleteById(Integer id);

    Result deleteBatch(String ids);

    Result queryByCondition(Integer id, String name, Integer eventId, String eventTitle, Integer page, Integer pageSize);
}
