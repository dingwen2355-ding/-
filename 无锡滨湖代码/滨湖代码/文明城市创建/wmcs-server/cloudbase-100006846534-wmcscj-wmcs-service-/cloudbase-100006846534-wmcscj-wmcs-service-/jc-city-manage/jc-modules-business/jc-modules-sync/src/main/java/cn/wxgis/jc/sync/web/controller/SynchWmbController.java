package cn.wxgis.jc.sync.web.controller;

import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.sync.web.service.SynchWmbService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/wmb")
@AllArgsConstructor
@Api(value = "WmbController", tags = "同步中心-文明办原系统功能相关APi")
public class SynchWmbController {

    private final SynchWmbService wmbService;

    /**
     * 获取考核周期数据
     */
    @GetMapping("/synchCycle")
    public JsonResult synchCycle() {
        wmbService.synchCycle();
        return JsonResult.ok();
    }

    /**
     * 文明城市点位类型
     */
    @GetMapping("/synchCheckPointsType")
    public JsonResult synchCheckPointsType() {
        wmbService.synchCheckPointsType();
        return JsonResult.ok();
    }

    /**
     * 文明城市检查项
     */
    @GetMapping("/synchCheckItem")
    public JsonResult synchCheckItem() {
        wmbService.synchCheckItem();
        return JsonResult.ok();
    }

    /**
     * 文明城市检查点位
     */
    @GetMapping("/synchCheckPoints")
    public JsonResult synchCheckPoints() {
        wmbService.synchCheckPoints();
        return JsonResult.ok();
    }

    /**
     * 文明城市事件列表
     */
    @GetMapping("/synchEvent")
    public JsonResult synchEvent() {
        wmbService.synchEvent();
        return JsonResult.ok();
    }


}
