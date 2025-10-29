package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.WaterPointsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 应急预案相关接口
 */
@Api(tags = "查询智慧办-道路积水监测点数据信息")
@RestController
@RequestMapping("waterPoints")
public class WaterPointsController {

    @Autowired
    WaterPointsService waterPointsService;

    @ApiOperation("查询智慧办-道路积水监测点数据信息")
    @GetMapping("getWaterPoints")
    public Result getWaterPoints(
            @RequestParam(value = "beginTime", required = false)
                    String beginTime,
            @RequestParam(value = "endTime", required = false)
                    String endTime,
            @RequestParam(value = "precinctId", required = false)
                    String precinctId,
            @RequestParam(value = "precinctName", required = false)
                    String precinctName) {
        Result result = waterPointsService.getWaterPoints(beginTime, endTime,precinctId,precinctName);
        return result;
    }

    @ApiOperation("更新街道信息")
    @PostMapping("updateWaterPointsStreet")
    public Result updateWaterPointsStreet() {
        Result result = waterPointsService.updateWaterPointsStreet();
        return result;
    }

}
