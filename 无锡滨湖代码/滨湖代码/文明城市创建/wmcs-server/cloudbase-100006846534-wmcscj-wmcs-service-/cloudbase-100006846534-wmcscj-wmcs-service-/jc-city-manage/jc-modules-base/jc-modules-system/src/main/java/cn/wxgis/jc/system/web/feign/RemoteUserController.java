package cn.wxgis.jc.system.web.feign;

import cn.wxgis.jc.common.constant.ProviderConstant;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.web.controller.BaseController;
import cn.wxgis.jc.security.annotation.InnerAuth;
import cn.wxgis.jc.system.req.SysUserSaveRequest;
import cn.wxgis.jc.system.web.service.SysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class RemoteUserController extends BaseController {

    private final SysUserService userService;

    /**
     * 获取当前用户信息
     */
    @InnerAuth
    @PostMapping(ProviderConstant.USER_FIND_USERNAME)
    public JsonResult findByUserName(@RequestParam("userName") String userName) {
        return JsonResult.ok(userService.getLoginUser(userName));
    }

    /**
     * 获取当前用户信息
     */
    @InnerAuth
    @PostMapping(ProviderConstant.USER_SAVE)
    public JsonResult save(@RequestBody SysUserSaveRequest user)
    {
        int result = userService.save(user);
        return JsonResult.ok(result);
    }

}
