package com.znv.manage.controller;


import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.ResultBean;
import com.znv.manage.bean.user.Role;
import com.znv.manage.service.RoleModuleService;
import com.znv.manage.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ：Yang GuanRong
 * @date ：Created in 2019/11/5
 * @author : modified by lilingling 2020
 */

@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleServiceController {

    @Resource
    RoleService roleService;

    @Autowired
    RoleModuleService roleModuleService;

    @ApiOperation("dataPermission:可以访问的数据层，1：所有数据，2：本部门的数据")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> insertRole(
            @RequestParam(value = "roleName") String roleName,
            @RequestParam(value = "upRoleId", required = false) String upRoleId,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "dataPermission", required = false) String dataPermission,
            @RequestParam(value = "moduleIds", required = false) String moduleIds,
            @RequestParam(value = "level", required = true) String level,
            @RequestParam(value = "userId", required = true) String userId
    ) {

        ResultBean<String> ret = new ResultBean<>();

        try {
            //用户创建的角色等级不得高于该用户(level值越低，等级越高)
            String result=roleService.insertRoleLegal(userId,level);
            if(null!=result){
                ret.setResult(ResultBean.FAILED);
                ret.setRemark(result);
                return ret;
            }
            Role role = new Role(null, roleName, upRoleId, description,dataPermission,level);
            roleService.insertRole(role);
            roleModuleService.insertRoleModules(role.getRoleId(), moduleIds);
            ret.addData(Arrays.asList(role.getRoleId()));
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;
    }

    @ApiOperation("角色删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultBean<String> deleteRoles(
            @RequestParam(value = "roleIds") String roleIds,
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "upRoleId", required = false) String upRoleId,
            @RequestParam(value = "dataPermission", required = false) String dataPermission,
            @RequestParam(value = "description", required = false) String description
    ){
        ResultBean<String> ret = new ResultBean<>();
        try {
            roleService.deleteRoles(roleIds, roleName, upRoleId, description,dataPermission);
            List<String> list = Arrays.asList(roleIds.split(","));
            for (String str: list) {
                roleModuleService.deleteRoleModules(null,str, null);
            }

            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;
    }

    @ApiOperation("角色编辑")
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultBean<String> updateRole(
            @RequestParam(value = "roleId", required = true) String roleId,
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "upRoleId", required = false) String upRoleId,
            @RequestParam(value = "dataPermission", required = false) String dataPermission,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "moduleIds", required = false) String moduleIds,
            @RequestParam(value = "level", required = false) String level
    ){
        ResultBean<String> ret = new ResultBean<>();
        try {
            roleService.updateRole(roleId, roleName, upRoleId, description,dataPermission,level);
            //先删除，在加
            if (!StringUtils.isEmpty(moduleIds) || moduleIds != null){
                roleModuleService.deleteRoleModules(null,roleId, null);
                roleModuleService.insertRoleModules(roleId, moduleIds);
            }
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;
    }

    @ApiOperation("角色查询")
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Role> queryRoles(
            @RequestParam(value = "roleIds", required = false) String roleIds,
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "upRoleId", required = false) String upRoleId,
            @RequestParam(value = "dataPermission", required = false) String dataPermission,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "level", required = false) String level
    ){
        ResultBean<Role> ret = new ResultBean<>();
        try {
            List<Role> roles = roleService.queryRoles(roleIds, roleName, upRoleId, description,dataPermission,level);
            ret.addData(roles);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;
    }

    @ApiOperation("角色名称是否存在")
    @RequestMapping(value = "/verifyName",method = RequestMethod.GET)
    @ResponseBody
    public Result verifyName(
            @RequestParam(value = "roleName") String roleName
    ){
        Result result = new Result();
        try {
            List<Role> list =  roleService.queryRoles(null, roleName, null, null,null,null);
            if (list.size()>0){
                result.setCode(1);
                result.setMessage("角色名字已存在");
            }

        } catch (Exception e) {
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());

        }
        return result;
    }
}
