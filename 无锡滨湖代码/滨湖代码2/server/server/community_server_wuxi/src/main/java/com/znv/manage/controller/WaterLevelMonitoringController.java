package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.JczdjbxxService;
import com.znv.manage.service.MzJzGhService;
import com.znv.manage.service.SwService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: WaterLevelMonitoringController
 * @Description:
 * @Author: 叶志鑫
 * @Date: 2021/07/14
 **/
@Slf4j
@RestController
@Api(tags = "水位/雨量监测")
@RequestMapping("/waterLevelMonitoring")
public class WaterLevelMonitoringController {

    @Autowired
    JczdjbxxService jczdjbxxService;
    @Autowired
    SwService swService;

    @ApiOperation(value = "/水位监测站点数据")
    @GetMapping("/getWaterLevelMonitoring")
    public Result getWaterLevelMonitoring(@RequestParam(value = "precinctId", required = false)
                                                  String precinctId,
                                          @RequestParam(value = "precinctName", required = false)
                                                  String precinctName) {
        Result result = new Result();
        //查询水位 雨量监测站点信息
        List<Map<String, Object>> waterZd = jczdjbxxService.selectWater(precinctId,precinctName);
        List<Map<String, Object>> rainZd = jczdjbxxService.selectRainFall(precinctId,precinctName);

        Map<String, Object> map = new HashMap<>();
        map.put("waterZd", waterZd);
        map.put("rainZd", rainZd);
        result.setData(map);
        return result;
    }

    @ApiOperation(value = "/水位雨量数据")
    @GetMapping("/getWaterAndRainList")
    public Result getWaterAndRainList(@RequestParam(value = "stcd", required = false) String stcd,
                                      @RequestParam(value = "beginTime", required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                                      @RequestParam(value = "endTime", required = false)
                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                      @RequestParam(value = "limit", required = false) Integer limit
    ) {
        Result result = new Result();

        //实时数据
        List<Map<String, Object>> reallist = swService.selectRealWaterAndRainList(stcd);
        //查询雨量、水位历史数据
        List<Map<String, Object>> historylist = swService.selectHistoryWaterAndRainList(stcd, beginTime, endTime, limit);
        Map<String, Object> map = new HashMap<>();
        map.put("reallist", reallist);
        map.put("historylist", historylist);
        result.setData(map);
        return result;
    }
}
