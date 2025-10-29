package com.znv.manage.service;



import com.znv.manage.bean.user.Role;

import java.util.List;

public interface RoleService {
    public void insertRole(
            Role role
    );
    public void deleteRoles(
            String roleIds,
            String roleName,
            String upRoleId,
            String description,
            String dataPermission
    );
    public void updateRole(
            String roleId,
            String roleName,
            String upRoleId,
            String description,
            String dataPermission,
            String level
    );
    public List<Role> queryRoles(
            String roleIds,
            String roleName,
            String upRoleId,
            String description,
            String dataPermission,
            String level
    );
   public String insertRoleLegal(String userId,String level);
}