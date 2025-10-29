package com.znv.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.impl.EventCommandServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/Command")
@Api(tags = "指挥中心")
public class EventCommandController {
    @Autowired
    private EventCommandServiceImpl eventCommandService;

    @ApiOperation("事件列表")
    @GetMapping("eventInfoList")
    public Result eventDataInfo(@RequestParam(value = "beginTime", required = false) String beginTime,
                                @RequestParam(value = "endTime", required = false) String endTime,
                                @RequestParam(value = "precinctId", required = false) String precinctId,
                                @RequestParam(value = "eventType", required = false) String eventType,
                                @RequestParam(value = "title", required = false) String title,
                                @RequestParam(value = "pageNum", required = false) Integer pageNum,   //第几页
                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        return eventCommandService.getEventData(beginTime, endTime, precinctId, title, eventType, pageNum, pageSize);
    }

    @ApiOperation("事件列表(类似、附近)")
    @GetMapping("eventInfoListByType")
    public Result eventInfoListByType(@RequestParam(value = "beginTime", required = false) String beginTime,
                                      @RequestParam(value = "endTime", required = false) String endTime,
                                      @RequestParam(value = "precinctId", required = false) String precinctId,
                                      @RequestParam(value = "title", required = false) String title,
                                      @RequestParam(value = "type", defaultValue = "1") String type,
                                      @RequestParam(value = "eventId") String eventId,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,   //第几页
                                      @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize) {

        return eventCommandService.getEventDataByType(title, beginTime, endTime, precinctId, type, eventId, pageNum, pageSize);
    }

