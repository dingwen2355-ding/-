package com.jslc.modules.szbh.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jslc.common.config.AbstractSsoConfiguration;
import com.jslc.common.config.SsoBackConfiguration;
import com.jslc.common.config.SsoFrontConfiguration;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhengyongbiao
 * @title: SsoLoginController
 * @projectName digit-binhu-back
 * @date 2023/5/18 17:34
 */
@Controller
@AllArgsConstructor
@Slf4j
public class SsoLoginController {
    @Autowired
    SsoBackConfiguration ssoBackConfiguration;

    @Autowired
    SsoFrontConfiguration ssoFrontConfiguration;

    /**
     * @描述
     * @创建人 zhengyongbiao
     * @创建时间 2023/5/17 13:35
     */
    @GetMapping("/sso/login")
    @ApiOperation(value = "重定向到单点登录认证中心获取授权码-后端", notes = "重定向到单点登录认证中心获取授权码-后端")
    public ModelAndView login(String code) {
        return doLoginHander(code, ssoBackConfiguration);
    }

    /**
     * @描述
     * @创建人 zhengyongbiao
     * @创建时间 2023/5/17 13:35
     */
    @GetMapping("/frontLogin")
    @ApiOperation(value = "重定向到单点登录认证中心获取授权码-前端", notes = "重定向到单点登录认证中心获取授权码-前端")
    public ModelAndView frontLogin(String code) {
        return doLoginHander(code, ssoFrontConfiguration);
    }


    @NotNull
    private ModelAndView doLoginHander(String code, AbstractSsoConfiguration configuration) {
        ModelAndView modelAndView = new ModelAndView();
        //判断请求是否有授权码，有授权码使用授权码获取token，无授权码重定向到单点服务系统获取授权码
        if (StrUtil.isNotBlank(code)) {
            String url = configuration.getAccessTokenUri() + "?grant_type=authorization_code" + "&client_id=" + configuration.getClientId() + "&client_secret=" + configuration.getClientSecret() + "&code=" + code + "&redirect_uri=" + configuration.getRedirectUri() + "&scope=all";
            log.info("AccessTokenUri:" + url);
            HttpRequest rq = HttpRequest.post(url);
            // 发起请求获取token
            HttpResponse response = rq.execute();
            String token = response.body();
            JSONObject rspVal = JSON.parseObject(token);
            //设置登录状态未一登录状态
            modelAndView.setViewName("redirect:" + configuration.getHomePage() + "?" + rspVal.getJSONObject("data").getString("accessToken"));
        } else {
            //重定向到单点登录认证中心获取授权码
            modelAndView.setViewName("redirect:" + configuration.getUserAuthorizationUri() + "?" + "client_id=" + configuration.getClientId() + "&client_secret=" + configuration.getClientSecret() + "&redirect_uri=" + configuration.getRedirectUri() + "&response_type=code&scope=all");
        }
        return modelAndView;
    }
}
