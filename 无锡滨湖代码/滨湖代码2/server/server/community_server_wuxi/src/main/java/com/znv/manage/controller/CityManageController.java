package com.znv.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.CityManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应急预案相关接口
 */
@Api(tags = "查询城管")
@RestController
@RequestMapping("cityManage")
public class CityManageController {

    @Autowired
    CityManageService cityManageService;

    @ApiOperation("查询城管部件")
    @GetMapping("getCityManageCategory")
    public Result getCityManageCategory() {
        Result result = new Result();
        JSONArray list = cityManageService.getCityManageCategory();
        result.setData(list);
        return result;
    }


}
