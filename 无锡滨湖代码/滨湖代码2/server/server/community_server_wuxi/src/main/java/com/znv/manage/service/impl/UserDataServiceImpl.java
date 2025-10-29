package com.znv.manage.service.impl;

import cn.hutool.http.Method;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.RIOUtil;
import com.znv.manage.service.UserDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class UserDataServiceImpl implements UserDataService {

    @Value("${userData.getToken.url}")
    private String getToken;

    @Value("${userData.appId}")
    private String appId;

    @Value("${userData.appSecret}")
    private String appSecret;

    @Value("${userData.url}")
    private String userDataUrl;

    @Value("${userData.redirect.url}")
    private String redirectUrl;

    @Value("${userData.server.url}")
    private String serverUrl;

    @Value("${userData.paasid:wxbhyxgl}")
    String paasid;

    @Value("${userData.paasToken:0e361338083087514abb3e8b9144590d}")
    String paasToken;


    @Autowired
    private RestTemplate restTemplate;


    @Override
    public String getToken() {
        String token = "";
        try {
            //校验参数
            if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(appSecret) || StringUtils.isEmpty(getToken) || StringUtils.isEmpty(userDataUrl)) {
                return "请检查系统配置文件项是否正确";
            }
            // 封装url
            String urlFormat = "%s?appId=%s&appSecret=%s";
            String url = String.format(urlFormat, getToken, appId, appSecret);
            String tokenUrl = url.replace("{url}", userDataUrl);

            // 发送请求，获取token
            JSONObject response = JSON.parseObject(RIOUtil.request(tokenUrl, Method.GET, appId, paasid, paasToken, null, null, null));
            log.info("Request to third platform api [url: {}], response result: {}", tokenUrl, response);

            if (!CollectionUtils.isEmpty(response) && response.containsKey("code") && 200 == response.getIntValue("code")
                    && response.containsKey("data") && !StringUtils.isEmpty(response.getString("data"))) {
                token = response.getString("data");
            }
        } catch (Exception e) {
            log.error("Get token url occur an exception! ", e);
        }
        return token;
    }


    @Override
    public Result refreshToken(String refreshToken) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(refreshToken)) {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("获取刷新token的值失败！");
                return result;
            }
            String urlFormat = "%s/oauth/token?grant_type=refresh_token&refresh_token=%s&client_id=%s&client_secret=%s";
            String url = String.format(urlFormat, serverUrl, refreshToken, appId, appSecret);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(headers);
            JSONObject response = JSON.parseObject(RIOUtil.request(url, Method.POST, appId, paasid, paasToken, null, null, null));
            log.info("Request to third platform api [url: {}], response result: {}", url, response);

            if (!CollectionUtils.isEmpty(response) && response.containsKey("code") && 200 == response.getIntValue("code")
                    && response.containsKey("data") && !CollectionUtils.isEmpty(response.getJSONObject("data"))) {
                result.setData(response.getJSONObject("data"));
            }
            result.setCode(response.getIntValue("code"));
            result.setMessage(response.getString("msg"));
        } catch (Exception e) {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Get User data url occur an exception! ", e);
        }
        return result;
    }

    @Override
    public Result getUserInfo(String token) {
        Result result = new Result();
        try {
            if (StringUtils.isEmpty(token)) {
                result.setCode(HttpStatus.UNAUTHORIZED.value());
                result.setMessage("获取刷新token的值失败！");
                return result;
            }
            String urlFormat = "%s/user/token?token=%s";
            String url = String.format(urlFormat, serverUrl, token);

            JSONObject response = JSON.parseObject(RIOUtil.request(url, Method.GET, appId, paasid, paasToken, null, null, null));
            log.info("Request to third platform api [url: {}], response result: {}", url, response);

            if (!CollectionUtils.isEmpty(response)
                    && response.containsKey("code") && 200 == response.getIntValue("code")
                    && response.containsKey("data") && !CollectionUtils.isEmpty(response.getJSONObject("data"))) {
                result.setData(response.getJSONObject("data"));
            }
            result.setCode(response.getIntValue("code"));
            result.setMessage(response.getString("msg"));
        } catch (Exception e) {
            result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            log.error("Get User data url occur an exception! ", e);
        }
        return result;
    }

}
