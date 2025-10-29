package cn.wxgis.jc.system.web.feign;

import cn.wxgis.jc.common.constant.ProviderConstant;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.web.controller.BaseController;
import cn.wxgis.jc.security.annotation.InnerAuth;
import cn.wxgis.jc.system.po.SysLogininfor;
import cn.wxgis.jc.system.web.service.SysLogininforService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class RemoteLogininforController extends BaseController {

    private final SysLogininforService logininforService;

    /**
     * 获取当前用户信息
     */
    @InnerAuth
    @PostMapping(ProviderConstant.LOGININFOR_SAVE)
    public JsonResult save(@RequestBody SysLogininfor operLog) {
        int result = logininforService.save(operLog);
        return JsonResult.ok(result);
    }

}
