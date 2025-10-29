package cn.wxgis.jc.system.web.controller;

import cn.wxgis.jc.common.constant.ConstantForError;
import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.log.annotation.Log;
import cn.wxgis.jc.log.enums.OperLogTypeEnum;
import cn.wxgis.jc.log.enums.OperLogUserTypeEnum;
import cn.wxgis.jc.system.po.SysRole;
import cn.wxgis.jc.system.req.SysRoleSaveRequest;
import cn.wxgis.jc.system.select.SysRoleSelect;
import cn.wxgis.jc.system.web.service.SysRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/role")
@AllArgsConstructor
@Api(value = "SysRoleController", tags = "角色相关APi")
public class SysRoleController {

    private final SysRoleService roleService;

    @ApiOperation(value ="角色分页列表", httpMethod ="POST")
    @PostMapping("/page")
    public JsonResult page(@RequestBody SysRoleSelect select) {
        IPage<SysRole> page = roleService.page(select);
        return JsonResult.ok(page);
    }

    @ApiOperation(value ="角色详情", httpMethod ="POST")
    @PostMapping("/{id}")
    public JsonResult info(@ApiParam(value = "角色id", required = true) @PathVariable String id){
        return JsonResult.ok(roleService.selectById(id));
    }


    @ApiOperation(value ="角色新增", httpMethod ="POST")
    @Log(title = "角色管理", type = OperLogTypeEnum.INSERT, desc = "角色新增", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/save")
    public JsonResult save(@Validated @RequestBody SysRoleSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.name, request.getName());
        ExistsReturn existsReturn = roleService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(roleService.save(request));
    }

    @ApiOperation(value ="角色修改", httpMethod ="POST")
    @Log(title = "角色管理", type = OperLogTypeEnum.UPDATE, desc = "角色修改", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/update")
    public JsonResult update(@Validated @RequestBody SysRoleSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.name, request.getName());
        existsParam.put(ExistsParam.id, request.getId());
        ExistsReturn existsReturn = roleService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(roleService.edit(request));
    }

    @ApiOperation(value ="角色删除", httpMethod ="POST")
    @Log(title = "角色管理", type = OperLogTypeEnum.DELETE, desc = "角色删除", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/delete/{id}")
    public JsonResult delete(@ApiParam(value = "角色id", required = true) @PathVariable String id){
        if (roleService.checkUserExistRole(id)) {
            return JsonResult.error(ConstantForError.EXISTE_USER_ROLE);
        }
        return JsonResult.ok(roleService.delete(id));
    }
}
