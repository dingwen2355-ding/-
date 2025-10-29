package com.znv.manage.service;

import java.util.List;
import java.util.Map;

public interface RoleModuleService {

    public void insertRoleModules(
            String roleId,
            String ModuleIds
    );

    public void deleteRoleModules(
            String ids,
            String roleId,
            String ModuleIds
    );

    public void updateRoleModules(
            String id,
            String roleId,
            String ModuleIds
    );

    public List<Map<String, Object>> queryRoleModules(
            String ids,
            String roleId,
            String ModuleIds
    );
}
