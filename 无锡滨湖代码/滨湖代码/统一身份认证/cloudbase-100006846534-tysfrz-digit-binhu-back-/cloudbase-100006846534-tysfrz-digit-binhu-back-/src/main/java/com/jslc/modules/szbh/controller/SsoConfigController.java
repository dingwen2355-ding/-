package com.jslc.modules.szbh.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jslc.common.config.AbstractSsoConfiguration;
import com.jslc.common.config.SsoBackConfiguration;
import com.jslc.common.config.SsoFrontConfiguration;
import com.jslc.modules.szbh.entity.OauthClientDetails;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhengyongbiao
 * @title: SsoLoginController
 * @projectName digit-binhu-back
 * @date 2023/5/18 17:34
 */
@RestController()
@RequestMapping("/sso/config")
@Slf4j
public class SsoConfigController {
    @Value("${sso.loginOutUri}")
    String logoutUri;

    @GetMapping("/front/logoutUri")
    public R<String> logoutUri() {
        return R.data(logoutUri);
    }

}
