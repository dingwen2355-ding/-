package com.znv.manage.controller;

import com.znv.manage.common.bean.CityFirmware;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.CityFirmwareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * TCityFirmware表控制层
 *
 * @author 0049003788
 * @since 2023-03-01 14:45:11
 */
@RestController
@RequestMapping("cityFirmware")
@Api(tags = "城市部件")
public class CityFirmwareController {
    @Resource
    private CityFirmwareService cityFirmwareService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public Result add(@RequestBody CityFirmware cityFirmware) {
        return cityFirmwareService.insert(cityFirmware);
    }

    @PostMapping("/edit")
    @ApiOperation("修改")
    public Result update(@RequestBody CityFirmware cityFirmware) {
        return cityFirmwareService.update(cityFirmware);
    }

    @GetMapping("/del")
    @ApiOperation("删除")
    public Result deleteById(@RequestParam(value = "id") Integer id) {
        return cityFirmwareService.deleteById(id);
    }

    @GetMapping("/del-batch")
    @ApiOperation("批量删除")
    public Result deleteBatch(@RequestParam(value = "ids") String ids) {
        return cityFirmwareService.deleteBatch(ids);
    }

    @GetMapping("/query")
    @ApiOperation("查询")
    public Result queryByCondition(@RequestParam(value = "id", required = false) Integer id,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "parentType", required = false) String parentType,
                                   @RequestParam(value = "type", required = false) String type,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return cityFirmwareService.queryByCondition(id, name, parentType, type, page, pageSize);
    }
}

