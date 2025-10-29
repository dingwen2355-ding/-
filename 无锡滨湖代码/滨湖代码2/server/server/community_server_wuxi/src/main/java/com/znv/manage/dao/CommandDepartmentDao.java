package com.znv.manage.dao;

import java.util.List;
import java.util.Map;

/**
 * TCfgCommandDepartment表数据库访问层
 *
 * @author 0049003788
 * @since 2022-11-28 11:18:21
 */
public interface CommandDepartmentDao {
    List<Map<String, Object>> queryCommand();

    List<Map<String, Object>> queryOrg(String commandName);

}

