package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.FwgdbService;
import com.znv.manage.service.HikService;
import com.znv.manage.service.SpAIgjxxService;
import com.znv.manage.service.SzxxbService;
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

/**
 * @Title: AgedServiceController
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/7/15 14:34
 **/
@Slf4j
@RestController
@Api(tags = "实时预警-民生服务")
@RequestMapping("/AgedService")
public class HealthServiceController {
    @Autowired
    HikService hikService;

    @Autowired
    FwgdbService fwgdbService;

    @Autowired
    SzxxbService szxxbService;

    @ApiOperation(value = "/养老服务-当日就诊人数")
    @GetMapping("/getAgedServiceToday")
    public Result getAgedServiceToday(@RequestParam(value = "beginTime", required = false)
                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                      @RequestParam(value = "endTime", required = false)
                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        Result fwgdbStatistics = fwgdbService.getFwgdbStatistics(beginTime, endTime);
        return fwgdbStatistics;
    }

    @ApiOperation(value = "/智慧殡葬-当日就诊人数")
    @GetMapping("/getSzxxbToday")
    public Result getSzxxbToday(@RequestParam(value = "beginTime", required = false)
                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                      @RequestParam(value = "endTime", required = false)
                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        Result szxxbStatistics = szxxbService.getSzxxbStatistics(beginTime, endTime);
        return szxxbStatistics;
    }
}
