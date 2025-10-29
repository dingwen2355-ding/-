package com.znv.manage.controller.resource;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.resource.CommonMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下拉菜单管理
 *
 * @author 0049003714
 */
@Api(tags = "下拉菜单管理")
@RestController
@RequestMapping("/menu")
public class CommonMenuController {

    @Autowired
    CommonMenuService commonMenuService;

    @ApiOperation("下拉菜单-所属辖区")
    @GetMapping("getRegion")
    public Result getRegion() {
        Result result = commonMenuService.getRegion();
        return result;
    }

    @ApiOperation("下拉菜单-管理单位")
    @GetMapping("getBelongUnit")
    public Result getBelongUnit() {
        Result result = commonMenuService.getBelongUnit();
        return result;
    }

    @ApiOperation("下拉菜单-管理类型")
    @GetMapping("getBeManagedType")
    public Result getBeManagedType() {
        Result result = commonMenuService.getBeManagedType();
        return result;
    }

    @ApiOperation("下拉菜单-管理级别")
    @GetMapping("getStorageLevel")
    public Result getStorageLevel() {
        Result result = commonMenuService.getStorageLevel();
        return result;
    }

    @ApiOperation("下拉菜单-存储类型")
    @GetMapping("getStorageType")
    public Result getStorageType() {
        Result result = commonMenuService.getStorageType();
        return result;
    }

    @ApiOperation("下拉菜单-所属行业")
    @GetMapping("getIndustry")
    public Result getIndustry() {
        Result result = commonMenuService.getIndustry();
        return result;
    }

    @ApiOperation("下拉菜单-救援类别")
    @GetMapping("getHelpType")
    public Result getHelpType() {
        Result result = commonMenuService.getHelpType();
        return result;
    }

}
