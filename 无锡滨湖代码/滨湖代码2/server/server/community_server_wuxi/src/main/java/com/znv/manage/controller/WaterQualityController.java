package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.AirQualityService;
import com.znv.manage.service.KqjczdService;
import com.znv.manage.service.SzzdService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: WaterQualityController
 * @Description:
 * @Author: yzx
 * @Date: 2021/07/20
 **/
@Slf4j
@RestController
@Api(tags = "水质监测")
@RequestMapping("/waterQuality")
public class WaterQualityController {

    @Autowired
    SzzdService szzdService;

    @Autowired
    WaterAuthorityService waterAuthorityService;

    @ApiOperation(value = "/水质监测站点数据")
    @GetMapping("/getWaterQuality")
    public Result getWaterQuality(@RequestParam(value = "precinctId", required = false)
                                          String precinctId,
                                  @RequestParam(value = "precinctName", required = false)
                                          String precinctName) {
        Result result = new Result();
        //查询水质测站点信息
        List<Map<String, Object>> list = szzdService.selectSiteList(precinctId,precinctName);

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        result.setData(map);
        return result;
    }

    @ApiOperation(value = "/水质数据")
    @GetMapping("/getWaterQualityRealAndHistory")
    public Result getWaterQualityRealAndHistory(@RequestParam(value = "stationname", required = false) String stationname,
                                                @RequestParam(value = "beginTime", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                                                @RequestParam(value = "endTime", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                                @RequestParam(value = "limit", required = false) Integer limit
    ) {
        Result result = new Result();

        //实时数据
        List<Map<String, Object>> reallist = waterAuthorityService.selectWaterQualityReal(stationname);
        //历史数据
        List<Map<String, Object>> historylist = waterAuthorityService.selectWaterQualityHistory(stationname, beginTime, endTime, limit);
        Map<String, Object> map = new HashMap<>();
        map.put("reallist", reallist);
        map.put("historylist", historylist);
        result.setData(map);
        return result;
    }
}
