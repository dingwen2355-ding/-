package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.service.AccessGovWeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author 6x7
 * @date 2022/06/30 10:04
 * @description
 */
@Slf4j
@Service
public class AccessGovWeChatServiceImpl implements AccessGovWeChatService {

    @Value("${wx.login.secret:THCZXQYWeINNo4E8NIVDo9P5lXWglZy6cUhIKMYsQLc}")
    String secret;
    @Value("${wx.login.corpId:ww29a8dfb1be6173c1}")
    String corpId;
    @Value("${wx.login.url:https://2.20.105.11:10443}")
    private String apiUrl;
    @Resource(name = "unSslRestTemplate")
    RestTemplate restTemplate;

    @Override
    public String getToken() {
        String tokenUrl = String.format("%s/cgi-bin/gettoken?corpid=%s&corpsecret=%s", apiUrl, corpId, secret);
        String result = restTemplate.getForObject(tokenUrl, String.class);
        if (!StringUtils.isEmpty(result)) {
            log.info("getToken Resp: {}", result);
            JSONObject retJson = JSON.parseObject(result);
            return retJson.getString("access_token");
        } else {
            log.info("gov wechat get token error,result:{}", result);
            throw new BusinessException("获取accessToken失败");
        }
    }


    @Override
    public String getUserIdByCode(String code) {
        String userIdUrl = String.format("%s/cgi-bin/user/getuserinfo?access_token=%s&code=%s", apiUrl,
                getToken(), code);
        String result = restTemplate.getForObject(userIdUrl, String.class);
        if (!StringUtils.isEmpty(result)) {
            log.info("getUserIdByCode Resp: {}", result);
            JSONObject retJson = JSON.parseObject(result);
            return retJson.getString("UserId");
        }
        return null;
    }

    @Override
    public String getUserMobile(String userId) {
        String userInfoUrl = String.format("%s/cgi-bin/user/get?access_token=%s&userid=%s", apiUrl,
                getToken(), userId);
        String result = restTemplate.getForObject(userInfoUrl, String.class);
        if (!StringUtils.isEmpty(result)) {
            log.info("getUserInfo Resp: {}", result);
            JSONObject retJson = JSON.parseObject(result);
            return retJson.getString("mobile");
        }
        return null;
    }
}
