package com.znv.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.service.ManageService;
import com.znv.manage.service.impl.DahuaServiceImpl;
import com.znv.manage.service.impl.SyncEventServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author znv
 * @Description: xxx描述该类主要说明
 */
@Slf4j
@Controller
@Api(tags = "测试用接口类")
public class TestController {

    @Autowired
    DahuaServiceImpl dahuaService;

    @Autowired
    SyncEventServiceImpl syncEventService;

    @ApiOperation(value = "swagger页面")
    @GetMapping(value = "/swagger")
    public String swagger(HttpSession session) {
        session.setAttribute("Authorization", "swagger");
        return "redirect:swagger-ui.html";
    }

    @ApiOperation(value = "websocket页面")
    @GetMapping("/websocket")
    public String websocket() {
        return "websocket";
    }

    @ApiOperation("yufei测试")
    @GetMapping("testDahua")
    @ResponseBody
    public Result testDahua() {
        dahuaService.dealLqyDanbinEventMessage();
        return new Result();
    }

    @ApiOperation(value = "手动同步事件")
    @GetMapping("/manualSyncEvent")
    @ResponseBody
    public Result manualSyncEvent(@RequestParam(value = "beginTime") String beginTime,
                                  @RequestParam(value = "endTime") String endTime) {
        Result result = new Result();

        JSONObject dateRange = new JSONObject();
        dateRange.put("creatDate", beginTime + "," + endTime);

        JSONObject dataFilter = new JSONObject();
        int saveCount = 0;

        //1.12345
        dataFilter.put("flowSource", "");
        saveCount += syncEventService.syncEvent(dataFilter, dateRange,"MAIN_12345");

        //2.其他事件
        dataFilter.put("flowSource", "");
        saveCount += syncEventService.syncEvent(dataFilter, dateRange,"NORMAL_MAIN");

        result.setData(saveCount);
        return result;
    }
}
