package com.znv.manage.controller;

import com.znv.manage.common.bean.DeviceInfo;
import com.znv.manage.common.bean.VideoDevice;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.DeviceService;
import com.znv.manage.service.impl.DahuaServiceImpl;
import com.znv.manage.service.impl.DeviceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 应急预案相关接口
 */
@Api(tags = "查询固定视频设备")
@RestController
@RequestMapping("queryDevice")
public class DeviceController {


    @Autowired
    DeviceServiceImpl deviceService;

    @Autowired
    DahuaServiceImpl dahuaService;

    @ApiOperation("查询固定视频设备")
    @GetMapping("getDeviceFixedVideo")
    public Result getDeviceFixedVideo(@RequestParam(value = "deviceTypeName", required = false) String deviceTypeName,
                                      @RequestParam(value = "precinctId", required = false) String precinctId,
                                      @RequestParam(value = "precinctName", required = false) String precinctName) {
        Result result = new Result();
        List<VideoDevice> list = deviceService.getDeviceFixedVideo(deviceTypeName, precinctName);
        result.setData(list);
        return result;
    }

    @ApiOperation("查询固定视频设备类型与图标")
    @GetMapping("getDeviceFixedVideoType")
    public Result getDeviceFixedVideoType(@RequestParam(value = "precinctId", required = false) String precinctId,
                                          @RequestParam(value = "precinctName", required = false) String precinctName) {
        Result result = new Result();
        List<Map<String, String>> list = deviceService.getDeviceFixedVideoType(precinctName);
        result.setData(list);
        return result;
    }

    @ApiOperation("查询轮播视频设备")
    @GetMapping("getDeviceRotationMapping")
    public Result getDeviceRotationMapping(@RequestParam(value = "rotationType", required = false) String rotationType, @RequestParam(value = "precinctId", required = false) String precinctId,
                                           @RequestParam(value = "precinctName", required = false) String precinctName) {
        Result result = new Result();
        List<Map<String, String>> list = deviceService.getDeviceRotationMapping(rotationType, precinctName);
        result.setData(list);
        return result;
    }

    @ApiOperation("查询轮播视频设备类型")
    @GetMapping("getDeviceRotationType")
    public Result getDeviceRotationType(@RequestParam(value = "precinctId", required = false) String precinctId,
                                        @RequestParam(value = "precinctName", required = false) String precinctName) {
        Result result = new Result();
        List<Map<String, String>> list = deviceService.getDeviceRotationType(precinctName);
        result.setData(list);
        return result;
    }

    @ApiOperation("查询城市之眼视频设备")
    @GetMapping("queryCityEyeDeviceList")
    public List<Map<String, Object>> queryCityEyeDeviceList(
        @RequestParam(value = "precinctId", required = false) String precinctId,
        @RequestParam(value = "deviceName", required = false) String deviceName) {
        return deviceService.queryCityEyeDeviceList(precinctId, deviceName);
    }

    @ApiOperation("查询高达单兵视频列表")
    @GetMapping("queryIndividualList")
    public Result queryIndividualList(@RequestParam(value = "precinctId", required = false) String precinctId,
                                      @RequestParam(value = "deviceId", required = false) String deviceId) {
        Result result = new Result();
        List<Map<String, Object>> list = deviceService.queryIndividualList(precinctId, deviceId);
        result.setData(list);
        return result;
    }

    @ApiOperation("查询高达单兵视频列表（对外）")
    @GetMapping("queryDanbinList")
    public Result queryDanbinList(@RequestParam(value = "precinctId", required = false) String precinctId,
                                  HttpServletRequest request) {
        Result result = new Result();
        if (!"8c57306d46885ac956674f009253b131".equals(request.getHeader("token"))) {
            result.setCode(401);
            result.setMessage("token校验失败!");
            return result;
        }
        List<Map<String, Object>> list = deviceService.queryIndividualList(precinctId, null);
        result.setData(list);
        return result;
    }

    @ApiOperation("查询单兵列表")
    @GetMapping("query32DeviceList")
    public Result query32DeviceList() {
        Result result = new Result();
        List<Map<String, Object>> list = deviceService.query32DeviceList();
        result.setData(list);
        return result;
    }

    @ApiOperation("查询布控球列表")
    @GetMapping("query34DeviceList")
    public Result query34DeviceList() {
        Result result = new Result();
        List<Map<String, Object>> list = deviceService.query34DeviceList();
        result.setData(list);
        return result;
    }

    @ApiOperation("查询视频会商设备")
    @GetMapping("query33DeviceList")
    public Result query33DeviceList(@RequestParam(value = "precinctId", defaultValue = "510100") String precinctId) {
        Result result = new Result();
        List<DeviceInfo> deviceInfo = deviceService.query33DeviceList(precinctId);
        result.setData(deviceInfo);
        return result;
    }

    @ApiOperation("查询无人机设备")
    @GetMapping("query35DeviceList")
    public Result query35DeviceList(@RequestParam(value = "precinctId", required = false) String precinctId,
                                    @RequestParam(value = "deviceKind", required = false) String deviceKind) {
        Result result = new Result();
        if (StringUtils.isEmpty(deviceKind)) {
            deviceKind = "35";
        }
        List<DeviceInfo> deviceInfo = deviceService.query35DeviceList(precinctId, deviceKind);
        result.setData(deviceInfo);
        return result;
    }

    @ApiOperation("坐标转换街镇")
    @GetMapping("getDeviceGpsIsNotNull")
    public Result getDeviceGpsIsNotNull() {
        Result result = new Result();
        int i = deviceService.getDeviceGpsIsNotNull();
        result.setData(i);
        return result;
    }

    @ApiOperation("大华token")
    @GetMapping("getDahuaToken")
    public String getDahuaToken() {
        return dahuaService.getToken();
    }

    @ApiOperation("手动同步测试")
    @GetMapping("dbsync")
    public void dbsync() {
        deviceService.syncDbEquip();
    }
}
