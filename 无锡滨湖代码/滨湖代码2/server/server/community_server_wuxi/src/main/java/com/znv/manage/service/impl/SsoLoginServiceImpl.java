package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.dao.SsoLoginDao;
import com.znv.manage.service.SsoLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@Slf4j
@Service
public class SsoLoginServiceImpl implements SsoLoginService {

//    @Value("${sso.authKey:0459bc43b19dcb02ee5eb803757c7cb36517306d77b81f054d54528c946bd507}")
//    private String ssoAuthKey;

    @Value("${sso.url.prefix:http://10.1.50.123/sso}")
    private String ssoUrlPrefix;

    private static RestTemplate restTemplate = new RestTemplate();
    static {
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 支持中文编码
    }

    @Autowired
    SsoLoginDao ssoLoginDao;

    @Override
    public JSONObject sendSmsCode(String phoneNumber, String precinctId) {
        if(StringUtils.isEmpty(precinctId)) {
            precinctId = "510100";
        }
        Map<String, String> ssoAuthkeyInfo = ssoLoginDao.querySsoAuthkey(precinctId);
        String ssoAuthKey = "";
        if (ssoAuthkeyInfo!=null) {
            ssoAuthKey = String.valueOf(ssoAuthkeyInfo.get("authkey"));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("authKey",ssoAuthKey);
        headers.setContentType(APPLICATION_JSON_UTF8);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phoneNumber",phoneNumber);
        jsonObject.put("paramCode","ssoLogonSms");
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toJSONString(), headers);
        ResponseEntity<String> result = restTemplate.exchange(ssoUrlPrefix+"/api/sso/partner/sendSmsCode", HttpMethod.POST, entity,String.class);
        JSONObject json = JSONObject.parseObject(result.getBody());
        return json;
    }

    @Override
    public JSONObject login(String authType, String loginId, String auth, String precinctId) {
        if(StringUtils.isEmpty(precinctId)) {
            precinctId = "510100";
        }
        Map<String, String> ssoAuthkeyInfo = ssoLoginDao.querySsoAuthkey(precinctId);
        String ssoAuthKey = "";
        if (ssoAuthkeyInfo!=null) {
            ssoAuthKey = String.valueOf(ssoAuthkeyInfo.get("authkey"));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("authKey",ssoAuthKey);
        headers.setContentType(APPLICATION_JSON_UTF8);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authType",authType);
        jsonObject.put("loginId",loginId);
        jsonObject.put("auth",auth);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toJSONString(), headers);
        log.info("login param:{}",entity);
        ResponseEntity<String> result = restTemplate.exchange(ssoUrlPrefix+"/api/sso/partner/login", HttpMethod.POST, entity,String.class);
        JSONObject json = JSONObject.parseObject(result.getBody());

        // 登录的路由改为前台根据url里自带的precinctId参数
        // 添加登录账号对应的区域id信息
//        Map<String, String> ssoUserInfo = ssoLoginDao.querySsoUserInfo(loginId);
//        String precinctId = "";
//        if (ssoUserInfo!=null) {
//            precinctId = String.valueOf(ssoUserInfo.get("precinctId"));
//        }
//        json.put("precinctId", precinctId);
        return json;
    }

    @Override
    public JSONObject logout(String accessToken, String userId, String precinctId) {
        if(StringUtils.isEmpty(precinctId)) {
            precinctId = "510100";
        }
        Map<String, String> ssoAuthkeyInfo = ssoLoginDao.querySsoAuthkey(precinctId);
        String ssoAuthKey = "";
        if (ssoAuthkeyInfo!=null) {
            ssoAuthKey = String.valueOf(ssoAuthkeyInfo.get("authkey"));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("authKey",ssoAuthKey);
        headers.add("accessToken",accessToken);
        headers.setContentType(APPLICATION_JSON_UTF8);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",userId);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toJSONString(), headers);
        ResponseEntity<String> result = restTemplate.exchange(ssoUrlPrefix+"/api/sso/partner/logout", HttpMethod.POST, entity,String.class);
        JSONObject json = JSONObject.parseObject(result.getBody());
        return json;
    }

    @Override
    public JSONObject checkToken(String accessToken, String userId, String precinctId) {
        if(StringUtils.isEmpty(precinctId)) {
            precinctId = "510100";
        }
        Map<String, String> ssoAuthkeyInfo = ssoLoginDao.querySsoAuthkey(precinctId);
        String ssoAuthKey = "";
        if (ssoAuthkeyInfo!=null) {
            ssoAuthKey = String.valueOf(ssoAuthkeyInfo.get("authkey"));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("authKey",ssoAuthKey);
        headers.add("accessToken",accessToken);
        headers.setContentType(APPLICATION_JSON_UTF8);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",userId);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toJSONString(), headers);
        log.info("checkToken param:{}",entity);
        ResponseEntity<String> result = restTemplate.exchange(ssoUrlPrefix+"/api/sso/partner/checkToken", HttpMethod.POST, entity,String.class);
        JSONObject json = JSONObject.parseObject(result.getBody());
        return json;
    }
}
