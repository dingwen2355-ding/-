package com.znv.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.SendSMSService;
import com.znv.manage.service.impl.SendSMSServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 短信发送接口
 */
@Api(tags = "短信发送接口")
@RestController
@RequestMapping("SMS")
@Slf4j
public class SendSMSController {

    @Autowired
    SendSMSService sendSMSService;

    @GetMapping("/send")
    @ApiOperation("发送短信")
    public Result sendSMS(@RequestParam(value = "phones") String phones,
                          @RequestParam(value = "code", required = false) String code,
                          @RequestParam(value = "appName", required = false) String appName,
                          @RequestParam(value = "action", required = false) String action) {
        return sendSMSService.sendSMS(phones, code, appName, action);
    }

    @PostMapping("/test")
    @ApiOperation("接口测试")
    public Result postForResult(@RequestParam(value = "url", required = true) String url,
                                @RequestParam(value = "params", required = true) String params) {
        Result result = new Result();
        JSONObject json = SendSMSServiceImpl.postForResult(url, params);
        result.setData(json);
        return result;
    }

}
