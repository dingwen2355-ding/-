package com.znv.manage.controller;


import com.znv.manage.common.bean.ImportantPeople;
import com.znv.manage.common.bean.ImportantPeopleGps;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.impl.ImportantPeopleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "重点人员")
@RestController
public class ImportantPeopleController {

    @Autowired
    private ImportantPeopleServiceImpl importantPeopleService;

    @ApiOperation(value = "重点人员信息列表")
    @GetMapping("/queryImportantPeopleList")
    public List<ImportantPeople> queryImportantPeopleList(
            @RequestParam(value = "id",required = false)String id,
            @RequestParam(value = "name",required = false)String name){
        return importantPeopleService.queryImportantPeopleList(id,name);
    }

    @ApiOperation(value = "流调信息-可视化")
    @GetMapping("/queryImportantRouteList")
    public List<Map<String,Object>> queryImportantRouteList(@RequestParam(value = "names",required = false)String names){
        return importantPeopleService.queryImportantRouteList(names);
    }

    @ApiOperation(value = "流调信息列表-管理弹窗")
    @GetMapping("/queryImportantGpsList")
    public List<ImportantPeopleGps> queryImportantRouteList(
            @RequestParam(value = "id",required = false)String id,
            @RequestParam(value = "name",required = false)String name){
        return importantPeopleService.queryImportantGpsList2(id,name);
    }

    @ApiOperation(value = "存储重点人员信息(新增或修改)")
    @PostMapping("/saveImportantPeopleInfo")
    public Result saveImportantPeopleInfo(
            @RequestBody ImportantPeople importantPeople){
        return importantPeopleService.saveImportantPeopleInfo(importantPeople);
    }

    @ApiOperation(value = "删除重点人员信息")
    @PostMapping("/deleteImportantPeopleByIds")
    public Result deleteImportantPeopleByIds(
            @RequestParam(value = "ids")String ids){
        return importantPeopleService.deleteImportantPeopleByIds(ids);
    }

    @ApiOperation(value = "存储重点人员流调信息(新增或修改)")
    @PostMapping("/saveImportantRouteInfo")
    public Result saveImportantRouteInfo(
            @RequestBody ImportantPeopleGps importantPeopleGps){
        return importantPeopleService.saveImportantRouteInfo(importantPeopleGps);
    }

    @ApiOperation(value = "删除重点人员流调信息")
    @PostMapping("/deleteImportantRouteByIds")
    public Result deleteImportantRouteByIds(
            @RequestParam(value = "ids")String ids){
        return importantPeopleService.deleteImportantRouteByIds(ids);
    }

    @GetMapping("/updateRouteGps")
    public int updateRouteGps(@RequestParam(value = "city",required = false)String city){
        return importantPeopleService.updateRouteGps(city);
    }
}
