package cn.wxgis.jc.sync.web.controller;

import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.sync.web.service.SynchUnifyService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/unify")
@AllArgsConstructor
@Api(value = "UnifyController", tags = "同步中心-统一身份认证同步相关APi")
public class SynchUnifyController {

    private final SynchUnifyService unifyService;

    /**
     * 同步组织数据
     */
    @PostMapping("/getToken")
    public JsonResult getToken() {
        return JsonResult.ok(unifyService.getToken());
    }

    /**
     * 同步组织数据
     */
    @PostMapping("/synchDept")
    public JsonResult synchDept(Integer noFetchChild) {
        return JsonResult.ok(unifyService.synchDeptData(noFetchChild));
    }

    /**
     * 同步用户数据
     */
    @PostMapping("/synchUser")
    public JsonResult synchUser(String id) {
        return JsonResult.ok(unifyService.synchUserData(id));
    }

    @GetMapping("/syncRegion")
    public JsonResult syncRegion() {
        unifyService.syncRegion();
        return JsonResult.ok();
    }

}
