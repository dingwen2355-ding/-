package com.znv.manage.controller;


import com.znv.manage.common.bean.Result;
import com.znv.manage.common.utils.AccessUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestAccessController {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(TestAccessController.class);


    @PostMapping("/testUrlResult")
    public Result testUrlResult(@RequestParam(value = "url")String url,
                                @RequestBody Map<String,String> map){
        Result result = new Result();
        try {
            Object object = AccessUtil.get(url,map);
            result.setData(object);
        }catch (Exception e){
            result.setCode(1);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }
}
