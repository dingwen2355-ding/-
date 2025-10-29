package com.znv.manage.controller;


import com.alibaba.fastjson.JSONObject;
import com.znv.manage.common.bean.DeviceInfo;
import com.znv.manage.common.bean.Result;
import com.znv.manage.dao.FusionVideoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class GpsController {

    @Autowired
    private FusionVideoDao fusionVideoDao;


    @GetMapping("/queryGpsByAddress")
    public Result queryGpsByAddress(@RequestParam(value = "address")String address,
                                    @RequestParam(value = "page")String page,
                                    @RequestParam(value = "limit")String limit){
        Result result = new Result();
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("token","7YhXk_h6JhqT9hfyvyLhXOhj_vt3zKjAukOcwkyKtrq9tuD1IUhIHDTz_tAqS-hu4EUDsUyRgwfZpKb8y8EvpSWUahgZagkn55_80v0UpNJ3v2k7EVIA-a2ZpkH3sQsZUTEb5WOTocgbf98UIDvjz0iC_4z6QMtA84BeJZS6Cls5CP4GhFXHB7xPJJsCaPFC");
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject msg = new JSONObject();
            msg.put("address",address);
            msg.put("page",page);
            msg.put("limit",limit);
            HttpEntity request = new HttpEntity(msg, headers);
            JSONObject jsonObject = restTemplate.postForObject("http://10.1.50.120/CDServer/rest/services/GeocodeKeyWord/Transfer",request, JSONObject.class);
            result.setData(jsonObject);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }


    @GetMapping("/queryDeviceListByGps")
    public Result queryDeviceListByGps(@RequestParam(value = "gpsx")String gpsx,
                                    @RequestParam(value = "gpsy")String gpsy){
        Result result = new Result();
        try {
            List<DeviceInfo> deviceInfoList = fusionVideoDao.getList2(gpsx,gpsy);
            result.setData(deviceInfoList);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }
}
