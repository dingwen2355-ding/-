package cn.wxgis.jc.system.web.controller;

import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.web.controller.BaseController;
import cn.wxgis.jc.log.annotation.Log;
import cn.wxgis.jc.log.enums.OperLogTypeEnum;
import cn.wxgis.jc.log.enums.OperLogUserTypeEnum;
import cn.wxgis.jc.system.po.SysDictType;
import cn.wxgis.jc.system.req.SysDictTypeSaveRequest;
import cn.wxgis.jc.system.select.SysDictTypeSelect;
import cn.wxgis.jc.system.web.service.SysDictTypeService;
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
@RequestMapping("/dictType")
@AllArgsConstructor
@Api(value = "SysDictTypeController", tags = "字典类型相关APi")
public class SysDictTypeController extends BaseController {

    private final SysDictTypeService dictTypeService;

    @ApiOperation(value ="字典类型分页列表", httpMethod ="POST")
    @PostMapping("/page")
    public JsonResult page(@RequestBody SysDictTypeSelect select) {
        IPage<SysDictType> page = dictTypeService.page(select);
        return JsonResult.ok(page);
    }

    @ApiOperation(value ="字典类型详情", httpMethod ="POST")
    @PostMapping("/{id}")
    public JsonResult info(@ApiParam(value = "字典类型id", required = true)  @PathVariable String id){
        return JsonResult.ok(dictTypeService.selectById(id));
    }


    @ApiOperation(value ="字典类型新增", httpMethod ="POST")
    @Log(title = "字典类型管理", type = OperLogTypeEnum.INSERT, desc = "字典类型新增", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/save")
    public JsonResult save(@Validated @RequestBody SysDictTypeSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.dictType, request.getDictType());
        ExistsReturn existsReturn = dictTypeService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(dictTypeService.save(request));
    }

    @ApiOperation(value ="字典类型修改", httpMethod ="POST")
    @Log(title = "字典类型管理", type = OperLogTypeEnum.UPDATE, desc = "字典类型修改", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/update")
    public JsonResult update(@Validated @RequestBody SysDictTypeSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.dictType, request.getDictType());
        existsParam.put(ExistsParam.id, request.getId());
        ExistsReturn existsReturn = dictTypeService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(dictTypeService.edit(request));
    }

    @ApiOperation(value ="字典类型删除", httpMethod ="POST")
    @Log(title = "字典类型管理", type = OperLogTypeEnum.DELETE, desc = "字典类型删除", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/delete/{id}")
    public JsonResult delete(@ApiParam(value = "字典类型id", required = true) @PathVariable String id){
        return JsonResult.ok(dictTypeService.delete(id));
    }


}
