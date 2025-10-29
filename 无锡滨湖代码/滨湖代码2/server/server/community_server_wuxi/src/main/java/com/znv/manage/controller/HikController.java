package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.HikService;
import com.znv.manage.service.impl.DeviceServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: ygr
 * @Date: 2019/10/16
 * @Description: 注：
 */
@Api(tags = "海康查询接口")
@RestController
public class HikController {

    @Autowired
    HikService hikService;

    @Resource
    private DeviceServiceImpl deviceService;

    @GetMapping("/hik/getRtspUrl")
    public Result getRtspNew(
        @RequestParam(value = "indexCode") String indexCode) {

        return hikService.getRtsp(indexCode);
    }

    @GetMapping("/hik/getHlsUrl")
    public Result getHlsNew(
        @RequestParam(value = "indexCode") String indexCode) {

        return hikService.getHls(indexCode);
    }

    @GetMapping("/hik/getCamera")
    public Result getCamera(@RequestParam(value = "pageNo") Integer pageNo,
                            @RequestParam(value = "pageSize") Integer pageSize) {
        return hikService.getCamera(pageNo,pageSize);
    }

    @GetMapping("/hik/synCamera")
    public void synCamera() {
        deviceService.saveHikDevice();
    }
}
