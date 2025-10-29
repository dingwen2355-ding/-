package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.DingDingService;
import com.znv.manage.service.FusionVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:
 * @Date:
 * @Description: 注：
 */
@Api(tags = "钉钉数据对接")
@RestController
public class DingDingController {

    @Autowired
    DingDingService dingDingService;

    @ApiOperation(value = "同步部门数据")
    @PostMapping("/dingDing/syncDepartmentData")
    public Result syncDepartmentData(
        @RequestParam(value = "deptId" , defaultValue = "1") String deptId) {
        return dingDingService.syncDepartment(deptId);
    }

    @ApiOperation(value = "同步通讯录数据")
    @PostMapping("/dingDing/syncCommunicationData")
    public Result syncCommunicationData(
            @RequestParam(value = "deptId" , defaultValue = "1") String deptId) {
        return dingDingService.syncCommunicationData(deptId);
    }

    @ApiOperation(value = "创建视频会议")
    @PostMapping("/dingDing/CreateVideoConferences")
    public Result CreateVideoConferences(
            @RequestParam(value = "unionIds") List<String> unionIds,
            @RequestParam(value = "titleName") String titleName) {
        return dingDingService.createVideoConferences(unionIds,titleName);
    }

}
