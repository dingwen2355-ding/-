package com.znv.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.znv.manage.service.SsoLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.objects.Global;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("sso")
@RestController
@Api(tags = "1.对接统一认证相关接口")
@Slf4j
public class SsoLoginController {

    @Autowired
    private SsoLoginService ssoLoginService;

    // 发送验证码
    @ApiOperation("1.发送验证码")
    @PostMapping("sendSmsCode")
    public JSONObject sendSmsCode(@RequestParam(value = "phoneNumber")String phoneNumber,
                                  @RequestParam(value = "precinctId",required = false)String precinctId) {

        return ssoLoginService.sendSmsCode(phoneNumber,precinctId);
    }

    // 登录
    @ApiOperation("2.登录(1 authType：password-账号登录 sms-⼿机号登录；" +
            "2 auth：authType=password时填写⽤户密码；authType=sms时填写⼿机验证码短信验证码)")
    @PostMapping("login")
    public JSONObject login(@RequestParam(value = "authType")String authType,
                            @RequestParam(value = "loginId")String loginId,
                            @RequestParam(value = "auth")String auth,
                            @RequestParam(value = "precinctId",required = false)String precinctId) {

        return ssoLoginService.login(authType, loginId, auth,precinctId);
    }

    // 登出
    @ApiOperation("3.登出")
    @PostMapping("logout")
    public JSONObject logout(@RequestParam(value = "accessToken")String accessToken,
                            @RequestParam(value = "userId")String userId,
                             @RequestParam(value = "precinctId",required = false)String precinctId) {

        return ssoLoginService.logout(accessToken, userId, precinctId);
    }

    // 校验token
    @ApiOperation("4.校验token")
    @PostMapping("checkToken")
    public JSONObject checkToken(@RequestParam(value = "accessToken")String accessToken,
                                @RequestParam(value = "userId")String userId,
                                 @RequestParam(value = "precinctId",required = false)String precinctId) {

        return ssoLoginService.checkToken(accessToken, userId, precinctId);
    }

    // 解析token
    @ApiOperation("5.解析token")
    @PostMapping("getAccessTokenInfo")
    public Map<String, String> getAccessTokenInfo(@RequestParam(value = "accessToken")String accessToken) {
        Algorithm algorithm = Algorithm.HMAC256("321");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("123")
                .build();
        Map<String, Claim> claims = JWT.decode(accessToken).getClaims();
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            map.put(entry.getKey(), entry.getValue().asString());
        }
        return map;
    }
}
