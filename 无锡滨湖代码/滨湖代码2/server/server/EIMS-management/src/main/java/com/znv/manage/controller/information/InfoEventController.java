package com.znv.manage.controller.information;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.information.InfoEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "信息事件")
@RequestMapping("/event")
@Validated
public class InfoEventController {
    @Autowired
    private InfoEventService infoEventService;

    @ApiOperation(value = "事件类别")
    @GetMapping(value = "/queryEventType")
    public Map<String, List<String>> queryEventType() {
        return infoEventService.queryEventType();
    }

    @ApiOperation(value = "事件列表")
    @GetMapping(value = "/queryEventList")
    public Result queryEventList(@RequestParam(value = "eventType", required = false) String eventType, @RequestParam(value = "eventType2", required = false) String eventType2,
                                 @RequestParam(value = "text", required = false) String text,
                                 @RequestParam(value = "reportPeople", required = false) String reportPeople,
                                 @RequestParam(value = "startTime", required = false) String startTime, @RequestParam(value = "endTime", required = false) String endTime,
                                 @RequestParam(value = "pageStart", required = false) Integer pageNum,
                                 @RequestParam(value = "pageLen", required = false) Integer pageSize) {

        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<Map<String, String>> list = infoEventService.queryEventList(eventType, eventType2, text, startTime, endTime, reportPeople);
        PageInfo pageInfo = new PageInfo(list);
        return new Result(pageInfo);
    }

    @ApiOperation(value = "事件详情")
    @GetMapping(value = "/queryEventInfo")
    public Map<String, Object> queryEventInfo(@RequestParam(value = "eventId") String eventId) {
        return infoEventService.queryEventInfo(eventId);
    }
}
