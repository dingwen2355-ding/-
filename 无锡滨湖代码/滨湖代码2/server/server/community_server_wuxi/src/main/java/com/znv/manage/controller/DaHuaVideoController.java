package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.DaHuaVideoService;
import com.znv.manage.service.impl.DahuaServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 大华相关接口
 */
@Api(tags = "大华获取实时监视URI")
@RestController
@RequestMapping("dahua")
public class DaHuaVideoController {

    @Autowired
    DaHuaVideoService daHuaVideoService;

    @Autowired
    DahuaServiceImpl dahuaService;

    @ApiOperation("获取实时监视URI")
    @GetMapping("getVideoUrl")
    public Result getVideoUrl(
            @RequestParam(value = "videoType")
                    String videoType,
            @RequestParam(value = "deviceId")
                    String deviceId) {
        Result result = daHuaVideoService.getVideoUrl(videoType, deviceId);
        return result;
    }

    @ApiOperation("城市之眼获取实时监视URI")
    @GetMapping("getCityEyesVideoUrl")
    public Result getCityEyesVideoUrl(
            @RequestParam(value = "deviceId")
                    String deviceId,
            @RequestParam(value = "protocol")
                    String protocol/*,
            @RequestParam(value = "resolution")
                    String resolution*/) {
        Result result = daHuaVideoService.getCityEyesVideoUrl(deviceId, protocol);
        return result;
    }

    @ApiOperation("城市之眼获取实时监视URI--关流")
    @GetMapping("closeCityEyesVideoUrl")
    public Result closeCityEyesVideoUrl(
            @RequestParam(value = "tgtSessionId")
                    String tgtSessionId) {
        Result result = daHuaVideoService.closeCityEyesVideoUrl(tgtSessionId);
        return result;
    }

    @ApiOperation("城市之眼同步视频设备")
    @GetMapping("SyncCityEyesDevice")
    public int SyncCityEyesDevice() {
        int data = daHuaVideoService.SyncCityEyesDevice();
        return data;
    }

    @ApiOperation("获取token")
    @GetMapping("getDahuaToken")
    public Result getDahuaToken() {
        Result result = new Result();
        result.setData(dahuaService.handleToken());
        return result;
    }

    @ApiOperation("获取设备信息")
    @GetMapping("getDeviceList")
    public Result getDeviceList(@RequestParam(value = "name", required = false)
                                        String name
    ) {
        Result result = daHuaVideoService.getDeviceList(name);
        return result;
    }

    @ApiOperation("获取设备信息(排除device_kind=666)")
    @GetMapping("getDeviceListNew")
    public Result getDeviceListNew(@RequestParam(value = "name", required = false)
                                           String name
    ) {
        Result result = daHuaVideoService.getDeviceListNew(name);
        return result;
    }

    @ApiOperation("设备数据同步")
    @GetMapping("syncDevice")
    public Result syncDevice() {
        return dahuaService.syncDevice();
    }

}
