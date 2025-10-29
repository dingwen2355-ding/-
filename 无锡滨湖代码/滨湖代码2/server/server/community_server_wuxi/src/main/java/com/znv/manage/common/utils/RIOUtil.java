package com.znv.manage.common.utils;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 里约网关请求工具
 *
 * @author junli
 * @date 2020-8-25
 */
@Slf4j
public class RIOUtil {

    /**
     * post请求
     *
     * @param paasId
     * @param paasToken
     * @param url       接口路径
     * @param method    请求方法
     * @param body      请求体
     * @param form      form参数
     * @return
     * @throws ParseException
     */
    public static String request(String url, Method method, String paasId, String paasToken, Map<String, String> header,
                                 String body, Map<String, Object> form) {
        if (Validator.isEmpty(header)) {
            header = MapUtil.newHashMap();
        }
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = UUID.randomUUID().toString();
        header.put("x-tif-paasid", paasId);
        //签名字符串
        //计算签名
        String signature = SecureUtil.sha256(timestamp + paasToken + nonce + timestamp);
        header.put("x-tif-signature", signature);
        //当前 unix 时间戳
        header.put("x-tif-timestamp", timestamp);
        //非重复的随机字符串
        header.put("x-tif-nonce", nonce);
        HttpRequest request = new HttpRequest(url);
        if (MapUtil.isNotEmpty(form)) {
            request.form(form);
        }
        if (Validator.isNotEmpty(body)) {
            request.body(body);
        }
        HttpResponse response = request
                .method(method)
                .addHeaders(header)
                .timeout(200000)//超时，毫秒
                .execute();
        log.info("request: \n{}", request.toString());
        log.info("method: \n{}", request.getMethod());
        return response.body();
    }


    /**
     * post请求
     *
     * @param paasId
     * @param paasToken
     * @param url       接口路径
     * @param method    请求方法
     * @param body      请求体
     * @param appId     应用id
     * @return
     * @throws ParseException
     */
    public static String request(String url, Method method, String appId, String paasId, String paasToken, Map<String, String> header,
                                 String body, Map<String, Object> form) {
        if (Validator.isEmpty(header)) {
            header = MapUtil.newHashMap();
        }
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = UUID.randomUUID().toString();
        header.put("x-tif-paasid", paasId);
        //签名字符串
        //计算签名
        String signature = SecureUtil.sha256(timestamp + paasToken + nonce + timestamp);
        header.put("x-tif-signature", signature);
        //当前 unix 时间戳
        header.put("x-tif-timestamp", timestamp);
        //非重复的随机字符串
        header.put("x-tif-nonce", nonce);
        header.put("appId", appId);
        HttpRequest request = new HttpRequest(url);
        if (Validator.isNotEmpty(form)) {
            request.form(form);
        }
        if (Validator.isNotEmpty(body)) {
            request.body(body);
        }
        HttpResponse response = request
                .method(method)
                .addHeaders(header)
                .timeout(200000)//超时，毫秒
                .execute();
        log.info("request: \n{}", request.toString());
        log.info("method: \n{}", request.getMethod());
        return response.body();
    }

    public static void main(String[] args) {
        String paasId = "health**";
        String appId = "health**";
        String paasToken = "N4sOkD7GSDFWO4**************";
        String url = "https://******************************************";
        Map map = new HashMap<>();
        map.put("content", "1A3i98=====1=3GjYeCg===ngugCzL===1=5re07f253a5814a6ea479734c789cd7a3cd0e3ab580d8e551435f2b6f743030c1f7=03f11e512");
        String body = JSON.toJSONString(map);
        String result = request(url, Method.POST, appId, paasId, paasToken, null, body, null);
        log.info("result: \n{}", result);
    }
}

