package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.ShzldldgdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 应急预案相关接口
 */
@Api(tags = "查询社会治理大联动工单 综治大联动-案件信息")
@RestController
@RequestMapping("shzldldgd")
public class ShzldldgdController {

    @Autowired
    ShzldldgdService shzldldgdService;

    @ApiOperation("查询社会治理大联动工单 综治大联动-案件信息")
    @GetMapping("getShzldldgd")
    public Result getShzldldgd(
            @RequestParam(value = "beginTime", required = false)
                    String beginTime,
            @RequestParam(value = "endTime", required = false)
                    String endTime,
            @RequestParam(value = "limit", required = false)
                    Integer limit,
            @RequestParam(value = "EVENTSUBJECTNAME", required = false)
                    String EVENTSUBJECTNAME,
            @RequestParam(value = "precinctId", required = false) String precinctId,
            @RequestParam(value = "precinctName", required = false) String precinctName) {
        Result result = shzldldgdService.getShzldldgd(beginTime, endTime, limit, EVENTSUBJECTNAME, precinctName);
        return result;
    }

    @ApiOperation("查询社会治理大联动工单 综治大联动-分类")
    @GetMapping("getShzldldgdType")
    public Result getShzldldgdType(@RequestParam(value = "precinctId", required = false) String precinctId,
                                   @RequestParam(value = "precinctName", required = false) String precinctName) {
        Result result = shzldldgdService.getShzldldgdType(precinctName);
        return result;
    }


    @ApiOperation("查询社会治理大联动工单 综治大联动-状态数量")
    @GetMapping("getShzldldgdStatusCount")
    public Result getShzldldgdStatusCount(
            @RequestParam(value = "beginTime", required = false)
                    String beginTime,
            @RequestParam(value = "endTime", required = false)
                    String endTime,
            @RequestParam(value = "precinctId", required = false) String precinctId,
            @RequestParam(value = "precinctName", required = false) String precinctName
    ) {
        Result result = shzldldgdService.getShzldldgdStatusCount(beginTime, endTime, precinctName);
        return result;
    }

}