    @ApiOperation("查询页面调用次数")
    @GetMapping("queryVistitCount")
    public String queryVistitCount(@RequestParam(value = "beginTime", required = false)
                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                                   @RequestParam(value = "endTime", required = false)
                                   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {

        return eventCommandService.queryVistitCount(beginTime, endTime);
    }

//    @ApiOperation(value = "上海指挥体系")
//    @GetMapping(value = "/communication")
//    public Map<String, List<Map<String, String>>> communication(@RequestParam("eventId") String eventId){
//        return eventCommandService.communication(eventId);
//    }
//
//    @ApiOperation(value = "上海指挥体系新")
//    @GetMapping(value = "/communicationNew")
//    public Result communicationNew(@RequestParam("eventId") String eventId, @RequestParam(value = "isMax",required = false) String isMax){
//        return eventCommandService.communicationNew(eventId,isMax);
//    }

    @ApiOperation(value = "上海指挥体系-根据部门排序(可视化)")
    @GetMapping(value = "/communicationOrderByDepartment")
    public Object communicationOrderByDepartment(@RequestParam("eventId") String eventId,
                                                 @RequestParam(value = "isMax", required = false) String isMax,
                                                 @RequestParam(value = "precinctId", required = false) String precinctId) {
        return eventCommandService.communicationOrderByDepartment(eventId, isMax, precinctId);
    }

    @ApiOperation(value = "上海指挥体系-区平台(管理)")
    @GetMapping(value = "/communicationOrderByDepartmentByPrecinctId")
    public Object communicationOrderByDepartmentByPrecinctId(@RequestParam(value = "isMax", required = false) String isMax,
                                                             @RequestParam(value = "precinctId", required = false) String precinctId,
                                                             @RequestParam(value = "planId") String planId,
                                                             @RequestParam(value = "linkType") String linkType) {
        if (StringUtils.isEmpty(precinctId)) {
            precinctId = "510100";
        }
        return eventCommandService.communicationOrderByDepartmentByPrecinctId(isMax, precinctId, planId, linkType);
    }

//    @ApiOperation(value = "上海指挥体系-市平台(管理)")
//    @GetMapping(value = "/communicationOrderByDepartmentByEventType")
//    public Object communicationOrderByDepartmentByEventType(@RequestParam(value = "isMax",required = false) String isMax,
//                                                 @RequestParam(value = "eventType")String eventType){
//        return eventCommandService.communicationOrderByDepartmentByEventType(isMax,eventType);
//    }

    @ApiOperation(value = "单位人员")
    @GetMapping(value = "/unitMember")
    public List<Map<String, String>> unitMember(@RequestParam("unitId") String unitId) {
        return eventCommandService.unitMember(unitId);
    }

    @ApiOperation(value = "区域人员")
    @GetMapping(value = "/areaMember")
    public List<Map<String, String>> areaMember(@RequestParam("areaCode") String areaCode) {
        return eventCommandService.areaMember(areaCode);
    }

    @ApiOperation(value = "添加指挥长")
    @PostMapping(value = "/leader")
    public void eventLeader(@RequestParam("eventId") String eventId, @RequestParam("data") String data) {
        eventCommandService.eventLeader(eventId, data);
    }

    /**
     * 查询配置页面的通讯录详情
     *
     * @return
     */
    @ApiOperation(value = "配置页面-查询事件关联详情")
    @GetMapping(value = "/queryEventCommunicationConfig")
    public Map<String, List<Map<String, String>>> queryEventCommunicationConfig(@RequestParam("eventId") String eventId) {
        return eventCommandService.queryEventCommunicationConfig(eventId);
    }

    /**
     * 修改事件关联信息
     * 传参格式
     * {
     * "precinctId": "101",
     * "eventType": "101",
     * "data": {
     * "1": {
     * "999997": "",
     * "999998": "leader",
     * "999999": "leader"
     * },
     * "2": ["1", "2", "3"],
     * "3": ["310107000000", "310107102000"],
     * "4": ["4", "5"]* 	}
     * }
     *
     * @param data
     */
//    @ApiOperation(value = "配置页面-修改事件关联详情(废除)")
//    @PostMapping(value = "/updateEventCommunication")
//    public void updateEventCommunication(@RequestBody JSONObject data){
//        eventCommandService.updateEventCommunication(data);
//    }
    @ApiOperation(value = "配置页面-修改指挥体系(根据区域)")
    @PostMapping(value = "/updateEventCommunicationByPrecinctId")
    public void updateEventCommunicationByPrecinctId(@RequestBody JSONObject data) {
        eventCommandService.updateEventCommunicationByPrecinctId(data);
    }

//    @ApiOperation(value = "配置页面-修改指挥体系(根据事件类型)")
//    @PostMapping(value = "/updateEventCommunicationByEventType")
//    public void updateEventCommunicationByEventType(@RequestBody JSONObject data){
//        eventCommandService.updateEventCommunicationByEventType(data);
//    }


//    @ApiOperation(value = "后端修改默认人")
//    @PostMapping(value = "/defaultLeader")
//    public void defaultLeader(@RequestParam("unitId") String unitId, @RequestParam("personId") String personId){
//        eventCommandService.defaultLeader(unitId,personId);
//    }


    /**
     * 事件id查询委办局系统列表
     *
     * @return
     */
    @ApiOperation(value = "事件id查询委办局系统列表,系统类型：日常管理和应急管理,不填就全部")
    @GetMapping(value = "/queryUnitSystemByEventId")
    public List<Map<String, Object>> queryUnitSystemByEventId(@RequestParam(value = "eventId", required = false) String eventId,
                                                              @RequestParam(value = "systemType", required = false) String systemType) {
        return eventCommandService.queryUnitSystemByEventId(eventId, systemType);
    }

    /**
     * 查询委办局系统列表
     *
     * @return
     */
    @ApiOperation(value = "查询委办局系统列表")
    @GetMapping(value = "/queryUnitSystemList")
    public List<Map<String, Object>> queryUnitSystemList() {
        return eventCommandService.queryUnitSystemList();
    }


    @ApiOperation(value = "查询系统")
    @GetMapping(value = "/queryUnitSystem")
    public Map<String, List<Map<String, String>>> queryUnitSystem(@RequestParam(value = "systemType", required = false) String systemType,
                                                                  @RequestParam(value = "unit", required = false) String unit,
                                                                  @RequestParam(value = "systemName", required = false) String systemName) {
        return eventCommandService.queryUnitSystem(systemType, unit, systemName);
    }

    @ApiOperation(value = "查询系统2")
    @GetMapping(value = "/queryUnitSystem2")
    public Map<String, List<Map<String, Object>>> queryUnitSystem2() {
        return eventCommandService.queryUnitSystem2();
    }

    @ApiOperation(value = "查询主题信息")
    @GetMapping(value = "/queryTopicInfoList")
    public List<Map<String, Object>> queryTopicInfoList(@RequestParam(value = "topicId", required = false) String topicId,
                                                        @RequestParam(value = "topicName", required = false) String topicName) {
        return eventCommandService.queryTopicInfoList(topicId, topicName);
    }

    /**
     * 查询现场指挥官
     *
     * @return
     */
    @ApiOperation(value = "查询现场指挥官")
    @GetMapping(value = "/queryEventCommunicationOn")
    public Map<String, Object> queryEventCommunicationOn(@RequestParam(value = "eventId") String eventId) {
        return eventCommandService.queryEventCommunicationOn(eventId);
    }

    @ApiOperation(value = "修改指挥长")
    @PostMapping(value = "/updateLeader")
    public void updateLeader(@RequestParam("eventId") String eventId, @RequestParam("type") String type, @RequestParam("leader") String leader) {
        eventCommandService.updateLeader(eventId, type, leader);
    }

    @ApiOperation(value = "修改指挥长2")
    @PostMapping(value = "/updateLeader2")
    public void updateLeader2(@RequestBody JSONObject json) {
        eventCommandService.updateLeader2(json);
    }

//    @ApiOperation(value = "修改指挥长市级")
//    @PostMapping(value = "/updateLeaderByEventType")
//    public void updateLeaderByEventType(@RequestBody JSONObject json){
//        eventCommandService.updateLeaderByEventType(json);
//    }

    @ApiOperation(value = "修改指挥长区级")
    @PostMapping(value = "/updateLeaderByPrecinctId")
    public void updateLeaderByPrecinctId(@RequestBody JSONObject json) {
        eventCommandService.updateLeaderByPrecinctId(json);
    }

    @ApiOperation(value = "应急资源")
    @GetMapping(value = "/queryEventResource")
    public Result queryEventResource(@RequestParam(value = "eventId") String eventId){
        return eventCommandService.queryEventResource(eventId);
    }
}
