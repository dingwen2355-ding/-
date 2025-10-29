package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.QxyjxxService;
import com.znv.manage.service.ShelterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: ShelterController
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/9/6 16:05
 **/
@Api(tags = "避难场所")
@RestController
@RequestMapping("shelter")
public class ShelterController {

    @Autowired
    ShelterService shelterService;

    @ApiOperation("坐标撒点")
    @GetMapping("getshelterCoordinatesList")
    public Result getshelterCoordinatesList() {
        Result result = shelterService.shelterCoordinatesList();
        return result;
    }

    @ApiOperation("查询坐标详情")
    @GetMapping("queryCoordinatesDetails")
    public Result queryCoordinatesDetails( @RequestParam(value = "id", required = true) Integer id) {
        Result result = shelterService.queryCoordinatesDetails(id);
        return result;
    }
}
