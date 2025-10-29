package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.ResultBean;
import com.znv.manage.service.LogService;
import com.znv.manage.common.utils.DateUtils;
import com.znv.manage.service.impl.LogServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "日志管理")
@Slf4j
public class LogController {

    @Resource
    LogService logService;

    @ApiOperation("日志查询")
    @GetMapping("/log")
    public ResultBean<Map<String, Object>> queryModules(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "operator", required = false) String operator,
            @RequestParam(value = "operUserId", required = false) String operUserId,
            @RequestParam(value = "operUserName", required = false) String operUserName,
            @RequestParam(value = "ip", required = false)  String ip,
            @RequestParam(value = "time", required = false) String time,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "time", required = false) String operatedUserId,
            @RequestParam(value = "operatedUserName", required = false) String operatedUserName,
            @RequestParam(value = "result", required = false) String result,
            @RequestParam(value = "detail", required = false) String detail,
            @RequestParam(value = "userId", required = false) String userId
    ) {
        ResultBean<Map<String, Object>> ret = new ResultBean<>();
        try {
            List<Map<String, Object>> maps = logService.queryLog(id,operator, operUserId, operUserName, ip,
                    DateUtils.strToDateLong(time), startTime, endTime, operatedUserId, operatedUserName,result,detail,userId);
            ret.addData(maps);
            ret.setResult(ResultBean.SUCESS);

        } catch (Exception e) {
            ret.setResult(ResultBean.FAILED);
            ret.setRemark(e.getMessage());
            log.error(e.toString());

        }
        return ret;
    }

    @ApiOperation("获取操作类型")
    @GetMapping("/getLogKinds")
    public Result queryLogKinds() {
        return logService.queryLogKinds();
    }

    @ApiOperation("导出日志excel")
    @GetMapping("/exportLogExcel")
    public Result exportLog(@RequestParam(value = "operUserName", required = false)String operUserName,
                            @RequestParam(value = "startTime", required = false)String startTime,
                            @RequestParam(value = "endTime", required = false) String endTime,
                            @RequestParam(value = "operator", required = false) String operator) {
        return logService.exportLog(operUserName, startTime, endTime, operator);
    }
}
