package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.WhpjyxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应急预案相关接口
 */
@Api(tags = "查询气象监测数据 气象局-自动站实况监测数据信息")
@RestController
@RequestMapping("whpjyxx")
public class WhpjyxxController {


    @Autowired
    WhpjyxxService whpjyxxService;

    @ApiOperation("查询气象监测数据 气象局-自动站实况监测数据信息")
    @GetMapping("getWhpjyxx")
    public Result getWhpjyxx(
            @RequestParam(value = "beginTime", required = false)
                    String beginTime,
            @RequestParam(value = "endTime", required = false)
                    String endTime,
            @RequestParam(value = "limit", required = false)
                    Integer limit) {
        Result result = whpjyxxService.getWhpjyxx(beginTime, endTime, limit);
        return result;
    }


}
