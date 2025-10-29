package cn.wxgis.jc.sync.web.controller;

import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.sync.web.service.SynchVideoService;
import cn.wxgis.jc.synch.video.vo.RealmonitorVo;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/video")
@AllArgsConstructor
@Api(value = "VideoController", tags = "同步中心-视频设备相关APi")
public class SynchVideoController {

    private SynchVideoService videoService;

    /**
     * 视频第一次验证接口
     */
    @GetMapping("/authorize")
    public JsonResult authorize() {
        return JsonResult.ok(videoService.authorizeToken());
    }

    /**
     * 保活
     */
    @PostMapping("/keepalive")
    public JsonResult keepalive() {
        return JsonResult.ok(videoService.keepalive());
    }

    /**
     * 获取组织信息
     */
    @PostMapping("/deviceTree")
    public JsonResult deviceTree(String id) {
        return JsonResult.ok(videoService.deviceTree(null));
    }

    /**
     * 获取设备列表
     */
    @PostMapping("/deviceList")
    public JsonResult deviceList(String id) {
        return JsonResult.ok(videoService.deviceList(id));
    }

    /**
     * 获取通道列表
     */
    @PostMapping("/channelList")
    public JsonResult channelList(String id) {
        return JsonResult.ok(videoService.channelList(id));
    }


    /**
     * 获取视频流（flv）
     */
    @PostMapping("/flv")
    public JsonResult flv(String channelId) {
        return JsonResult.ok(videoService.flv(channelId));
    }

    /**
     * 获取视频流（rtsp）
     */
    @PostMapping("/rtsp")
    public JsonResult rtsp(String channelId) {
        return JsonResult.ok(videoService.rtsp(channelId));
    }

    /**
     * 获取视频流（hls）
     */
    @PostMapping("/hls")
    public JsonResult hls(String channelId) {
        RealmonitorVo realmonitor = new RealmonitorVo();
        realmonitor = videoService.hls(channelId);
        return JsonResult.ok(realmonitor);
    }



    @PostMapping("/synchDevice")
    public JsonResult synchDevice() {
        return JsonResult.ok(videoService.synchDevice(null));
    }

    @PostMapping("/synchChannel")
    public JsonResult synchChannel() {
        return JsonResult.ok(videoService.synchChannel());
    }



}
