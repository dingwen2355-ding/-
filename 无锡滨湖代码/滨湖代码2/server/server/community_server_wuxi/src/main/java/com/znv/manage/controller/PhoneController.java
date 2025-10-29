package com.znv.manage.controller;


import com.znv.manage.common.bean.Result;
import com.znv.manage.service.impl.PhoneServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "电话服务-叙简")
@RequestMapping(value = "/phone")
public class PhoneController {

    @Autowired
    private PhoneServiceImpl phoneService;


    @GetMapping("/call")
    @ApiOperation("呼叫")
    public Result call(@RequestParam(value = "callee")String callee){
        return phoneService.call(callee);
    }

    @GetMapping("/hungup")
    @ApiOperation("挂断")
    public Result hungup(@RequestParam(value = "callee")String callee){
        return phoneService.hungup(callee);
    }
}
