package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.HikService;
import com.znv.manage.service.YwgdService;
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
 * @Title: ConformingBuildingController
 * @Description:
 * @Author: 叶志鑫
 * @Date: 2021/07/14
 **/
@Slf4j
@RestController
@Api(tags = "违建预警")
@RequestMapping("/conformingBuilding")
public class ConformingBuildingController {

    @Autowired
    YwgdService ywgdService;

    @ApiOperation(value = "/违建预警数据")
    @GetMapping("/getConformingBuildingAlarm")
    public Result getConformingBuildingAlarm(@RequestParam(value = "beginTime", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                                             @RequestParam(value = "endTime", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                             @RequestParam(value = "precinctId", required = false)
                                                     String precinctId,
                                             @RequestParam(value = "precinctName", required = false)
                                                     String precinctName,
                                             @RequestParam(value = "limit", required = false) Integer limit) {
        Result result = new Result();
        List<Map<String, Object>> list = ywgdService.selectConformingBuildingAlarm(beginTime, endTime,precinctId, precinctName,limit);
        int count = ywgdService.getConformingBuildingAlarmCount(beginTime, endTime);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        result.setData(map);
        return result;
    }

}
