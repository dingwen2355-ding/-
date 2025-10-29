package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.DingDingService;
import com.znv.manage.service.FireAwarenessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author:
 * @Date:
 * @Description: 注：
 */
@Api(tags = "消防安全-实时预警")
@RestController
public class FireAwarenessController {

    @Autowired
    DingDingService dingDingService;

    @Autowired
    FireAwarenessService fireAwarenessService;

    @ApiOperation(value = "实时感知预警")
    @PostMapping("/fireAwareness/getRealTimePerception")
    public Result syncDepartmentData(
            @RequestParam(value = "beginTime", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
            @RequestParam(value = "endTime", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
            @RequestParam(value = "precinctId", required = false)
                    String precinctId,
            @RequestParam(value = "precinctName", required = false)
                    String precinctName,
            @RequestParam(value = "limit", required = false) Integer limit
           ) {
        return fireAwarenessService.getRealTimePerception(beginTime, endTime, precinctId,precinctName,limit);
    }

    @ApiOperation(value = "传感器在线率")
    @PostMapping("/fireAwareness/sensorOnlineRate")
    public Result sensorOnlineRate(@RequestParam(value = "precinctId", required = false)
                                               String precinctId,
                                   @RequestParam(value = "precinctName", required = false)
                                               String precinctName) {
        return fireAwarenessService.sensorOnlineRate(precinctId,precinctName);
    }


}
