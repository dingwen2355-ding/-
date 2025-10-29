package com.znv.manage.controller;

import com.znv.manage.common.bean.ResultBean;
import com.znv.manage.service.RoleModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ：Yang GuanRong
 * @date ：Created in 2019/11/5
 */

@Api(tags = "角色和模块关系管理")
@RestController
@RequestMapping("/roleModule")
@Slf4j
public class RoleModuleController {

    @Resource
    RoleModuleService roleModuleService;

    @ApiOperation("角色模块关系新增")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ResponseBody
    public ResultBean<String> insertRolePrivileges(
            @RequestParam(value = "roleId", required = true) String roleId,
            @RequestParam(value = "moduleIds", required = true) String moduleIds
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            roleModuleService.insertRoleModules(roleId, moduleIds);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;

    }


    @ApiOperation("角色模块关系删除")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultBean<String> deleteRolePrivileges(
            @RequestParam(value = "ids" , required = false) String ids,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "moduleIds", required = false) String moduleIds
    ) {

        ResultBean<String> ret = new ResultBean<>();
        try {
            roleModuleService.deleteRoleModules(ids, roleId, moduleIds);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;
    }


    @ApiOperation("角色模块关系编辑")
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ResponseBody
    public ResultBean<String> updateRolePrivilege(
            @RequestParam(value = "id", required = true) String id,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "moduleIds", required = false) String moduleIds
    ) {
        ResultBean<String> ret = new ResultBean<>();
        try {
            roleModuleService.updateRoleModules(id, roleId, moduleIds);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;
    }


    @ApiOperation("角色模块关系查询")
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean<Map<String, Object>> queryRolePrivileges(
            @RequestParam(value = "ids", required = false) String ids,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "moduleIds", required = false) String moduleIds
    ) {
        ResultBean<Map<String, Object>> ret = new ResultBean<>();
        try {
            List<Map<String, Object>> datas = roleModuleService.queryRoleModules(ids, roleId, moduleIds);
            ret.addData(datas);
            ret.setResult(ResultBean.SUCESS);
            ret.setRemark("success");

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;
    }
}
