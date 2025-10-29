package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.SyncCar;
import com.znv.manage.service.SyncCarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * TDataSyncCar表控制层
 *
 * @author 0049003788
 * @since 2023-01-10 16:11:04
 */
@RestController
@RequestMapping("syncCar")
@Api(tags = "同步对接车辆")
public class SyncCarController {
    @Resource
    private SyncCarService syncCarService;

    @PostMapping("/add")
    @ApiOperation("新增")
    public Result add(@RequestBody SyncCar syncCar) {
        return syncCarService.insert(syncCar);
    }

    @PostMapping("/edit")
    @ApiOperation("修改")
    public Result update(@RequestBody SyncCar syncCar) {
        return syncCarService.update(syncCar);
    }

    @GetMapping("/del")
    @ApiOperation("删除")
    public Result deleteById(@RequestParam(value = "id") String id) {
        return syncCarService.deleteById(id);
    }

    @GetMapping("/del-batch")
    @ApiOperation("批量删除")
    public Result deleteBatch(@RequestParam(value = "ids") String ids) {
        return syncCarService.deleteBatch(ids);
    }

    @GetMapping("/query-condition")
    @ApiOperation("查询")
    public Result queryByCondition(@RequestParam(value = "license", required = false) String license,
                                   @RequestParam(value = "dept", required = false) String dept,
                                   @RequestParam(value = "online", required = false) String online,
                                   @RequestParam(value = "type", required = false) String type,
                                   @RequestParam(value = "districtCode", required = false) String districtCode,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return syncCarService.queryByCondition(license, dept, online, type, districtCode, page, pageSize);
    }
}

