package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.UserDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "用户组织数据接入")
@RestController
@Slf4j
@RequestMapping("/UserData")
public class UserDataController {
    @Autowired
    private UserDataService userDataService;

    @ApiOperation("获取数据token")
    @GetMapping("/getToken")
    public String getToken() {
        return userDataService.getToken();
    }

    @ApiOperation("获取用户数据列表")
    @GetMapping("/getUserList")
    public Result getUserList() {
        return userDataService.getUserList();
    }

    @ApiOperation("获取组织数据列表")
    @GetMapping("/getPrecinctList")
    public Result getPrecinctList() {
        return userDataService.getPrecinctList();
    }

    @ApiOperation("获取单个用户数据")
    @GetMapping("/getOneUser")
    public Result getOneUser(@RequestParam(name = "account") String account) {
        return userDataService.getOneUser(account);
    }

    @ApiOperation("发送信息")
    @GetMapping("/sendMessage")
    public Result sendMessage(@RequestParam(name = "mobile") String mobile,
                              @RequestParam(name = "msg") String msg) {
        return userDataService.sendMessage(mobile, msg);
    }

    @ApiOperation("获取鉴权token")
    @GetMapping("/getAuthToken")
    public Result getAuthToken(@RequestParam(name = "code") String code,
                               @RequestParam(name = "requestAppId") String requestAppId,
                               @RequestParam(name = "requestAppSecret") String requestAppSecret,
                               @RequestParam(name = "requestRedirectUrl") String requestRedirectUrl,
                               HttpServletRequest request) {
        return userDataService.oAuthToken(code, requestAppId, requestAppSecret, requestRedirectUrl, request);
    }

    @ApiOperation("刷新token")
    @GetMapping("/refreshToken")
    public Result refreshToken(@RequestParam(name = "refreshToken") String refreshToken) {
        return userDataService.refreshToken(refreshToken);
    }

    @ApiOperation("根据鉴权token获取用户信息")
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam(name = "token") String token) {
        return userDataService.getUserInfo(token);
    }


}
