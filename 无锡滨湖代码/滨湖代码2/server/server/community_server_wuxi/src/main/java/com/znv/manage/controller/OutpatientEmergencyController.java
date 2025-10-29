package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.MzJzGhService;
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
 * @Title: OutpatientEmergencyController
 * @Description:
 * @Author: 叶志鑫
 * @Date: 2021/07/14
 **/
@Slf4j
@RestController
@Api(tags = "/门诊/急诊挂号")
@RequestMapping("/outpatientEmergency")
public class OutpatientEmergencyController {

    @Autowired
    MzJzGhService mzJzGhService;

    @ApiOperation(value = "/门诊/急诊挂号数据")
    @GetMapping("/getOutpatientEmergency")
    public Result getOutpatientEmergency(@RequestParam(value = "beginTime", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                                             @RequestParam(value = "endTime", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                             @RequestParam(value = "limit", required = false) Integer limit,
                                             @RequestParam(value = "precinctId", required = false) String precinctId,
                                             @RequestParam(value = "precinctName", required = false) String precinctName) {
        return mzJzGhService.getOutpatientEmergencyCount(beginTime, endTime, precinctId, precinctName);
    }


}
