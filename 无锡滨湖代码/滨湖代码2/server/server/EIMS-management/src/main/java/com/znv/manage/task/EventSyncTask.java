package com.znv.manage.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.dao.event.EventSyncDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件同步定时任务
 *
 * @author ：0049003788
 * @date ：2023/7/12 15:20
 */
@Slf4j
@Component
public class EventSyncTask {
    @Autowired
    EventSyncDao eventSyncDao;

    @Autowired
    RestTemplate restTemplate;

    @Value("${event.sync.url:http://192.250.161.26:88}")
    String url;

    JSONObject getTokenRequestRow;

    private static final String TOKEN_ROUTE = "/wechatApp/api/user/getToken";
    private static final String EVENT_ROUTE = "/wechatApp/api/wechat/flow/findFlowList";

    private static final Map<String, String> SOURCE_MAP = new HashMap<>();


    public EventSyncTask(@Value("${event.sync.username:sa}") String username,
                         @Value("${event.sync.password:SXRzbUBXU1g=}") String password) {
        getTokenRequestRow = new JSONObject();
        getTokenRequestRow.put("username", username);
        getTokenRequestRow.put("password", password);
        getTokenRequestRow.put("loginType", "sso");
        SOURCE_MAP.put("MAIN_12345", "12345");
        SOURCE_MAP.put("CHENGYUN", "城管事件");
        SOURCE_MAP.put("CHENGYUN_MAIN", "基层吹哨");
        SOURCE_MAP.put("NORMAL_MAIN体征预警", "体征预警");
        SOURCE_MAP.put("NORMAL_MAIN文明城市", "文明城市");
        SOURCE_MAP.put("NORMAL_MAIN告警事件", "告警事件");
        SOURCE_MAP.put("NORMAL_MAIN灵锡随手拍", "灵锡随手拍");
        SOURCE_MAP.put("NORMAL_MAIN领导交办", "领导交办");
        SOURCE_MAP.put("NORMAL_MAIN经济云大脑", "经济云大脑");
        SOURCE_MAP.put("NORMAL_MAIN网格事件", "网格事件");
        SOURCE_MAP.put("NORMAL_MAIN城管事件", "城管事件");
        SOURCE_MAP.put("NORMAL_MAIN主动发现", "主动发现");
    }

    @Scheduled(initialDelay = 10000, fixedRate = 60 * 60 * 1000)
    @Async
    public void syncEvent() {
        log.info("start syncEvent...");
        String eventUrl = url + EVENT_ROUTE;
        String token;
        try {
            token = getToken();
        } catch (Exception e) {
            log.info("get token error:{}", e.toString());
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", token);

        for (Map.Entry<String, String> entry : SOURCE_MAP.entrySet()) {
            int page = 1;
            boolean loopFlag = true;
            while (loopFlag) {
                JSONObject getDataRequestBody = new JSONObject();
                JSONObject mapVo = new JSONObject();
                mapVo.put("page", page);
                mapVo.put("size", 300);
                mapVo.put("action", "all");
                getDataRequestBody.put("mapVo", mapVo);

                //对NORMAL_MAIN类型的modelKey使用dataFilter细分来源
                if (entry.getKey().contains("NORMAL_MAIN")) {
                    JSONObject dataFilter = new JSONObject();
                    dataFilter.put("flowSource.keyword", entry.getValue());
                    mapVo.put("dataFilter", dataFilter);
                    mapVo.put("modelKey", "NORMAL_MAIN");
                } else {
                    mapVo.put("modelKey", entry.getKey());
                }

                HttpEntity<JSONObject> requestEntity = new HttpEntity<>(getDataRequestBody, headers);
                ResponseEntity<JSONObject> responseEntity;
                try {
                    responseEntity = restTemplate.exchange(eventUrl, HttpMethod.POST, requestEntity, JSONObject.class);
                } catch (Exception e) {
                    log.info("get syncEvent error,url:{},modelKey:{},e:{}", eventUrl, entry.getKey(), e.toString());
                    break;
                }
                JSONObject responseBody = responseEntity.getBody();
                if (responseBody != null && responseBody.containsKey("code") && responseBody.getInteger("code").equals(200) && responseBody.containsKey("data")) {
                    JSONArray dataList = responseBody.getJSONArray("data");
                    if (dataList == null || dataList.isEmpty()) {
                        break;
                    }
                    if (dataList.size() < 300) {
                        loopFlag = false;
                    }
                    eventSyncDao.insertOrUpdate(dataList, entry.getValue());
                    page = page + 1;
                } else {
                    throw new BusinessException("get syncEvent data error,response:" + responseBody);
                }
            }
        }
        log.info("end get syncEvent data...");
    }

    private String getToken() {
        log.info("start get syncEvent token...");
        String tokenUrl = url + TOKEN_ROUTE;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("checkToken", "no");
        HttpEntity<JSONObject> requestEntity = new HttpEntity<>(getTokenRequestRow, headers);
        ResponseEntity<JSONObject> responseEntity = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, JSONObject.class);
        JSONObject responseBody = responseEntity.getBody();
        if (responseBody != null && "0000".equals(responseBody.getString("code")) && responseBody.containsKey("data")) {
            JSONObject data = responseBody.getJSONObject("data");
            if (data.containsKey("token")) {
                log.info("get syncEvent token success:" + data.getString("token"));
                return data.getString("token");
            }
        }
        //没返回的话报错
        throw new BusinessException("get sync error,url" + tokenUrl + ",param:" + getTokenRequestRow.toJSONString() + ",response" + responseBody);

    }


}
