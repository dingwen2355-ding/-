package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.EmergencySuppliesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: EmergencySuppliesController
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/9/6 16:34
 **/
@Api(tags = "应急物资")
@RestController
@RequestMapping("emergencySupplies")
public class EmergencySuppliesController {
    @Autowired
    EmergencySuppliesService emergencySuppliesService;

    @ApiOperation("坐标撒点")
    @GetMapping("emergencySuppliesCoordinatesList")
    public Result emergencySuppliesCoordinatesList() {
        Result result = emergencySuppliesService.emergencySuppliesCoordinatesList();
        return result;
    }

    @ApiOperation("查询坐标详情")
    @GetMapping("queryCoordinatesDetails")
    public Result queryCoordinatesDetails( @RequestParam(value = "id", required = true) Integer id) {
        Result result = emergencySuppliesService.queryCoordinatesDetails(id);
        return result;
    }
}
