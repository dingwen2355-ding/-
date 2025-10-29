package com.znv.manage.controller;

import com.znv.manage.common.bean.EventInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.impl.EventManageServiceImpl;
import com.znv.manage.service.impl.FusionVideoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "事件信息管理")
@RestController
@RequestMapping("manage")
public class EventManageController {

    @Autowired
    private EventManageServiceImpl eventManageService;

    @Autowired
    private FusionVideoServiceImpl fusionVideoService;

    @PostMapping("/addEvent")
    public Result addEvent(@RequestBody EventInfo eventInfo){
        return eventManageService.addEvent(eventInfo);
    }

    /**
     *
     * @return
     */
    @ApiOperation(value = "查询事件基础信息")
    @GetMapping(value = "/queryEventInfo")
    public List<Map<String,Object>> queryEventInfo(@RequestParam(value = "eventId",required = false) String eventId,
                                                   @RequestParam(value = "type",required = false) String type,
                                                   @RequestParam(value = "title",required = false) String title,
                                                   @RequestParam(value = "address",required = false) String address,
                                                   @RequestParam(value = "happenTime",required = false) String happenTime,
                                                   @RequestParam(value = "endTime",required = false) String endTime,
                                                   @RequestParam(value = "level",required = false) String level,
                                                   @RequestParam(value = "area",required = false) String area,
                                                   @RequestParam(value = "isFalsity",defaultValue = "0")String isFalsity,
                                                   @RequestParam(value = "precinctId",required = false)String precinctId){
        return eventManageService.queryEventInfo(eventId,type,title,address,happenTime,endTime,level,area,isFalsity,precinctId);
    }

    @ApiOperation(value = "查询事件类型列表")
    @GetMapping(value = "/queryEventTypeList")
    public List<Map<String,Object>> queryEventTypeList(){
        return eventManageService.queryEventTypeList();
    }

    @PostMapping("updateEvent")
    public Result updateEventInfoByKey(@RequestParam(value = "eventId",required = false)String eventId,
                                       @RequestParam(value = "emergencyPlanIds",required = false)String emergencyPlanIds){
        return eventManageService.updateEventInfoByKey(eventId, emergencyPlanIds);
    }

    @PostMapping("updateVideoListByEventId")
    public Result updateVideoListByEventId(){
        return fusionVideoService.updateVideoListByEventId2();
    }
}
