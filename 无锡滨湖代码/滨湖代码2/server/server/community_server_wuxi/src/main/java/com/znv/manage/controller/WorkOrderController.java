package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.QxyjxxService;
import com.znv.manage.service.WorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.executor.ResultExtractor;
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
@Api(tags = "查询市民热线工单,市12345平台-工单信息表")
@RestController
@RequestMapping("workOrder")
public class WorkOrderController {

    @Autowired
    WorkOrderService workOrderService;

    @ApiOperation("查询市民热线工单,市12345平台-工单信息表")
    @GetMapping("getWorkOrder")
    public Result getWorkOrder(
            @RequestParam(value = "beginTime", required = false)
                    String beginTime,
            @RequestParam(value = "endTime", required = false)
                    String endTime,
            @RequestParam(value = "limit", required = false)
                    Integer limit,
            @RequestParam(value = "precinctId", required = false) String precinctId,
            @RequestParam(value = "precinctName", required = false) String precinctName) {
        Result result = workOrderService.getWorkOrder(beginTime, endTime, limit, precinctName);
        return result;
    }


    @ApiOperation("同步市民热线工单,市12345平台-工单信息表")
    @GetMapping("syncWorkOrder")
    public Result syncWorkOrder(
            @RequestParam(value = "syncDate", required = false)
                    String syncDate
    ) {
        int i = workOrderService.syncWorkOrder(syncDate);
        Result result = new Result();
        result.setData(i);
        return result;
    }

}
