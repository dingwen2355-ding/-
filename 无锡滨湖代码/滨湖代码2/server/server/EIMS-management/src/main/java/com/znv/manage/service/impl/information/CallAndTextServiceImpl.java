package com.znv.manage.service.impl.information;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.dao.ManageDao;
import com.znv.manage.service.information.CallAndTextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author 6x7
 * @date 2022/05/31 17:32
 * @description
 */
@Slf4j
@Service
public class CallAndTextServiceImpl implements CallAndTextService {

    @Value("${communicate.baseUrl:http://172.20.22.10:8089}")
    private String baseUrl;
    @Value("${communicate.caller:8172}")
    private String defaultCaller;

    @Value("${communicate.loginUrl:/nuas/api/v1/signin}")
    private String loginUrl;
    @Value("${communicate.callUrl:/nuas/api/v1/calls}")
    private String callUrl;
    @Value("${communicate.textUrl:/nuas/api/v1/sms}")
    private String textUrl;
    @Value("${communicate.hangUpUrl:/nuas/api/v1/conversations/}")
    private String hangUpUrl;
    @Value("${communicate.imei:nuchat_lc01_123}")
    private String iMei;
    @Value("${communicate.username:lc01}")
    private String username;
    @Value("${communicate.password:lc@12345}")
    private String password;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ManageDao manageDao;

    /**
     * 电话拨打、短信发送时携带的token
     */
    private String token = "1";

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public JSONObject login() {
        JSONObject param = new JSONObject();
        param.put("imei", iMei);
        param.put("userName", username);
        param.put("password", password);
        HttpEntity<String> httpEntity = new HttpEntity<>(param.toJSONString(), getCommonHeader());
        JSONObject responseJson = new JSONObject();
        try {
            String response = restTemplate.postForObject(baseUrl + loginUrl, httpEntity, String.class);
            if (!StringUtils.isEmpty(response)) {
                responseJson = JSON.parseObject(response);
                JSONObject dataJson = responseJson.getJSONObject("data");
                if (dataJson != null) {
                    token = dataJson.getString("token");
                    if (!StringUtils.isEmpty(token)) {
                        log.info("登录成功！获取Token: {}", token);
                        return responseJson;
                    }
                }
            }
            log.info("获取Token失败！本次响应信息：{}", responseJson);
        } catch (Exception e) {
            responseJson.put("error", e);
            log.error("login throw Ex: ", e);
        }
        return responseJson;
    }

