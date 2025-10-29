package com.znv.manage.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class AccessUtil {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(AccessUtil.class);

    private static String tokenUrl = "http://10.214.111.238:9090/oauth/token?client_id=ywtg&client_secret=ywtg";
    private static String authorization = "BasiceXd0Zzp5d3Rn";
    private static String urlPrefix = "?client_id=ywtg&access_token=";
    private static RestTemplate restTemplate = new RestTemplate();

    public static Object get(String url, Map<String,String> params) {
        Object result = new Object();
        try {
            url = url + urlPrefix + getAccessToken();
            StringBuilder stringBuilderUrl = new StringBuilder();
            if (params != null && params.size() > 0) {
                for (String key : params.keySet()) {
                    stringBuilderUrl.append("&");
                    stringBuilderUrl.append(key);
                    stringBuilderUrl.append("=");
                    stringBuilderUrl.append(params.get(key));
                }
                url = url + stringBuilderUrl.toString();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",authorization);
            ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(headers),String.class);
            result = res.getBody();
        }catch (Exception e){
            logger.error(url + "数据获取异常："+e.getMessage());
        }
        return result;
    }

    private static String getAccessToken(){
        String accessToken = "";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",authorization);
            ResponseEntity<String> result = restTemplate.exchange(tokenUrl, HttpMethod.POST, new HttpEntity<String>(headers),String.class);
            JSONObject jsonObject = JSONObject.parseObject(result.getBody());
            accessToken = jsonObject.getString("access_token");
        }catch (Exception e){
            logger.error("accessToken 获取异常："+e.getMessage());
        }
        return accessToken;
    }

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("xxx","xx");
        map.put("time","2021-01-02");
        System.out.println(AccessUtil.get("http://10.214.111.238:9090/service/api/xzspj/t_12345_source", map));
    }
}
