package com.znv.manage.controller;


import com.znv.manage.common.bean.Result;
import com.znv.manage.service.CenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "数据中台")
@RequestMapping("/center")
public class CenterController {

    @Autowired
    private CenterService centerService;

    @ApiOperation("查询煤矿企业")
    @GetMapping("/queryCoalMine")
    public List<Map<String,Object>> queryCoalMine(){
        return centerService.queryCoalMine();
    }

    @ApiOperation("查询重点企业")
    @GetMapping("/queryEnterprise")
    public List<Map<String,Object>> queryEnterprise(){
        return centerService.queryEnterprise();
    }

    @ApiOperation("查询水雨情监测站")
    @GetMapping("/queryObservationStation")
    public List<Map<String,Object>> queryObservationStation(){
        return centerService.queryObservationStation();
    }

    @ApiOperation("查询易灾点")
    @GetMapping("/queryProneDisaster")
    public List<Map<String,Object>> queryProneDisaster(){
        return centerService.queryProneDisaster();
    }

    @ApiOperation("查询内涝点")
    @GetMapping("/queryWaterlogging")
    public List<Map<String,Object>> queryWaterlogging(){
        return centerService.queryWaterlogging();
    }

    @ApiOperation("查询水库")
    @GetMapping("/queryReservoir")
    public List<Map<String,Object>> queryReservoir(){
        return centerService.queryReservoir();
    }

    @ApiOperation("查询运输力量")
    @GetMapping("/queryTransport")
    public List<Map<String,Object>> queryTransport(){
        return centerService.queryTransport();
    }

    @ApiOperation("查询气象站")
    @GetMapping("/queryWeatherStation")
    public List<Map<String,Object>> queryWeatherStation(){
        return centerService.queryWeatherStation();
    }

    @ApiOperation("查询应急救援队伍")
    @GetMapping("/queryRescue")
    public List<Map<String,Object>> queryRescue(){
        return centerService.queryRescue();
    }

    @ApiOperation("查询重大危险源")
    @GetMapping("/queryHazard")
    public List<Map<String,Object>> queryHazard(){
        return centerService.queryHazard();
    }

    @ApiOperation("查询医疗机构")
    @GetMapping("/queryMedicalInstitution")
    public List<Map<String,Object>> queryMedicalInstitution(){
        return centerService.queryMedicalInstitution();
    }

    @ApiOperation("查询舆情总条数")
    @GetMapping("/queryCount")
    public Long queryCount(){
        return centerService.queryCount();
    }

    @ApiOperation("查询舆情本月和今日条数")
    @GetMapping("/queryMonthCount")
    public Result queryMonthCount() {
        return centerService.queryMonthCount();
    }

    @ApiOperation("查询微博舆情")
    @GetMapping("/queryWeibo")
    public Result queryWeibo() {
        return centerService.queryWeibo();
    }

    @ApiOperation("查询网上舆情")
    @GetMapping("/queryOthers")
    public Result queryOthers() {
        return centerService.queryOthers();
    }

    @ApiOperation("查询48小时传播趋势变化")
    @GetMapping("/query48Hours")
    public Result query48Hours() {
        return centerService.query48Hours();
    }

    @ApiOperation("查天气")
    @GetMapping("/queryWeather")
    public Result queryWeather() {
        return centerService.queryWeather();
    }

}
