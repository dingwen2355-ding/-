package com.znv.manage.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.exception.BusinessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 事件对接处置发送请求工具类
 *
 * @author ：0049003788
 * @date ：2022/8/23 14:37
 */
public class EventRequestUtil {

    public static final String TOKEN_USERNAME = "yxpt";
    public static final String TOKEN_PASSWORD = "UXdlckBXU1g=";

    public static JSONObject postRequest(RestTemplate restTemplate, String url, JSONObject param, String tokenUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", getToken(restTemplate, tokenUrl));
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, httpEntity, JSONObject.class);
        JSONObject result = responseEntity.getBody();
        if (result == null) {
            throw new BusinessException("get response failed: responseJson null,url:" + url);
        }
        return result;
    }

    public static String getToken(RestTemplate restTemplate, String tokenUrl) {
        JSONObject requestJson = new JSONObject();
        requestJson.put("username", TOKEN_USERNAME);
        requestJson.put("password", TOKEN_PASSWORD);
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(tokenUrl, requestJson, JSONObject.class);
        JSONObject responseJson = responseEntity.getBody();
        if (responseJson == null) {
            throw new BusinessException("get token failed: responseJson null");
        }
        if (!"0000".equals(responseJson.getString("code"))) {
            throw new BusinessException("get token failed:" + responseJson.getString("msg"));
        }
        return responseJson.getJSONObject("data").getString("token");
    }
}
