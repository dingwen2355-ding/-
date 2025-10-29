package com.znv.manage.service.event;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.EventNode;

/**
 * TCfgEventNode表服务接口
 *
 * @author 0049003788
 * @since 2022-12-09 16:32:20
 */
public interface EventNodeService {

    Result insert(EventNode eventNode);

    Result update(EventNode eventNode);

    Result deleteById(Integer id);

    Result deleteBatch(String ids);

    Result queryByCondition(Integer id, String nodeName, Integer page, Integer pageSize);
}
