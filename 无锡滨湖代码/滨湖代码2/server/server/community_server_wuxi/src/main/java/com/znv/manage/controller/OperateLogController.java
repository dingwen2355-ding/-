package com.znv.manage.controller;


import com.znv.manage.common.bean.OperateLog;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.impl.OperateLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class OperateLogController {

    @Autowired
    private OperateLogServiceImpl operateLogService;


    @PostMapping("/saveLog")
    public Result saveLog(@RequestBody OperateLog operateLog,
                          @RequestParam(value = "precinctId",required = false)String precinctId){
        if(StringUtils.isEmpty(precinctId)){
            operateLog.setPrecinctId("510100");
        }
        return operateLogService.saveLog(operateLog);
    }

    @GetMapping("/queryOperatelogList")
    public Result queryOperatelogList(
            @RequestParam(value = "operatorName",required = false)String operatorName,
            @RequestParam(value = "precinctId",required = false)String precinctId,
            @RequestParam(value = "beginTime", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
            @RequestParam(value = "endTime", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
            @RequestParam(value = "pageStart",defaultValue = "1")Integer pageStart,
                          @RequestParam(value = "pageLen",defaultValue = "10")Integer pageLen){
        return operateLogService.queryOperatelogList(operatorName,precinctId,beginTime,endTime,pageStart,pageLen);
    }


}
