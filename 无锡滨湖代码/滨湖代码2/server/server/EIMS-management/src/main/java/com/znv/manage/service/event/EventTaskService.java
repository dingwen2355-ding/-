package com.znv.manage.service.event;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.EventTask;

/**
 * TCfgEventTask表服务接口
 *
 * @author 0049003788
 * @since 2022-12-09 16:32:59
 */
public interface EventTaskService {

    Result insert(EventTask eventTask);

    Result update(EventTask eventTask);

    Result deleteById(Integer id);

    Result deleteBatch(String ids);

    Result queryByCondition(Integer id, String taskTitle, String eventTitle, Integer contactId, String person, String personId, String status, String eventId, Integer page, Integer pageSize);

    Result queryEventTask(Integer eventId, String eventTitle, Integer eventTypeId, String sourceId,
                          String streetName, String communityName, String startTime, String endTime,
                          String status, String taskStartTime, String taskEndTime, Integer page, Integer pageSize);
}
