package com.znv.manage.controller;


import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.ArrivedResource;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.impl.ArrivedResourceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author FXQ
 * @date 2020/06/22
 */


@RestController
@Api(tags = "到场资源力量")
@Slf4j
public class ArrivedResourceController {


    @Autowired
    private ArrivedResourceServiceImpl arrivedResourceService;

    @ApiOperation(value = "根据事件id查到场资源力量(可视化页面)")
    @GetMapping(value = "/queryArrivedResourceListByEventId")
    public Result queryArrivedResourceListByEventId(@RequestParam(value = "eventId",required = true) String eventId){
        Result result = new Result();
        try {
            List<Map<String, Object>> list = arrivedResourceService.queryArrivedResourceListByEventId(eventId);
            result.setData(list);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "查委办局列表,type:1 政府单位  2 企业单位")
    @GetMapping(value = "/queryUnitList")
    public Result queryUnitList(@RequestParam(value = "type",required = false) String type,
                                @RequestParam(value = "precinctId",required = false)String precinctId){
        Result result = new Result();
        try {
            List<Map<String, Object>> list = arrivedResourceService.queryUnitList(type,precinctId);
            result.setData(list);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "查资源力量列表")
    @GetMapping(value = "/queryResourceTypeList")
    public Result queryResourceTypeList(){
        Result result = new Result();
        try {
            List<Map<String, Object>> list = arrivedResourceService.queryResourceTypeList();
            result.setData(list);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "根据事件id查到场资源力量(管理页面)")
    @GetMapping(value = "/queryArrivedResourceList")
    public Result queryArrivedResourceList(@RequestParam(value = "eventId",required = true) String eventId,
                                           @RequestParam(value = "precinctId",required = false)String precinctId){
        Result result = new Result();
        try {
            List<Map<String, Object>> list = arrivedResourceService.queryArrivedResourceList(eventId,precinctId);
            result.setData(list);
        } catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }


    @ApiOperation(value = "添加到场资源力量-参谋页")
    @PostMapping(value = "/saveArrivedResource")
    public Result saveArrivedResource(@RequestBody JSONObject data){
        Result result = new Result();
        result = arrivedResourceService.saveArrivedResource(data);
        return result;
    }

    @ApiOperation(value = "添加到场资源力量(管理页面)")
    @PostMapping(value = "/insertArrivedResource")
    public Result insertArrivedResource(@RequestBody List<ArrivedResource> arrivedResources){
        return arrivedResourceService.insertArrivedResource(arrivedResources);
    }

    @ApiOperation(value = "更新到场资源力量(管理页面)")
    @PostMapping(value = "/updateArrivedResource")
    public Result updateArrivedResource(@RequestBody List<ArrivedResource> arrivedResources){
        return arrivedResourceService.updateArrivedResource(arrivedResources);
    }

    @ApiOperation(value = "删除到场资源力量(管理页面)")
    @PostMapping(value = "/deleteArrivedResourceById")
    public Result deleteArrivedResourceById(@RequestParam(value = "ids",required = true) String ids){
        Result result = new Result();
        try {
            int count = arrivedResourceService.deleteArrivedResourceById(ids);
            result.setMessage("delete success count:"+count);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

}
