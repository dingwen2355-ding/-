package com.znv.manage.controller;

/*
 * 事件处置记录类
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.HistoryDealContent;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.impl.EventDealServiceImpl;
import com.znv.manage.service.impl.SyncEventServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("eventDeal")
@Api(tags = "事件处置")
public class EventDealController {

    @Autowired
    EventDealServiceImpl eventDealService;

    @Autowired
    SyncEventServiceImpl syncEventService;

    @ApiOperation("事件处置流程")
    @GetMapping("queryFlow")
    public Result getEventDealFlowInfo(@RequestParam("eventId") String eventId,
                                       @RequestParam(value = "status",required = false) String status){
        return eventDealService.getEventDealFlowInfo(eventId, status);
    }

    @ApiOperation("事件处置获取")
    @GetMapping("getEventDeal")
    public JSONObject getEventDeal(@RequestParam("eventId") String eventId) {
        return syncEventService.getDealContentForWeb2(eventId);
    }

//    @ApiOperation("事件最新处置流程")
//    @GetMapping("getEventNewDealFlowInfo")
//    public Map<String, Object> getEventNewDealFlowInfo(@RequestParam("eventId") String eventId){
//        return eventDealService.getEventNewDealFlowInfo(eventId);
//    }

    @ApiOperation("根据工单获取多任务详情")
    @GetMapping("queryTaskByParam")
    public Object queryTaskByParam(@RequestParam("flowNo") String flowNo){
        return syncEventService.queryTaskByParam(flowNo);
    }

    @ApiOperation(value = "处置力量列表")
    @GetMapping(value = "/queryOperators")
    public Set<Map<String,Object>> queryOperators(@RequestParam("eventId") String eventId){
        return eventDealService.queryOperators(eventId);
    }

    @ApiOperation("事件详细信息")
    @GetMapping("eventInfo")
    public Result getEventInfo(@RequestParam("eventId") String eventId){
        return eventDealService.getEventInfo(eventId);
    }

    @ApiOperation("insert")
    @PostMapping("insertEventDealFlow")
    public Result insertEventDealFlowInfo(@RequestBody HistoryDealContent historyDealContent){
        System.err.println(historyDealContent);
        return eventDealService.insertEventDealFlowInfo(historyDealContent);
    }

    @ApiOperation("update")
    @PostMapping("updateEventDealFlow")
    public Result updateEventDealFlowInfo(@RequestBody HistoryDealContent historyDealContent){
        return eventDealService.updateEventDealFlowInfo(historyDealContent);
    }

    @ApiOperation("delete")
    @PostMapping("deleteEventDealFlow")
    public Result deleteEventDealFlowInfo(@RequestParam("id") String ids){
        return eventDealService.deleteEventDealFlowInfo(ids);
    }

    @ApiOperation("批量更新/插入")
    @PostMapping("batchUpdateDeafFlow")
    public Result batchUpdateDeafFlow(@RequestBody JSONObject json){
        String eventId = json.getString("eventId");
        if(StringUtils.isNotBlank(eventId)){
            JSONArray list = json.getJSONArray("lists");
            return eventDealService.batchUpdateDeafFlow(eventId, list);
        }else{
            return new Result(500,"未传事件id");
        }

    }

    @ApiOperation("根据事件经纬度获取最近摄像头")
    @PostMapping("getVideoListByEvent")
    public Result getVideoListByEvent(@RequestParam(value = "gpsX") String gpsX,
                                      @RequestParam(value = "gpsY") String gpsY){
        return eventDealService.getVideoListByEvent(gpsX,gpsY);
    }
}
