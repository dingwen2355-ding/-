package cn.wxgis.jc.system.web.controller;

import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.common.web.controller.BaseController;
import cn.wxgis.jc.log.annotation.Log;
import cn.wxgis.jc.log.enums.OperLogTypeEnum;
import cn.wxgis.jc.log.enums.OperLogUserTypeEnum;
import cn.wxgis.jc.system.model.SysDictDataVO;
import cn.wxgis.jc.system.po.SysDictData;
import cn.wxgis.jc.system.req.SysDictDataSaveRequest;
import cn.wxgis.jc.system.select.SysDictDataSelect;
import cn.wxgis.jc.system.web.service.SysDictDataService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
@RequestMapping("/dictData")
@AllArgsConstructor
@Api(value = "SysDictDataController", tags = "字典数据相关APi")
public class SysDictDataController extends BaseController {

    private final SysDictDataService dictDataService;

    @ApiOperation(value ="字典数据分页列表", httpMethod ="POST")
    @PostMapping("/page")
    public JsonResult page(@RequestBody SysDictDataSelect select) {
        IPage<SysDictData> page = dictDataService.page(select);
        return JsonResult.ok(page);
    }

    @ApiOperation(value ="字典数据列表", httpMethod ="GET")
    @GetMapping("/list/{dictType}")
    public JsonResult list(@ApiParam(value = "字典类型", required = true)  @PathVariable String dictType) {
        List<SysDictData> list = dictDataService.listByDictType(dictType);
        return JsonResult.ok(Constant.resultKey, list);
    }

    @ApiOperation(value ="字典类型详情", httpMethod ="POST")
    @PostMapping("/{id}")
    public JsonResult info(@ApiParam(value = "字典类型id", required = true)  @PathVariable String id){
        return JsonResult.ok(dictDataService.selectById(id));
    }

    @ApiOperation(value ="字典数据新增", httpMethod ="POST")
    @Log(title = "字典数据管理", type = OperLogTypeEnum.INSERT, desc = "字典数据新增", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/save")
    public JsonResult save(@Validated @RequestBody SysDictDataSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.dictType, request.getDictType());
        existsParam.put(ExistsParam.dictLabel, request.getDictLabel());
        existsParam.put(ExistsParam.dictValue, request.getDictValue());
        ExistsReturn existsReturn = dictDataService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(dictDataService.save(request));
    }

    @ApiOperation(value ="字典数据修改", httpMethod ="POST")
    @Log(title = "字典数据管理", type = OperLogTypeEnum.UPDATE, desc = "字典数据修改", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/update")
    public JsonResult update(@Validated @RequestBody SysDictDataSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.dictType, request.getDictType());
        existsParam.put(ExistsParam.dictLabel, request.getDictLabel());
        existsParam.put(ExistsParam.dictValue, request.getDictValue());
        existsParam.put(ExistsParam.id, request.getId());
        ExistsReturn existsReturn = dictDataService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(dictDataService.edit(request));
    }

    @ApiOperation(value ="字典数据删除", httpMethod ="POST")
    @Log(title = "字典数据管理", type = OperLogTypeEnum.DELETE, desc = "字典数据删除", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/delete/{id}")
    public JsonResult delete(@ApiParam(value = "字典类型id", required = true)  @PathVariable String id){
        return JsonResult.ok(dictDataService.delete(id));
    }

    @Log(title = "设置字典值", type = OperLogTypeEnum.UPDATE, operUserType = OperLogUserTypeEnum.MANAGE)
    @ApiOperation(value ="设置字典值", httpMethod ="POST")
    @PostMapping("/set")
    public JsonResult set(@ApiParam(value = "新增传参") @Validated @RequestBody List<SysDictDataSaveRequest> requests){
        if (StringUtils.isEmpty(requests)) return JsonResult.ok();
        int r = dictDataService.set(requests);
        return JsonResult.ok(r);
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @ApiOperation(value ="根据字典key查询字典数据", httpMethod ="GET")
    @GetMapping(value = "/type/{dictType}")
    public JsonResult dictType(@PathVariable String dictType) {
        List<SysDictData> data = dictDataService.getDictDataByDictType(dictType);
        return JsonResult.ok(data);
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @ApiOperation(value ="查询所有数据字典", httpMethod ="GET")
    @GetMapping(value = "/all")
    public JsonResult all() {
        List<SysDictDataVO> data = dictDataService.getAllDict();
        return JsonResult.ok(Constant.resultKey, data);
    }

}
