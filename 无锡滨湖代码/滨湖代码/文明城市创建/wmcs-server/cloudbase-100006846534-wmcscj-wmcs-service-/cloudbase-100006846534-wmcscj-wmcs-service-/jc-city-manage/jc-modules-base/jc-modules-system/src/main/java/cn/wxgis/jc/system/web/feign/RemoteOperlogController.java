package cn.wxgis.jc.system.web.feign;

import cn.wxgis.jc.common.constant.ProviderConstant;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.web.controller.BaseController;
import cn.wxgis.jc.security.annotation.InnerAuth;
import cn.wxgis.jc.system.po.SysOperLog;
import cn.wxgis.jc.system.web.service.SysOperlogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class RemoteOperlogController extends BaseController {

    private final SysOperlogService operlogService;

    /**
     * 获取当前用户信息
     */
    @InnerAuth
    @PostMapping(ProviderConstant.OPERLOG_SAVE)
    public JsonResult save(@RequestBody SysOperLog operLog) {
        int result = operlogService.save(operLog);
        return JsonResult.ok(result);
    }

}
