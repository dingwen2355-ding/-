package com.znv.manage.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleModuleServiceMapper {

    public void insertRoleModules(
            @Param(value = "roleId") String roleId,
            @Param(value = "roleModules") List roleModules
    );

    public void deleteRoleModules(
            @Param(value = "ids") String ids,
            @Param(value = "roleId") String roleId,
            @Param(value = "roleModules") String roleModules
    );

    public void updateRoleModules(
            @Param(value = "id") String id,
            @Param(value = "roleId") String roleId,
            @Param(value = "roleModules") String roleModules
    );

    public List<Map<String, Object>> queryRoleModules(
            @Param(value = "ids") String ids,
            @Param(value = "roleId") String roleId,
            @Param(value = "roleModules") String roleModules
    );

}
