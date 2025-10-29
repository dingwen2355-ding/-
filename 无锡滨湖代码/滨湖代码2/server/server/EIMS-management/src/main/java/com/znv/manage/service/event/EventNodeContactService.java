package com.znv.manage.service.event;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.EventNodeContact;

import java.util.List;

/**
 * TCfgEventNodeContact表服务接口
 *
 * @author 0049003788
 * @since 2022-12-09 16:32:32
 */
public interface EventNodeContactService {

    Result insert(EventNodeContact eventNodeContact);

    Result update(EventNodeContact eventNodeContact);

    Result updateOrder(List<EventNodeContact> list);

    Result deleteById(Integer id);

    Result deleteBatch(String ids);

    Result queryByCondition(Integer id, Integer eventId, Integer nodeId, String isHidden, Integer order, Integer page, Integer pageSize);
}
