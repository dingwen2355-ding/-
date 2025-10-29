package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.HikService;
import com.znv.manage.service.SpAIgjxxService;
import com.znv.manage.service.ZnsbgjsjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Title: SpAIgjxxController
 * @Description:
 * @Author: 李贝德
 * @Date: 2021/7/15 9:41
 **/
@Slf4j
@RestController
@Api(tags = "实时预警-城市管理")
@RequestMapping("/SpAIgjxx")
public class CityMangementController {

    @Autowired
    HikService hikService;

    @Autowired
    SpAIgjxxService spAIgjxxService;

    @Autowired
    ZnsbgjsjService znsbgjsjService;

    @ApiOperation(value = "/街面秩序-当日视频AI告警数据")
    @GetMapping("/getSpAIgjxxToday")
    public Result getSpAIgjxxToday( @RequestParam(value = "beginTime", required = false)
                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                       @RequestParam(value = "endTime", required = false)
                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        Result result = spAIgjxxService.getSpAIgjxxStatistics(beginTime,endTime);
        return result;
    }

    @ApiOperation(value = "/工地预警-当日告警数据")
    @GetMapping("/getZnsbjsjToday")
    public Result getZnsbjsjToday(@RequestParam(value = "beginTime", required = false)
                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                      @RequestParam(value = "endTime", required = false)
                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        Result result = znsbgjsjService.getZnsbgjsjStatistics(beginTime,endTime);
        return result;
    }

    @ApiOperation(value = "/视频AI预警-图片轮播")
    @GetMapping("/getSpAIyjStatistics")
    public Result getSpAIyjStatistics(@RequestParam(value = "beginTime", required = false)
                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                                  @RequestParam(value = "endTime", required = false)
                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        Result result = spAIgjxxService.getSpAIyjStatistics(beginTime, endTime);
        return result;
    }

    @ApiOperation(value = "/视频AI预警-告警统计当日TOP5")
    @GetMapping("/SpAIgjxxTop5Today")
    public Result SpAIgjxxTop5Today(@RequestParam(value = "beginTime", required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                                      @RequestParam(value = "endTime", required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        Result result = spAIgjxxService.SpAIgjxxTop5Today(beginTime, endTime);
        return result;


    }
}
