package com.znv.manage.controller.resource;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.resource.HistoricalEvent;
import com.znv.manage.service.resource.HistoricalEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "历史事件")
@RestController
@RequestMapping("HistoricalEvent")
public class HistoricalEventController {

    @Autowired
    private HistoricalEventService historicalEventService;

    @GetMapping("/queryHistoricalEventList")
    @ApiOperation("查询历史事件列表")
    public PageInfo<HistoricalEvent> queryHistoricalEventList(@RequestParam(value = "accidentTitle", required = false) String accidentTitle,
                                                              @RequestParam(value = "accidentAddress", required = false) String accidentAddress,
                                                              @RequestParam(value = "accidentType", required = false) String accidentType,
                                                              @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return historicalEventService.queryHistoricalEventList(accidentTitle, accidentAddress, accidentType, pageNum, pageSize);
    }

    @PostMapping("/deleteHistoricalEvent")
    @ApiOperation("删除历史事件")
    public Result deleteHistoricalEvent(@RequestParam(value = "ids", required = false) String ids) {
        return historicalEventService.deleteHistoricalEvent(ids);
    }

    @PostMapping("/insertSelective")
    @ApiOperation("新增历史事件")
    public Result insertSelective(@RequestParam(value = "historicalEvent") String data,
                                  @RequestParam(value = "files", required = false) MultipartFile files) {
        HistoricalEvent historicalEvent = JSONObject.parseObject(data,HistoricalEvent.class);
        return historicalEventService.insertSelective(historicalEvent,files);
    }

    @PostMapping("/updateByPrimaryKeySelective")
    @ApiOperation("修改历史事件")
    public Result updateByPrimaryKeySelective(@RequestParam(value = "historicalEvent") String data,
                                              @RequestParam(value = "files", required = false) MultipartFile files) {
        HistoricalEvent historicalEvent = JSONObject.parseObject(data,HistoricalEvent.class);
        return historicalEventService.updateByPrimaryKeySelective(historicalEvent,files);
    }
}
