package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.encrypt.Base64Util;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.SSLUtils;
import com.znv.manage.service.SendSMSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author: zl
 **/
@Service
@Slf4j
public class SendSMSServiceImpl implements SendSMSService {


    @Value("${SMS.url}")
    public String url;

    private static RestTemplate restTemplate = new RestTemplate();


    public static JSONObject postForResult(String url, String params) {
        log.error("SendSMSController postForResult start : url = " + url + "; json = " + params);
        JSONObject jsonResult = new JSONObject();
        try {
            String result = restTemplate.postForObject(url, params, String.class);
            jsonResult = JSONObject.parseObject(Base64Util.decoderBase64(result));
            log.error("SendSMSController postForResult jsonResult:" + jsonResult);
        } catch (Exception e) {
            log.error("SendSMSController postForResult error : " + e.getMessage());
        }
        return jsonResult;
    }

    @Override
    public Result sendSMS(String phones, String code, String appName, String action) {
        Result result = new Result();
        try {
            //跳过ssl证书验证
            SSLUtils.ignoreSsl();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "APPCODE 63bfaede1994537e16579402");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("accountId", "A2023011214552655");
            jsonObject.put("phoneNumber", phones);
            jsonObject.put("signId", "S2022081805234223");
            jsonObject.put("templateId", "M2022091815444949");
            jsonObject.put("timestamp", 1659091572);
            jsonObject.put("needReply", false);
            String templateParam = "{'code':'" + code + "','AppName':'" + appName + "','Action':'" + action + "',}";
            jsonObject.put("templateParam", templateParam);
            HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject, httpHeaders);
            ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.POST,
                    httpEntity, JSONObject.class);
            result.setData(response.getBody());

        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

}
