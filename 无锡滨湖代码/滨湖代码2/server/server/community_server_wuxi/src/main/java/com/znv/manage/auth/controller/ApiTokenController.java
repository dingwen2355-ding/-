package com.znv.manage.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.auth.annotation.ApiTokenCheck;
import com.znv.manage.auth.config.ApiTokenConfig;
import com.znv.manage.auth.service.ApiTokenService;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @Auther: yf
 * @Date: 2020/6/30
 * @Description:
 */

@RestController
@Slf4j
@Api(tags="token鉴权接口")
@RequestMapping("/auth")
public class ApiTokenController {

    @Autowired
    ApiTokenConfig apiTokenConfig;

    @Autowired
    ApiTokenService apiTokenService;

    @ApiOperation(value = "获取token")
    @GetMapping(value = "/getToken")
    public Result getToken(HttpSession session) {
        Result result = new Result();

        JSONObject tokenJson = new JSONObject();
        // 按照uuid生成token
        String token = UUID.randomUUID().toString().replaceAll("-","");

        // 存储token，并设置超期时间，默认半小时
//        session.setAttribute(token, DateUtil.getStringDate());
//        session.setMaxInactiveInterval(apiTokenConfig.getExpiration());
        apiTokenService.storeToken(token,session);

        // 返回token
        tokenJson.put(apiTokenConfig.getHeader(),token);
        result.setData(tokenJson);

        return result;
    }

    @ApiOperation(value = "校验token有效性")
    @GetMapping(value = "/checkToken")
    public Result checkToken(@RequestParam(value = "token") String token, HttpSession session) {

        Result result = new Result();
//        result.setData(session.getAttribute(token) != null);
        result.setData(apiTokenService.checkToken(token,session));

        return result;
    }

    @ApiOperation(value = "设置本地调试用鉴权session")
    @PostMapping(value = "/setDebugSession")
    public Result setDebugSession(HttpSession session) {
        session.setAttribute(apiTokenConfig.getDebugSessionKey(), DateUtil.getStringDate());
        session.setMaxInactiveInterval(0); // 调试用session永不超时

        return new Result(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
    }

    @ApiOperation(value = "测试鉴权接口(不需要token加密)")
    @GetMapping(value = "/testApiTokenNoEncrypt")
    @ApiTokenCheck
    public Result test1() {
        return new Result(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
    }

    @ApiOperation(value = "测试鉴权接口(需要token加密)")
    @GetMapping(value = "/testApiTokenEncrypt")
    @ApiTokenCheck(isTokenEncrypt = true)
    public Result test2() {
        return new Result(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
    }

    @ApiOperation(value = "测试鉴权接口(不需要鉴权)")
    @GetMapping(value = "/testApiToken")
    public Result test3() {
        return new Result(ResultCodeEnum.NORMAL.getCode(), ResultCodeEnum.NORMAL.getName());
    }
}
