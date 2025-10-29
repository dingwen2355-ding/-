package com.znv.manage.controller;

import com.znv.manage.common.bean.EmergencyPlan;
import com.znv.manage.common.bean.Plan;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.impl.EmergencyPlanServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 应急预案相关接口
 */
@Api(tags = "应急预案相关接口")
@RestController
@RequestMapping("emergencyPlan")
public class EmergencyPlanController {


    @Autowired
    EmergencyPlanServiceImpl emergencyPlanService;

    @ApiOperation("查看应急预案")
    @GetMapping("queryEmergency")
    public Result getEmergencyInfo(@RequestParam("eventId") String eventId){

        return emergencyPlanService.getEmergencyInfo(eventId);
    }

    @ApiOperation("修改")
    @PostMapping("updateEmergency")
    public Result updateEmergencyInfo(@RequestBody EmergencyPlan emergencyPlan){

        return emergencyPlanService.updateEmergencyInfo(emergencyPlan);
    }

    @ApiOperation("insert")
    @PostMapping("insertEmergency")
    public Result insertEmergencyInfo(@RequestBody EmergencyPlan emergencyPlan){
        return emergencyPlanService.insertEmergencyInfo(emergencyPlan);
    }

    @ApiOperation("delete")
    @PostMapping("deleteEmergency")
    public Result deleteEmergencyInfo(@RequestParam("id") String emergencyPlanIds){
        return emergencyPlanService.deleteEmergencyInfo(emergencyPlanIds);
    }


    @ApiOperation("查看应急预案")
    @GetMapping("queryEmergencyNew")
    public Result getEmergencyInfoNew(@RequestParam(value = "eventId", required = false) String eventId,
                                      @RequestParam(value = "keyWords", required = false) String keyWords,
                                      @RequestParam(value = "emIds", required = false) String emIds,
                                      @RequestParam(value = "precinctId", required = false) String precinctId,
                                      @RequestParam(value = "typeName", required = false) String typeName,
                                      @RequestParam(value = "typeId", required = false) String typeId){

        return emergencyPlanService.getEmergencyInfoNew(eventId, keyWords, emIds,precinctId,typeName,typeId);
    }

    @ApiOperation("查看应急预案2")
    @GetMapping("queryEmergencyInfo")
    public Result queryEmergencyInfo(@RequestParam(value = "eventId") String eventId){

        return emergencyPlanService.queryEmergencyInfo(eventId);
    }

    @ApiOperation("修改应急预案配置信息")
    @GetMapping("updateEmergencyPlanCfg")
    public Result updateEmergencyPlanCfg(@RequestParam(value = "eventId") String eventId,
                                     @RequestParam(value = "planId")String planId,
                                     @RequestParam(value = "planLevel",required = false)String planLevel,
                                     @RequestParam(value = "planStatus",required = false)String planStatus,
                                     @RequestParam(value = "startTime", required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                     @RequestParam(value = "endTime", required = false)
                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                     @RequestParam(value = "operator")String operator,
                                         @RequestParam(value = "startPerson")String startPerson){

        return emergencyPlanService.updateEmergencyPlanCfg(eventId,planId,planLevel,planStatus,startTime,endTime,operator,startPerson);
    }


    @ApiOperation("查预案列表")
    @GetMapping("queryPlanList")
    public List<Plan> queryPlanList(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "precinctId", required = false) String precinctId,
                                    @RequestParam(value = "typeName", required = false) String typeName,
                                    @RequestParam(value = "typeId", required = false) String typeId){

        return emergencyPlanService.queryPlanList(name,precinctId,typeName,typeId);
    }

    @ApiOperation("批量删除预案")
    @PostMapping("deletePlanByIds")
    public Result deletePlanByIds(@RequestParam(value = "ids") String ids){
        return emergencyPlanService.deletePlanByIds(ids);
    }

    @ApiOperation(value = "添加应急预案")
    @PostMapping(value = "/addPlan")
    public Result addPlan(@RequestParam("file") MultipartFile file,
                          @RequestParam(value = "planName")String planName,
                          @RequestParam(value = "planTypeId")String planTypeId,
                          @RequestParam(value = "precinctId",defaultValue = "510100")String precinctId) {
        return emergencyPlanService.addPlan(file,planName,planTypeId,precinctId);
    }

    @ApiOperation(value = "修改应急预案")
    @PostMapping(value = "/updatePlan")
    public Result updatePlan(@RequestParam(value = "file",required = false) MultipartFile file,
                             @RequestParam(value = "planTypeId")String planTypeId,
                          @RequestParam(value = "planName")String planName,
                             @RequestParam(value = "precinctId")String precinctId,
                          @RequestParam(value = "planId")String planId) {
        return emergencyPlanService.updatePlan(file,planName,planTypeId,planId,precinctId);
    }

    @ApiOperation("查预案类型列表")
    @GetMapping("queryPlanTypeList")
    public List<Map<String,String>> queryPlanTypeList(){

        return emergencyPlanService.queryPlanTypeList();
    }


}
