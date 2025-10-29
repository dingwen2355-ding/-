package com.znv.manage.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.bean.SyncDevice;
import com.znv.manage.common.utils.SSLUtils;
import com.znv.manage.dao.DaHuaVideoDao;
import com.znv.manage.service.DaHuaVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class DaHuaVideoServiceImpl implements DaHuaVideoService {

    private static String getVideoUrl = "https://2.20.28.22:7902/videoService/realmonitor/uri?channelId=";
    //private static String getVideoUrl = "http://2.20.28.22:8314/videoService/realmonitor/uri?channelId=";
    private static String cityEyesUrl = "xf-public-api-device-search/v1/stream/live/open";
    private static String closeCityEyesUrl = "xf-public-api-device-search/v1/stream/live/close";
    private static String getDeviceUrl = "xf-public-api-device-search/v1/device/search-device-by-page?deviceIconFlag=1&page=";
    private RestTemplate restTemplate = new RestTemplate();

    private static Logger logger = LoggerFactory.getLogger(DaHuaVideoServiceImpl.class);

    @Value("${cityEyes_ip}")
    private String cityEyesIp;

    @Value("${cityEyes_token}")
    private String cityEyesToken;

    @Value("${cityEyes_personid}")
    private String cityEyesPersonId;

    @Autowired
    DaHuaVideoDao daHuaVideoDao;

    @Autowired
    DahuaServiceImpl dahuaService;

    @Override
    public Result getVideoUrl(String videoType, String deviceId) {
        try {
            SSLUtils.ignoreSsl();
        } catch (Exception e) {
            logger.error("跳过ssl验证失败:{}", e.toString());
        }
//        dahuaService.keepAlive();
        Result result = new Result();
        StringBuilder stringBuilderUrl = new StringBuilder();
        stringBuilderUrl.append(deviceId);
        stringBuilderUrl.append("&");
        stringBuilderUrl.append("scheme=");
        stringBuilderUrl.append(videoType);
        String url = getVideoUrl + stringBuilderUrl.toString();
        String token = dahuaService.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Subject-Token", token);
        result.setData(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(headers), JSONObject.class).getBody());
        return result;
    }

    @Override
    public Result getCityEyesVideoUrl(String deviceId, String protocol) {
        Result result = new Result();
        RestTemplate restTemplate = new RestTemplate();
        JSONObject msg = new JSONObject();
        msg.put("deviceId", deviceId);
        msg.put("protocol", protocol);
        msg.put("resolution", "");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Connection", "close");
        headers.add("Authorization", cityEyesToken);
        headers.add("PERSON-ID", DigestUtil.md5Hex(cityEyesPersonId));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
        //restTemplate.postForObject(cityEyesIp + cityEyesUrl, request, JSONObject.class);
        //logger.info("取出原数据--"+ restTemplate.postForObject(cityEyesIp + cityEyesUrl, request, JSONObject.class));
        result.setData(restTemplate.postForObject(cityEyesIp + cityEyesUrl, request, JSONObject.class));
        return result;
    }

    @Override
    public Result closeCityEyesVideoUrl(String tgtSessionId) {
        Result result = new Result();
        RestTemplate restTemplate = new RestTemplate();
        JSONObject msg = new JSONObject();
        msg.put("tgtSessionId", tgtSessionId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Connection", "close");
        headers.add("Authorization", cityEyesToken);
        headers.add("PERSON-ID", DigestUtil.md5Hex(cityEyesPersonId));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(msg.toJSONString(), headers);
        //restTemplate.postForObject(cityEyesIp + closeCityEyesUrl, request, JSONObject.class);
        //logger.info("取出原数据--"+ restTemplate.postForObject(cityEyesIp + closeCityEyesUrl, request, JSONObject.class));
        result.setData(restTemplate.postForObject(cityEyesIp + closeCityEyesUrl, request, JSONObject.class));
        return result;
    }

    @Override
    public int SyncCityEyesDevice() {
        int pageNumber = 0;
        int pageSize = 1000;
        int i = 0;

        //清空中间表
        daHuaVideoDao.deleteTempDevice();

        boolean flag = true;
        try {
            do {
                pageNumber += 1;
                RestTemplate restTemplate = new RestTemplate();
                StringBuilder stringBuilderUrl = new StringBuilder();
                stringBuilderUrl.append(pageNumber);
                stringBuilderUrl.append("&");
                stringBuilderUrl.append("size=");
                stringBuilderUrl.append(pageSize);
                String url = cityEyesIp + getDeviceUrl + stringBuilderUrl.toString();
                HttpHeaders headers = new HttpHeaders();
                headers.add("Accept", "application/json");
                headers.add("Connection", "close");
                headers.add("Authorization", cityEyesToken);
                headers.add("PERSON-ID", DigestUtil.md5Hex(cityEyesPersonId));
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
                logger.info("开始对接城市之眼设备数据");
                //logger.info("url:"+url);
                //logger.info("Authorization:"+cityEyesToken);
                //logger.info("PERSON-ID:"+DigestUtil.md5Hex(cityEyesPersonId));
                ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
                //logger.info("取出元数据1--"+ entity);
                JSONObject jsonObject = JSONObject.parseObject(entity.getBody());
                //logger.info("取出元数据--"+ jsonObject);
                JSONObject object = jsonObject.getJSONObject("data");
                //logger.info("取出数据--"+ object);
                JSONArray array = object.getJSONArray("content");
                //logger.info("取出数据列--"+ array);
                List<SyncDevice> list = JSON.parseArray(String.valueOf(array), SyncDevice.class);
                //logger.info("转化数据列--"+ list);
                if (!CollectionUtils.isEmpty(list) || (list.size() != 0)) {
                    //存入中间表
                    i += insertTempDevice(list);
                } else {
                    logger.info("insertSyncDevice list.size为0");
                }
                if (list.size() < pageSize) {
                    flag = false;
                }
               /* try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            } while (flag);
            daHuaVideoDao.updatePrecinctId1();
            daHuaVideoDao.updatePrecinctId2();
            daHuaVideoDao.updatePrecinctId3();
            //同步中间表和正式表
            //删除多余设备
            daHuaVideoDao.deleteUselessDevice();
            //添加新增设备
            daHuaVideoDao.insertAddDevice();

        } catch (Exception e) {
            logger.error("getData res error ： " + e.getMessage());
        }
        logger.info("总设备数：" + i);
        logger.info("pageNumber为" + pageNumber);
        return i;
    }

    @Override
    public Result getDeviceList(String name) {
        Result result = new Result();
        List<Map<String, Object>> list = daHuaVideoDao.getDeviceList(name);
        result.setData(list);
        return result;
    }

    @Override
    public Result getDeviceListNew(String name) {
        Result result = new Result();
        List<Map<String, Object>> list = daHuaVideoDao.getDeviceListNew(name);
        result.setData(list);
        return result;
    }

    public int insertSyncDevice(List<SyncDevice> list) {
        int i = 0;
        try {
            i += this.daHuaVideoDao.insertSyncDevice(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public int insertTempDevice(List<SyncDevice> list) {
        int i = 0;
        try {
            i += this.daHuaVideoDao.insertTempDevice(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }


    public static void main(String[] args) {
        System.out.println(DigestUtil.md5Hex("421083199802252412"));
    }
}
