package cn.wxgis.jc.system.auth.controller;

import cn.wxgis.jc.common.domain.res.LoginBodyRes;
import cn.wxgis.jc.common.enums.basedata.SystemEnum;
import cn.wxgis.jc.common.exception.BaseException;
import cn.wxgis.jc.common.log.LoginLog;
import cn.wxgis.jc.common.result.JsonResult;
import cn.wxgis.jc.common.util.StringUtils;
import cn.wxgis.jc.security.service.TokenService;
import cn.wxgis.jc.system.auth.enums.AuthorizationTypeEnum;
import cn.wxgis.jc.system.auth.service.SysLoginService;
import cn.wxgis.jc.system.auth.service.SysOAuthLoginService;
import cn.wxgis.jc.system.auth.service.SysWechatService;
import cn.wxgis.jc.system.model.LoginUser;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/login")
@AllArgsConstructor
@Api(value = "LoginController", tags = "用户登陆相关Api")
public class LoginController {

    private final TokenService tokenService;
    private final SysLoginService loginService;
    private final SysOAuthLoginService oAuthLoginService;
    private final SysWechatService wechatService;

    @LoginLog(value = SystemEnum.LoginEnum.SYSTEM_LOGIN)
    @PostMapping("")
    @Operation(summary = "用户登陆接口")
    public JsonResult getAccessToken(@ApiParam(value = "登录信息", required = true)
                                         @Validated @RequestBody LoginBodyRes loginBody)
            throws BaseException {
        // 用户登录
        LoginUser userInfo = loginService.getLoginUserByAccount(loginBody);
        // 获取登录token
        return JsonResult.ok(tokenService.createToken(userInfo));
    }

    @LoginLog(value = SystemEnum.LoginEnum.THIRD_LOGIN)
    @GetMapping("/authorization")
    @Operation(summary = "第三方授权（PC登录）")
    public JsonResult authorization() {
        String url = oAuthLoginService.authorization(AuthorizationTypeEnum.PC.getCode());
        return JsonResult.ok(url);
    }

    @LoginLog(value = SystemEnum.LoginEnum.THIRD_LOGIN)
    @PostMapping("/tohome")
    @Operation(summary = "统一授权登录")
    public JsonResult tohome(String code) {
        LoginUser userInfo = oAuthLoginService.unclassifiedLogin(code, AuthorizationTypeEnum.PC.getCode());
        if (StringUtils.isNull(userInfo)) return JsonResult.error("获取第三方授权失败");
        // 获取登录token
        Map<String, Object> token = tokenService.createToken(userInfo);
        log.info("获取权限信息：" + token.toString());
        return JsonResult.ok(token);
    }

    @GetMapping("/logout")
    @Operation(summary = "第三方退出")
    public JsonResult logout() {
        String url = oAuthLoginService.logout();
        return JsonResult.ok(url);
    }


    @LoginLog(value = SystemEnum.LoginEnum.WECHAT_LOGIN)
    @GetMapping("/wechat")
    @Operation(summary = "政务微信授权")
    public JsonResult wechat(String code) {
        LoginUser userInfo = wechatService.wechatLogin(code);
        if (StringUtils.isNull(userInfo)) return JsonResult.error("政务微信登陆失败");
        // 获取登录token
        Map<String, Object> map = tokenService.createToken(userInfo);
        return JsonResult.ok(map);
    }

    @GetMapping("/signature")
    @Operation(summary = "政务微信签名")
    public JsonResult signature() {
        return JsonResult.ok(wechatService.signature());
    }


    @LoginLog(value = SystemEnum.LoginEnum.THIRD_LOGIN)
    @GetMapping("/authorization/screen")
    @Operation(summary = "第三方授权（大屏登录）")
    public JsonResult authorizationScreen() {
        String url = oAuthLoginService.authorization(AuthorizationTypeEnum.SCREEN.getCode());
        return JsonResult.ok(url);
    }

    @LoginLog(value = SystemEnum.LoginEnum.THIRD_LOGIN)
    @PostMapping("/tohome/screen")
    @Operation(summary = "统一授权登录")
    public JsonResult tohomeScreen(String code) {
        LoginUser userInfo = oAuthLoginService.unclassifiedLogin(code, AuthorizationTypeEnum.SCREEN.getCode());
        if (StringUtils.isNull(userInfo)) return JsonResult.error("获取第三方授权失败");
        // 获取登录token
        Map<String, Object> token = tokenService.createToken(userInfo);
        log.info("获取权限信息：" + token.toString());
        return JsonResult.ok(token);
    }

}
