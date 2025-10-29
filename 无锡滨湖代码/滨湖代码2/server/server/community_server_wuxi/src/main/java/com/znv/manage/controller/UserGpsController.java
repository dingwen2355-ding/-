package com.znv.manage.controller;


import com.znv.manage.common.bean.Result;
import com.znv.manage.service.impl.UserGpsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/xycy")
@Slf4j
public class UserGpsController {

    @Autowired
    private UserGpsServiceImpl userGpsService;

    @GetMapping("/queryUserList")
    public Result queryUserList(@RequestParam(value = "userId",required = false)String userId,
                                @RequestParam(value = "eventId",required = false) String eventId){
        Result result = new Result();
        try {
            List<Map<String,Object>> list = new ArrayList<>();
            if(!StringUtils.isEmpty(eventId)){
                list =  userGpsService.queryUserGpsList(userId,eventId);
            }
            result.setData(list);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

    @GetMapping("/queryUserInfoById")
    public Result queryUserInfoById(@RequestParam(value = "userId")String userId){
        Result result = new Result();
        try {
            Map<String,Object> map = userGpsService.queryUserGpsListByUserId(userId);
            result.setData(map);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            log.error(e.getMessage());
        }
        return result;
    }

}
