package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.service.HikService;
import com.znv.manage.service.WaterAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Title: WaterAuthorityController
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/6/29 15:51
 **/
@Slf4j
@RestController
@Api(tags = "实时预警-防台防汛")
@RequestMapping("/waterAuth")
public class WaterAuthorityController {

    @Autowired
    HikService hikService;

    @Autowired
    WaterAuthorityService waterAuthorityService;

    @ApiOperation(value = "/水位预警-当日告警数据")
    @GetMapping("/getZyjbToday")
    public Result get(@RequestParam(value = "beginTime", required = false)
                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                      @RequestParam(value = "endTime", required = false)
                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        Result yjzbStatistics = waterAuthorityService.getYjzbStatistics(beginTime,endTime);
        return yjzbStatistics;
    }
}