    @Override
    public JSONObject call(String region, String phoneNum, String microPhoneCaller) {
        //增加麦克风通话对接，同电话接口，但是username、passsword、主叫号码不一样
        //并且要支持按登录账号配置主叫号
        //add by ljx at 0920 start
        if (!StringUtils.isEmpty(microPhoneCaller) && !"null".equals(microPhoneCaller)) {
            JSONObject responseJson = new JSONObject();
            String[] configInfo = microPhoneCaller.split(",");
            if (configInfo.length == 3) {
                String name = configInfo[0];
                String pwd = configInfo[1];
                String caller = configInfo[2];

                //登录获取token
                JSONObject param = new JSONObject();
                param.put("imei", iMei);
                param.put("userName", name);
                param.put("password", pwd);
                HttpEntity<String> httpEntity = new HttpEntity<>(param.toJSONString(), getMicroPhoneHeader("1"));

                try {
                    String response = restTemplate.postForObject(baseUrl + loginUrl, httpEntity, String.class);
                    if (!StringUtils.isEmpty(response)) {
                        responseJson = JSON.parseObject(response);
                        JSONObject dataJson = responseJson.getJSONObject("data");
                        if (dataJson != null) {
                            String microPhoneToken = dataJson.getString("token");
                            if (!StringUtils.isEmpty(microPhoneToken)) {
                                log.info("登录成功！获取麦克风通话Token: {}", microPhoneToken);

                                //拿到token，调用拨打电话接口
                                param.clear();
                                param.put("called", phoneNum);
                                param.put("caller", caller);
                                param.put("id", "");

                                httpEntity = new HttpEntity<>(param.toJSONString(), getMicroPhoneHeader(microPhoneToken));
                                try {
                                    response = restTemplate.postForObject(baseUrl + callUrl, httpEntity, String.class);
                                    if (!StringUtils.isEmpty(response)) {
                                        responseJson = JSON.parseObject(response);
                                    }
                                    log.info("麦克风拨打完毕！响应内容：{}", responseJson);
                                } catch (Exception e) {
                                    responseJson.put("error", e);
                                    log.error("throw Ex: ", e);
                                }
                            }
                        }
                    }
                    log.info("获取Token失败！本次响应信息：{}", responseJson);
                } catch (Exception e) {
                    responseJson.put("error", e);
                    log.error("login throw Ex: ", e);
                }
            }
            return responseJson;
            //add by ljx at 0920 end
        } else {//已有的打电话逻辑代码，不动
            JSONObject param = new JSONObject();
            String caller = manageDao.selectCallerByRegion(region);
            if (StringUtils.isEmpty(caller)) {
                caller = defaultCaller;
            }
            param.put("called", phoneNum);
            param.put("caller", caller);
            param.put("id", "");
            HttpEntity<String> httpEntity = new HttpEntity<>(param.toJSONString(), getCommonHeader());
            JSONObject responseJson = new JSONObject();
            try {
                String response = restTemplate.postForObject(baseUrl + callUrl, httpEntity, String.class);
                if (!StringUtils.isEmpty(response)) {
                    responseJson = JSON.parseObject(response);
                }
                log.info("电话拨打完毕！响应内容：{}", responseJson);
            } catch (Exception e) {
                responseJson.put("error", e);
                log.error("throw Ex: ", e);
            }
            return responseJson;
        }
    }

    @Override
    public JSONObject hangUp(String phoneNum) {
        JSONObject responseJson = new JSONObject();
        try {
            HttpEntity<String> httpEntity = new HttpEntity<>(null, getCommonHeader());
            ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + hangUpUrl + phoneNum, HttpMethod.DELETE, httpEntity, String.class);
            responseJson = JSON.parseObject(responseEntity.getBody());
            log.info("电话挂断完毕！响应内容：{}", responseJson);
        } catch (Exception e) {
            responseJson.put("error", e);
            log.error("throw Ex: ", e);
        }
        return responseJson;
    }

    @Override
    public JSONObject text(String phoneNum, String content) {
        JSONObject param = new JSONObject();
        param.put("target", phoneNum);
        param.put("message", content);
        param.put("source", "");
        param.put("status", 0);
        param.put("time", 0);
        param.put("type", 0);
        HttpEntity<String> httpEntity = new HttpEntity<>(param.toJSONString(), getCommonHeader());
        JSONObject responseJson = new JSONObject();
        try {
            String response = restTemplate.postForObject(baseUrl + textUrl, httpEntity, String.class);
            if (!StringUtils.isEmpty(response)) {
                responseJson = JSON.parseObject(response);
            }
            log.info("短息发送完毕！响应内容：{}", responseJson);
        } catch (Exception e) {
            responseJson.put("error", e);
            log.error("throw Ex: ", e);
        }
        return responseJson;
    }

    private HttpHeaders getCommonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Nonce", Collections.singletonList("12345678"));
        headers.put("X-Timestamp", Collections.singletonList("1530782756705"));
        headers.put("X-Signature", Collections.singletonList("5288f22b25e5e52fc008df074da838b2e00ab8cc"));
        headers.put("X-Token", Collections.singletonList(token));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private HttpHeaders getMicroPhoneHeader(String microPhoneToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Nonce", Collections.singletonList("12345678"));
        headers.put("X-Timestamp", Collections.singletonList("1530782756705"));
        headers.put("X-Signature", Collections.singletonList("5288f22b25e5e52fc008df074da838b2e00ab8cc"));
        headers.put("X-Token", Collections.singletonList(microPhoneToken));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
