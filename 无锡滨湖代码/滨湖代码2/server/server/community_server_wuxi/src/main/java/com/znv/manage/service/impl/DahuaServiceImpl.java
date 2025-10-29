package com.znv.manage.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.BusinessException;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.common.utils.DateUtil;
import com.znv.manage.common.utils.SSLUtils;
import com.znv.manage.dao.DeviceMapper;
import com.znv.manage.service.DahuaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Slf4j
public class DahuaServiceImpl implements DahuaService {

    @Value("${dahua.userName}")
    private String userName;
    @Value("${dahua.password}")
    private String password;
    private static String token = "";
    private static int loginCount = 0;

    @Value("${dahua.url:http://2.20.28.22:8314/videoService/accounts/authorize}")
    private String loginUrl;
    @Value("${dahua.keepAliveUrl:https://2.20.28.22:8320/videoService/accounts/token/keepalive}")
    private String keepAliveUrl;
    @Value("${longquan.gpsUrl:http://127.0.0.1:6699/dsu/gps/user?userId=}")
    private String LongQuanGpsUrl;
    @Value("${longquan.onoff:true}")
    private String onoff;

    @Value("${device.url:http://2.20.28.22:8314}")
    private String deviceUrl;

    @Value("${longquan.gpsUrl.subscribe.prefix:http://127.0.0.1:6699/}")
    private String lqyDanbinUrlPrefix;

//    private static int lastMsgId = -1;

    private static final String DAHUA_DABIN_TOKEN_KEY = "X-Subject-Token";
    @Autowired
    private DeviceMapper deviceMapper;

    private static RestTemplate restTemplate = new RestTemplate();

