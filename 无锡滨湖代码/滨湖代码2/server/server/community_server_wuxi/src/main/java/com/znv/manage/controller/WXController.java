package com.znv.manage.controller;


import com.znv.manage.common.bean.Result;
import com.znv.manage.service.impl.WXServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WXController  {

    @Autowired
    private WXServiceImpl wxService;

    @GetMapping("getWxURl")
    public Result getWxURl(@RequestParam(value = "userId")String userId,
                           @RequestParam(value = "precinctId",defaultValue = "510100")String precinctId){
        return wxService.getWxURl(userId,precinctId);
    }
}
