package com.znv.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.service.PhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService {

    private static RestTemplate restTemplate = new RestTemplate();

    @Value("${phone.url:http://10.14.216.182:8080}")
    private String url;
    @Value("${phone.userName:zhzx2}")
    private String userName;
    @Value("${phone.password:1399aac573ae55a9a162a569efe34a5c78443cb5d5853f8318721d273764e37c}")
    private String password;
    @Value("${phone.caller:8004}")
    private String caller;

    private static String token;

    public static String getToken() {
        return token;
    }

    @Override
    public void login() {
        try {
            JSONObject jsonObject = restTemplate.getForObject(url +
                    "/scooper-core-rest/data/system/authManage/loginTo?" +
                    "accUsername=" + userName + "&accPassword=" +
                    password, JSONObject.class);
            if ("0".equals(jsonObject.getString("code"))) {
                token = jsonObject.getString("data");
            }
        } catch (Exception e) {
            log.error("电话服务登录异常：" + e.getMessage());
        }
    }

    @Override
    public void keepAlive() {
        try {
            restTemplate.getForObject(url + "/scooper-core-rest/data/system/authManage/heartbeat?" +
                    "token=" + token, JSONObject.class);
        } catch (Exception e) {
            log.error("保活异常：" + e.getMessage());
            log.info("重新登录");
            login();
        }

    }

    @Override
    public Result call(String callee) {
        Result result = new Result();
        try {
            JSONObject jsonObject = restTemplate.getForObject(url + "/dispatch-web/api/call/makeCall?" +
                    "token=" + token + "&caller=" + caller + "&callee=" + callee, JSONObject.class);
            result.setData(jsonObject);
        } catch (Exception e) {
            log.error("呼叫异常：" + e.getMessage());
            result.setMessage(e.getMessage());
            result.setCode(1);
        }
        return result;
    }

    @Override
    public Result hungup(String callee) {
        Result result = new Result();
        try {
            JSONObject jsonObject = restTemplate.getForObject(url + "/dispatch-web/api/call/hungup?" +
                    "token=" + token + "&tel=" + callee, JSONObject.class);
            result.setData(jsonObject);
        } catch (Exception e) {
            log.error("挂断异常：" + e.getMessage());
            result.setMessage(e.getMessage());
            result.setCode(1);
        }
        return result;
    }
}
