package com.znv.manage.controller;

import com.znv.manage.common.bean.Result;
import com.znv.manage.service.JdjlService;
import com.znv.manage.service.MzJzGhService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: LettersAndVisitsController
 * @Description:
 * @Author: 叶志鑫
 * @Date: 2021/07/14
 **/
@Slf4j
@RestController
@Api(tags = "信访接待")
@RequestMapping("/lettersAndVisits")
public class LettersAndVisitsController {

    @Autowired
    JdjlService jdjlService;

    @ApiOperation(value = "/信访接待记录数据")
    @GetMapping("/getLettersAndVisits")
    public Result getLettersAndVisits(@RequestParam(value = "beginTime", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                                             @RequestParam(value = "endTime", required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                             @RequestParam(value = "limit", required = false) Integer limit) {
        Result result = new Result();
        int count = jdjlService.getLettersAndVisitsCount(beginTime,endTime);
        Map<String,Object> map = new HashMap<>();
        map.put("list","");
        map.put("count",count);
        result.setData(map);
        return result;
    }


}
