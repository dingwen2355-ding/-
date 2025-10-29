package com.znv.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.service.EventSyncService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 事件对接(对方调我方接口传入数据)
 *
 * @author ：0049003788
 * @date ：2023/2/9 16:52
 */
@Api(tags = "事件对接")
@RestController
@RequestMapping("proj_name/ioc/event")
public class EventSyncController {
    @Autowired
    EventSyncService eventSyncService;

    @ApiOperation(value = "查询事件基础信息")
    @PostMapping(value = "/assignEvent")
    public JSONObject assignEvent(@RequestBody JSONObject json,
                                  HttpServletRequest request) {
        return eventSyncService.assignEvent(json, request);
    }
}
