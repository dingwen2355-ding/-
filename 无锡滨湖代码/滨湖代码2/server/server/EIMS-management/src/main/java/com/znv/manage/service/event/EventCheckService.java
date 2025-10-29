package com.znv.manage.service.event;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.event.EventCheck;
import com.znv.manage.common.bean.event.EventCheckFeedback;

/**
 * TCfgEventCheck表服务接口
 *
 * @author 0049003788
 * @since 2023-05-18 15:14:36
 */
public interface EventCheckService {

    Result insertCheck(EventCheck eventCheck);

    Result updateCheck(EventCheck eventCheck);

    Result deleteCheckById(Integer id);

    Result queryCheckByCondition(Integer id, Integer eventId, String checkPersonName, Integer checkUserId, String requirement, Integer page, Integer pageSize);

    Result insertFeedback(EventCheckFeedback feedback);

    Result updateFeedback(EventCheckFeedback feedback);

    Result deleteFeedbackById(Integer id);

    Result queryFeedbackByCondition(Integer id, Integer checkId, Integer page, Integer pageSize);
}
