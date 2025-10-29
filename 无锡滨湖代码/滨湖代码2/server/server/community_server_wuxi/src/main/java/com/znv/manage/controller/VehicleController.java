package com.znv.manage.controller;


import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.service.impl.AmbulanceServiceImpl;
import com.znv.manage.service.impl.UserGpsServiceImpl;
import com.znv.manage.service.impl.VehicleServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vehicle")
@Api(tags = "危化品运输车辆")
@Slf4j
public class VehicleController {

    @Autowired
    private VehicleServiceImpl vehicleService;


    @GetMapping("/queryVehicleGpsList")
    public List<Map> queryVehicleGpsList(
            @RequestParam(value = "dateStr",required = false)String dateStr,
            @RequestParam(value = "vehicleNo",required = false)String vehicleNo){
        if(StringUtils.isEmpty(dateStr)){
            dateStr = DateUtil.getStringDateShort();
        }
        return vehicleService.queryVehicleGpsList(vehicleNo,dateStr);
    }

    @GetMapping("/queryVehicleGpsListByVehicleNo")
    public List<Map> queryVehicleGpsListByVehicleNo(
            @RequestParam(value = "dateStr",required = false)String dateStr,
            @RequestParam(value = "deviceNo")String vehicleNo){
        if(StringUtils.isEmpty(dateStr)){
            dateStr = DateUtil.getStringDateShort();
        }
        return vehicleService.queryVehicleGpsListByVehicleNo(vehicleNo,dateStr);
    }

    @GetMapping("/queryBusList")
    public List<Map<String,Object>> queryBusList(
            @RequestParam(value = "hours",required = false)String hours,
            @RequestParam(value = "vehicleNo",required = false)String vehicleNo){
        return vehicleService.queryBusList(vehicleNo,hours);
    }

    @GetMapping("/queryTaxiList")
    public List<Map<String,Object>> queryTaxiList(
            @RequestParam(value = "hours",required = false)String hours,
            @RequestParam(value = "vehicleNo",required = false)String vehicleNo){
        return vehicleService.queryTaxiList(vehicleNo,hours);
    }

    @GetMapping("/queryTruckList")
    public List<Map<String,Object>> queryTruckList(
            @RequestParam(value = "hours",required = false)String hours,
            @RequestParam(value = "vehicleNo",required = false)String vehicleNo){
        return vehicleService.queryTruckList(vehicleNo,hours);
    }

    @GetMapping("/queryXFCList")
    public List<Map<String,String>> queryXFCList(
            @RequestParam(value = "vehicleNo",required = false)String vehicleNo){
        return vehicleService.queryXFCList(vehicleNo);
    }
}
