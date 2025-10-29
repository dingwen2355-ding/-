package com.ruoyi.system.service;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.RestTemplateUtils;
import com.ruoyi.common.constant.ServerUrlCon;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.vo.CommonRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class ApiRequestService {

    @Resource
    private RestTemplateUtils restTemplateUtils;

    @Resource
    private RedisCache redisCache;


    public CommonRes insightPostJson(String url, JSONObject jsonObject) {

        String insightToken;

        if (redisCache.hasKey("insightToken")) {
            insightToken = redisCache.getCacheObject("insightToken");
        } else {
            insightToken = getToken();
        }
        HttpHeaders headers = new HttpHeaders() {
            {
                setContentType(MediaType.APPLICATION_JSON);
                set("token", insightToken);
            }
        };
        CommonRes commonRes = restTemplateUtils.postJson(url, CommonRes.class, jsonObject.toJSONString(), headers);
        if (commonRes.getCode() == 500 && commonRes.getData().toString().equals("token验证失败")) {
            headers.set("token", getToken());
            return restTemplateUtils.postJson(url, CommonRes.class, jsonObject.toJSONString(), headers);
        } else {
            return commonRes;
        }
    }

    public String getToken() {
        String url = ServerUrlCon.firstApiUrl.getToken;
        JSONObject jsonObject = new JSONObject() {{
            put("userName", "gjpt");
            put("password", "MTIzNDU2");
            put("loginType", "sso");
        }};
        HttpHeaders headers = new HttpHeaders() {
            {
                setContentType(MediaType.APPLICATION_JSON);
                set("checkToken", "no");
            }
        };
        CommonRes res = restTemplateUtils.postJson(url, CommonRes.class, jsonObject.toJSONString(), headers);
        JSONObject resJsonObject = JSONObject.from(res.getData());
        String insightToken = resJsonObject.get("token").toString();
        redisCache.setCacheObject("insightToken", insightToken);
        return insightToken;
    }
    @Async
    @Scheduled(cron = "0 0 * * * ?")
    public void getWeather() {
        String url = "http://2.22.51.26:30000/tianqiapis/?version=today&unit=m&language=zh&query=滨湖区&appid=test&appsecret=test888";
        String body = HttpRequest.get(url).header("Connection","keep-alive").execute().body();
        JSONObject resJsonObject = JSONObject.parseObject(body);
        Integer errcode =(Integer) resJsonObject.get("errcode");
        if (!errcode.equals(0)) {
            getWeather();
        } else {
            redisCache.setCacheObject("todayWeather", resJsonObject);
        }
    }
}