    @Override
    public String handleToken() {
        try {
            if ("true".equals(onoff)) {
                SSLUtils.ignoreSsl();
                RestTemplate restTemplate = new RestTemplate();
                SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
                requestFactory.setOutputStreaming(false);
                restTemplate.setRequestFactory(requestFactory);
                restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
                    public void handleError(ClientHttpResponse response) throws IOException {
                        if (response.getStatusCode() != HttpStatus.UNAUTHORIZED) {
                            super.handleError(response);
                        }
                    }
                });
                log.info("获取大华token第1次交互...");
                JSONObject msg = new JSONObject();
                msg.put("userName", userName);
                msg.put("clientType", "winpc");
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
                JSONObject jsonObject = restTemplate.postForObject(loginUrl, request, JSONObject.class);
                log.info("获取大华token第1次交互结果:" + jsonObject);
                log.info("获取大华token第2次交互...");
                String realm = jsonObject.getString("realm");
                String randomKey = jsonObject.getString("randomKey");
                String signature = getSignature(realm, randomKey);
                JSONObject msg2 = new JSONObject();
                msg2.put("userName", userName);
                msg2.put("signature", signature);
                msg2.put("randomKey", randomKey);
                msg2.put("encryptType", "MD5");
                msg2.put("clientType", "winpc");
                HttpHeaders headers2 = new HttpHeaders();
                headers2.setContentType(MediaType.APPLICATION_JSON_UTF8);
                HttpEntity request2 = new HttpEntity(msg2.toJSONString(), headers2);
                JSONObject jsonObject2 = restTemplate.postForObject(loginUrl, request2, JSONObject.class);
                log.info("获取大华token第2次交互结果：" + jsonObject2);
                token = jsonObject2.getString("token");
                if (!StringUtils.isEmpty(token)) {
                    loginCount = 0;
                }
            }
        } catch (Exception e) {
            log.error("获取大华token异常:" + e.getMessage());
        }
        return token;
    }

    // 大华 龙泉驿区单兵订阅gps
    public void subscribeGpsEvent() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add(DAHUA_DABIN_TOKEN_KEY, token);
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            JSONObject msg = new JSONObject();
            msg.put("departmentId", "80c2f191c2b611ec8eab9023b4a548d6");
            msg.put("userId", "6033389319293056");
            msg.put("subscribe", true);
            HttpEntity entity = new HttpEntity(msg.toJSONString(), headers);
            String url = lqyDanbinUrlPrefix + "dsu/gps/subscribe";
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            if (result.getStatusCodeValue() == 200) {
                log.info("龙泉驿单兵订阅gps成功！");
            } else {
                log.info("龙泉驿单兵订阅gps失败！");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    // 大华 龙泉驿区单兵通过接口获取长轮询单兵设备gps信息
    public void dealLqyDanbinEventMessage() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(DAHUA_DABIN_TOKEN_KEY, token);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        // url接口的前面ip端口正常需通过3.5.2 消息订阅 接口获得，这边暂时设置为固定值
        String url = "http://10.32.216.171:8306/" + "videoService/eventCenter/messages/subscribe";

        //1 第一次请求填-1，得到当前时刻的最新msgId(此时不会返回消息内容)。接下来用返回的最新的 msgId 作为入参，获取后续产生的消息内容
        int beginMsgId = -1;
        String param = "?type=19&msgId=" + beginMsgId;
        ResponseEntity<JSONObject> forEntity = restTemplate.exchange(url + param, HttpMethod.GET,
                new HttpEntity<String>(headers), JSONObject.class);

        JSONObject body = forEntity.getBody();
        beginMsgId = body.getInteger("nextMsgId");

//        2 用返回的最新的 msgId 作为入参，获取后续产生的消息内容
        beginMsgId = beginMsgId - 64;
        param = "?type=19&msgId=" + beginMsgId;
        forEntity = restTemplate.exchange(url + param, HttpMethod.GET,
                new HttpEntity<String>(headers), JSONObject.class);
        body = forEntity.getBody();
        log.info("获取龙泉驿区单兵信息接口数据返回：{}", body);

//        JSONObject body = JSONObject.parseObject("{\n" +
//                "    \"nextMsgId\": \"13564\",\n" +
//                "    \"totalCount\": 62,\n" +
//                "    \"results\": [\n" +
//                "        \"{\\\"id\\\":82677,\\\"info\\\":{\\\"departmentId\\\":\\\"e5042a43b31c11eca89174d6cb85ce3c\\\",\\\"departmentName\\\":\\\"街镇融合通信平台\\\",\\\"gps\\\":[{\\\"angle\\\":116,\\\"channelId\\\":\\\"6044260975642880\\\",\\\"dataSource\\\":3,\\\"deviceCode\\\":\\\"6044260975577344\\\",\\\"deviceId\\\":\\\"6044260975577344\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":{},\\\"gpsX\\\":104.175575,\\\"gpsY\\\":30.650118,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":2,\\\"time\\\":\\\"20220428T082341.773Z\\\",\\\"userId\\\":\\\"6044467890030656\\\",\\\"userName\\\":\\\"十陵街道002\\\",\\\"userType\\\":\\\"2\\\"}]},\\\"method\\\":\\\"dsu.gps\\\"}\",\n" +
//                "        \"{\\\"id\\\":82678,\\\"info\\\":{\\\"departmentId\\\":\\\"80c2f191c2b611ec8eab9023b4a548d6\\\",\\\"departmentName\\\":\\\"龙泉驿区\\\",\\\"gps\\\":[{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"bd1a2a970e494889b58e39e68e852832\\\",\\\"deviceId\\\":\\\"6045246286923968\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.25305,\\\"gpsY\\\":30.59072,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"1783b732210c4be68906424a13dcb318\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082349.079Z\\\",\\\"userId\\\":\\\"6045246292428928\\\",\\\"userName\\\":\\\"区治理中心002\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"22fab81e03a24d9985bf370b0eb5a751\\\",\\\"deviceId\\\":\\\"6033394170267840\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.06207,\\\"gpsY\\\":30.587542,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"1783b732210c4be68906424a13dcb318\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082346.972Z\\\",\\\"userId\\\":\\\"6033394177804416\\\",\\\"userName\\\":\\\"ptdj1002\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"f2edd86369344a6982e062acf7975314\\\",\\\"deviceId\\\":\\\"6045203426248896\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.25284,\\\"gpsY\\\":30.590717,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"1783b732210c4be68906424a13dcb318\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082349.222Z\\\",\\\"userId\\\":\\\"6045203429591168\\\",\\\"userName\\\":\\\"区治理中心001\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":359,\\\"channelId\\\":\\\"6045170255399104\\\",\\\"dataSource\\\":3,\\\"deviceCode\\\":\\\"6045170255268032\\\",\\\"deviceId\\\":\\\"6045170255268032\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":{},\\\"gpsX\\\":104.25283,\\\"gpsY\\\":30.590717,\\\"height\\\":482,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"1783b732210c4be68906424a13dcb318\\\",\\\"startCount\\\":2,\\\"time\\\":\\\"20220428T082348.619Z\\\",\\\"userId\\\":\\\"6045203429591168\\\",\\\"userName\\\":\\\"区治理中心001\\\",\\\"userType\\\":\\\"2\\\"}]},\\\"method\\\":\\\"dsu.gps\\\"}\",\n" +
//                "        \"{\\\"id\\\":82679,\\\"info\\\":{\\\"departmentId\\\":\\\"e5042a43b31c11eca89174d6cb85ce3c\\\",\\\"departmentName\\\":\\\"街镇融合通信平台\\\",\\\"gps\\\":[{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"6085e3bcd4ef4e48bfaf5b8c373a3739\\\",\\\"deviceId\\\":\\\"6044463846197504\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.17556,\\\"gpsY\\\":30.650112,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082348.504Z\\\",\\\"userId\\\":\\\"6044463854716992\\\",\\\"userName\\\":\\\"十陵街道001\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":221,\\\"channelId\\\":\\\"6005466328860928\\\",\\\"dataSource\\\":3,\\\"deviceCode\\\":\\\"6005466328336640\\\",\\\"deviceId\\\":\\\"6005466328336640\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":{},\\\"gpsX\\\":104.17556,\\\"gpsY\\\":30.650112,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":2,\\\"time\\\":\\\"20220428T082347.456Z\\\",\\\"userId\\\":\\\"6044463854716992\\\",\\\"userName\\\":\\\"十陵街道001\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"5d58d0ba7d4141f09a294d01fe45e695\\\",\\\"deviceId\\\":\\\"6044501492893952\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.25304,\\\"gpsY\\\":30.590723,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082350.125Z\\\",\\\"userId\\\":\\\"6044501500889152\\\",\\\"userName\\\":\\\"西河街道001\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":359,\\\"channelId\\\":\\\"6005519398471936\\\",\\\"dataSource\\\":3,\\\"deviceCode\\\":\\\"6005519398340864\\\",\\\"deviceId\\\":\\\"6005519398340864\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":{},\\\"gpsX\\\":104.25304,\\\"gpsY\\\":30.590721,\\\"height\\\":482,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":2,\\\"time\\\":\\\"20220428T082345.236Z\\\",\\\"userId\\\":\\\"6044501500889152\\\",\\\"userName\\\":\\\"西河街道001\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":116,\\\"channelId\\\":\\\"6044260975642880\\\",\\\"dataSource\\\":3,\\\"deviceCode\\\":\\\"6044260975577344\\\",\\\"deviceId\\\":\\\"6044260975577344\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":{},\\\"gpsX\\\":104.175575,\\\"gpsY\\\":30.650118,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":2,\\\"time\\\":\\\"20220428T082346.779Z\\\",\\\"userId\\\":\\\"6044467890030656\\\",\\\"userName\\\":\\\"十陵街道002\\\",\\\"userType\\\":\\\"2\\\"}]},\\\"method\\\":\\\"dsu.gps\\\"}\",\n" +
//                "        \"{\\\"id\\\":82680,\\\"info\\\":{\\\"departmentId\\\":\\\"e5042a43b31c11eca89174d6cb85ce3c\\\",\\\"departmentName\\\":\\\"街镇融合通信平台\\\",\\\"gps\\\":[{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"2542d91cfcf447358f6d1fe22e933289\\\",\\\"deviceId\\\":\\\"6044467884787968\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.175575,\\\"gpsY\\\":30.650118,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082346.322Z\\\",\\\"userId\\\":\\\"6044467890030656\\\",\\\"userName\\\":\\\"十陵街道002\\\",\\\"userType\\\":\\\"2\\\"}]},\\\"method\\\":\\\"dsu.gps\\\"}\",\n" +
//                "        \"{\\\"id\\\":82735,\\\"info\\\":{\\\"departmentId\\\":\\\"80c2f191c2b611ec8eab9023b4a548d6\\\",\\\"departmentName\\\":\\\"龙泉驿区\\\",\\\"gps\\\":[{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"bd1a2a970e494889b58e39e68e852832\\\",\\\"deviceId\\\":\\\"6045246286923968\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.25305,\\\"gpsY\\\":30.59072,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"1783b732210c4be68906424a13dcb318\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082524.022Z\\\",\\\"userId\\\":\\\"6045246292428928\\\",\\\"userName\\\":\\\"区治理中心002\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"22fab81e03a24d9985bf370b0eb5a751\\\",\\\"deviceId\\\":\\\"6033394170267840\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.06207,\\\"gpsY\\\":30.587524,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"1783b732210c4be68906424a13dcb318\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082520.684Z\\\",\\\"userId\\\":\\\"6033394177804416\\\",\\\"userName\\\":\\\"ptdj1002\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"f2edd86369344a6982e062acf7975314\\\",\\\"deviceId\\\":\\\"6045203426248896\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.25284,\\\"gpsY\\\":30.590717,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"1783b732210c4be68906424a13dcb318\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082524.22Z\\\",\\\"userId\\\":\\\"6045203429591168\\\",\\\"userName\\\":\\\"区治理中心001\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":359,\\\"channelId\\\":\\\"6045170255399104\\\",\\\"dataSource\\\":3,\\\"deviceCode\\\":\\\"6045170255268032\\\",\\\"deviceId\\\":\\\"6045170255268032\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":{},\\\"gpsX\\\":104.25283,\\\"gpsY\\\":30.590717,\\\"height\\\":482,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"1783b732210c4be68906424a13dcb318\\\",\\\"startCount\\\":2,\\\"time\\\":\\\"20220428T082523.628Z\\\",\\\"userId\\\":\\\"6045203429591168\\\",\\\"userName\\\":\\\"区治理中心001\\\",\\\"userType\\\":\\\"2\\\"}]},\\\"method\\\":\\\"dsu.gps\\\"}\",\n" +
//                "        \"{\\\"id\\\":82736,\\\"info\\\":{\\\"departmentId\\\":\\\"e5042a43b31c11eca89174d6cb85ce3c\\\",\\\"departmentName\\\":\\\"街镇融合通信平台\\\",\\\"gps\\\":[{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"6085e3bcd4ef4e48bfaf5b8c373a3739\\\",\\\"deviceId\\\":\\\"6044463846197504\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.17557,\\\"gpsY\\\":30.650113,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082523.485Z\\\",\\\"userId\\\":\\\"6044463854716992\\\",\\\"userName\\\":\\\"十陵街道001\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":221,\\\"channelId\\\":\\\"6005466328860928\\\",\\\"dataSource\\\":3,\\\"deviceCode\\\":\\\"6005466328336640\\\",\\\"deviceId\\\":\\\"6005466328336640\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":{},\\\"gpsX\\\":104.17556,\\\"gpsY\\\":30.650112,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":2,\\\"time\\\":\\\"20220428T082522.452Z\\\",\\\"userId\\\":\\\"6044463854716992\\\",\\\"userName\\\":\\\"十陵街道001\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"5d58d0ba7d4141f09a294d01fe45e695\\\",\\\"deviceId\\\":\\\"6044501492893952\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.25304,\\\"gpsY\\\":30.590723,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082525.114Z\\\",\\\"userId\\\":\\\"6044501500889152\\\",\\\"userName\\\":\\\"西河街道001\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":359,\\\"channelId\\\":\\\"6005519398471936\\\",\\\"dataSource\\\":3,\\\"deviceCode\\\":\\\"6005519398340864\\\",\\\"deviceId\\\":\\\"6005519398340864\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":{},\\\"gpsX\\\":104.25304,\\\"gpsY\\\":30.590721,\\\"height\\\":482,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":2,\\\"time\\\":\\\"20220428T082525.228Z\\\",\\\"userId\\\":\\\"6044501500889152\\\",\\\"userName\\\":\\\"西河街道001\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"2542d91cfcf447358f6d1fe22e933289\\\",\\\"deviceId\\\":\\\"6044467884787968\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.175575,\\\"gpsY\\\":30.650118,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082521.321Z\\\",\\\"userId\\\":\\\"6044467890030656\\\",\\\"userName\\\":\\\"十陵街道002\\\",\\\"userType\\\":\\\"2\\\"}]},\\\"method\\\":\\\"dsu.gps\\\"}\",\n" +
//                "        \"{\\\"id\\\":82737,\\\"info\\\":{\\\"departmentId\\\":\\\"e5042a43b31c11eca89174d6cb85ce3c\\\",\\\"departmentName\\\":\\\"街镇融合通信平台\\\",\\\"gps\\\":[{\\\"angle\\\":116,\\\"channelId\\\":\\\"6044260975642880\\\",\\\"dataSource\\\":3,\\\"deviceCode\\\":\\\"6044260975577344\\\",\\\"deviceId\\\":\\\"6044260975577344\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":{},\\\"gpsX\\\":104.175576,\\\"gpsY\\\":30.650119,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"e494e0b640d14e12b87fcab2860c44b4\\\",\\\"startCount\\\":2,\\\"time\\\":\\\"20220428T082521.781Z\\\",\\\"userId\\\":\\\"6044467890030656\\\",\\\"userName\\\":\\\"十陵街道002\\\",\\\"userType\\\":\\\"2\\\"}]},\\\"method\\\":\\\"dsu.gps\\\"}\",\n" +
//                "        \"{\\\"id\\\":82738,\\\"info\\\":{\\\"departmentId\\\":\\\"80c2f191c2b611ec8eab9023b4a548d6\\\",\\\"departmentName\\\":\\\"龙泉驿区\\\",\\\"gps\\\":[{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"22fab81e03a24d9985bf370b0eb5a751\\\",\\\"deviceId\\\":\\\"6033394170267840\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.06207,\\\"gpsY\\\":30.587524,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"1783b732210c4be68906424a13dcb318\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082525.684Z\\\",\\\"userId\\\":\\\"6033394177804416\\\",\\\"userName\\\":\\\"ptdj1002\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"f2edd86369344a6982e062acf7975314\\\",\\\"deviceId\\\":\\\"6045203426248896\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.25284,\\\"gpsY\\\":30.590717,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"1783b732210c4be68906424a13dcb318\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082529.225Z\\\",\\\"userId\\\":\\\"6045203429591168\\\",\\\"userName\\\":\\\"区治理中心001\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":359,\\\"channelId\\\":\\\"6045170255399104\\\",\\\"dataSource\\\":3,\\\"deviceCode\\\":\\\"6045170255268032\\\",\\\"deviceId\\\":\\\"6045170255268032\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":{},\\\"gpsX\\\":104.25283,\\\"gpsY\\\":30.590717,\\\"height\\\":482,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"1783b732210c4be68906424a13dcb318\\\",\\\"startCount\\\":2,\\\"time\\\":\\\"20220428T082528.62Z\\\",\\\"userId\\\":\\\"6045203429591168\\\",\\\"userName\\\":\\\"区治理中心001\\\",\\\"userType\\\":\\\"2\\\"},{\\\"angle\\\":0,\\\"channelId\\\":\\\"\\\",\\\"dataSource\\\":1,\\\"deviceCode\\\":\\\"bd1a2a970e494889b58e39e68e852832\\\",\\\"deviceId\\\":\\\"6045246286923968\\\",\\\"deviceName\\\":\\\"\\\",\\\"extendInfo\\\":null,\\\"gpsX\\\":104.25305,\\\"gpsY\\\":30.59072,\\\"height\\\":0,\\\"speed\\\":0,\\\"srcDomainCode\\\":\\\"1783b732210c4be68906424a13dcb318\\\",\\\"startCount\\\":0,\\\"time\\\":\\\"20220428T082529.021Z\\\",\\\"userId\\\":\\\"6045246292428928\\\",\\\"userName\\\":\\\"区治理中心002\\\",\\\"userType\\\":\\\"2\\\"}]},\\\"method\\\":\\\"dsu.gps\\\"}\"\n" +
//                "    ]\n" +
//                "}");
        JSONArray results = body.getJSONArray("results");

        for (Object obj : results) {
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(obj));
            if (!"dsu.gps".equals(jsonObject.getString("method"))) {
                continue;
            }
            // 获取json中的坐标数据
            JSONArray gpsArray = jsonObject.getJSONObject("info").getJSONArray("gps");
            JSONArray tmpArray = new JSONArray();
            for (Object gpsObj : gpsArray) {
                JSONObject tmpJsonobject = new JSONObject();
                JSONObject gpsJsonObject = (JSONObject) gpsObj;
                tmpJsonobject.put("deviceId", gpsJsonObject.getString("deviceId"));
                tmpJsonobject.put("userName", gpsJsonObject.getString("userName"));
                tmpJsonobject.put("userId", gpsJsonObject.getString("userId"));
                tmpJsonobject.put("srcDomainCode", gpsJsonObject.getString("srcDomainCode"));
                tmpJsonobject.put("deviceKind", "31");
                tmpJsonobject.put("precinctId", "510112");
                tmpJsonobject.put("operator", "龙泉驿区");
                tmpJsonobject.put("gpsX", gpsJsonObject.getString("gpsX"));
                tmpJsonobject.put("gpsY", gpsJsonObject.getString("gpsY"));
                tmpJsonobject.put("time", DateUtil.getChangedDateStr(gpsJsonObject.getString("time"), DateUtil.formatYMDHmsTZ));
                tmpArray.add(tmpJsonobject);
            }

            // 保存到设备表
            try {
                if (!tmpArray.isEmpty()) {
                    int count = deviceMapper.insertLqyDanbinGps(tmpArray);
                    log.info("更新龙泉驿区单兵设备坐标数据条数：{}", count);
                }
            } catch (Exception ex) {
                log.error("龙泉驿单兵存储gps数据失败，跳过当前数据：{}", jsonObject.getInteger("id"));
                continue;
            }
        }

    }

    // 大华 龙泉驿区获取单兵订阅gps的消息订阅地址
    // 客户端或者其他需要感知消息变更的子系统服务,可以通过此接口申请一个消息订阅地址。 根据
    //token 值分配一个地址返回，之后客户端或子系统向该地址发起轮询请求获取消息
    //如果按 http 请求,那么返回的地址就是 http 的端口,如果按 https 请求,返回的是 https 端口
    public JSONObject getSubscribeGpsUrl() {

        HttpHeaders headers = new HttpHeaders();
        headers.add(DAHUA_DABIN_TOKEN_KEY, token);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<JSONObject> forEntity = restTemplate.exchange(
                lqyDanbinUrlPrefix + "videoService/eventCenter/messages/subscribeAddress", HttpMethod.GET,
                new HttpEntity<String>(headers), JSONObject.class);

        JSONObject body = forEntity.getBody();
        return body;
    }

    @Override
    public String getToken() {
        return handleToken();
    }

    static HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    static RestTemplate timeOutRestTemplate = new RestTemplate(httpRequestFactory);

    static {
        httpRequestFactory.setConnectionRequestTimeout(4000);
        httpRequestFactory.setConnectTimeout(4000);
        httpRequestFactory.setReadTimeout(4000);
    }

    @Override
    public void keepAlive() {
        try {
            if ("true".equals(onoff)) {
                if (loginCount == 5) {
                    log.info("重登5次失败，取消保活。");
                    return;
                }

                SSLUtils.ignoreSsl();
                JSONObject msg = new JSONObject();
                msg.put("token", token);
                HttpHeaders headers = new HttpHeaders();
                headers.add(DAHUA_DABIN_TOKEN_KEY, token);
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
                timeOutRestTemplate.exchange(keepAliveUrl, HttpMethod.PUT, request, JSONObject.class);
            }
        } catch (Exception e) {
            log.error("大华token保活异常：" + e.getMessage());
            log.info("保活异常，重新登录,重登次数:" + loginCount);
            if (loginCount < 5) {
                loginCount++;
                this.handleToken();
            }
        }

    }

    @Override
    public void updateGps() {
        try {
            if ("true".equals(onoff)) {
                RestTemplate restTemplate = new RestTemplate();
                String deviceIds = deviceMapper.queryLongQuanDanBinIds();
                String url = LongQuanGpsUrl + deviceIds;
                HttpHeaders headers = new HttpHeaders();
                headers.add("X-Subject-Token", token);
                ResponseEntity<JSONObject> forEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), JSONObject.class);
                JSONObject jsonObject = forEntity.getBody();
                if (!CollectionUtils.isEmpty(jsonObject.getJSONArray("curGpsInfo"))) {
                    deviceMapper.updateLqyDanbinGps(jsonObject.getJSONArray("curGpsInfo"));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public String getSignature(String realm, String randomKey) {
        String signature = DigestUtil.md5Hex(password);
        signature = DigestUtil.md5Hex(userName + signature);
        signature = DigestUtil.md5Hex(signature);
        signature = DigestUtil.md5Hex(userName + ":" + realm + ":" + signature);
        signature = DigestUtil.md5Hex(signature + ":" + randomKey);
        return signature;
    }

    public static void main(String[] args) {
        DahuaServiceImpl dahuaService = new DahuaServiceImpl();
        System.out.println(dahuaService.getSignature("TheNextService", "70a4b6d32ca879a811183f9db5413dd4_73cacbe6-cc30-45c2-85a6-398b026d5fab"));
    }

    @Override
    public Result syncDevice() {
        Result result = new Result();
        try {
            int count = 0;
            token = handleToken();
            //跳过ssl验证
            SSLUtils.ignoreSsl();
            //递归调取 获得所有设备 根组织id为空
            JSONArray jsonArray = new JSONArray();
            HttpHeaders headers = new HttpHeaders();
            headers.set(DAHUA_DABIN_TOKEN_KEY, token);
            getDeviceByRecursion(count, "", headers, 0);

            log.info("大华设备同步----共:{}条设备", count);
            result.setData(count);
        } catch (Exception e) {
            log.error(e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(e.toString());
        }
        return result;
    }

    private void getDeviceByRecursion(int count, String orgId, HttpHeaders headers, int callCounter) {
        int page = 1;
        JSONArray orgs = new JSONArray();

        while (true) {
            String getOrgUrl = deviceUrl + "/videoService/devicesManager/deviceTree?id=" +
                    orgId + "&nodeType=1&typeCode=01&page=" + page + "&pageSize=100";
            ResponseEntity<JSONObject> forEntity;
            try {
//                //每100次重新保活
//                if (callCounter >= 100) {
//                    keepAlive();
//                    headers.set(DAHUA_DABIN_TOKEN_KEY, token);
//                    callCounter = 0;
//                }
                forEntity = restTemplate.exchange(getOrgUrl, HttpMethod.GET, new HttpEntity<>(headers), JSONObject.class);
            } catch (Exception e) {
                log.info("get deviceError,url:{},exception:{}", getOrgUrl, e.toString());
                break;
            }
            JSONObject data = forEntity.getBody();
            if (data != null) {
                JSONArray orgArray = data.getJSONArray("results");
                if (!CollectionUtils.isEmpty(orgArray)) {
                    for (int i = 0; i < orgArray.size(); i++) {
                        JSONObject org = orgArray.getJSONObject(i);
                        if (org != null && org.getInteger("nodeType") == 1) {
                            JSONArray deviceArray = getDeviceByOrg(org.getString("id"), headers);
                            if (!CollectionUtils.isEmpty(deviceArray)) {
                                count += deviceMapper.replaceDeviceBySync(deviceArray);
                            }
                            getDeviceByRecursion(count, org.getString("id"), headers, callCounter++);
                            orgs.add(org);
                        }
                    }
                }
                if (data.getInteger("nextPage") == -1) {
                    break;
                }
                page++;
            } else {
                throw new BusinessException("getDeviceByRecursion response null,url:" + getOrgUrl);
            }

        }
        //插入部门
        if (!CollectionUtils.isEmpty(orgs)) {
            deviceMapper.replaceOrgBySync(orgs);
        }
    }

    private JSONArray getDeviceByOrg(String orgId, HttpHeaders headers) {
        JSONArray jsonArray = new JSONArray();
        int page = 1;
        while (true) {
            String getDeviceUrl = deviceUrl + "/videoService/devicesManager/deviceTree?id=" +
                    orgId + "&nodeType=1&typeCode=01;0;ALL;ALL&page=" + page + "&pageSize=100";
            ResponseEntity<JSONObject> forEntity;
            try {
                forEntity = restTemplate.exchange(getDeviceUrl, HttpMethod.GET, new HttpEntity<>(headers), JSONObject.class);
            } catch (Exception e) {
                log.info("get deviceError,url:{},exception:{}", getDeviceUrl, e.toString());
                break;
            }
            JSONObject data = forEntity.getBody();
            if (data != null) {
                JSONArray deviceArray = data.getJSONArray("results");
                if (!CollectionUtils.isEmpty(deviceArray)) {
                    for (int i = 0; i < deviceArray.size(); i++) {
                        JSONObject device = deviceArray.getJSONObject(i);
                        if (device.getInteger("nodeType") == 3) {
                            device.put("orgId", orgId);
                            if(device.getBoolean("isOnline")){
                                device.put("onofflineState",0);
                            }else {
                                device.put("onofflineState",1);
                            }
                            jsonArray.add(device);
                        }
                    }
                } else {
                    break;
                }
                if (data.getInteger("nextPage") == -1) {
                    break;
                }
                page++;
            } else {
                throw new BusinessException("getDeviceByOrg response null,url:" + getDeviceUrl);
            }

        }
        return jsonArray;
    }

}
