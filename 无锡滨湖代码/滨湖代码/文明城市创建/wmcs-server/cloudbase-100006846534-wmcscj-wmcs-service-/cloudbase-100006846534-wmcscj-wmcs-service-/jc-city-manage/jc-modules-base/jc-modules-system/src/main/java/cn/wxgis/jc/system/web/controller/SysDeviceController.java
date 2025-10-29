package cn.wxgis.jc.system.web.controller;

import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.log.annotation.Log;
import cn.wxgis.jc.log.enums.OperLogTypeEnum;
import cn.wxgis.jc.log.enums.OperLogUserTypeEnum;
import cn.wxgis.jc.system.po.SysDevice;
import cn.wxgis.jc.system.req.SysDeviceBindUserRequest;
import cn.wxgis.jc.system.req.SysDeviceSaveRequest;
import cn.wxgis.jc.system.req.SysUserSaveRequest;
import cn.wxgis.jc.system.resp.SysDeviceResponse;
import cn.wxgis.jc.system.resp.SysUserResponse;
import cn.wxgis.jc.system.select.SysDeviceSelect;
import cn.wxgis.jc.system.select.SysUserSelect;
import cn.wxgis.jc.system.web.service.SysDeviceService;
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
@RequestMapping("/device")
@AllArgsConstructor
@Api(value = "SysDeviceController", tags = "设备管理")
public class SysDeviceController {

    private final SysDeviceService deviceService;

    @ApiOperation(value ="设备分页列表", httpMethod ="POST")
    @PostMapping("/page")
    public JsonResult page(@RequestBody SysDeviceSelect select) {
        IPage<SysDeviceResponse> page = deviceService.page(select);
        return JsonResult.ok(page);
    }

    @ApiOperation(value ="设备详情", httpMethod ="POST")
    @PostMapping("/get/{id}")
    public JsonResult info(@ApiParam(value = "用户id", required = true) @PathVariable String id){
        SysDeviceResponse user = deviceService.findResponseById(id);
        return JsonResult.ok(user);
    }


    @ApiOperation(value ="设备新增", httpMethod ="POST")
    @Log(title = "设备管理", type = OperLogTypeEnum.INSERT, desc = "设备新增", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/save")
    public JsonResult save(@Validated @RequestBody SysDeviceSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.name, request.getName());
        existsParam.put(ExistsParam.code, request.getCode());
        ExistsReturn existsReturn = deviceService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(deviceService.save(request));
    }

    @ApiOperation(value ="设备修改", httpMethod ="POST")
    @Log(title = "设备管理", type = OperLogTypeEnum.UPDATE, desc = "设备修改", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/update")
    public JsonResult update(@Validated @RequestBody SysDeviceSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.name, request.getName());
        existsParam.put(ExistsParam.code, request.getCode());
        existsParam.put(ExistsParam.id, request.getId());
        ExistsReturn existsReturn = deviceService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(deviceService.edit(request));
    }

    @ApiOperation(value ="设备删除", httpMethod ="POST")
    @Log(title = "设备管理", type = OperLogTypeEnum.DELETE, desc = "设备删除", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/delete/{id}")
    public JsonResult delete(@ApiParam(value = "id", required = true) @PathVariable String id){
        return JsonResult.ok(deviceService.delete(id));
    }

    @ApiOperation(value ="绑定用户", httpMethod ="POST")
    @Log(title = "设备管理", type = OperLogTypeEnum.UPDATE, desc = "绑定用户", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/bind")
    public JsonResult bind(@Validated @RequestBody SysDeviceBindUserRequest request){
        return JsonResult.ok(deviceService.bind(request));
    }

    @ApiOperation(value ="解除绑定", httpMethod ="POST")
    @Log(title = "设备管理", type = OperLogTypeEnum.UPDATE, desc = "解除绑定", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/unbind")
    public JsonResult unbind(@Validated @RequestBody SysDeviceBindUserRequest request){
        return JsonResult.ok(deviceService.unbind(request));
    }


}
