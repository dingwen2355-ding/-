package com.znv.manage.controller;

import com.znv.manage.common.bean.Device;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.DeviceService;
import com.znv.manage.service.QxyjxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 应急预案相关接口
 */
@Api(tags = "查询气象监测数据,气象局-气象预警信息")
@RestController
@RequestMapping("qxyjxx")
public class QxyjxxController {


    @Autowired
    QxyjxxService qxyjxxService;

    @ApiOperation("查询气象监测数据,气象局-气象预警信息")
    @GetMapping("getQxyjxx")
    public Result getQxyjxx(
            @RequestParam(value = "beginTime", required = false)
                    String beginTime,
            @RequestParam(value = "endTime", required = false)
                    String endTime,
            @RequestParam(value = "limit", required = false)
                    Integer limit) {
        Result result = qxyjxxService.getQxyjxx(beginTime, endTime);
        return result;
    }


}
