package com.znv.manage.service.impl;


import com.znv.manage.bean.user.Role;
import com.znv.manage.dao.RoleServiceMapper;
import com.znv.manage.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleServiceMapper roleServiceMapper;

    @Override
    public void insertRole(Role role) {
        roleServiceMapper.insertRole(role);
    }

    @Override
    public void deleteRoles(String roleIds, String roleName, String upRoleId, String description,String dataPermission) {
        roleServiceMapper.deleteRoles(roleIds, roleName, upRoleId, description,dataPermission);
    }

    @Override
    public void updateRole(String roleId, String roleName, String upRoleId, String description,String dataPermission
            ,String level) {
        roleServiceMapper.updateRole(roleId, roleName, upRoleId, description,dataPermission,level);
    }

    @Override
    public List<Role> queryRoles(String roleIds, String roleName, String upRoleId, String description,String dataPermission
    ,String level) {
        return roleServiceMapper.queryRoles(roleIds, roleName, upRoleId, description,dataPermission,level);
    }

    @Override
    public String insertRoleLegal(String userId,String level){
        String result=null;
        String levelCreatUserStr=roleServiceMapper.getLevelByUserId(userId);
        if(null==levelCreatUserStr || "".equals(levelCreatUserStr)){
            result="userId["+userId+"]不存在或该用户的角色不存在";
            return result;//若该userId或roleId不存在，则也提示非法
        }

        Integer levelNewRole=Integer.parseInt(level);
        Integer levelCreatUser=Integer.parseInt(levelCreatUserStr);

        if(levelCreatUser<levelNewRole){
           return result;//合法
        }

        result="新角色level值需高于创建者,请重新输入[level值越高,角色等级越低]";
        return result;
    }
}
