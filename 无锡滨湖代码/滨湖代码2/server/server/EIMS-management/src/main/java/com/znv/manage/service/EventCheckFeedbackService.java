package com.znv.manage.service;

import com.znv.manage.common.bean.event.EventCheckFeedback;
import com.znv.manage.common.bean.Result;

/**
 * TCfgEventCheckFeedback表服务接口
 *
 * @author 0049003788
 * @since 2023-05-18 15:18:59
 */
public interface EventCheckFeedbackService {

    Result insert(EventCheckFeedback eventCheckFeedback);

    Result update(EventCheckFeedback eventCheckFeedback);

    Result deleteById(Integer id);

    Result deleteBatch(String ids);

    Result queryByCondition(Integer id, String isTrue, String checkContent, String fileUrls, Integer checkId, Integer page, Integer pageSize);
}
