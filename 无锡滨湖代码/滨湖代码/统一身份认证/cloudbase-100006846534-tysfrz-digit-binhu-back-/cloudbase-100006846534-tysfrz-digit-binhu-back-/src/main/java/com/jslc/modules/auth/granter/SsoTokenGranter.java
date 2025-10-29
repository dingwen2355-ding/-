package com.jslc.modules.auth.granter;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jslc.common.config.SsoBackConfiguration;
import com.jslc.modules.auth.provider.ITokenGranter;
import com.jslc.modules.auth.provider.TokenParameter;
import com.jslc.modules.system.entity.UserInfo;
import com.jslc.modules.system.service.IUserService;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.utils.WebUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhengyongbiao
 * @title: SsoTokenGranter
 * @projectName digit-binhu-back
 * @description: 单点登录
 * @date 2023/5/19 12:38
 */
@Component
@AllArgsConstructor
public class SsoTokenGranter implements ITokenGranter {
    public static final String GRANT_TYPE = "ssoToken";
    private final IUserService userService;
    private final SsoBackConfiguration ssoBackConfiguration;

    @Override
    public UserInfo grant(TokenParameter tokenParameter) {
        HttpServletRequest request = WebUtil.getRequest();
        String token = tokenParameter.getArgs().getStr("username");
        HttpRequest rq = HttpRequest.get(ssoBackConfiguration.getUserTokenUrl() + "?token=" + token);
        // 发起请求获取token
        HttpResponse response = rq.execute();
        String rspuser = response.body();
        JSONObject rspVal = JSON.parseObject(rspuser);
        Long userId = rspVal.getJSONObject("data").getLong("id");
        UserInfo userInfo = userService.userInfo(userId);
        return userInfo;
    }
}
