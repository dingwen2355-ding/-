package com.znv.manage.controller;


import com.znv.manage.service.impl.AmbulanceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "救护车")
@RequestMapping("/ambulance")
public class AmbulanceController {

    @Autowired
    private AmbulanceServiceImpl ambulanceService;


    @GetMapping("/queryAmbulanceGpsList")
    public List<Map> queryAmbulanceGpsList(@RequestParam(value = "deviceNo",required = false)String deviceNo){
        return ambulanceService.queryAmbulanceGpsList(deviceNo);
    }

    @GetMapping("/queryAmbulanceGpsListByDeviceNo")
    public List<Map> queryAmbulanceGpsListByDeviceNo(@RequestParam(value = "deviceNo")String deviceNo){
        return ambulanceService.queryAmbulanceGpsListByDeviceNo(deviceNo);
    }
}
