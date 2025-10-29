package com.znv.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.RescueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 应急预案相关接口
 */
@Api(tags = "查询救援")
@RestController
@RequestMapping("rescue")
public class RescueController {

    @Autowired
    RescueService rescueService;

    @ApiOperation("查询救援队伍")
    @GetMapping("getRescueTeam")
    public Result getRescueTeam() {
        Result result = new Result();
        List<Map<String, String>> list = rescueService.getRescueTeam();
        result.setData(list);
        return result;
    }


    @ApiOperation("查询救援队伍")
    @GetMapping("getRescueDevice")
    public Result getRescueDevice() {
        Result result = new Result();
        List<Map<String, String>> list = rescueService.getRescueDevice();
        result.setData(list);
        return result;
    }

}
