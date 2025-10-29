package com.znv.manage.service.impl;

import com.znv.manage.dao.RoleModuleServiceMapper;
import com.znv.manage.service.RoleModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class RoleModuleServiceImpl implements RoleModuleService {

    @Resource
    RoleModuleServiceMapper rolePrivilegeServiceMapper;

    @Override
    public void insertRoleModules(String roleId, String ModuleIds) {
        if(ModuleIds!=null&&!"".equals(ModuleIds)){
            List<String> privilegeIdList = Arrays.asList(ModuleIds.split(","));
            rolePrivilegeServiceMapper.insertRoleModules(roleId, privilegeIdList);
        }
    }

    @Override
    public void deleteRoleModules(String ids, String roleId, String ModuleIds) {
        rolePrivilegeServiceMapper.deleteRoleModules(ids, roleId, ModuleIds);
    }

    @Override
    public void updateRoleModules(String id, String roleId, String ModuleIds) {
        rolePrivilegeServiceMapper.updateRoleModules(id, roleId, ModuleIds);
    }

    @Override
    public List<Map<String, Object>> queryRoleModules(String ids, String roleId, String ModuleIds) {
        return rolePrivilegeServiceMapper.queryRoleModules(ids,roleId,ModuleIds);
    }
}
