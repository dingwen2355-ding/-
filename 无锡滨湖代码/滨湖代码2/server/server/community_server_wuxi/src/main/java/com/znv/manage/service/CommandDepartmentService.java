package com.znv.manage.service;

import com.znv.manage.common.bean.Result;

/**
 * TCfgCommandDepartment表服务接口
 *
 * @author 0049003788
 * @since 2022-11-28 11:18:21
 */
public interface CommandDepartmentService {
    Result queryCommand();

    Result queryOrg(String commandName);
}
