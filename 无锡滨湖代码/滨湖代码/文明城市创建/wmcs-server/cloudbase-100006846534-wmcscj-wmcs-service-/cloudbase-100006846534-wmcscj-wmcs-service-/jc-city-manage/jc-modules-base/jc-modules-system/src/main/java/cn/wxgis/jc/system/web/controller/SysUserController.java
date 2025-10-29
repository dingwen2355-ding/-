package cn.wxgis.jc.system.web.controller;

import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.web.controller.BaseController;
import cn.wxgis.jc.log.annotation.Log;
import cn.wxgis.jc.log.enums.OperLogTypeEnum;
import cn.wxgis.jc.log.enums.OperLogUserTypeEnum;
import cn.wxgis.jc.system.req.SysUserSaveRequest;
import cn.wxgis.jc.system.resp.SysUserResponse;
import cn.wxgis.jc.system.select.SysUserSelect;
import cn.wxgis.jc.system.web.service.SysUserService;
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
@RequestMapping("/user")
@AllArgsConstructor
@Api(value = "SysUserController", tags = "用户信息相关APi")
public class SysUserController extends BaseController {

    private final SysUserService userService;

    @ApiOperation(value ="用户分页列表", httpMethod ="POST")
    @PostMapping("/page")
    public JsonResult page(@RequestBody SysUserSelect select) {
        IPage<SysUserResponse> page = userService.page(select);
        return JsonResult.ok(page);
    }

    @ApiOperation(value ="用户详情", httpMethod ="POST")
    @PostMapping("/{id}")
    public JsonResult info(@ApiParam(value = "用户id", required = true) @PathVariable String id){
        SysUserResponse user = userService.findResponseById(id);
        return JsonResult.ok(user);
    }


    @ApiOperation(value ="用户新增", httpMethod ="POST")
    @Log(title = "用户管理", type = OperLogTypeEnum.INSERT, desc = "用户新增", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/save")
    public JsonResult save(@Validated @RequestBody SysUserSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.userName, request.getUserName());
        existsParam.put(ExistsParam.phonenumber, request.getPhonenumber());
        ExistsReturn existsReturn = userService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(userService.save(request));
    }

    @ApiOperation(value ="用户修改", httpMethod ="POST")
    @Log(title = "用户管理", type = OperLogTypeEnum.UPDATE, desc = "用户修改", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/update")
    public JsonResult update(@Validated @RequestBody SysUserSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.userName, request.getUserName());
        existsParam.put(ExistsParam.phonenumber, request.getPhonenumber());
        existsParam.put(ExistsParam.id, request.getId());
        ExistsReturn existsReturn = userService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(userService.edit(request));
    }

    @ApiOperation(value ="用户删除", httpMethod ="POST")
    @Log(title = "用户管理", type = OperLogTypeEnum.DELETE, desc = "用户删除", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/delete/{id}")
    public JsonResult delete(@ApiParam(value = "用户id", required = true) @PathVariable String id){
        return JsonResult.ok(userService.delete(id));
    }
}
