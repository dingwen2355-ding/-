package com.znv.manage.service.event;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.Event;

/**
 * TCfgEvent表服务接口
 *
 * @author 0049003788
 * @since 2022-12-09 13:41:36
 */
public interface EventService {

    Result insert(Event event);

    Result update(Event event);

    Result updateDevice(Integer eventId, String deviceIds);

    Result deleteById(Integer id);

    Result deleteBatch(String ids);

    Result queryByCondition(Integer id, String eventTitle, Integer typeId, Integer streetId, Integer communityId,
                            String sourceId, String streetName, String communityName, String startTime,
                            String endTime, String status, String typeName, String sourceName, String statusIds, String userId, Integer page, Integer pageSize);

    Result export(String ids);

    Result getContactByEventId(Integer eventId);

    Result getSyncEventCount(String startTime, String endTime);

    Result getSyncEventCountBySource(String startTime, String endTime);

    Result syncEventList(String startTime, String endTime,Integer pageNum,Integer pageSize);
}
