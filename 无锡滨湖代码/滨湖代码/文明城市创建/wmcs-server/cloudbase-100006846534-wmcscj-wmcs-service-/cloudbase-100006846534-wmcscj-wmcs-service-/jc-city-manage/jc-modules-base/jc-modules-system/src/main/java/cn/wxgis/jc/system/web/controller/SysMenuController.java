package cn.wxgis.jc.system.web.controller;

import cn.wxgis.jc.common.constant.Constant;
import cn.wxgis.jc.common.constant.ConstantForError;
import cn.wxgis.jc.common.domain.exists.ExistsParam;
import cn.wxgis.jc.common.domain.exists.ExistsReturn;
import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.enums.system.MenuPlatformEnum;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.log.annotation.Log;
import cn.wxgis.jc.log.enums.OperLogTypeEnum;
import cn.wxgis.jc.log.enums.OperLogUserTypeEnum;
import cn.wxgis.jc.security.utils.SecurityUtils;
import cn.wxgis.jc.system.req.SysMenuSaveRequest;
import cn.wxgis.jc.system.resp.LoginMenuResponse;
import cn.wxgis.jc.system.resp.SysMenuResponse;
import cn.wxgis.jc.system.select.SysMenuSelect;
import cn.wxgis.jc.system.web.service.SysMenuService;
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
@RequestMapping("/menu")
@AllArgsConstructor
@Api(value = "SysMenuController", tags = "菜单相关APi")
public class SysMenuController {

    private final SysMenuService menuService;

    @ApiOperation(value ="菜单列表", httpMethod ="POST")
    @PostMapping("/list")
    public JsonResult list(@RequestBody SysMenuSelect select) {
        List<SysMenuResponse> deptTree = menuService.listMenuTree(select);
        return JsonResult.ok(Constant.resultKey, deptTree);
    }

    @ApiOperation(value ="菜单详情", httpMethod ="POST")
    @PostMapping("/{id}")
    public JsonResult info(@ApiParam(value = "菜单id", required = true) @PathVariable String id){
        return JsonResult.ok(menuService.findResponseById(id));
    }


    @ApiOperation(value ="菜单新增", httpMethod ="POST")
    @Log(title = "菜单管理", type = OperLogTypeEnum.INSERT, desc = "菜单新增", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/save")
    public JsonResult save(@Validated @RequestBody SysMenuSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.name, request.getName());
        existsParam.put(ExistsParam.parentId, request.getParentId());
        existsParam.put(ExistsParam.platform, String.valueOf(request.getPlatform()));
        ExistsReturn existsReturn = menuService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(menuService.save(request));
    }

    @ApiOperation(value ="菜单修改", httpMethod ="POST")
    @Log(title = "菜单管理", type = OperLogTypeEnum.UPDATE, desc = "菜单修改", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/update")
    public JsonResult update(@Validated @RequestBody SysMenuSaveRequest request){
        Map<String, String> existsParam = new HashMap<>();
        existsParam.put(ExistsParam.name, request.getName());
        existsParam.put(ExistsParam.parentId, request.getParentId());
        existsParam.put(ExistsParam.id, request.getId());
        existsParam.put(ExistsParam.platform, String.valueOf(request.getPlatform()));
        ExistsReturn existsReturn = menuService.exists(existsParam);
        if (existsReturn.isExists()) return JsonResult.error(GlobalStatusEnum.DATA_EXISTE, existsReturn.getMessage());
        return JsonResult.ok(menuService.edit(request));
    }

    @ApiOperation(value ="菜单删除", httpMethod ="POST")
    @Log(title = "菜单管理", type = OperLogTypeEnum.DELETE, desc = "菜单删除", operUserType = OperLogUserTypeEnum.MANAGE)
    @PostMapping("/delete/{id}")
    public JsonResult delete(@ApiParam(value = "菜单id", required = true) @PathVariable String id){
        if (menuService.hasChildByMenuId(id)) {
            return JsonResult.error(ConstantForError.EXISTE_MENU_CHILD);
        }
        if (menuService.checkMenuExistRole(id)) {
            return JsonResult.error(ConstantForError.EXISTE_MENU_ROLE);
        }
        return JsonResult.ok(menuService.delete(id));
    }


    /**
     * 获取路由信息
     */
    @GetMapping("getRouters")
    @ApiOperation(value ="获取登陆路由菜单", httpMethod ="GET")
    public JsonResult getRouters(Integer platform) {
        if (StringUtils.isNull(platform)) platform = MenuPlatformEnum.PC.getCode();
        if (StringUtils.isNull(SecurityUtils.getLoginUser())) return JsonResult.error(GlobalStatusEnum.FORBIDDEN);
        String userId = SecurityUtils.getUserId();
        LoginMenuResponse menus = menuService.getMenuTreeByUserId(userId, platform);
        return JsonResult.ok(menus);
    }

}
