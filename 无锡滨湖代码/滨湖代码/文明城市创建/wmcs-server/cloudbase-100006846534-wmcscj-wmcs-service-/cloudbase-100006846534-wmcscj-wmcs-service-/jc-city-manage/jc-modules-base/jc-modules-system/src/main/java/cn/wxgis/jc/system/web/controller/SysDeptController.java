package cn.wxgis.jc.system.web.controller;

import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.constant.ConstantForError;
import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.enums.system.DeptTypeEnum;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.web.controller.BaseController;
import cn.wxgis.jc.log.annotation.Log;
import cn.wxgis.jc.log.enums.OperLogTypeEnum;
import cn.wxgis.jc.log.enums.OperLogUserTypeEnum;
import cn.wxgis.jc.system.req.SysDeptSaveRequest;
import cn.wxgis.jc.system.resp.SysDeptResponse;
import cn.wxgis.jc.system.select.SysDeptSelect;
import cn.wxgis.jc.system.web.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/dept")
@AllArgsConstructor
@Api(value = "SysDeptController", tags = "部门相关APi")
public class SysDeptController extends BaseController {

    private final SysDeptService deptService;

    @ApiOperation(value ="部门列表", httpMethod ="POST")
    @PostMapping("/list")
    public JsonResult list(@RequestBody SysDeptSelect select) {
        List<SysDeptResponse> deptTree = deptService.listDeptTree(select);
        return JsonResult.ok(Constant.resultKey, deptTree);
    }

    @ApiOperation(value ="街道部门列表", httpMethod ="POST")
    @PostMapping("/region")
    public JsonResult region(@RequestBody SysDeptSelect select) {
        select.setType(DeptTypeEnum.REGION.getCode());
        List<SysDeptResponse> deptTree = deptService.listDeptTree(select);
        return JsonResult.ok(Constant.resultKey, deptTree);
    }



    @ApiOperation(value ="部门列表", httpMethod ="POST")
    @PostMapping("/list2")
    public JsonResult list2(@RequestBody SysDeptSelect select) {
        List<SysDeptResponse> deptTree = deptService.listDeptTree(select);
        return JsonResult.ok(Constant.resultKey, deptTree);
    }

    @ApiOperation(value ="部门详情", httpMethod ="POST")
    @PostMapping("/{id}")
    public JsonResult info(@ApiParam(value = "部门id", required = true) @PathVariable String id){
        return JsonResult.ok(deptService.findResponseById(id));
    }

    @ApiOperation(value ="部门新增", httpMethod ="POST")
    @Log(title = "部门管理", type = OperLogTypeEnum.INSERT, desc = "部门新增", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/save")
    public JsonResult save(@Validated @RequestBody SysDeptSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.name, request.getName());
        existsParam.put(ExistsParam.parentId, request.getParentId());
        ExistsReturn existsReturn = deptService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(deptService.save(request));
    }

    @ApiOperation(value ="部门修改", httpMethod ="POST")
    @Log(title = "部门管理", type = OperLogTypeEnum.UPDATE, desc = "部门修改", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/update")
    public JsonResult update(@Validated @RequestBody SysDeptSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.name, request.getName());
        existsParam.put(ExistsParam.parentId, request.getParentId());
        existsParam.put(ExistsParam.id, request.getId());
        ExistsReturn existsReturn = deptService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(deptService.edit(request));
    }

    @ApiOperation(value ="部门删除", httpMethod ="POST")
    @Log(title = "部门管理", type = OperLogTypeEnum.DELETE, desc = "部门删除", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/delete/{id}")
    public JsonResult delete(@ApiParam(value = "部门id", required = true)  @PathVariable String id){
        if (deptService.hasChildById(id)) {
            return JsonResult.error(ConstantForError.EXISTE_DEPT_CHILD);
        }
        if (deptService.checkDeptExistUser(id)) {
            return JsonResult.error(ConstantForError.EXISTE_DEPT_USER);
        }
        return JsonResult.ok(deptService.delete(id));
    }

}
