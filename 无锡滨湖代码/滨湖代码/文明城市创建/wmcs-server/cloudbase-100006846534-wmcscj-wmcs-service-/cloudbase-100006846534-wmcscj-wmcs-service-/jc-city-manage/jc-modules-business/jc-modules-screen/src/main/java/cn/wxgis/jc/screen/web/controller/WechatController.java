package cn.wxgis.jc.screen.web.controller;

import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.screen.domain.select.WfEventListSelect;
import cn.wxgis.jc.screen.web.service.WfEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/wechat")
@AllArgsConstructor
@Api(value = "WechatController", tags = "微信统计接口-")
public class WechatController  {

    private final WfEventService eventService;

    @ApiOperation(value ="点位办结率", httpMethod ="GET")
    @GetMapping("/pointsAvg")
    public JsonResult pointsAvg(String regionName, String cycle) {
        return JsonResult.ok(eventService.getEventPointsAvg(regionName, cycle));
    }


    @ApiOperation(value ="点位事件类型占比率", httpMethod ="GET")
    @GetMapping("/eventTypeAvg")
    public JsonResult eventTypeAvg(String regionName, String cycle) {
        return JsonResult.ok(eventService.getEventTypeAvg(regionName, cycle));
    }

    @ApiOperation(value ="事件类型", httpMethod ="GET")
    @GetMapping("/workEvent")
    public JsonResult workEvent(WfEventListSelect select) {
        return JsonResult.ok(eventService.getWorkEvent(select));
    }
}
