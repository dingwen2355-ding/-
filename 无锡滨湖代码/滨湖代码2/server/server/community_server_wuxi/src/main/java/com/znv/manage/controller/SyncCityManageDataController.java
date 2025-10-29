package com.znv.manage.controller;

import com.znv.manage.common.bean.Device;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.DeviceService;
import com.znv.manage.service.SyncCityManageDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 应急预案相关接口
 */
@Api(tags = "查询固定视频设备")
@RestController
@RequestMapping("sync")
public class SyncCityManageDataController {


    @Autowired
    SyncCityManageDataService syncCityManageDataService;

    @ApiOperation("查询固定视频设备")
    @GetMapping("data")
    public Result synchronizeData() {
        Result result = new Result();
        int i = syncCityManageDataService.syncData();
        result.setData(i);

        return result;
    }


}
