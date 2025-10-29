package cn.wxgis.jc.system.web.controller;

import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.system.resp.SysRoleMenuResponse;
import cn.wxgis.jc.system.web.service.SysRoleMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/roleMenu")
@AllArgsConstructor
@Api(value = "SysRoleMenuController", tags = "角色菜单相关APi")
public class SysRoleMenuController {

    private final SysRoleMenuService roleMenuService;

    @ApiOperation(value ="获取角色资源列表", httpMethod ="GET")
    @GetMapping("/list/{roleId}")
    public JsonResult list(@ApiParam(value = "角色id", required = true) @PathVariable String roleId) {
        if (StringUtils.isEmpty(roleId)) return JsonResult.error(GlobalStatusEnum.PARAM_VALIDATED);
        List<SysRoleMenuResponse> list = roleMenuService.list(roleId);
        return JsonResult.ok(Constant.resultKey, list);
    }

}
