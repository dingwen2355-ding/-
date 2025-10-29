package com.znv.manage.service.event;

import com.znv.manage.common.bean.event.AssessProject;
import com.znv.manage.common.bean.Result;

/**
 * TCfgAssessProject表服务接口
 *
 * @author 0049003788
 * @since 2022-12-09 13:39:56
 */
public interface AssessProjectService {

    Result insert(AssessProject assessProject);

    Result update(AssessProject assessProject);

    Result deleteById(Integer id);

    Result deleteBatch(String ids);

    Result queryByCondition(Integer id, String projectName, Integer page, Integer pageSize);
}
