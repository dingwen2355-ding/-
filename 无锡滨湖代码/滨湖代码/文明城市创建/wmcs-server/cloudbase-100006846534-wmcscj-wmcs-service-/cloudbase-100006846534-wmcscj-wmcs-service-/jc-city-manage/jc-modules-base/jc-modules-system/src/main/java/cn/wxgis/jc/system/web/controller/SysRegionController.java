package cn.wxgis.jc.system.web.controller;

import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.web.controller.BaseController;
import cn.wxgis.jc.log.annotation.Log;
import cn.wxgis.jc.log.enums.OperLogTypeEnum;
import cn.wxgis.jc.log.enums.OperLogUserTypeEnum;
import cn.wxgis.jc.system.req.SysRegionSaveRequest;
import cn.wxgis.jc.system.resp.SysRegionResponse;
import cn.wxgis.jc.system.select.SysRegionSelect;
import cn.wxgis.jc.system.web.service.SysRegionService;
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
@RequestMapping("/region")
@AllArgsConstructor
@Api(value = "SysRegionController", tags = "区划相关APi")
public class SysRegionController extends BaseController {

    private final SysRegionService regionService;


    @ApiOperation(value ="区划列表", httpMethod ="POST")
    @PostMapping("/list")
    public JsonResult list(@RequestBody SysRegionSelect select) {
        List<SysRegionResponse> regionTree = regionService.listRegionTree(select);
        return JsonResult.ok(Constant.resultKey, regionTree);
    }

    @ApiOperation(value ="区划详情", httpMethod ="POST")
    @PostMapping("/{id}")
    public JsonResult info(@ApiParam(value = "区划id", required = true) @PathVariable String id){
        return JsonResult.ok(regionService.findResponseById(id));
    }

    @ApiOperation(value ="区划新增", httpMethod ="POST")
    @Log(title = "区划管理", type = OperLogTypeEnum.INSERT, desc = "区划新增", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/save")
    public JsonResult save(@Validated @RequestBody SysRegionSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.regionName, request.getRegionName());
        existsParam.put(ExistsParam.regionCode, request.getRegionCode());
        existsParam.put(ExistsParam.parentId, request.getParentId());
        ExistsReturn existsReturn = regionService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(regionService.save(request));
    }

    @ApiOperation(value ="区划修改", httpMethod ="POST")
    @Log(title = "区划管理", type = OperLogTypeEnum.UPDATE, desc = "区划修改", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/update")
    public JsonResult update(@Validated @RequestBody SysRegionSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.regionName, request.getRegionName());
        existsParam.put(ExistsParam.regionCode, request.getRegionCode());
        existsParam.put(ExistsParam.parentId, request.getParentId());
        existsParam.put(ExistsParam.id, request.getId());
        ExistsReturn existsReturn = regionService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(regionService.edit(request));
    }

    @ApiOperation(value ="区划删除", httpMethod ="POST")
    @Log(title = "区划管理", type = OperLogTypeEnum.DELETE, desc = "区划删除", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/delete/{id}")
    public JsonResult delete(@ApiParam(value = "区划id", required = true)  @PathVariable String id){
        return JsonResult.ok(regionService.delete(id));
    }


    @ApiOperation(value ="更新所属部门", httpMethod ="GET")
    @GetMapping("/updateByDept")
    public JsonResult updateByDept() {
        return JsonResult.ok(regionService.updateByDept());
    }
}
