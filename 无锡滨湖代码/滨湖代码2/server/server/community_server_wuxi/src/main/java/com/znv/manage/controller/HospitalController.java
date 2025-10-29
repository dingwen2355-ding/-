package com.znv.manage.controller;


import com.znv.manage.service.impl.HospitalServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "医疗机构")
@RequestMapping("huawei")
public class HospitalController {

    @Autowired
    private HospitalServiceImpl hospitalService;

    @ApiOperation(value = "定点医院")
    @GetMapping("/queryDdHospitalList")
    public List<Map<String,String>> queryDdHospitalList(){
        return hospitalService.queryDdHospitalList();
    }

    @ApiOperation(value = "医院")
    @GetMapping("/query2HospitalList")
    public List<Map<String,String>> query2HospitalList(){
        return hospitalService.query2HospitalList();
    }

    @ApiOperation(value = "隔离点")
    @GetMapping("/queryIsolationList")
    public List<Map<String,String>> queryIsolationList(){
        return hospitalService.queryIsolationList();
    }
}
