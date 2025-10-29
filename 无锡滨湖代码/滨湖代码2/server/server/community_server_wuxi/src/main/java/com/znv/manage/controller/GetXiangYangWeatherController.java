package com.znv.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.Result;
import com.znv.manage.common.exception.ResultCodeEnum;
import com.znv.manage.dao.EventCommandDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@Api(tags = "获取襄阳当前网格天气")
public class GetXiangYangWeatherController {

    @Value("${weather.token}")
    private String ACCESS_TOKEN;
    @Value("${weather.secret}")
    private String ACCESS_SECRET;

    @Value("${weather.url:http://172.61.132.86:9091}")
    private String URL;

    @Autowired
    private EventCommandDao eventCommandDao;

    @GetMapping("/getXiangYangWeather")
    @ApiOperation("查询襄阳当前网格天气")
    public Result getXiangYangWeather() {
        Result result = new Result();
        eventCommandDao.addVisitLog();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken", ACCESS_TOKEN);
        headers.add("AppSecret", ACCESS_SECRET);
        try {
            String url = URL + "/cdlabapi/meteorology/observation/surfHourRegionDataList?region=成都市";
            log.error("获取襄阳天气 URL:"+url);
            ResponseEntity<JSONObject> forEntity = restTemplate.exchange(url, HttpMethod.GET,
                    new HttpEntity<String>(headers), JSONObject.class);
            log.error("获取襄阳天气 forEntity:"+forEntity);
            JSONObject body = forEntity.getBody();
            result.setData(body);
            result.setCode(ResultCodeEnum.NORMAL.getCode());
            result.setMessage(ResultCodeEnum.NORMAL.getName());
        } catch (Exception e) {
            log.error("获取襄阳天气异常："+e.toString());
            result.setCode(ResultCodeEnum.SYSTEMERROR.getCode());
            result.setMessage(ResultCodeEnum.SYSTEMERROR.getName());
        }
        return result;
    }
}
