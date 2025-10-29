package com.znv.manage.dao;


import com.znv.manage.bean.user.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleServiceMapper {
    public void insertRole(
            Role role
    );
    public void deleteRoles(
            @Param(value = "roleIds") String roleIds,
            @Param(value = "roleName") String roleName,
            @Param(value = "upRoleId") String upRoleId,
            @Param(value = "description") String description,
            @Param(value = "dataPermission") String dataPermission);
    public void updateRole(
            @Param(value = "roleId") String roleId,
            @Param(value = "roleName") String roleName,
            @Param(value = "upRoleId") String upRoleId,
            @Param(value = "description") String description,
            @Param(value = "dataPermission") String dataPermission,
            @Param(value = "level") String level);

    public List<Role> queryRoles(
            @Param(value = "roleIds") String roleIds,
            @Param(value = "roleName") String roleName,
            @Param(value = "upRoleId") String upRoleId,
            @Param(value = "description") String description,
            @Param(value = "dataPermission") String dataPermission,
            @Param(value = "level") String level);

    String getLevelByUserId(@Param(value = "userId") String userId);
}
